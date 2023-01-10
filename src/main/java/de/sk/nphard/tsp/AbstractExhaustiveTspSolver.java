package de.sk.nphard.tsp;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Abstract implementation of {@link TspSolver} for exhaustive searches. Bundles up commonly used methods of the instantiable
 * implementations.
 */
public abstract class AbstractExhaustiveTspSolver implements TspSolver {

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
        // index vertices from [0; vertices.size-1]
        int[] indices = IntStream.range(0, vertices.size()).toArray();
        return this.solveShortestTourForNonSpecialCases(vertices, indices);
    }

    abstract @NotNull Pair<List<UnEdge>, Integer> solveShortestTourForNonSpecialCases(@NotNull List<UnVertex> vertices, int @NotNull [] indices);

    private void validateInputGraph(@NotNull UnAdjacencyList adjacencyList) {
        if (!UndirectedGraphUtils.isCompleteGraph(adjacencyList)) {
            throw new IllegalArgumentException(NOT_A_COMPLETE_GRAPH_EXCEPTION_MSG);
        }
    }
}
