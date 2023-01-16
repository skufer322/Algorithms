package de.sk.nphard.tsp;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Interface defining the methods of implementations creating all different permutations of specific inputs.
 */
public interface PermutationAlg {

    /**
     * Creates all possible permutations of {@code elements}, and returns them as a list
     * of size {@code elements.length}! (note the '!', i.e. faculty).
     *
     * @param elements array for which all possible permutations are to be created
     * @return list of all possible permutations for the given array
     */
    @NotNull List<int[]> createAllPermutations(int @NotNull [] elements);
}
