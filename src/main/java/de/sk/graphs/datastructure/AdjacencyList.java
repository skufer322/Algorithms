package de.sk.graphs.datastructure;

import java.util.List;

/**
 * Interface defining the general methods for adjacency list implementations (both for directed and undirected graphs).
 */
public interface AdjacencyList {

    String NULL_ELEMENT_IN_LISTS_EXCEPTION_MSG_TEXT_FORMAT = "Null elements are allowed neither in the vertices " +
            "list nor the edges list. Vertices: %s. Edges: %s";

    List<? extends Vertex> vertices();

    List<? extends Edge> edges();
}
