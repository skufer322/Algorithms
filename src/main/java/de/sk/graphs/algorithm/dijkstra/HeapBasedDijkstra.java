package de.sk.graphs.algorithm.dijkstra;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Heap-based implementation of Dijkstra's algorithm for the single-source shortest path problem which runs in O(m log n).
 */
public class HeapBasedDijkstra extends AbstractDijkstra {

    private final Set<DiVertex> processedVertices;
    private final PriorityQueue<DiVertex> heap;

    /**
     * Constructor.
     */
    public HeapBasedDijkstra() {
        this.processedVertices = new HashSet<>();
        this.heap = new PriorityQueue<>();
    }

    @Override
    public void determineSingleSourceShortestPaths(@NotNull DiAdjacencyList adjacencyList, @NotNull DiVertex s) {
        this.processedVertices.clear();
        this.heap.clear();
        this.initializeKeyValues(adjacencyList, s);
        this.heap.addAll(adjacencyList.vertices());
        while (!this.heap.isEmpty()) {
            DiVertex removedVertex = this.heap.poll();
            removedVertex.setLen(removedVertex.getKey());
            this.processedVertices.add(removedVertex);
            this.updateVerticesAndRestoreHeapInvariant(removedVertex);
        }
    }

    private void updateVerticesAndRestoreHeapInvariant(@NotNull DiVertex removedVertex) {
        for (DiEdge outgoingEdge : removedVertex.getOutgoingEdges()) {
            DiVertex head = outgoingEdge.head();
            if (!this.processedVertices.contains(head)) {
                this.heap.remove(head);
                head.setKey(Math.min(head.getKey(), outgoingEdge.tail().getLen() + outgoingEdge.getWeight()));
                this.heap.add(head);
            }
        }
    }
}
