package de.sk.nphard;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
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
    }
}
