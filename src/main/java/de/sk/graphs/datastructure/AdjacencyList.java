package de.sk.graphs.datastructure;

import java.util.List;

/**
 * Interface defining the methods for adjacency list implementations representing undirected graphs.
 */
public interface AdjacencyList {

    List<Vertex> vertices();

    List<Edge> edges();
}
