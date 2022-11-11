package de.sk.basics.ch3.inversions.inversionscounter;

import org.jetbrains.annotations.NotNull;

/**
 * Defines the interface for implementations counting the number of inversions in an array.
 */
public interface InversionCounter {

    /**
     * Counts the inversions in the given array and returns the count.
     *
     * @param array array for which the number of inversions shall be counted
     * @return number of inversions in the given array
     */
    long countInversions(int @NotNull [] array);
}