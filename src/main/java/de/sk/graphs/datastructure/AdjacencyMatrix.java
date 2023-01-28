package de.sk.graphs.datastructure;

import org.jetbrains.annotations.NotNull;

/**
 * Interface defining the general methods for adjacency matrix implementations (for both directed and undirected graphs).
 */
public interface AdjacencyMatrix {

    int MARKER_NON_EXISTING_EDGE = Integer.MIN_VALUE;

    /**
     * Adds an edge with a weight of {@code weight} between the vertices (with indices) {@code v} and {@code w}.
     *
     * @param v      index of the first endpoint/vertex of the edge to add
     * @param w      index of the second endpoint/vertex of the edge to add
     * @param weight weight of the edge to add
     */
    void addEdge(int v, int w, int weight);

    /**
     * Removes the edge between the vertices (with indices) {@code v} and {@code w}.
     *
     * @param v index of the first endpoint/vertex of the edge to remove
     * @param w index of the second endpoint/vertex of the edge to remove
     */
    void removeEdge(int v, int w);

    /**
     * Returns the {@code weight} of the edge between the vertices (with indices) {@code v} and {@code w}.
     * Returns {@link AdjacencyMatrix#MARKER_NON_EXISTING_EDGE} if none such edge exists.
     *
     * @param v index of the first endpoint/vertex of the edge to return the weight of
     * @param w index of the second endpoint/vertex of the edge to return the weight of
     * @return weight of the edge between the vertices (with indices) {@code v} and {@code w}
     */
    int getEdgeWeight(int v, int w);

    /**
     * Returns the adjacency information for the vertex (with index) {@code v}, i.e. the weights of the edges between
     * {@code v} and all vertices of the graph (respectively {@link AdjacencyMatrix#MARKER_NON_EXISTING_EDGE} if there
     * is no edge between {@code v} and a specific vertex).
     * <br><br>
     * Effectively returns a row of the adjacency matrix.
     *
     * @param v (index of the) vertex for which the adjacency information is to be returned
     * @return row of the adjacency matrix representing the adjacency information for the vertex (with index) {@code v}
     */
    int @NotNull [] getAdjacencyInformation(int v);

    /**
     * Returns the number of vertices the graph has.
     *
     * @return the graph's number of vertices
     */
    int getNumberOfVertices();
}
