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
     * the starting vertex.
     * <p>
     * The return value of the method is void since the implementing methods only determine the length of the shortest path
     * from {@code s} to each vertex (the information on this length is stored in the {@code len} attribute of the respective
     * {@link DiVertex} representing a vertex of the directed graph). If the shortest paths themselves are to be represented
     * by the edges or the vertices, the implementation of the method and its signature need to be adjusted.
     *
     * @param adjacencyList graph
     * @param s             starting vertex from which the shortest paths to all reachable vertices are to be determined
     */
    void determineSingleSourceShortestPaths(@NotNull DiAdjacencyList adjacencyList, @NotNull DiVertex s);
}
