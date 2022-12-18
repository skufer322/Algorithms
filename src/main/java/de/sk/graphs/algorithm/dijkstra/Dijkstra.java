package de.sk.graphs.algorithm.dijkstra;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.jetbrains.annotations.NotNull;

/**
 * Interface defining the methods for implementations of Dijkstra's algorithm solving the single-source shortest path problem.
 */
public interface Dijkstra {

    /**
     * For the given graph represented as adjacency list, determines the shortest paths to all vertices reachable from
     * the start vertex.
     *
     * @param adjacencyList graph
     * @param s             start vertex from which the shortest paths to all reachable vertices shall be determined
     */
    void determineSingleSourceShortestPaths(@NotNull DiAdjacencyList adjacencyList, @NotNull DiVertex s);
}
