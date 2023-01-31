package de.sk.nphard;

/**
 * Class holding general constants related to algorithms for NP-hard problems.
 */
public class NpHardnessConstants {

    // permutation creators
    public static final String IN_PERMUTATION_ALG_HEAPS = "Permutation-Alg-Heaps";

    // TSP solvers
    public static final String IN_EXHAUSTIVE_CACHED_PERMUTATIONS_TSP_SOLVER = "Exhaustive-Search-Cached-Permutations-TSP-Solver";
    public static final String IN_EXHAUSTIVE_MEMORY_EFFICIENT_TSP_SOLVER = "Exhaustive-Search-Memory-Efficient-TSP-Solver";
    public static final String IN_NEAREST_NEIGHBOR_TSP_SOLVER = "Nearest-Neighbor-TSP-Solver";
    public static final String IN_2OPT_HEURISTIC_TSP_SOLVER = "2OPT-Heuristic-TSP-Solver";
    public static final String IN_RANDOM_TSP_SOLVER = "Random-TSP-Solver";
    public static final String IN_BELLMAN_HELD_KARP_TSP_SOLVER = "Bellman-Held-Karp-TspSolver";

    // MakeSpan Solvers
    public static final String IN_GRAHAMS_MAKE_SPAN_SOLVER = "Grahams-Alg-MakeSpan-Solver";
    public static final String IN_LPT_FIRST_MAKE_SPAN_SOLVER = "Longest-Processing-Time-First-MakeSpan-Solver";

    // Influence Maximization solvers
    public static final String IN_GREEDY_INFLUENCE_MAXIMIZATION_SOLVER = "Greedy-Influence-Maximization-Solver";
    public static final int PV_GREEDY_INFLUENCE_MAXIMIZATION_REPETITIONS = 10_000;
    public static final long PV_GREEDY_INFLUENCE_MAXIMIZATION_SEED_FOR_RANDOM = -5617441268317618110L;

    // Shortest K-Path Solvers
    public static final String IN_RANDOMIZED_COLOR_CODER_SHORTEST_K_PATH_SOLVER = "Randomized-Color-Coder-Shortest-K-Path-Solver";

    // Panchromatic Path Solvers
    public static final String IN_DP_PANCHROMATIC_PATHS_SOLVER = "Dynamic-Programming-Panchromatic-Path-Solver";

    // Subset Generators
    public static final String IN_GOSPERS_HACK_SUBSET_GENERATOR = "Gosper's-Hack-SubsetGenerator";

    // Graph Colorists
    public static final String IN_UNIFORM_GRAPH_COLORIST = "Uniform-Graph-Colorist";

    // Names and Values for Seeds
    public static final String IN_SEED_FOR_RANDOM_IN_RANDOM_TSP_SOLVER = "Seed-for-Random-in-Random-TspSolver";
    public static final long PV_SEED_FOR_RANDOM_IN_RANDOM_TSP_SOLVER = 1532359670133886118L;
    public static final String IN_SEED_FOR_RANDOM_IN_UNIFORM_GRAPH_COLORIST = "Seed-for-Random-in-Uniform-Graph-Colorist";
    public static final long PV_SEED_FOR_RANDOM_IN_UNIFORM_GRAPH_COLORIST = -2478585821527133343L;

    // other Injection-Related Names and Values
    public static final String IN_FAILURE_PROBABILITY_FOR_RANDOMIZED_COLOR_CODER_SHORTEST_K_PATH_SOLVER = "Failure-Probability-Randomized-Color-Coder-Shortest-K-Path-Solver";
    public static final double PV_FAILURE_PROBABILITY_FOR_RANDOMIZED_COLOR_CODER_SHORTEST_K_PATH_SOLVER = 0.1d;  // ]0;1[
}
