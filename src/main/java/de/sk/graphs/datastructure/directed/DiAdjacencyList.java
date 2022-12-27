package de.sk.graphs.datastructure.directed;

import de.sk.graphs.util.CommonGraphUtils;
import de.sk.graphs.datastructure.AdjacencyList;
import de.sk.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Straight-forward {@link AdjacencyList} implementation for directed graphs.
 *
 * @param vertices the graph's vertices
 * @param edges    the graph's edges
 */
public record DiAdjacencyList(List<DiVertex> vertices, List<DiEdge> edges) implements AdjacencyList {

    /**
     * Constructor.
     *
     * @param vertices vertices of the graph
     * @param edges    edges of the graph
     */
    public DiAdjacencyList(@NotNull List<DiVertex> vertices, @NotNull List<DiEdge> edges) {
        // validate integrity of given vertices and edges
        if (CollectionUtils.containsNullElement(vertices) || CollectionUtils.containsNullElement(edges)) {
            throw new IllegalArgumentException(String.format(NULL_ELEMENT_IN_LISTS_EXCEPTION_MSG_TF, vertices, edges));
        }
        CommonGraphUtils.validateEdgesAreInGraph(vertices, edges);
        // set class members
        this.vertices = new ArrayList<>(vertices);
        this.edges = new ArrayList<>(edges);
    }

    @Override
    public @NotNull List<DiVertex> vertices() {
        return this.vertices;
    }

    @Override
    public @NotNull List<DiEdge> edges() {
        return this.edges;
    }

    @Override
    public @NotNull String toString() {
        return "DiAdjacencyList(" + System.lineSeparator() +
                "vertices=" + this.vertices + "," + System.lineSeparator() +
                "edges=[" + this.edges + "]" + System.lineSeparator() +
                ")";
    }
}
