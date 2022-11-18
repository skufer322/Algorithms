package de.sk.graphs.datastructure;

import de.sk.graphs.GraphConstants;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Representation of an edge in an undirected graph.
 */
public class Edge {

    static final String BLANK_NAME_PASSED_EXCEPTION_MSG_TEXT_FORMAT = "The name passed for the edge is blank: %s";

    private final String name;
    private final Set<Vertex> vertices;

    /**
     * @param name name of the edge
     * @param v    first endpoint of the edge
     * @param w    second endpoint of the edge
     */
    public Edge(@NotNull String name, @NotNull Vertex v, @NotNull Vertex w) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(String.format(BLANK_NAME_PASSED_EXCEPTION_MSG_TEXT_FORMAT, name));
        }
        this.name = name;
        this.vertices = Set.of(v, w); // immutable set
        v.addEdge(this);
        w.addEdge(this);
    }

    /**
     * Returns the edge's name.
     *
     * @return name of the edge
     */
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * Returns the edge's endpoint vertices. Returns them as set since in an undirected graph, edges do not have a
     * head or tail vertex.
     *
     * @return vertices of the edge
     */
    public @NotNull Set<Vertex> getVertices() {
        return this.vertices;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;

        final Edge other = (Edge) obj;
        return this.vertices.equals(other.getVertices());
    }

    @Override
    public @NotNull String toString() {
        String verticesToString = this.vertices.stream().map(Vertex::getName).collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
        return "Edge(name=" + this.name + ", vertices=[" + verticesToString + "])";
    }
}
