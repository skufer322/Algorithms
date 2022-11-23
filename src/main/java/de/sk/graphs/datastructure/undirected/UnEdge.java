package de.sk.graphs.datastructure.undirected;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.datastructure.Edge;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Representation of an edge in an undirected graph.
 */
public class UnEdge implements Edge {

    private final String name;
    private final Set<UnVertex> vertices;

    /**
     * @param name name of the edge
     * @param v    first endpoint of the edge
     * @param w    second endpoint of the edge
     */
    public UnEdge(@NotNull String name, @NotNull UnVertex v, @NotNull UnVertex w) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(String.format(BLANK_NAME_PASSED_EXCEPTION_MSG_TEXT_FORMAT, name));
        }
        this.name = name;
        this.vertices = Set.of(v, w); // immutable set
        v.addEdge(this);
        w.addEdge(this);
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * Returns the edge's endpoint vertices as set. In an undirected graph, there is no order of edges.
     *
     * @return vertices of the edge
     */
    @Override
    public @NotNull Set<UnVertex> getVertices() {
        return this.vertices;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;

        final UnEdge other = (UnEdge) obj;
        return this.vertices.equals(other.getVertices());
    }

    @Override
    public @NotNull String toString() {
        String verticesToString = this.vertices.stream().map(UnVertex::getName).collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
        return "Edge(name=" + this.name + ", vertices=[" + verticesToString + "])";
    }
}
