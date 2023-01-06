package de.sk.dynamicprogramming.graphsrevisited.floydwarshall;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static de.sk.graphs.GraphConstants.POSITIVE_INFINITY;

/**
 * Implementation of the Floyd-Warshall algorithm, solving the All-Pairs Shortest Path problem for graphs with
 * negative edge lengths. Time complexity: O(n³).
 */
public class FloydWarshallAlg {

    static final String GRAPH_CONTAINS_NEGATIVE_CYCLE_EXCEPTION_MSG = "The given graph contains a negative cycle. Therefore, " +
            "the Floyd-Warshall algorithm cannot solve the All-Pairs Shortest Path problem.";

    // map for being able to connect a vertex to its index in i/j matrix
    private final Map<DiVertex, Integer> indexForVertexLookup = new HashMap<>();
    private DiVertex[][] predecessors;

    /**
     * TODO
     *
     * @param adjacencyList graph with possibly negative edge lengths for which the All-Pairs Shortest Path problem is to be solved
     * @return
     */
    public @NotNull Map<DiVertex, Map<DiVertex, Pair<List<DiVertex>, Integer>>> determineShortestPathForAllPairs(@NotNull DiAdjacencyList adjacencyList) {
        this.clearDatastructures();
        List<DiVertex> vertices = adjacencyList.vertices();
        this.predecessors = new DiVertex[vertices.size()][vertices.size()];
        int[][] lengthsOfShortestPaths = this.calculateShortestPathForAllPairs(vertices);
        return this.reconstructShortestPathForAllPairs(vertices, lengthsOfShortestPaths);
    }

    private int[] @NotNull [] calculateShortestPathForAllPairs(@NotNull List<DiVertex> vertices) {
        int[][][] lengthsOfShortestPaths = new int[vertices.size() + 1][vertices.size()][vertices.size()];
        // base case ('k' = 0 -> no vertex is allowed as an internal vertex of a shortest path)
        for (int i = 0; i < vertices.size(); i++) { // 'i' indexes the origins
            DiVertex v = vertices.get(i);
            this.indexForVertexLookup.put(v, i);
            for (int j = 0; j < vertices.size(); j++) { // 'j' indexes the destinations
                DiVertex w = vertices.get(j);
                if (v == w) {
                    lengthsOfShortestPaths[0][i][j] = 0;
                } else {
                    int lengthOfEdgeVW = this.getLengthOfEdgeBetweenVertices(v, w);
                    lengthsOfShortestPaths[0][i][j] = lengthOfEdgeVW;
                    if (lengthOfEdgeVW != POSITIVE_INFINITY) {
                        this.predecessors[i][j] = v;  // store predecessor for reconstruction of the shortest paths
                    }
                }
            }
        }
        // systematically solve all subproblems
        for (int k = 1; k <= vertices.size(); k++) { // 'k' is the prefix index
            for (int i = 0; i < vertices.size(); i++) { // 'i' is the index of origin vertex v
                for (int j = 0; j < vertices.size(); j++) { // 'j' is the index of destination vertex w
                    // recurrence of optimal substructure ...
                    // ... case1
                    int candidate1 = lengthsOfShortestPaths[k - 1][i][j];
                    // ... case2
                    // in the 2nd/'i' and 3rd/'j' dimension of the array, 'k-1' must be used instead of 'k' (because |k| is 1 larger than |origins| and |destinations|)
                    int lengthOfPathVtoK = lengthsOfShortestPaths[k - 1][i][k - 1];
                    int lengthOfPathKtoW = lengthsOfShortestPaths[k - 1][k - 1][j];
                    int candidate2 = (lengthOfPathVtoK == POSITIVE_INFINITY || lengthOfPathKtoW == POSITIVE_INFINITY)   // prevent possible integer overflow
                            ? POSITIVE_INFINITY     // if one of the paths has length INFINITY, the sum of both also has length INFINITY
                            : lengthOfPathVtoK + lengthOfPathKtoW; // else, use the sum of both paths
                    // select the minimum candidate as new length of the shortest path for current subproblem
                    lengthsOfShortestPaths[k][i][j] = Math.min(candidate1, candidate2);
                    if (candidate2 < candidate1) {
                        this.predecessors[i][j] = vertices.get(k - 1); // store predecessor for reconstruction of the shortest paths
                    }
                }
            }
        }
        // check for negative cycles
        for (int i = 0; i < vertices.size(); i++) {
            if (lengthsOfShortestPaths[vertices.size()][i][i] < 0) {
                throw new IllegalArgumentException(GRAPH_CONTAINS_NEGATIVE_CYCLE_EXCEPTION_MSG);
            }
        }
        return lengthsOfShortestPaths[vertices.size()];
    }

    private int getLengthOfEdgeBetweenVertices(@NotNull DiVertex origin, @NotNull DiVertex destination) {
        for (DiEdge outgoingEdge : origin.getOutgoingEdges()) {
            if (outgoingEdge.head() == destination) {
                return outgoingEdge.getWeight();
            }
        }
        // no edge exists between tail and head -> return infinity
        return POSITIVE_INFINITY;
    }

    private @NotNull Map<DiVertex, Map<DiVertex, Pair<List<DiVertex>, Integer>>> reconstructShortestPathForAllPairs(@NotNull List<DiVertex> vertices,
                                                                                                                    int[] @NotNull [] lengthsOfShortestPaths) {
        Map<DiVertex, Map<DiVertex, Pair<List<DiVertex>, Integer>>> shortestPathForAllPairs = new HashMap<>();
        for (int i = lengthsOfShortestPaths.length - 1; i >= 0; i--) {
            DiVertex origin = vertices.get(i);
            Map<DiVertex, Pair<List<DiVertex>, Integer>> shortestPathsToOrigin = new HashMap<>();
            for (int j = lengthsOfShortestPaths.length - 1; j >= 0; j--) {
                int lengthOfShortestPath = lengthsOfShortestPaths[i][j];
                if (lengthOfShortestPath != POSITIVE_INFINITY) {
                    DiVertex destination = vertices.get(j);
                    List<DiVertex> shortestPathFromOriginToDestination = this.reconstructShortestPathToDestination(destination, i);
                    Pair<List<DiVertex>, Integer> shortestPathAndItsLength = new ImmutablePair<>(shortestPathFromOriginToDestination, lengthOfShortestPath);
                    shortestPathsToOrigin.put(destination, shortestPathAndItsLength);
                }
            }
            shortestPathForAllPairs.put(origin, shortestPathsToOrigin);
        }
        return shortestPathForAllPairs;
    }

    private @NotNull List<DiVertex> reconstructShortestPathToDestination(@NotNull DiVertex destination, int i) {
        List<DiVertex> shortestPath = new ArrayList<>(Collections.singletonList(destination));
        int idxOfDestination = this.indexForVertexLookup.get(destination);
        DiVertex predecessor = this.predecessors[i][idxOfDestination];
        while (predecessor != null) { // if predecessor == null, the latest predecessor was the origin
            shortestPath.add(predecessor);
            int idxOfPredecessor = this.indexForVertexLookup.get(predecessor);
            // get next predecessor
            predecessor = this.predecessors[i][idxOfPredecessor];
        }
        Collections.reverse(shortestPath); // reverse list such that path starts at origin
        return shortestPath;
    }

    private void clearDatastructures() {
        this.indexForVertexLookup.clear();
    }
}
