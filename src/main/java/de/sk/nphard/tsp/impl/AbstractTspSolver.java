package de.sk.nphard.tsp.impl;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.nphard.tsp.TspSolver;
import de.sk.util.CollectionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract implementation of {@link TspSolver}. Bundles commonly used methods of the instantiable implementations.
 */
public abstract class AbstractTspSolver implements TspSolver {

    static final String NOT_A_COMPLETE_GRAPH_EXCEPTION_MSG = "The given graph is not a complete graph. There must be an edge between each pair of vertices v,w ∈ V.";
    static final String WEIGHTS_OF_ALL_EDGES_MUST_BE_GREATER_EXCEPTION_MSG_TF = "The weight/length of all edges must be greater 0. The edge with name '%s' violates this requirement with a weight/length of %d.";
    static final String NO_EDGE_BACK_TO_STARTING_VERTEX_FOUND_EXCEPTION_MSG_TF = "Internal error: No edge back to the starting vertex %s found.";
    static final String TOUR_IS_INVALID_EXCEPTION_MSG_TF = "INTERNAL ERROR: The vertex with name '%s' appears not exactly twice in the tour (%d occurrence(s)). Hence, the determined solution is not a valid TSP tour.";

    public @NotNull Pair<List<UnEdge>, Integer> determineShortestTour(@NotNull UnAdjacencyList adjacencyList) {
        // validate input graph is a complete graph
        this.validateInputGraph(adjacencyList);
        List<UnVertex> vertices = adjacencyList.vertices();
        // check special cases which do not require the creation of permutations
        if (vertices.size() == 1) {
            return new ImmutablePair<>(Collections.emptyList(), 0);
        }
        if (vertices.size() == 2) {
            UnEdge edge = vertices.get(0).getEdges().get(0);
            return new ImmutablePair<>(Collections.singletonList(edge), edge.getWeight());
        }
        @NotNull Pair<List<UnEdge>, Integer> tour = this.solveShortestTourProblemForCommonCases(adjacencyList);
        this.validateIntegrityOfTour(tour);
        return tour;
    }

    /**
     * Template method to use by instantiable implementations solving the TSP.
     */
    abstract @NotNull Pair<List<UnEdge>, Integer> solveShortestTourProblemForCommonCases(@NotNull UnAdjacencyList adjacencyList);

    private void validateInputGraph(@NotNull UnAdjacencyList adjacencyList) {
        if (!UndirectedGraphUtils.isCompleteGraph(adjacencyList)) {
            throw new IllegalArgumentException(NOT_A_COMPLETE_GRAPH_EXCEPTION_MSG);
        }
        for (UnEdge edge : adjacencyList.edges()) {
            if (edge.getWeight() <= 0) {
                throw new IllegalArgumentException(String.format(WEIGHTS_OF_ALL_EDGES_MUST_BE_GREATER_EXCEPTION_MSG_TF, edge.getName(), edge.getWeight()));
            }
        }
    }

    void validateEdgeBackToStartingVertexIsNotNull(@NotNull UnVertex startingVertex, @Nullable UnEdge edgeBackToStartingVertex) {
        if (edgeBackToStartingVertex == null) {
            throw new IllegalStateException(String.format(NO_EDGE_BACK_TO_STARTING_VERTEX_FOUND_EXCEPTION_MSG_TF, startingVertex.getName()));
        }
    }

    void validateIntegrityOfTour(@NotNull Pair<List<UnEdge>, Integer> tour) {
        Map<UnVertex, Integer> vertexOccurrences = new HashMap<>();
        for (UnEdge edge : tour.getLeft()) {
            List<UnVertex> verticesOfEdge = edge.getVertices().stream().toList();
            UnVertex v = verticesOfEdge.get(0);
            UnVertex w = verticesOfEdge.get(1);
            CollectionUtils.incrementCounterForElement(v, vertexOccurrences);
            CollectionUtils.incrementCounterForElement(w, vertexOccurrences);
        }
        for (Map.Entry<UnVertex, Integer> entry : vertexOccurrences.entrySet()) {
            if (entry.getValue() != 2) {
                throw new IllegalStateException(String.format(TOUR_IS_INVALID_EXCEPTION_MSG_TF, entry.getKey().getName(), entry.getValue()));
            }
        }
    }
}
