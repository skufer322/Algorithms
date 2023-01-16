package de.sk.nphard.maximumcoverage;

import org.jetbrains.annotations.NotNull;

import java.util.BitSet;
import java.util.Random;
import java.util.Set;

/**
 * Interface defining the methods for implementations solving the Maximum Coverage problem. The determined solution
 * might be an approximate solutions since the Maximum Coverage problem is NP-hard.
 */
public interface MaxCoverageSolver {

    /**
     * For the given {@code subsets} of ground units, determines the {@code k} subsets maximizing the coverage
     * of ground units and returns these subsets in a {@link Set}. The given {@code random} is required for making
     * random decisions during processing, e.g. solving ties of equally "good" solutions arbitrarily.
     * <br><br>
     * The determined solution might be an approximate solution since the Maximum Coverage problem is NP-hard.
     *
     * @param subsets set of ground unit subsets out of which the {@code k} subsets leading to the Maximum Coverage are to be determined
     * @param k number of subsets in the solution
     * @param random random number generator used to make random decisions during processing
     * @return {@code k} subsets maximizing the coverage (might be an approximate solution)
     */
    @NotNull Set<BitSet> determineMaximumCoverage(@NotNull Set<BitSet> subsets, int k, @NotNull Random random);
}
