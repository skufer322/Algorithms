package de.sk.graphs.datastructure.undirected;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.datastructure.Vertex;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representation of a {@link Vertex}  in an undirected graph.
 */
public class UnVertex implements Vertex {

    static final String VERTEX_NOT_PART_OF_ADDED_EDGE_EXCEPTION_MSG_TEXT_FORMAT = "Vertex %s is not part of added edge %s.";

    private final String name;
    private final List<UnEdge> edges;
    private boolean isExplored;
    private int level;
    private int graphSearchPosition;
    private int cc;

    /**
     * Constructor.
     *
     * @param name name of the vertex
     */
    public UnVertex(@NotNull String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(String.format(BLANK_NAME_PASSED_EXCEPTION_MSG_TEXT_FORMAT, name));
        }
        this.name = name;
        this.edges = new ArrayList<>();
        this.isExplored = false;
        this.graphSearchPosition = -1;
        this.level = -1;
        this.cc = -1;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    public @NotNull List<UnEdge> getEdges() {
        return this.edges;
    }

    /**
     * Adds the given {@code edge} to the vertex's edges. Throws an {@link IllegalArgumentException} if the given {@code edge}
     * does not have the vertex itself as one of its endpoints.
     *
     * @param edge edge to be added to the vertex's edges
     */
    public void addEdge(@NotNull UnEdge edge) {
        if (!edge.getVertices().contains(this)) {
            throw new IllegalArgumentException(String.format(VERTEX_NOT_PART_OF_ADDED_EDGE_EXCEPTION_MSG_TEXT_FORMAT,
                    this.name, edge));
        }
        this.edges.add(edge);
    }

    public boolean removeEdge(@NotNull UnEdge edge) {
        return this.edges.remove(edge);
    }

    @Override
    public boolean isExplored() {
        return isExplored;
    }

    @Override
    public void setExplored(boolean isExplored) {
        this.isExplored = isExplored;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGraphSearchPosition() {
        return graphSearchPosition;
    }

    public void setGraphSearchPosition(int graphSearchPosition) {
        this.graphSearchPosition = graphSearchPosition;
    }

    @Override
    public int getCc() {
        return cc;
    }

    @Override
    public void setCc(int cc) {
        this.cc = cc;
    }

    @Override
    public @NotNull String toString() {
        String edgesToString = this.edges.stream().map(UnEdge::getName).collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
        return "Vertex(name=" + this.name + ", edges=[" + edgesToString + "], isExplored=" + this.isExplored +
                ", level=" + this.level + ", graphSearchPosition=" + this.graphSearchPosition + ", cc=" + this.cc + ")";
    }

    public @NotNull String toStringWithoutEdges() {
        return "Vertex(name=" + this.name + ", isExplored=" + this.isExplored +
                ", level=" + this.level + ", graphSearchPosition=" + this.graphSearchPosition + ", cc=" + this.cc + ")";
    }

    public @NotNull String toStringOnlyWithNameAndEdges() {
        String edgesToString = this.edges.stream().map(UnEdge::getName).collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
        return "Vertex(name=" + this.name + ", edges=[" + edgesToString + "])";
    }
}
