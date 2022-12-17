package de.sk.graphs.datastructure;

import java.util.List;

/**
 * Interface defining the general methods for adjacency list implementations (both for directed and undirected graphs).
 * Adjacency lists represent graphs made up of vertices (objects) and edges (pairwise relations between objects).
 */
public interface AdjacencyList {

    String NULL_ELEMENT_IN_LISTS_EXCEPTION_MSG_TEXT_FORMAT = "Null elements are allowed neither in the vertices " +
            "list nor the edges list. Vertices: %s. Edges: %s";

    /**
     * Returns the vertices of the graph the adjacency list represents.
     *
     * @return the vertices of the graph
     */
    List<? extends Vertex> vertices();

    /**
     * Returns the edges of the graph the adjacency list represents.
     *
     * @return the edges of the graph
     */
    List<? extends Edge> edges();
}
