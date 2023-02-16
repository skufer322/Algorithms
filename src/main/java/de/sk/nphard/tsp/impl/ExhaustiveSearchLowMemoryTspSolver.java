package de.sk.nphard.tsp.impl;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnAdjacencyMatrix;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.nphard.GraphPathUtils;
import de.sk.util.SortingUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Instantiable implementation of {@link AbstractExhaustiveTspSolver}. Time complexity: exponential running time.
 * <br><br>
 * All possible permutations of the vertices are created one after another. Instantly after a permutation is created,
 * the corresponding tour and its length are determined.
 * <br><br>
 * Therefore, in contrast to {@link ExhaustiveSearchTspSolver}, no caching of all the possible permutations is required
 * which is much more memory-efficient.
 */
public class ExhaustiveSearchLowMemoryTspSolver extends AbstractExhaustiveTspSolver {

    private List<Pair<Integer, Integer>> shortestTourAsIndices;
    private int lengthOfShortestTour;
    private UnAdjacencyMatrix adjacencyMatrix;

    @Override
    @NotNull Pair<List<UnEdge>, Integer> solveShortestTourViaExhaustiveSearch(@NotNull UnAdjacencyList adjacencyList, int @NotNull [] indices) {
        // convert graph to adjacency matrix
        this.adjacencyMatrix = UndirectedGraphUtils.convertToAdjacencyMatrix(adjacencyList);
        this.shortestTourAsIndices = Collections.emptyList();
        this.lengthOfShortestTour = Integer.MAX_VALUE;
        this.createAllPermutationsRecAndDetermineCorrespondingTours(indices.length, indices);
        List<UnEdge> shortestTour = GraphPathUtils.convertPathToRepresentationFittingAnAdjacencyList(this.shortestTourAsIndices, adjacencyList);
        return new ImmutablePair<>(shortestTour, this.lengthOfShortestTour);
    }

    private void createAllPermutationsRecAndDetermineCorrespondingTours(int n, int @NotNull [] elements) {
        if (n == 1) {
            this.processPermutation(elements);
        } else {
            for (int i = 0; i < n - 1; i++) {
                createAllPermutationsRecAndDetermineCorrespondingTours(n - 1, elements);
                if (n % 2 == 0) {
                    SortingUtils.swapElements(elements, i, n - 1);
                } else {
                    SortingUtils.swapElements(elements, 0, n - 1);
                }
            }
            createAllPermutationsRecAndDetermineCorrespondingTours(n - 1, elements);
        }
    }

    private void processPermutation(int @NotNull [] permutation) {
        List<Pair<Integer, Integer>> currentTourAsIndices = this.determineTourForPermutation(permutation);
        // determine length of the tour
        int lengthOfCurrentTour = this.determineLengthOfTour(currentTourAsIndices, this.adjacencyMatrix);
        // check if the tour is the new shortest tour
        if (lengthOfCurrentTour < this.lengthOfShortestTour) {
            this.shortestTourAsIndices = currentTourAsIndices;
            this.lengthOfShortestTour = lengthOfCurrentTour;
        }
    }
}
