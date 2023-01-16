package de.sk.nphard.maximumcoverage;

import de.sk.util.BitSetUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Greedy implementation of {@link MaxCoverageSolver}, leading to possibly only approximately correct solutions.
 * However, the solutions are to be guaranteed to cover at least a fraction of 1 - (1 - k)^k of the optimal solution.
 * Time complexity: O(kms), where s is the maximum size of a subset.
 */
public class GreedyCoverage implements MaxCoverageSolver {

    static final String K_MUST_BE_GREATER_EQUAL_1_EXCEPTION_MSG_TF = "k must be greater/equal 1. Given k: %d";

    private final Set<BitSet> selectedSubsets = new HashSet<>();
    private final BitSet coverage = new BitSet();
    private final Set<BitSet> candidates = new HashSet<>();

    @Override
    public @NotNull Set<BitSet> determineMaximumCoverage(@NotNull Set<BitSet> subsets, int k, @NotNull Random random) {
        if (k < 1) {
            throw new IllegalArgumentException(String.format(K_MUST_BE_GREATER_EQUAL_1_EXCEPTION_MSG_TF, k));
        }
        this.clearDatastructures();
        this.candidates.addAll(subsets);
        // initially, simply select largest subset
        List<BitSet> largestSubsets = BitSetUtils.determineLargestBitSets(subsets);
        BitSet selectedSubset = largestSubsets.get(random.nextInt(largestSubsets.size())); // break possible ties arbitrarily
        this.addSubsetToSolution(selectedSubset);
        // next, out of the remaining subsets, add the subset maximizing the coverage until k subsets are selected
        for (int i = 1; i < k; i++) {
            List<BitSet> subsetsMaximizingCoverage = this.determineBitSetsMaximizingCoverage();
            selectedSubset = subsetsMaximizingCoverage.get(random.nextInt(subsetsMaximizingCoverage.size())); // break possible ties arbitrarily
            this.addSubsetToSolution(selectedSubset);
        }
        return new HashSet<>(this.selectedSubsets);
    }

    private void addSubsetToSolution(@NotNull BitSet selectedSubset) {
        this.selectedSubsets.add(selectedSubset);
        this.coverage.or(selectedSubset);
        this.candidates.remove(selectedSubset);
    }

    private @NotNull List<BitSet> determineBitSetsMaximizingCoverage() {
        int maxCoverage = Integer.MIN_VALUE;
        List<BitSet> bitSetsMaximizingCoverage = new ArrayList<>();
        BitSet cloneOfCandidate;
        for (BitSet candidate : this.candidates) {
            // we don't want to alter the candidate itself, so clone it before making the required BitSet operations
            cloneOfCandidate = (BitSet) candidate.clone();
            // check by how much the candidate would increase the current
            // coverage
            cloneOfCandidate.andNot(this.coverage);
            int additionalCoverageOfCandidate = cloneOfCandidate.cardinality();
            if (additionalCoverageOfCandidate > maxCoverage) {
                // candidate is the new single subset maximizing the coverage -> clear result list and add only candidate
                maxCoverage = additionalCoverageOfCandidate;
                bitSetsMaximizingCoverage.clear();
                bitSetsMaximizingCoverage.add(candidate);
            } else if (additionalCoverageOfCandidate == maxCoverage) {
                // additional coverage of candidate is tied with some other subsets -> add candidate to result list
                bitSetsMaximizingCoverage.add(candidate);
            }
        }
        return bitSetsMaximizingCoverage;
    }

    private void clearDatastructures() {
        this.selectedSubsets.clear();
        this.coverage.clear();
        this.candidates.clear();
    }
}
