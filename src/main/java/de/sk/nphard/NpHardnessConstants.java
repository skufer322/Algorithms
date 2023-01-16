package de.sk.nphard;

/**
 * Class holding general constants related to algorithms for NP-hard problems.
 */
public class NpHardnessConstants {

    // permutation creators
    public static final String IN_PERMUTATION_ALG_HEAPS= "Permutation-Alg-Heaps";

    // TSP solvers
    public static final String IN_EXHAUSTIVE_CACHED_PERMUTATIONS_TSP_SOLVER = "Exhaustive-Search-Cached-Permutations-TSP-Solver";
    public static final String IN_EXHAUSTIVE_MEMORY_EFFICIENT_TSP_SOLVER = "Exhaustive-Search-Memory-Efficient-TSP-Solver";
    public static final String IN_NEAREST_NEIGHBOR_TSP_SOLVER = "Nearest-Neighbor-TSP-Solver";

    // MakeSpan Solvers
    public static final String IN_GRAHAMS_MAKE_SPAN_SOLVER = "Grahams-Alg-MakeSpan-Solver";
    public static final String IN_LPT_FIRST_MAKE_SPAN_SOLVER = "Longest-Processing-Time-First-MakeSpan-Solver";

    // Influence Maximization solvers
    public static final String IN_GREEDY_INFLUENCE_MAXIMIZATION_SOLVER = "Greedy-Influence-Maximization-Solver";
    public static final int GREEDY_INF_MAX_REPETITIONS = 1_000;
    public static final long GREEDY_INF_MAX_SEED_FOR_RANDOM = -5617441268317618110L;
}
