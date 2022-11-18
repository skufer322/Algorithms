package de.sk.graphs.datastructure;

import de.sk.graphs.GraphConstants;
import de.sk.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Straight-forward adjacency list implementation.
 *
 * @param vertices the graph's vertices
 * @param edges    the graph's edges
 */
public record AdjacencyListImpl(List<Vertex> vertices, List<Edge> edges) implements AdjacencyList {

    static final String NULL_ELEMENT_IN_LISTS_EXCEPTION_MSG_TEXT_FORMAT = "Null elements are allowed neither in the vertices " +
            "list nor the edges list. Vertices: %s. Edges: %s";
    static final String EDGE_ENDPOINTS_NOT_IN_GRAPH_EXCEPTION_MSG_TEXT_FORMAT = "At least one endpoint of edge %s ([%s]) is incident to " +
            "a vertex not in the adjacency list ([%s]).";

    /**
     * @param vertices vertices of the graph
     * @param edges    edges of the graph
     */
    public AdjacencyListImpl(@NotNull List<Vertex> vertices, @NotNull List<Edge> edges) {
        // validate integrity of given vertices and edges
        if (CollectionUtils.containsNullElement(vertices) || CollectionUtils.containsNullElement(edges)) {
            throw new IllegalArgumentException(String.format(NULL_ELEMENT_IN_LISTS_EXCEPTION_MSG_TEXT_FORMAT, vertices, edges));
        }
        this.validateEdgesAreInGraph(vertices, edges);
        // set class members
        this.vertices = new ArrayList<>(vertices);
        this.edges = new ArrayList<>(edges);
    }

    private void validateEdgesAreInGraph(@NotNull List<Vertex> vertices, @NotNull List<Edge> edges) {
        Set<Vertex> verticesForValidation = new HashSet<>(vertices); // create set to speed up containment checks
        for (Edge edge : edges) {
            if (!verticesForValidation.containsAll(edge.getVertices())) {
                String nameOfVerticesOfTheEdge = edge.getVertices().stream().map(Vertex::getName)
                        .collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
                String nameOfVerticesInGraph = vertices.stream().map(Vertex::getName)
                        .collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
                throw new IllegalArgumentException(String.format(EDGE_ENDPOINTS_NOT_IN_GRAPH_EXCEPTION_MSG_TEXT_FORMAT,
                        edge.getName(), nameOfVerticesOfTheEdge, nameOfVerticesInGraph));
            }
        }
    }

    @Override
    public @NotNull List<Vertex> vertices() {
        return this.vertices;
    }

    @Override
    public @NotNull List<Edge> edges() {
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
