package de.sk.nphard.tsp;

import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.util.SortingUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Instantiable implementation of {@link AbstractExhaustiveTspSolver}. Time complexity: exponential.
 * All possible permutations of the vertices are created one after another. Instantly after a permutation is created,
 * the corresponding tour and its length are determined.
 * Therefore, in contrast to {@link ExhaustiveSearchTspSolver}, no caching of all the possible permutations is required
 * which is much more memory-efficient.
 */
public class ExhaustiveSearchLowMemoryTspSolver extends AbstractExhaustiveTspSolver {

    private List<UnEdge> shortestTour;
    private int lengthOfShortestTour;
    private List<UnVertex> vertices;

    @Override
    @NotNull Pair<List<UnEdge>, Integer> solveShortestTourForNonSpecialCases(@NotNull List<UnVertex> vertices, int @NotNull [] indices) {
        this.vertices = vertices;
        this.shortestTour = Collections.emptyList();
        this.lengthOfShortestTour = Integer.MAX_VALUE;
        this.createAllPermutationsRecAndDetermineCorrespondingTours(indices.length, indices);
        return new ImmutablePair<>(this.shortestTour, this.lengthOfShortestTour);
    }

    private void createAllPermutationsRecAndDetermineCorrespondingTours(int n, int @NotNull [] elements) {
        if (n == 1) {
            this.determineTourForPermutation(elements);
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

    private void determineTourForPermutation(int @NotNull [] permutation) {
        List<UnEdge> currentTour = new ArrayList<>();
        for (int i = 0; i < permutation.length; i++) {
            int currentIdx = permutation[i];
            // if at the end of the permutation, go back to start; else go to next of the permutation
            int nextIdx = i == permutation.length - 1 ? permutation[0] : permutation[i + 1];
            UnVertex currentVertex = this.vertices.get(currentIdx);
            UnEdge currentEdge = currentIdx < nextIdx ? currentVertex.getEdges().get(nextIdx - 1) : currentVertex.getEdges().get(nextIdx);
            currentTour.add(currentEdge);
        }
        // determine length of the tour
        int lengthOfCurrentTour = currentTour.stream().map(UnEdge::getWeight).mapToInt(Integer::intValue).sum();
        // check if the tour is the new shortest tour
        if (lengthOfCurrentTour < this.lengthOfShortestTour) {
            this.shortestTour = currentTour;
            this.lengthOfShortestTour = lengthOfCurrentTour;
        }
    }
}
