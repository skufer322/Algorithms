package de.sk.nphard.tsp.piggyback;

import org.jetbrains.annotations.NotNull;

import java.util.BitSet;
import java.util.List;

/**
 * Interface defining various methods to create all {@code k}-subsets (i.e. a subset of {@code k} elements) of an
 * {@code n}-set (i.e. a set of {@code n} elements), {@code n} ≥ {@code k}.
 * <br>
 * Defines various methods, each for which the returned single {@code k}-subsets are represented in a different way
 * (e.g. as {@link Integer}s or {@link BitSet}s).
 */
public interface SubsetGenerator {

    /**
     * Generates all {@code k}-subsets of an {@code n}-set, and returns these subsets as a list of {@link Integer}s. Each
     * {@link Integer}s corresponds to one {@code k}-subset.
     * <br>br>
     * For example, for an {@code n}=5-set of {1, 2, 3, 4, 5}, a possible {@code k}=3-set is {1, 3, 4}. Represented as a binary string,
     * this 3-set is 01101 (assuming the least significant bit comes last). If this binary string is interpreted as
     * an {@link Integer}, the set is 26 (2^1 + 2^3 + 2^4).
     *
     * @param k number of elements a subset is to include
     * @param n number of elements in the total set
     * @return all possible enumerations of {@code k}-subsets of the {@code n}-set, subsets represented as {@link Integer}s
     */
    @NotNull List<Integer> generateAllKSubsetsOfAnNSetAsInts(int k, int n);

    /**
     * Generates all {@code k}-subsets of an {@code n}-set, and returns these subsets as a list of {@link BitSet}s. Each
     * {@link BitSet}s corresponds to one {@code k}-subset.
     * <br><br>
     * For example, for for an {@code n}=5-set of {1, 2, 3, 4, 5}, one possible {@code k}=3-set is {1, 3, 4}. Represented
     * as a binary string, this 3-set is 01101 (assuming the least significant bit comes last), corresponding to the indices
     * {1, 3, 4} of a {@link BitSet}.
     *
     * @param k number of elements a subset is to include
     * @param n number of elements in the total set
     * @return all possible enumerations of {@code k}-subsets of the {@code n}-set, subsets represented as {@link BitSet}s
     */
    @NotNull List<BitSet> generateAllKSubsetsOfAnNSetAsBitSets(int k, int n);
}
