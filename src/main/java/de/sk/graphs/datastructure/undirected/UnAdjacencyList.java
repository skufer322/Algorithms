package de.sk.graphs.datastructure.undirected;

import de.sk.graphs.util.CommonGraphUtils;
import de.sk.graphs.datastructure.AdjacencyList;
import de.sk.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Straight-forward {@link AdjacencyList} implementation for undirected graphs.
 *
 * @param vertices the graph's vertices
 * @param edges    the graph's edges
 */
public record UnAdjacencyList(List<UnVertex> vertices, List<UnEdge> edges) implements AdjacencyList {

    /**
     * @param vertices vertices of the graph
     * @param edges    edges of the graph
     */
    public UnAdjacencyList(@NotNull List<UnVertex> vertices, @NotNull List<UnEdge> edges) {
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
    public @NotNull List<UnVertex> vertices() {
        return this.vertices;
    }

    @Override
    public @NotNull List<UnEdge> edges() {
        return this.edges;
    }

    @Override
    public @NotNull String toString() {
        return "AdjacencyListImpl(" + System.lineSeparator() +
                "vertices=" + this.vertices + "," + System.lineSeparator() +
                "edges=[" + this.edges + "]" + System.lineSeparator() +
                ")";
    }
}
