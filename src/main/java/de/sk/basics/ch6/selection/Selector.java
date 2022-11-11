package de.sk.basics.ch6.selection;

import org.jetbrains.annotations.NotNull;

/**
 * Interface defining the methods for implementations solving the selection problem (selecting the i-th-largest element
 * or i-th-order statistics from an array).
 */
public interface Selector {

    /**
     * Returns the i-th-largest element/i-th-order statistics for the given array.
     * @param array array
     * @param ithOrderStatistics specification which element to return (the i-th-largest element)
     * @return the i-th-largest element of the array
     */
    int select(int @NotNull [] array, int ithOrderStatistics);
}
