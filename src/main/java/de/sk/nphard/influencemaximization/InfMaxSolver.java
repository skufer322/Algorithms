package de.sk.nphard.influencemaximization;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Interface defining the methods for implementations solving the Influence Maximization problem. The determined solution
 * might be an approximate solutions since the Influence Maximization problem is NP-hard.
 */
public interface InfMaxSolver {

    /**
     * Determines the set of {@code k} seed vertices with maximum influence for the given graph (represented as adjacency list).
     * The edges in the cascade model are activated with probability {@code p}. The determined solution might only be an
     * approximate solution.
     *
     * @param adjacencyList graph for which the set of {@code k} seeds with maximum influence are to be determined
     * @param p probability that an edge in the graph is activated
     * @param k number of seed vertices in the solution
     * @return set of vertices with maximum influence for the given graph (might be an approximate solution)
     */
    @NotNull Set<DiVertex> maximizeInfluence(@NotNull DiAdjacencyList adjacencyList, double p, int k);
}
