package de.sk.graphs.datastructure.directed;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.datastructure.Vertex;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representation of a {@link Vertex} in a directed graph.
 */
public class DiVertex implements Vertex, Comparable<DiVertex> {

    // default attribute values (DAV) for vertices in directed graphs (DG)
    public static final boolean DAV_DG_IS_EXPLORED = false;
    public static final int DAV_DG_TOP_SORT_POSITION = -1;
    public static final int DAV_DG_CC = -1;
    public static final int DAV_DG_NUM_SCC = -1;
    public static final int DAV_DG_LEN = -1;
    public static final int DAV_DG_KEY = -1;

    // text format (tf) strings for exception messages
    static final String NOT_HEAD_OF_INCOMING_EDGE_EXCEPTION_MSG_TF = "Vertex %s is not the head of edge %s.";
    static final String NOT_TAIL_OF_OUTGOING_EDGE_EXCEPTION_MSG_TF = "Vertex %s is not the tail of edge %s";

    // regular attributes
    private final String name;
    private final List<DiEdge> incomingEdges;
    private final List<DiEdge> outgoingEdges;
    // attributes which can be modified in diverse graph algorithms
    private boolean isExplored;
    private int topSortPosition;
    private int cc;
    private int numScc;
    private int len;
    private int key;

    /**
     * Constructor.
     *
     * @param name name of the vertex
     */
    public DiVertex(@NotNull String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(String.format(BLANK_NAME_PASSED_EXCEPTION_MSG_TF, name));
        }
        this.name = name;
        this.incomingEdges = new ArrayList<>();
        this.outgoingEdges = new ArrayList<>();
        this.isExplored = false;
        this.topSortPosition = -1;
        this.cc = -1;
        this.numScc = -1;
        this.len = -1;
        this.key = -1;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    public @NotNull List<DiEdge> getIncomingEdges() {
        return this.incomingEdges;
    }

    public @NotNull List<DiEdge> getOutgoingEdges() {
        return this.outgoingEdges;
    }

    /**
     * Adds the edge to the list of incoming edges. Throws an {@link IllegalArgumentException} if this vertex is not the
     * head vertex of the edge.
     *
     * @param edge edge to add to the incoming edges
     */
    public void addIncomingEdge(@NotNull DiEdge edge) {
        if (edge.head() != this) {
            throw new IllegalArgumentException(String.format(NOT_HEAD_OF_INCOMING_EDGE_EXCEPTION_MSG_TF, this.name, edge));
        }
        this.incomingEdges.add(edge);
    }

    /**
     * Adds the edge to the list of outgoing edges. Throws an {@link IllegalArgumentException} if this vertex is not the
     * tail vertex of the edge.
     *
     * @param edge edge to add to the outgoing edges
     */
    public void addOutgoingEdge(@NotNull DiEdge edge) {
        if (edge.tail() != this) {
            throw new IllegalArgumentException(String.format(NOT_TAIL_OF_OUTGOING_EDGE_EXCEPTION_MSG_TF, this.name, edge));
        }
        this.outgoingEdges.add(edge);
    }

    @Override
    public boolean isExplored() {
        return this.isExplored;
    }

    @Override
    public void setExplored(boolean isExplored) {
        this.isExplored = isExplored;
    }

    public int getTopSortPosition() {
        return this.topSortPosition;
    }

    public void setTopSortPosition(int topSortPosition) {
        this.topSortPosition = topSortPosition;
    }

    @Override
    public int getCc() {
        return this.cc;
    }

    @Override
    public void setCc(int cc) {
        this.cc = cc;
    }

    public int getNumScc() {
        return numScc;
    }

    public void setNumScc(int numScc) {
        this.numScc = numScc;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    /**
     * Resets all attribute values of the vertex which could be set in diverse algorithms to their default values.
     * <p>
     * The {@code name} and the set of {@code incomingEdges}, and the set of {@code outgoingEdges} are not reset.
     */
    public void resetAttributeValuesModifiableByAlgorithms() {
        this.isExplored = DAV_DG_IS_EXPLORED;
        this.topSortPosition = DAV_DG_TOP_SORT_POSITION;
        this.cc = DAV_DG_CC;
        this.numScc = DAV_DG_NUM_SCC;
        this.len = DAV_DG_LEN;
        this.key = DAV_DG_KEY;
    }

    //    @Override
    public @NotNull String toString2() {
        String incomingEdgesToString = this.incomingEdges.stream().map(DiEdge::getName).collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
        String outgoingEdgesToString = this.outgoingEdges.stream().map(DiEdge::getName).collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
        return "Vertex(name=" + this.name + ", incoming edges=[" + incomingEdgesToString + "] outgoing edges=[" + outgoingEdgesToString + "], isExplored=" +
                this.isExplored + ", topSortPosition=" + this.topSortPosition + ", cc=" + this.cc + ", scc=" + this.numScc + ", len=" + this.len +
                ", key=" + this.key + ")";
    }

    @Override
    public @NotNull String toString() {
        return "Vertex(name=" + this.name + ", isExplored=" + this.isExplored + ", topSortPosition=" + this.topSortPosition + ", cc=" + this.cc +
                ", scc=" + this.numScc + ", len=" + this.len + ", key=" + this.key + ")";
    }

    @Override
    public int compareTo(@NotNull DiVertex other) {
        return Integer.compare(this.key, other.getKey());
    }
}
