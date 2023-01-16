package de.sk.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

/**
 * Utility class supplying utility methods for operations with or on {@link BitSet}s.
 */
public final class BitSetUtils {

    static final String EMPTY_COLLECTION_OF_BIT_SETS_EXCEPTION_MSG = "Cannot determine largest BitSet from empty collection of Bitsets.";

    private BitSetUtils() {
        // only utils
    }

    /**
     * Determines the largest {@link BitSet}s from the given collection, i.e. the {@link BitSet}s with the largest
     * cardinality/number of bits set to {@code true}. All the largest {@link BitSet}s are contained in the
     * returned list.
     *
     * @param bitSets collection of {@link BitSet}s from which the largest one are to be determined
     * @return list containing all of the collection's largest {@link BitSet}s
     */
    public static @NotNull List<BitSet> determineLargestBitSets(@NotNull Collection<BitSet> bitSets) {
        if (bitSets.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_COLLECTION_OF_BIT_SETS_EXCEPTION_MSG);
        }
        int greatestNumberOfSetBits = Integer.MIN_VALUE;
        List<BitSet> largestBitSets = new ArrayList<>(); // init result list
        for (BitSet bitSet : bitSets) {
            if (bitSet.cardinality() > greatestNumberOfSetBits) {
                // bitSet is new single largest greatest BitSet -> clear result list and add only bitSet
                greatestNumberOfSetBits = bitSet.cardinality();
                largestBitSets.clear();
                largestBitSets.add(bitSet);
            } else if (bitSet.cardinality() == greatestNumberOfSetBits) {
                // cardinality of bitSet is tied with largest BitSet(s) so far -> add bitSet to result list
                largestBitSets.add(bitSet);
            }
        }
        return largestBitSets;
    }
}
