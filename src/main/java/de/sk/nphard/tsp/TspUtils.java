package de.sk.nphard.tsp;

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
 * Utility class supplying utility methods for operations related to the TSP.
 */
public final class TspUtils {

    static final String TOUR_AND_GRAPH_DO_MOT_MATCH_EXCEPTION_MSG_TF = "The tour and the graph do not match: For the edge with endpoints " +
            "indices %d and %d, there is no corresponding edge in the adjacency list representation of the graph.";

    private TspUtils() {
        // only utilities
    }

    /**
     * Converts the given TSP {@code tour} of edges created from an {@code adjacencyList} representation of the tour's graph
     * to a TSP tour representation fitting an adjacency matrix representation of the same graph.
     * <br><br>
     * In the returned list, an edge of the tour is represented as a pair of integers denoting the indices of the
     * edge's two endpoints (i.e. the two vertices which are connected by the very edge). The index of a vertex is
     * determined from its index in the vertices' list of the {@code adjacencyList}.
     *
     * @param tour          TSP tour created from an {@code adjacencyList} representation to be converted to a representation fitting an adjacency matrix representation
     * @param adjacencyList graph through which the TSP tour travels
     * @return representation of the tour fitting an adjacency matrix representation (i.e. the edges are represented by the indices of their endpoints)
     */
    public static @NotNull List<Pair<Integer, Integer>> convertTourToRepresentationFittingAnAdjacencyMatrix(@NotNull List<UnEdge> tour, @NotNull UnAdjacencyList adjacencyList) {
        Map<UnVertex, Integer> indexLookupForVertices = UndirectedGraphUtils.createIndexLookupForVertices(adjacencyList);
        List<Pair<Integer, Integer>> convertedTour = new ArrayList<>();
        for (UnEdge edge : tour) {
            List<Integer> indicesOfEndpoints = edge.getVertices().stream().map(indexLookupForVertices::get).toList();
            Pair<Integer, Integer> convertedEdge = new ImmutablePair<>(indicesOfEndpoints.get(0), indicesOfEndpoints.get(1));
            convertedTour.add(convertedEdge);
        }
        return convertedTour;
    }

    /**
     * Converts the given TSP {@code tour} of edges created from an adjacency matrix representation of the tour's graph
     * to a TSP tour representation fitting the given {@code adjacencyList} representation of the same graph.
     * <br><br>
     * In the given {@code tourFittingAdjacencyMatrix}, an edge of the tour is represented as a pair of integers denoting
     * the indices of the edge's two endpoints (i.e. the two vertices which are connected by the very edge). The index of
     * a vertex is determined from its index in the vertices' list of the {@code adjacencyList}.
     * <br><br>
     * In the returned tour, the edges are represented by their corresponding {@link UnEdge} objects from the edges' list of the
     * {@code adjacencyList}.
     * <br><br>
     * Is the reverse operation of {@link TspUtils#convertTourToRepresentationFittingAnAdjacencyMatrix}.
     *
     * @param tourFittingAdjacencyMatrix TSP tour created from from an adjacency matrix representation of the given graph (which is represented as {@code adjacencyList})
     * @param adjacencyList              graph through which the TSP tour travels
     * @return representation of the tour fitting an adjacency matrix representation (i.e. the edges are represented by their corresponding {@link UnEdge} objects)
     */
    public static @NotNull List<UnEdge> determineTourFromRepresentationFittingAnAdjacencyMatrix(@NotNull List<Pair<Integer, Integer>> tourFittingAdjacencyMatrix, @NotNull UnAdjacencyList adjacencyList) {
        List<UnVertex> vertices = adjacencyList.vertices();
        List<UnEdge> tour = new ArrayList<>();
        for (Pair<Integer, Integer> edgeFittingAdjacencyMatrix : tourFittingAdjacencyMatrix) {
            int idxOfV = edgeFittingAdjacencyMatrix.getLeft();
            int idxOfW = edgeFittingAdjacencyMatrix.getRight();
            UnVertex v = vertices.get(idxOfV);
            UnVertex w = vertices.get(idxOfW);
            UnEdge edge = Optional.ofNullable(UndirectedGraphUtils.getEdgeConnectingVAndW(v, w))
                    .orElseThrow(() -> new IllegalArgumentException(String.format(TOUR_AND_GRAPH_DO_MOT_MATCH_EXCEPTION_MSG_TF, idxOfV, idxOfW)));
            tour.add(edge);
        }
        return tour;
    }
}
