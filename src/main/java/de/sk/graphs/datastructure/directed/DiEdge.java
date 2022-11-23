package de.sk.graphs.datastructure.directed;

import de.sk.graphs.datastructure.Edge;
import de.sk.graphs.datastructure.Vertex;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Representation of an edge in an undirected graph.
 */
public record DiEdge(String name, DiVertex tail, DiVertex head) implements Edge {

    /**
     * @param name name of the edge
     * @param tail tail vertex of the edge
     * @param head head vertex of the edge
     */
    public DiEdge(@NotNull String name, @NotNull DiVertex tail, @NotNull DiVertex head) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(String.format(BLANK_NAME_PASSED_EXCEPTION_MSG_TEXT_FORMAT, name));
        }
        this.name = name;
        this.tail = tail;
        this.head = head;
        this.tail.addOutgoingEdge(this);
        this.head.addIncomingEdge(this);
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull DiVertex tail() {
        return this.tail;
    }

    @Override
    public @NotNull DiVertex head() {
        return this.head;
    }

    @Override
    public @NotNull Set<? extends Vertex> getVertices() {
        return Set.of(this.tail, this.head);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;

        final DiEdge other = (DiEdge) obj;
        return this.tail.equals(other.tail()) && this.head.equals(other.head());
    }

    @Override
    public @NotNull String toString() {
        String verticesToString = Stream.of(this.tail, this.head).map(DiVertex::getName).collect(Collectors.joining(",head="));
        return "Edge(name=" + this.name + ", vertices=[tail=" + verticesToString + "])";
    }
}
