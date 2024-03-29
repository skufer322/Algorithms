package de.sk.graphs.datastructure.undirected;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.datastructure.Edge;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Representation of an {@link Edge} in an undirected graph.
 */
public class UnEdge implements Edge, Comparable<UnEdge> {

    private final String name;
    private final int weight;
    private final Set<UnVertex> vertices;

    /**
     * Constructor.
     *
     * @param name name of the edge
     * @param v    first endpoint of the edge
     * @param w    second endpoint of the edge
     */
    public UnEdge(@NotNull String name, @NotNull UnVertex v, @NotNull UnVertex w) {
        this(name, 1, v, w);
    }

    /**
     * Constructor.
     *
     * @param name   name of the edge
     * @param weight weight of the edge
     * @param v      first endpoint of the edge
     * @param w      second endpoint of the edge
     */
    public UnEdge(@NotNull String name, int weight, @NotNull UnVertex v, @NotNull UnVertex w) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(String.format(BLANK_NAME_PASSED_EXCEPTION_MSG_TF, name));
        }
        this.name = name;
        this.weight = weight;
        this.vertices = Set.of(v, w); // immutable set
        v.addEdge(this);
        w.addEdge(this);
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    public int getWeight() {
        return this.weight;
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
        if (other.getWeight() != this.weight) return false;
        return this.vertices.equals(other.getVertices());
    }

    @Override
    public @NotNull String toString() {
        String verticesToString = this.vertices.stream().map(UnVertex::getName).collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
        return "Edge(name=" + this.name + ", weight=" + this.weight + ", vertices=[" + verticesToString + "])";
    }

    @Override
    public int compareTo(@NotNull UnEdge other) {
        return Integer.compare(this.weight, other.getWeight());
    }
}
