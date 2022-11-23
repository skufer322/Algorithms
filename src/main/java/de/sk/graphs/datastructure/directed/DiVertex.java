package de.sk.graphs.datastructure.directed;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.datastructure.Vertex;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representation of a vertex in a directed graph.
 */
public class DiVertex implements Vertex {

    static final String NOT_HEAD_OF_INCOMING_EDGE_EXCEPTION_MSG_TEXT_FORMAT = "Vertex %s is not the head of edge %s.";
    static final String NOT_TAIL_OF_OUTGOING_EDGE_EXCEPTION_MSG_TEXT_FORMAT = "Vertex %s is not the tail of edge %s";

    private final String name;
    private final List<DiEdge> incomingEdges;
    private final List<DiEdge> outgoingEdges;

    private boolean isExplored;
    private int topSortPosition;
    private int cc;
    private int numScc;

    /**
     * @param name name of the vertex
     */
    public DiVertex(@NotNull String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(String.format(BLANK_NAME_PASSED_EXCEPTION_MSG_TEXT_FORMAT, name));
        }
        this.name = name;
        this.incomingEdges = new ArrayList<>();
        this.outgoingEdges = new ArrayList<>();
        this.isExplored = false;
        this.topSortPosition = -1;
        this.cc = -1;
        this.numScc = -1;
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
     * Adds the edge to the list of incoming edges. Throws an IllegalArgumentException if this vertex is not the
     * head vertex of the edge.
     *
     * @param edge edge to add to the incoming edges
     */
    public void addIncomingEdge(@NotNull DiEdge edge) {
        if (edge.head() != this) {
            throw new IllegalArgumentException(String.format(NOT_HEAD_OF_INCOMING_EDGE_EXCEPTION_MSG_TEXT_FORMAT, this.name, edge));
        }
        this.incomingEdges.add(edge);
    }

    /**
     * Adds the edge to the list of outgoing edges. Throws an IllegalArgumentException if this vertex is not the
     * tail vertex of the edge.
     *
     * @param edge edge to add to the outgoing edges
     */
    public void addOutgoingEdge(@NotNull DiEdge edge) {
        if (edge.tail() != this) {
            throw new IllegalArgumentException(String.format(NOT_TAIL_OF_OUTGOING_EDGE_EXCEPTION_MSG_TEXT_FORMAT, this.name, edge));
        }
        this.outgoingEdges.add(edge);
    }

    @Override
    public boolean isExplored() {
        return this.isExplored;
    }

    @Override
    public void setExplored(boolean explored) {
        this.isExplored = explored;
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

    //    @Override
    public @NotNull String toString2() {
        // TODO numScc ergänzen
        String incomingEdgesToString = this.incomingEdges.stream().map(DiEdge::name).collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
        String outgoingEdgesToString = this.outgoingEdges.stream().map(DiEdge::name).collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
        return "Vertex(name=" + this.name + ", incoming edges=[" + incomingEdgesToString + "] outgoing edges=[" + outgoingEdgesToString + "], isExplored="
                + this.isExplored + ", topSortPosition=" + this.topSortPosition + ", cc=" + this.cc + ")";
    }

    @Override
    public @NotNull String toString() {
        // TODO numScc ergänzen
        return "Vertex(name=" + this.name + ", isExplored=" + this.isExplored + ", topSortPosition=" + this.topSortPosition + ", cc=" + this.cc
                + ", scc=" + this.numScc +")";
    }
}
