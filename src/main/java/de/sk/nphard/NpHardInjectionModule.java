package de.sk.nphard;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import de.sk.nphard.influencemaximization.GreedySamplingInfMaxSolver;
import de.sk.nphard.influencemaximization.InfMaxSolver;
import de.sk.nphard.makespan.GrahamsAlg;
import de.sk.nphard.makespan.LongestProcessingTimeFirstAlg;
import de.sk.nphard.makespan.MakeSpanSolver;
import de.sk.nphard.tsp.*;

public class NpHardInjectionModule extends AbstractModule {

    @Override
    protected void configure() {
        // permutation creators
        bind(PermutationAlg.class).to(HeapsPermutationAlg.class);
        bind(PermutationAlg.class).annotatedWith(Names.named(NpHardnessConstants.IN_PERMUTATION_ALG_HEAPS)).to(HeapsPermutationAlg.class);

        // TSP solvers
        bind(TspSolver.class).to(ExhaustiveSearchLowMemoryTspSolver.class);
        bind(TspSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_EXHAUSTIVE_CACHED_PERMUTATIONS_TSP_SOLVER)).to(ExhaustiveSearchTspSolver.class);
        bind(TspSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_EXHAUSTIVE_MEMORY_EFFICIENT_TSP_SOLVER)).to(ExhaustiveSearchLowMemoryTspSolver.class);
        bind(TspSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_NEAREST_NEIGHBOR_TSP_SOLVER)).to(NearestNeighborTspSolver.class);

        // MakeSpan solvers
        bind(MakeSpanSolver.class).to(LongestProcessingTimeFirstAlg.class);
        bind(MakeSpanSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_GRAHAMS_MAKE_SPAN_SOLVER)).to(GrahamsAlg.class);
        bind(MakeSpanSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_LPT_FIRST_MAKE_SPAN_SOLVER)).to(LongestProcessingTimeFirstAlg.class);

        // Influence Maximization solvers
        bind(Integer.class).toInstance(NpHardnessConstants.GREEDY_INF_MAX_REPETITIONS);
        bind(Long.class).toInstance(NpHardnessConstants.GREEDY_INF_MAX_SEED_FOR_RANDOM);
        try {
            bind(InfMaxSolver.class).toConstructor(GreedySamplingInfMaxSolver.class.getConstructor(Integer.TYPE, Long.TYPE));
            bind(InfMaxSolver.class).annotatedWith(Names.named(NpHardnessConstants.IN_GREEDY_INFLUENCE_MAXIMIZATION_SOLVER)).toConstructor(GreedySamplingInfMaxSolver.class.getConstructor(Integer.TYPE, Long.TYPE));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
