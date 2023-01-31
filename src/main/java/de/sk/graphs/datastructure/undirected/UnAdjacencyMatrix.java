package de.sk.graphs.datastructure.undirected;

import de.sk.graphs.datastructure.AdjacencyMatrix;
import de.sk.util.AdditionalArrayUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of {@link AdjacencyMatrix} for undirected graphs.
 */
public class UnAdjacencyMatrix implements AdjacencyMatrix {

    static final String NUMBER_OF_NODES_TOO_LOW_EXCEPTION_MSG_TF = "The number of nodes must be greater 0. Given: %d.";
    static final String EDGE_WITH_SAME_ENDPOINTS_EXCEPTION_MSG_TF = "Edges must have different endpoints. For the given edge, both endpoints are '%d'.";
    static final String EDGE_WEIGHT_NOT_ALLOWED_EXCEPTION_MSG_TF = "Edges with edge weight %d are not allowed (this value is the marker for non-existing edges).";
    static final String REMOVE_EDGE_OPERATION_NOT_SUPPORTED_EXCEPTION_MSG = "The removeEdge operation is not supported by this implementation.";
    static final String EDGE_IDX_OUT_OF_BOUNDS_EXCEPTION_MSG_TF = "The given edge index '%d' is out of bounds, it must be between [0;%d]";

    private final int[][] matrix;

    /**
     * Constructor.
     *
     * @param numberOfNodes number of nodes the graph represented as adjacency matrix has
     */
    public UnAdjacencyMatrix(int numberOfNodes) {
        if (numberOfNodes <= 0) {
            throw new IllegalArgumentException(String.format(NUMBER_OF_NODES_TOO_LOW_EXCEPTION_MSG_TF, numberOfNodes));
        }
        this.matrix = new int[numberOfNodes][numberOfNodes];
        AdditionalArrayUtils.setAllElementsOfMatrixToValue(this.matrix, MARKER_NON_EXISTING_EDGE);
    }

    @Override
    public void addEdge(int v, int w, int weight) {
        if (v == w) {
            throw new IllegalArgumentException(String.format(EDGE_WITH_SAME_ENDPOINTS_EXCEPTION_MSG_TF, v));
        }
        if (weight == MARKER_NON_EXISTING_EDGE) {
            throw new IllegalArgumentException(String.format(EDGE_WEIGHT_NOT_ALLOWED_EXCEPTION_MSG_TF, weight));
        }
        this.verifyEdgeIndex(v);
        this.verifyEdgeIndex(w);
        // undirected edge is symmetric
        this.matrix[v][w] = weight;
        this.matrix[w][v] = weight;
    }

    @Override
    public void removeEdge(int v, int w) {
        throw new UnsupportedOperationException(REMOVE_EDGE_OPERATION_NOT_SUPPORTED_EXCEPTION_MSG);
    }

    @Override
    public int getEdgeWeight(int v, int w) {
        this.verifyEdgeIndex(v);
        this.verifyEdgeIndex(w);
        return this.matrix[v][w];
    }

    @Override
    public int @NotNull [] getAdjacencyInformation(int v) {
        this.verifyEdgeIndex(v);
        return this.matrix[v];
    }

    private void verifyEdgeIndex(int edgeIndex) {
        if (edgeIndex < 0 || edgeIndex > this.matrix.length) {
            throw new IllegalArgumentException(String.format(EDGE_IDX_OUT_OF_BOUNDS_EXCEPTION_MSG_TF, edgeIndex, this.matrix.length - 1));
        }
    }

    @Override
    public int getNumberOfVertices() {
        return this.matrix.length;
    }
}
