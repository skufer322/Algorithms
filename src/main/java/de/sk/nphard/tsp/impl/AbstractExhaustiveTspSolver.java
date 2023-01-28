package de.sk.nphard.tsp.impl;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnAdjacencyMatrix;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
        return this.solveShortestTourViaExhaustiveSearch(adjacencyList, indices);
    }

    abstract @NotNull Pair<List<UnEdge>, Integer> solveShortestTourViaExhaustiveSearch(@NotNull UnAdjacencyList adjacencyList, int @NotNull [] indices);

    @NotNull List<Pair<Integer, Integer>> determineTourForPermutation(int @NotNull [] permutation) {
        List<Pair<Integer, Integer>> currentTourAsIndices = new ArrayList<>();
        for (int i = 0; i < permutation.length; i++) {
            int currentVertex = permutation[i];
            int nextIdx = i == permutation.length - 1 ? permutation[0] : permutation[i + 1];
            Pair<Integer, Integer> edgeOfTour = new ImmutablePair<>(currentVertex, nextIdx);
            currentTourAsIndices.add(edgeOfTour);
        }
        return currentTourAsIndices;
    }

    int determineLengthOfTour(@NotNull List<Pair<Integer, Integer>> currentTourAsIndices, @NotNull UnAdjacencyMatrix adjacencyMatrix) {
        return currentTourAsIndices.stream().map(edge -> adjacencyMatrix.getEdgeWeight(edge.getLeft(), edge.getRight())).mapToInt(Integer::intValue).sum();
    }
}
