package de.sk.graphs.datastructure;

import org.jetbrains.annotations.NotNull;

/**
 * TODO
 */
public interface AdjacencyMatrix {

    /**
     * TODO
     *
     * @param v
     * @param w
     * @param weight
     */
    void addEdge(int v, int w, int weight);

    /**
     * TODO
     *
     * @param v
     * @param w
     */
    void removeEdge(int v, int w);

    /**
     * TODO
     *
     * @param v
     * @param w
     * @return
     */
    int getEdgeWeight(int v, int w);

    /**
     * @param v
     * @return
     */
    int @NotNull [] getAdjacentEdges(int v);
}
