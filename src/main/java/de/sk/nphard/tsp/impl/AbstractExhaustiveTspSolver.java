package de.sk.nphard.tsp.impl;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Abstract implementation of {@link AbstractExhaustiveTspSolver}. Bundles common methods used for implementations using
 * an exhaustive search approach to solve the TSP.
 */
public abstract class AbstractExhaustiveTspSolver extends AbstractTspSolver {

    @Override
    @NotNull Pair<List<UnEdge>, Integer> solveShortestTourProblemForCommonCases(@NotNull UnAdjacencyList adjacencyList) {
        List<UnVertex> vertices = adjacencyList.vertices();
        // index vertices from [0; vertices.size-1]
        int[] indices = IntStream.range(0, vertices.size()).toArray();
        return this.solveShortestTourViaExhaustiveSearch(vertices, indices);
    }

    abstract @NotNull Pair<List<UnEdge>, Integer> solveShortestTourViaExhaustiveSearch(@NotNull List<UnVertex> vertices, int @NotNull [] indices);
}
