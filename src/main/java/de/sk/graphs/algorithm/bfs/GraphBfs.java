package de.sk.graphs.algorithm.bfs;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Interface defining the methods for graph search implementations applying a breadth-first search.
 */
public interface GraphBfs {

    /**
     * Conducts a breadth-first search for the given graph, starting from starting vertex {@code s}. Returns the vertices connected
     * to {@code s} in the order of visit.
     *
     * @param adjacencyList adjacency list of the graph for which the bfs is to be conducted
     * @param s             starting vertex
     * @return vertices connected to {@code s} in the order of visit
     */
    @NotNull List<UnVertex> conductBfs(@NotNull UnAdjacencyList adjacencyList, @NotNull UnVertex s);
}
