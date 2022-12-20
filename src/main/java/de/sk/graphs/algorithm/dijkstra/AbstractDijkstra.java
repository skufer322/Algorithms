package de.sk.graphs.algorithm.dijkstra;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract class implementing common methods required by implementations of {@link Dijkstra}'s algorithm.
 * TODO: in utility class auslagern?
 */
public abstract class AbstractDijkstra implements Dijkstra {

    /**
     * Initializes the {@code len} values for all vertices in the graph (represented as an adjacency list).
     * The {@code len} value of the starting vertex {@code s} is set to 0, for all other vertices it is set to {@code Integer.MAX_VALUE}.
     *
     * @param adjacencyList graph
     * @param s             starting vertex
     */
    void initializeLenValues(@NotNull DiAdjacencyList adjacencyList, @NotNull DiVertex s) {
        for (DiVertex vertex : adjacencyList.vertices()) {
            if (vertex != s) {
                vertex.setLen(Integer.MAX_VALUE);
            } else {
                vertex.setLen(0);
            }
        }
    }

    /**
     * Initializes the {@code key} values for all vertices in the graph (represented as an adjacency list).
     * The {@code key} value of the starting vertex {@code s} is set to 0, for all other vertices it is set to
     * {@code Integer.MAX_VALUE}.
     *
     * @param adjacencyList graph
     * @param s             starting vertex
     */
    void initializeKeyValues(@NotNull DiAdjacencyList adjacencyList, @NotNull DiVertex s) {
        for (DiVertex vertex : adjacencyList.vertices()) {
            if (vertex != s) {
                vertex.setKey(Integer.MAX_VALUE);
            } else {
                vertex.setKey(0);
            }
        }
    }
}
