package de.sk.graphs.dfs;

import de.sk.graphs.datastructure.AdjacencyList;
import de.sk.graphs.datastructure.Vertex;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Interface defining the methods for graph search implementations applying a depth-first search.
 */
public interface GraphDfs {

    /**
     * Conducts a depth-first search for the given graph, starting from start vertex s. Returns the vertices connected
     * to s in the order of visit.
     *
     * @param adjacencyList adjacency list of the graph for which the dfs is to be conducted
     * @param s             start vertex
     * @return vertices connected to s in the order of visit
     */
    @NotNull List<Vertex> conductDfs(@NotNull AdjacencyList adjacencyList, @NotNull Vertex s);
}
