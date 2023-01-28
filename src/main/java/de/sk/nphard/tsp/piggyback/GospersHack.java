package de.sk.nphard.tsp.piggyback;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Implementation of {@link SubsetGenerator} using Gosper's hack to create all {@code k}-subsets of an {@code n}-set in linear time.
 * <br><br>
 * Explanation of Gosper's hack:
 * <a href="http://programmingforinsomniacs.blogspot.com/2018/03/gospers-hack-explained.html">Gosper's hack explained.</a>
 */
public class GospersHack implements SubsetGenerator {

    static final String K_MUST_NOT_BE_GREATER_N_EXCEPTION_MSG_TF = "The k ('%d') of a k-subset cannot be greater than the " +
            "n ('%d') of its n-set.";

    @Override
    public @NotNull List<Integer> generateAllKSubsetsOfAnNSetAsInts(int k, int n) {
        this.validateInput(k, n);
        List<Integer> indices = new ArrayList<>();
        int set = (1 << k) - 1;
        int limit = (1 << n);
        while (set < limit) {
            indices.add(set);
            // Gosper's hack
            int c = set & -set;
            int r = set + c;
            set = (((r ^ set) >> 2) / c) | r;
        }
        return indices;
    }


    @Override
    public @NotNull List<BitSet> generateAllKSubsetsOfAnNSetAsBitSets(int k, int n) {
        this.validateInput(k, n);
        List<BitSet> indices = new ArrayList<>();
        int set = (1 << k) - 1;
        int limit = (1 << n);
        while (set < limit) {
            indices.add(BitSet.valueOf(new long[]{set}));
            // Gosper's hack
            int c = set & -set;
            int r = set + c;
            set = (((r ^ set) >> 2) / c) | r;
        }
        return indices;
    }

    private void validateInput(int k, int n) {
        if (k > n) {
            throw new IllegalArgumentException(String.format(K_MUST_NOT_BE_GREATER_N_EXCEPTION_MSG_TF, k, n));
        }
    }
}
