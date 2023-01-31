package de.sk.nphard.kpaths;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.nphard.NpHardnessConstants;
import de.sk.nphard.kpaths.colorize.GraphColorist;
import de.sk.nphard.kpaths.panchromatic.PanchromaticPathSolver;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link ShortestKPathSolver} utilizing color coding and randomization to exactly solve the problem
 * with a parameterizable level of confidence (1 - δ, with δ being the failure probability).
 * <br>
 * Time complexity: O(eᵏ / srqt(2πk) ) ln (1/δ).
 */
public class RandomizedColorCoder implements ShortestKPathSolver {

    @Inject
    private GraphColorist graphColorist;

    @Inject
    @Named(NpHardnessConstants.IN_FAILURE_PROBABILITY_FOR_RANDOMIZED_COLOR_CODER_SHORTEST_K_PATH_SOLVER)
    private double failureProbability;

    @Inject
    private PanchromaticPathSolver panchromaticPathSolver;

    @Override
    public @NotNull Pair<List<UnEdge>, Integer> shortestKPath(@NotNull UnAdjacencyList adjacencyList, int k) {
        int numberOfTrials = this.calculateNumberOfTrials(k, this.failureProbability);
        int lengthOfShortestKPath = Integer.MAX_VALUE;
        List<UnEdge> shortestKPath = Collections.emptyList();
        for (int t = 0; t < numberOfTrials; t++) {
            Map<UnVertex, Integer> colorizedGraph = this.graphColorist.colorizeGraph(adjacencyList, k, 0);
            Pair<List<UnEdge>, Integer> shortestKPathForColorization = this.panchromaticPathSolver.determineShortestPanchromaticKPath(adjacencyList, k, colorizedGraph);
            if (shortestKPathForColorization.getRight() < lengthOfShortestKPath) {
                lengthOfShortestKPath = shortestKPathForColorization.getRight();
                shortestKPath = shortestKPathForColorization.getLeft();
            }
        }
        return new ImmutablePair<>(shortestKPath, lengthOfShortestKPath);
    }

    private int calculateNumberOfTrials(int k, double failureProbability) {
        return (int) Math.ceil((Math.exp(k) / Math.sqrt(2 * Math.PI * k)) * Math.log(1 / failureProbability));
    }
}
