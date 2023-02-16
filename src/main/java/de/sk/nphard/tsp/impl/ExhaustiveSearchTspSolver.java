package de.sk.nphard.tsp.impl;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnAdjacencyMatrix;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.nphard.GraphPathUtils;
import de.sk.nphard.tsp.piggyback.PermutationAlg;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Instantiable implementation of {@link AbstractExhaustiveTspSolver}. Time complexity: exponential running time.
 * <br><br>
 * All possible permutations of the vertices are pre-created with an instance of {@link  PermutationAlg} and stored in a list.
 * The algorithm then iterates over the list to determine the solution.
 * <br><br>
 * {@link ExhaustiveSearchLowMemoryTspSolver} is an improved exhaustive search which integrates the creation of the
 * permutation with the determination of the corresponding tour and therefore is much more memory-efficient.
 */
public class ExhaustiveSearchTspSolver extends AbstractExhaustiveTspSolver {

    @Inject
    private PermutationAlg permutationCreator;

    @Override
    @NotNull Pair<List<UnEdge>, Integer> solveShortestTourViaExhaustiveSearch(@NotNull UnAdjacencyList adjacencyList, int @NotNull [] indices) {
        // create all permutations of the array indices (one permutation represents one possible tour)
        List<int[]> permutations = permutationCreator.createAllPermutations(indices);
        // convert graph to adjacency matrix
        UnAdjacencyMatrix adjacencyMatrix = UndirectedGraphUtils.convertToAdjacencyMatrix(adjacencyList);
        List<Pair<Integer, Integer>> shortestTourAsIndices = new ArrayList<>();
        int lengthOfShortestTour = Integer.MAX_VALUE;
        for (int[] permutation : permutations) {
            List<Pair<Integer, Integer>> currentTourAsIndices = new ArrayList<>();
            for (int i = 0; i < permutation.length; i++) {
                int currentVertex = permutation[i];
                int nextIdx = i == permutation.length - 1 ? permutation[0] : permutation[i + 1];
                Pair<Integer, Integer> edgeOfTour = new ImmutablePair<>(currentVertex, nextIdx);
                currentTourAsIndices.add(edgeOfTour);
            }
            // determine length of the tour
            int lengthOfCurrentTour = this.determineLengthOfTour(currentTourAsIndices, adjacencyMatrix);
            // check if the tour is the new shortest tour
            if (lengthOfCurrentTour < lengthOfShortestTour) {
                shortestTourAsIndices = currentTourAsIndices;
                lengthOfShortestTour = lengthOfCurrentTour;
            }
        }
        List<UnEdge> shortestTour = GraphPathUtils.convertPathToRepresentationFittingAnAdjacencyList(shortestTourAsIndices, adjacencyList);
        return new ImmutablePair<>(shortestTour, lengthOfShortestTour);
    }
}
