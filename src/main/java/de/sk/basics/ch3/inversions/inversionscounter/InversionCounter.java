package de.sk.basics.ch3.inversions.inversionscounter;

import org.jetbrains.annotations.NotNull;

/**
 * Defines the interface for implementations counting the number of inversions in an array.
 */
public interface InversionCounter {

    /**
     * Counts the number of inversions in the given {@code array} and returns the count.
     *
     * @param array array for which the number of inversions are to be counted
     * @return number of inversions in the given {@code array}
     */
    long countInversions(int @NotNull [] array);
}
