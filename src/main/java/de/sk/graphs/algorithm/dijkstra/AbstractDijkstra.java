package de.sk.graphs.algorithm.dijkstra;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDijkstra implements Dijkstra {

    void setLenValues(@NotNull DiAdjacencyList adjacencyList, @NotNull DiVertex s) {
        for (DiVertex vertex : adjacencyList.vertices()) {
            if (vertex != s) {
                vertex.setLen(Integer.MAX_VALUE);
            } else {
                vertex.setLen(0);
            }
        }
    }

    void setKeyValues(@NotNull DiAdjacencyList adjacencyList, @NotNull DiVertex s) {
        for (DiVertex vertex : adjacencyList.vertices()) {
            if (vertex != s) {
                vertex.setKey(Integer.MAX_VALUE);
            } else {
                vertex.setKey(0);
            }
        }
    }
}
