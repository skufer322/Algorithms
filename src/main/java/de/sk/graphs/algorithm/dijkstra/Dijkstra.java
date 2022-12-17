package de.sk.graphs.algorithm.dijkstra;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.jetbrains.annotations.NotNull;

public interface Dijkstra {

    void determineSingleSourceShortestPaths(@NotNull DiAdjacencyList adjacencyList, @NotNull DiVertex s);
}
