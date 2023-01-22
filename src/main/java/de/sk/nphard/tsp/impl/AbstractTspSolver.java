package de.sk.nphard.tsp.impl;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.nphard.tsp.TspSolver;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Abstract implementation of {@link TspSolver}. Bundles commonly used methods of the instantiable implementations.
 */
public abstract class AbstractTspSolver implements TspSolver {

    static final String NOT_A_COMPLETE_GRAPH_EXCEPTION_MSG = "The given graph is not a complete graph. There must be an edge between each pair of vertices v,w ∈ V.";

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
        return this.solveShortestTourProblemForCommonCases(adjacencyList);
    }

    /**
     * Template method to use by instantiable implementations solving the TSP.
     */
    abstract @NotNull Pair<List<UnEdge>, Integer> solveShortestTourProblemForCommonCases(@NotNull UnAdjacencyList adjacencyList);

    private void validateInputGraph(@NotNull UnAdjacencyList adjacencyList) {
        if (!UndirectedGraphUtils.isCompleteGraph(adjacencyList)) {
            throw new IllegalArgumentException(NOT_A_COMPLETE_GRAPH_EXCEPTION_MSG);
        }
    }
}
