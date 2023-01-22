package de.sk.nphard.influencemaximization;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import de.sk.util.AdditionalArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Implementation of {@link InfMaxSolver} utilizing a greedy algorithm.
 * <br>
 * Time complexity: O(knm²r) (k = number of seeds in the solution, n = number of vertices, m = number of edges,
 * r = number of repetitions for estimating the influence of a vertex).
 */
public class GreedySamplingInfMaxSolver implements InfMaxSolver {

    static final String REPETITIONS_MUST_BE_GREATER_0_EXCEPTION_MSG_TF = "The number of repetitions must be greater than zero. Given: %d";
    static final String INVALID_ACTIVATION_PROBABILITY_EXCEPTION_MSG_TF = "Activation probability p has to be between ]0;1]. Given: %f.";
    static final String INVALID_NUMBER_OF_SEEDS_SPECIFIED_EXCEPTION_MSG_TF = "The number k of seeds must bet between 1 and the number of vertices " +
            "in the graph (%d). Given: %d.";

    private final Random random;
    private final int repetitions; // could also be made dependent on number of edges, theoretically

    private final Set<DiVertex> seedsWithMaxInfluence = new LinkedHashSet<>();

    /**
     * Constructor.
     *
     * @param repetitions number of repetitions estimating the influence of a vertex
     * @param seed        seed for the random number generator used in the estimation process
     */
    public GreedySamplingInfMaxSolver(int repetitions, long seed) {
        if (repetitions <= 0) {
            throw new IllegalArgumentException(String.format(REPETITIONS_MUST_BE_GREATER_0_EXCEPTION_MSG_TF, repetitions));
        }
        this.repetitions = repetitions;
        this.random = new Random(seed);
    }

    @Override
    public @NotNull Set<DiVertex> maximizeInfluence(@NotNull DiAdjacencyList adjacencyList, double p, int k) {
        if (p <= 0 || p > 1) {
            throw new IllegalArgumentException(String.format(INVALID_ACTIVATION_PROBABILITY_EXCEPTION_MSG_TF, p));
        }
        if (k < 1 || k > adjacencyList.vertices().size()) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_SEEDS_SPECIFIED_EXCEPTION_MSG_TF, adjacencyList.vertices().size(), k));
        }
        this.clearDatastructures();
        List<DiVertex> vertices = adjacencyList.vertices();
        while (this.seedsWithMaxInfluence.size() < k) {
            // the vertices which have not been selected as seeds yet are the candidates -> one will be selected as new seed
            List<DiVertex> candidates = vertices.stream().filter(vertex -> !this.seedsWithMaxInfluence.contains(vertex)).toList();
            // for each candidate, aggregate the number of additionally reachable nodes over all the repetitions
            long[] numberOfReachableNodes = new long[candidates.size()]; // could be normalized, but would not change result
            for (int i = 0; i < repetitions; i++) {
                // current H ⊆ E as edge statuses
                Map<DiEdge, Boolean> edgeStatuses = InfMaxUtils.flipEdges(adjacencyList, p, this.random);
                this.determineAdditionalInfluenceOfCandidatesInH(edgeStatuses, candidates, numberOfReachableNodes);
            }
            List<Integer> indicesOfBestCandidates = AdditionalArrayUtils.indicesOfLargestElement(numberOfReachableNodes);
            int indexOfNextSeed = indicesOfBestCandidates.get(this.random.nextInt(indicesOfBestCandidates.size())); // break possible ties arbitrarily
            DiVertex nextSeed = candidates.get(indexOfNextSeed);
            this.seedsWithMaxInfluence.add(nextSeed);
        }
        return new HashSet<>(this.seedsWithMaxInfluence);
    }

    private void determineAdditionalInfluenceOfCandidatesInH(@NotNull Map<DiEdge, Boolean> edgeStatuses, @NotNull List<DiVertex> candidates, long @NotNull [] numberOfReachableNodes) {
        // determine vertices reachable from current seeds
        Set<DiVertex> verticesReachableViaSeedsInH = new HashSet<>();
        for (DiVertex seed : this.seedsWithMaxInfluence) {
            Set<DiVertex> verticesReachableViaSeed = InfMaxUtils.determineReachableVerticesConsideringEdgeStatuses(seed, edgeStatuses);
            verticesReachableViaSeedsInH.addAll(verticesReachableViaSeed);
        }
        // assess candidates for their increase in influence
        int j = 0;
        for (DiVertex candidate : candidates) {
            // assess with vertices are reachable from the candidate
            Set<DiVertex> verticesReachableViaCandidate = InfMaxUtils.determineReachableVerticesConsideringEdgeStatuses(candidate, edgeStatuses);
            // ⚠⚠⚠ not 100% sure if this is the correct way to assess increased influence, no details about it in the book ⚠⚠⚠
            // only count newly reachable vertices
            verticesReachableViaCandidate.removeAll(verticesReachableViaSeedsInH);
            numberOfReachableNodes[j] += verticesReachableViaCandidate.size();
            j++;
        }
    }

    private void clearDatastructures() {
        this.seedsWithMaxInfluence.clear();
    }
}
