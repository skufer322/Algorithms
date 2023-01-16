package de.sk.nphard.tsp;

import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
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
    @NotNull Pair<List<UnEdge>, Integer> solveShortestTourForNonSpecialCases(@NotNull List<UnVertex> vertices, int @NotNull [] indices) {
        // create all permutations of the array indices (one permutation represents one possible tour)
        List<int[]> permutations = permutationCreator.createAllPermutations(indices);
        // init left and right of return value
        List<UnEdge> shortestTour = Collections.emptyList();
        int lengthOfShortestTour = Integer.MAX_VALUE;
        // iterate over all permutations and determine the shortest tour
        for (int[] permutation : permutations) {
            List<UnEdge> currentTour = new ArrayList<>();
            for (int i = 0; i < permutation.length; i++) {
                int currentIdx = permutation[i];
                // if at the end of the permutation, go back to the start of the tour; else go to the next index of the permutation (= next vertex of the tour)
                int nextIdx = i == permutation.length - 1 ? permutation[0] : permutation[i + 1];
                UnVertex currentVertex = vertices.get(currentIdx);
                UnEdge currentEdge = currentIdx < nextIdx ? currentVertex.getEdges().get(nextIdx - 1) : currentVertex.getEdges().get(nextIdx);
                currentTour.add(currentEdge);
            }
            // determine length of the tour
            int lengthOfCurrentTour = currentTour.stream().map(UnEdge::getWeight).mapToInt(Integer::intValue).sum();
            // check if the tour is the new shortest tour
            if (lengthOfCurrentTour < lengthOfShortestTour) {
                shortestTour = currentTour;
                lengthOfShortestTour = lengthOfCurrentTour;
            }
        }
        return new ImmutablePair<>(shortestTour, lengthOfShortestTour);
    }
}
