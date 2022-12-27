package de.sk.greedy.mst.prim;

import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.graphs.algorithm.cc.UnConnectedComponents;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.greedy.mst.MstAlg;
import de.sk.greedy.mst.MstUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Heap-based implementation of Kruskal's algorithm for determining the Minimum Spanning Tree of a given
 * connected, undirected graph {@link MstAlg}. Time complexity: O(m log n).
 */
public class PrimsAlgHeapBased implements MstAlg {

    private final List<UnEdge> mst = new ArrayList<>();
    private final Set<UnVertex> connectedByMst = new HashSet<>();
    private final Map<UnVertex, WinnerOfVertex> winnersOfVertices = new HashMap<>();
    private final PriorityQueue<WinnerOfVertex> heap = new PriorityQueue<>();

    @Override
    public @NotNull List<UnEdge> determineMst(@NotNull UnAdjacencyList undirectedGraph) {
        // verify integrity of graph
        MstUtils.verifyIntegrityOfGraph(undirectedGraph);
        this.clearDatastructures();
        // start actual algorithm -> select a random vertex as starting vertex
        UnVertex start = undirectedGraph.vertices().get(0);
        this.connectedByMst.add(start);
        // initially fill heap with adjacent vertices to start
        this.initializeHeap(start, undirectedGraph);
        // one by one, select edges for minimum spanning tree
        while (!this.heap.isEmpty()) {
            WinnerOfVertex nextWinnerOfWinners = this.heap.poll();
            this.mst.add(nextWinnerOfWinners.getEdge());
            UnVertex newlyConnectedVertex = nextWinnerOfWinners.getVertex();
            this.connectedByMst.add(newlyConnectedVertex);
            this.updateAffectedWinnersToMaintainHeapInvariant(newlyConnectedVertex);
        }
        return Collections.unmodifiableList(this.mst);
    }

    private void initializeHeap(@NotNull UnVertex start, @NotNull UnAdjacencyList undirectedGraph) {
        // @formatter:off
        Map<UnVertex, UnEdge> adjacentVerticesOfStartAndCorrespondingEdges = start.getEdges().stream()
                                        .filter(edge -> edge.getVertices().contains(start))
                                        .collect(Collectors.toMap(edge -> UndirectedGraphUtils.getOtherVertexOfEdge(edge, start), Function.identity())); // @formatter:off
        for (UnVertex vertex : undirectedGraph.vertices()) {
            if (vertex != start) {
                UnEdge edgeBetweenStartAndVertex =  adjacentVerticesOfStartAndCorrespondingEdges.get(vertex);
                int score = edgeBetweenStartAndVertex != null ? edgeBetweenStartAndVertex.getWeight() : Integer.MAX_VALUE;
                WinnerOfVertex winnerOfVertex = new WinnerOfVertex(score, vertex, edgeBetweenStartAndVertex);
                this.winnersOfVertices.put(vertex, winnerOfVertex);
                this.heap.add(winnerOfVertex);
            }
        }
    }

    private void updateAffectedWinnersToMaintainHeapInvariant(@NotNull UnVertex newlyConnectedVertex) {
        for (UnEdge edge : newlyConnectedVertex.getEdges()) {
            UnVertex otherVertex = UndirectedGraphUtils.getOtherVertexOfEdge(edge, newlyConnectedVertex);
            if (!this.connectedByMst.contains(otherVertex)) {
                WinnerOfVertex winnerOfOtherVertex = this.winnersOfVertices.get(otherVertex);
                this.heap.remove(winnerOfOtherVertex);
                int currentScore = winnerOfOtherVertex.getScore();
                int possiblyLowerScore = edge.getWeight();
                UnEdge winnerEdge = possiblyLowerScore < currentScore ? edge : winnerOfOtherVertex.getEdge();
                winnerOfOtherVertex.setScore(currentScore);
                winnerOfOtherVertex.setEdge(winnerEdge);
                this.winnersOfVertices.put(otherVertex, winnerOfOtherVertex);
                this.heap.add(winnerOfOtherVertex);
            }
        }
    }

    private void clearDatastructures() {
        this.mst.clear();
        this.connectedByMst.clear();
        this.winnersOfVertices.clear();
        this.heap.clear();
    }

    private static class WinnerOfVertex implements Comparable<WinnerOfVertex> {

        private int score;
        private final UnVertex vertex;
        private UnEdge edge;

        public WinnerOfVertex(int score, @NotNull UnVertex vertex, @Nullable UnEdge edge) {
            this.score = score;
            this.vertex = vertex;
            this.edge = edge;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public UnVertex getVertex() {
            return vertex;
        }

        public void setEdge(@NotNull UnEdge edge) {
            this.edge = edge;
        }

        public UnEdge getEdge() {
            return edge;
        }

        @Override
        public int compareTo(@NotNull PrimsAlgHeapBased.WinnerOfVertex o) {
            return Integer.compare(this.score, o.getScore());
        }
    }
}
