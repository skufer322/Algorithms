package de.sk.graphs.datastructure;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Interface defining the general methods for edge implementations (both for directed and undirected graphs).
 */
public interface Edge {

    String BLANK_NAME_PASSED_EXCEPTION_MSG_TEXT_FORMAT = "The name passed for the edge is blank: %s";

    /**
     * Returns the name of the edge.
     *
     * @return name of the edge
     */
    @NotNull String getName();

    /**
     * Returns the weight of the edge.
     *
     * @return weight of the edge
     */
    int getWeight();

    /**
     * Returns the vertices of the edge in random order. Note that for an edge in a directed graph, this can only be used
     * to check which two vertices make up the edge, but NOT which vertex is tail and which is head. (For an edge in an
     * undirected graphs, there is no order of vertices.)
     *
     * @return vertices of the edge in random order
     */
    @NotNull Set<? extends Vertex> getVertices();
}
