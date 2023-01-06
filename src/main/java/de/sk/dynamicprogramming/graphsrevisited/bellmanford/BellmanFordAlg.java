package de.sk.dynamicprogramming.graphsrevisited.bellmanford;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static de.sk.graphs.GraphConstants.POSITIVE_INFINITY;

/**
 * Implementation of the Bellman-Ford algorithm, solving the sSingle-Source Shortest Path problem for graphs with
 * negative edge lengths. Time complexity: O(mn).
 */
public class BellmanFordAlg {

    static final String GRAPH_CONTAINS_NEGATIVE_CYCLE_EXCEPTION_MSG = "The given graph contains a negative cycle. Therefore, " +
            "the Bellman-Ford algorithm cannot solve the Single-Source Shortest Path problem.";

    // map for being able to connect a vertex to its index in pathLengths array
    private final Map<DiVertex, Integer> indexForVertexLookup = new HashMap<>();
    private DiVertex[] predecessors;

    /**
     * For the given graph with possibly negative edge lengths(represented as adjacency list), determines the shortest
     * paths from {@code startingVertex} to all vertices of the graph.
     * <p>
     * The returned {@link Map} contains the graph's vertices as keys and their respective shortest path information as values
     * (as a {@link Pair}). This {@link Pair} represents the information on its vertex's shortest path:
     * The key/left of the pair is the list of vertices building up the shortest path from {@code startingVertex} to the vertex,
     * the value/right of the pair is the length of the shortest path.
     * <p>
     * If the graph contains a negative cycle, an {@link IllegalArgumentException} is thrown.
     *
     * @param adjacencyList  graph with possibly negative edge lengths for which the Single-Source Shortest Path problem is to be solved
     * @param startingVertex starting vertex from which the shortest paths to all vertices of the graph are to be determined
     * @return map with the vertices as keys and the information on their respective shortest paths as value
     */
    public @NotNull Map<DiVertex, Pair<List<DiVertex>, Integer>> determineShortestPathsFromS(@NotNull DiAdjacencyList adjacencyList, @NotNull DiVertex startingVertex) {
        this.clearDatastructures();
        List<DiVertex> vertices = adjacencyList.vertices();
        this.predecessors = new DiVertex[vertices.size()];
        int[] lengthsOfShortestPaths = this.calculateLengthsOfShortestPaths(vertices, startingVertex);
        return this.reconstructShortestPaths(vertices, lengthsOfShortestPaths);
    }

    private int @NotNull [] calculateLengthsOfShortestPaths(@NotNull List<DiVertex> vertices, @NotNull DiVertex startingVertex) {
        int[][] pathLengths = new int[vertices.size() + 1][vertices.size()];
        // base case (edge budget 'i' = 0)
        for (int j = 0; j < vertices.size(); j++) { // 'j' indexes vertices
            DiVertex v = vertices.get(j);
            pathLengths[0][j] = v == startingVertex ? 0 : POSITIVE_INFINITY;
            this.indexForVertexLookup.put(v, j);
        }
        // systematically solve all subproblems
        for (int i = 1; i <= vertices.size(); i++) { // 'i' indexes edge budget
            boolean stable = true;
            for (int j = 0; j < vertices.size(); j++) {
                DiVertex v = vertices.get(j);
                // determine the minimal length path to 'v' from all vertices 'w' with (w,v) ∈ E
                Pair<Integer, DiVertex> minPathLengthFromAllWs = this.getMinPathLengthFromIncomingEdges(v, pathLengths[i - 1]);
                // recurrence (the length of the shortest path is either the previous one for edge budget 'i-1' or the just determined minimal length path)
                pathLengths[i][j] = Math.min(pathLengths[i - 1][j], minPathLengthFromAllWs.getLeft());
                // check if the length of the shortest path to 'v' for edge budget 'i' is different from the one for edge budget 'i-1'
                if (pathLengths[i][j] != pathLengths[i - 1][j]) {
                    stable = false; // if the length of the shortest path has changed, the solution is not stable, yet
                    // store the vertex from which the new shortest path to 'v' directly comes from as predecessor of 'v'
                    this.predecessors[this.indexForVertexLookup.get(v)] = minPathLengthFromAllWs.getRight();
                }
            }
            if (stable) {
                return pathLengths[i];
            }
        }
        throw new IllegalArgumentException(GRAPH_CONTAINS_NEGATIVE_CYCLE_EXCEPTION_MSG);
    }

    private @NotNull Pair<Integer, DiVertex> getMinPathLengthFromIncomingEdges(@NotNull DiVertex v, int @NotNull [] previousBatch) {
        List<DiEdge> incomingEdges = v.getIncomingEdges();
        int minPathLength = POSITIVE_INFINITY;
        DiVertex predecessor = null;
        for (DiEdge incomingEdge : incomingEdges) {
            DiVertex w = incomingEdge.tail();
            int shortestKnownPathToW = previousBatch[this.indexForVertexLookup.get(w)];
            // take care of possible integer overflow
            int currentPathLength = shortestKnownPathToW != POSITIVE_INFINITY ? shortestKnownPathToW + incomingEdge.getWeight() : POSITIVE_INFINITY;
            if (currentPathLength < minPathLength) {
                minPathLength = currentPathLength;
                predecessor = w;
            }
        }
        return new ImmutablePair<>(minPathLength, predecessor);
    }

    private @NotNull Map<DiVertex, Pair<List<DiVertex>, Integer>> reconstructShortestPaths(@NotNull List<DiVertex> vertices, int @NotNull [] lengthsOfShortestPaths) {
        Map<DiVertex, Pair<List<DiVertex>, Integer>> shortestPaths = new HashMap<>();
        for (DiVertex vertex : vertices) {
            Pair<List<DiVertex>, Integer> shortestPathToVertexAndItsLength = this.reconstructShortestPathToVertexAndItsLength(vertex, lengthsOfShortestPaths);
            shortestPaths.put(vertex, shortestPathToVertexAndItsLength);
        }
        return shortestPaths;
    }

    private @NotNull Pair<List<DiVertex>, Integer> reconstructShortestPathToVertexAndItsLength(@NotNull DiVertex vertex, int @NotNull [] lengthsOfShortestPath) {
        int currentIdx = this.indexForVertexLookup.get(vertex);
        int lengthOfShortestPathToVertex = lengthsOfShortestPath[currentIdx];
        List<DiVertex> shortestPathToVertex = new ArrayList<>(Collections.singletonList(vertex));
        DiVertex currentPredecessor = this.predecessors[currentIdx];
        while (currentPredecessor != null) {
            shortestPathToVertex.add(currentPredecessor);
            currentIdx = this.indexForVertexLookup.get(currentPredecessor);
            currentPredecessor = this.predecessors[currentIdx];
        }
        Collections.reverse(shortestPathToVertex);
        return new ImmutablePair<>(shortestPathToVertex, lengthOfShortestPathToVertex);
    }

    private void clearDatastructures() {
        this.indexForVertexLookup.clear();
    }
}
