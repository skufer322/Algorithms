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

    // default attribute values (DAV) for vertices in undirected graphs (UG)
    static final boolean DAV_UG_IS_EXPLORED = false;
    static final int DAV_UG_GRAPH_SEARCH_POSITION = -1;
    static final int DAV_UG_LEVEL = -1;
    static final int DAV_UG_CC = -1;

    // text format (tf) strings for exception messages
    static final String VERTEX_NOT_PART_OF_ADDED_EDGE_EXCEPTION_MSG_TF = "Vertex %s is not part of added edge %s.";

    // "regular" attributes
    private final String name;
    private final List<UnEdge> edges;
    // attributes which can be modified in diverse graph algorithms
    private final int weight;
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
        this(name, -1);
    }

    public UnVertex(@NotNull String name, int weight) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(String.format(BLANK_NAME_PASSED_EXCEPTION_MSG_TF, name));
        }
        this.name = name;
        this.edges = new ArrayList<>();
        this.weight = weight;
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
            throw new IllegalArgumentException(String.format(VERTEX_NOT_PART_OF_ADDED_EDGE_EXCEPTION_MSG_TF,
                    this.name, edge));
        }
        this.edges.add(edge);
    }

    public boolean removeEdge(@NotNull UnEdge edge) {
        return this.edges.remove(edge);
    }

    public int getWeight() {
        return weight;
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

    /**
     * Resets all attribute values of the vertex which could be set in diverse algorithms to their default values.
     * <p>
     * The {@code name}, set of edges {@code edges}, and the {@code weight} are not reset.
     */
    public void resetAttributeValuesModifiableByAlgorithms() {
        this.isExplored = DAV_UG_IS_EXPLORED;
        this.graphSearchPosition = DAV_UG_GRAPH_SEARCH_POSITION;
        this.level = DAV_UG_LEVEL;
        this.cc = DAV_UG_CC;
    }

    @Override
    public @NotNull String toString() {
        return "Vertex(name=" + this.name + ", edges=[" + this.edgesToString() + "], weight=" + this.weight + ", isExplored=" + this.isExplored +
                ", level=" + this.level + ", graphSearchPosition=" + this.graphSearchPosition + ", cc=" + this.cc + ")";
    }

    public @NotNull String toStringWithoutEdges() {
        return "Vertex(name=" + this.name + ", weight=" + this.weight + ", isExplored=" + this.isExplored +
                ", level=" + this.level + ", graphSearchPosition=" + this.graphSearchPosition + ", cc=" + this.cc + ")";
    }

    public @NotNull String toStringOnlyWithNameAndEdges() {
        return "Vertex(name=" + this.name + ", edges=[" + this.edgesToString() + "])";
    }

    private @NotNull String edgesToString() {
        return this.edges.stream().map(UnEdge::getName).collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
    }
}
