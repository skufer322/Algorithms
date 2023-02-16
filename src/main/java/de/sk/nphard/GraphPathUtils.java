package de.sk.nphard;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Utility class supplying utility methods for operations related to paths in graphs.
 */
public final class GraphPathUtils {

    static final String PATH_AND_GRAPH_DO_MOT_MATCH_EXCEPTION_MSG_TF = "The path and the graph do not match: For the edge with endpoint " +
            "indices %d and %d, there is no corresponding edge in the adjacency list representation of the graph.";

    private GraphPathUtils() {
        // only utilities
    }

    /**
     * Converts the given {@code path} of edges created from an {@code adjacencyList} representation of the path's graph
     * to a path representation fitting an adjacency matrix representation of the same graph.
     * <br><br>
     * In the returned list, an edge of the path is represented as a pair of integers denoting the indices of the
     * edge's two endpoints (i.e. the two vertices which are connected by the very edge). The index of a vertex is
     * determined from its index in the vertices' list of the {@code adjacencyList}.
     *
     * @param path          path created from an {@code adjacencyList} representation to be converted to a representation fitting an adjacency matrix representation
     * @param adjacencyList graph through which the path travels
     * @return representation of the path fitting an adjacency matrix representation (i.e. the edges are represented by the indices of their endpoints)
     */
    public static @NotNull List<Pair<Integer, Integer>> convertPathToRepresentationFittingAnAdjacencyMatrix(@NotNull List<UnEdge> path, @NotNull UnAdjacencyList adjacencyList) {
        Map<UnVertex, Integer> indexLookupForVertices = UndirectedGraphUtils.createIndexLookupForVertices(adjacencyList);
        List<Pair<Integer, Integer>> convertedPath = new ArrayList<>();
        for (UnEdge edge : path) {
            List<Integer> indicesOfEndpoints = edge.getVertices().stream().map(indexLookupForVertices::get).toList();
            Pair<Integer, Integer> convertedEdge = new ImmutablePair<>(indicesOfEndpoints.get(0), indicesOfEndpoints.get(1));
            convertedPath.add(convertedEdge);
        }
        return convertedPath;
    }

    /**
     * Converts the given path of edges ({@code pathFittingAdjacencyMatrixRepresentation} ) created from an adjacency matrix
     * representation of the graph to a path representation fitting the given {@code adjacencyList} representation of the same
     * graph.
     * <br><br>
     * In the given {@code pathFittingAdjacencyMatrixRepresentation}, an edge of the path is represented as a pair of integers denoting
     * the indices of the edge's two endpoints (i.e. the two vertices which are connected by the very edge). The index of
     * a vertex is determined from its index in the vertices' list of the {@code adjacencyList}.
     * <br><br>
     * In the returned path, the edges are represented by their corresponding {@link UnEdge} objects from the list of edges of the
     * {@code adjacencyList}.
     * <br><br>
     * Is the reverse operation of {@link GraphPathUtils#convertPathToRepresentationFittingAnAdjacencyMatrix}.
     *
     * @param pathFittingAdjacencyMatrixRepresentation path created from an adjacency matrix representation of the given graph (which is represented as {@code adjacencyList})
     * @param adjacencyList                            graph through which the path travels
     * @return representation of the path fitting an adjacency matrix representation (i.e. the edges are represented by their corresponding {@link UnEdge} objects)
     */
    public static @NotNull List<UnEdge> convertPathToRepresentationFittingAnAdjacencyList(@NotNull List<Pair<Integer, Integer>> pathFittingAdjacencyMatrixRepresentation, @NotNull UnAdjacencyList adjacencyList) {
        List<UnVertex> vertices = adjacencyList.vertices();
        List<UnEdge> path = new ArrayList<>();
        for (Pair<Integer, Integer> edgeFittingAdjacencyMatrix : pathFittingAdjacencyMatrixRepresentation) {
            int idxOfV = edgeFittingAdjacencyMatrix.getLeft();
            int idxOfW = edgeFittingAdjacencyMatrix.getRight();
            UnVertex v = vertices.get(idxOfV);
            UnVertex w = vertices.get(idxOfW);
            UnEdge edge = Optional.ofNullable(UndirectedGraphUtils.getEdgeConnectingVAndW(v, w))
                    .orElseThrow(() -> new IllegalArgumentException(String.format(PATH_AND_GRAPH_DO_MOT_MATCH_EXCEPTION_MSG_TF, idxOfV, idxOfW)));
            path.add(edge);
        }
        return path;
    }
}
