package de.sk.nphard;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import de.sk.nphard.influencemaximization.GreedySamplingInfMaxSolver;
import de.sk.nphard.influencemaximization.InfMaxSolver;
import de.sk.nphard.kpaths.RandomizedColorCoder;
import de.sk.nphard.kpaths.ShortestKPathSolver;
import de.sk.nphard.kpaths.colorize.GraphColorist;
import de.sk.nphard.kpaths.colorize.UniformGraphColorist;
import de.sk.nphard.kpaths.panchromatic.DPPanchromaticPathSolver;
import de.sk.nphard.kpaths.panchromatic.PanchromaticPathSolver;
import de.sk.nphard.makespan.GrahamsAlg;
import de.sk.nphard.makespan.LongestProcessingTimeFirstAlg;
import de.sk.nphard.makespan.MakeSpanSolver;
import de.sk.nphard.tsp.TspSolver;
import de.sk.nphard.tsp.impl.*;
import de.sk.nphard.tsp.piggyback.GospersHack;
import de.sk.nphard.tsp.piggyback.HeapsPermutationAlg;
import de.sk.nphard.tsp.piggyback.PermutationAlg;
import de.sk.nphard.tsp.piggyback.SubsetGenerator;

public class NpHardInjectionModule extends AbstractModule {

    @Override
    protected void configure() {
        // Permutation Creators
        bind(PermutationAlg.class).to(HeapsPermutationAlg.class);
        bind(PermutationAlg.class).annotatedWith(Names.named(NpHardnessConstants.IN_PERMUTATION_ALG_HEAPS)).to(HeapsPermutationAlg.class);

        // TSP solvers
        bind(TspSolver.class).to(ExhaustiveSearchLowMemoryTspSolver.class);
        bind(TspSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_EXHAUSTIVE_CACHED_PERMUTATIONS_TSP_SOLVER)).to(ExhaustiveSearchTspSolver.class);
        bind(TspSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_EXHAUSTIVE_MEMORY_EFFICIENT_TSP_SOLVER)).to(ExhaustiveSearchLowMemoryTspSolver.class);
        bind(TspSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_NEAREST_NEIGHBOR_TSP_SOLVER)).to(NearestNeighborTspSolver.class);
        bind(TspSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_2OPT_HEURISTIC_TSP_SOLVER)).to(TwoOptHeuristicTspSolver.class);
        // binding construction a -> compare with b
        bind(long.class).annotatedWith(Names.named(NpHardnessConstants.IN_SEED_FOR_RANDOM_IN_RANDOM_TSP_SOLVER)).toInstance(NpHardnessConstants.PV_SEED_FOR_RANDOM_IN_RANDOM_TSP_SOLVER);
        bind(TspSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_RANDOM_TSP_SOLVER)).to(RandomTspSolver.class);
        bind(TspSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_BELLMAN_HELD_KARP_TSP_SOLVER)).to(BellmanHeldKarpTspSolver.class);

        // MakeSpan solvers
        bind(MakeSpanSolver.class).to(LongestProcessingTimeFirstAlg.class);
        bind(MakeSpanSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_GRAHAMS_MAKE_SPAN_SOLVER)).to(GrahamsAlg.class);
        bind(MakeSpanSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_LPT_FIRST_MAKE_SPAN_SOLVER)).to(LongestProcessingTimeFirstAlg.class);

        // Influence Maximization solvers
        bind(int.class).toInstance(NpHardnessConstants.PV_GREEDY_INFLUENCE_MAXIMIZATION_REPETITIONS);
        // binding construction b -> compare with a
        bind(long.class).toInstance(NpHardnessConstants.PV_GREEDY_INFLUENCE_MAXIMIZATION_SEED_FOR_RANDOM);
        try {
            bind(InfMaxSolver.class).toConstructor(GreedySamplingInfMaxSolver.class.getConstructor(Integer.TYPE, Long.TYPE));
            bind(InfMaxSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_GREEDY_INFLUENCE_MAXIMIZATION_SOLVER)).toConstructor(GreedySamplingInfMaxSolver.class.getConstructor(Integer.TYPE, Long.TYPE));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // Shortest K-Path Solvers
        bind(ShortestKPathSolver.class).to(RandomizedColorCoder.class);
        bind(ShortestKPathSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_RANDOMIZED_COLOR_CODER_SHORTEST_K_PATH_SOLVER)).to(RandomizedColorCoder.class);

        // Panchromatic Path Solvers
        bind(PanchromaticPathSolver.class).to(DPPanchromaticPathSolver.class);
        bind(PanchromaticPathSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_DP_PANCHROMATIC_PATHS_SOLVER)).to(DPPanchromaticPathSolver.class);

        // Subset Generators
        bind(SubsetGenerator.class).to(GospersHack.class);
        bind(SubsetGenerator.class).annotatedWith(Names.named(NpHardnessConstants.IN_GOSPERS_HACK_SUBSET_GENERATOR)).to(GospersHack.class);

        // Graph Colorists
        bind(GraphColorist.class).to(UniformGraphColorist.class);
        bind(long.class).annotatedWith(Names.named(NpHardnessConstants.IN_SEED_FOR_RANDOM_IN_UNIFORM_GRAPH_COLORIST)).toInstance(NpHardnessConstants.PV_SEED_FOR_RANDOM_IN_UNIFORM_GRAPH_COLORIST);
        bind(double.class).annotatedWith(Names.named(NpHardnessConstants.IN_FAILURE_PROBABILITY_FOR_RANDOMIZED_COLOR_CODER_SHORTEST_K_PATH_SOLVER)).toInstance(NpHardnessConstants.PV_FAILURE_PROBABILITY_FOR_RANDOMIZED_COLOR_CODER_SHORTEST_K_PATH_SOLVER);
        bind(GraphColorist.class).annotatedWith(Names.named(NpHardnessConstants.IN_UNIFORM_GRAPH_COLORIST)).to(UniformGraphColorist.class);
    }
}
