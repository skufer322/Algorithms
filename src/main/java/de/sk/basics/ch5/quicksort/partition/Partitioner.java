package de.sk.basics.ch5.quicksort.partition;

import org.jetbrains.annotations.NotNull;

/**
 * Interface defining methods for implementations of the partitioning subroutine.
 */
public interface Partitioner {

    /**
     * Partitions the given (sub){@code array} (specified per left and right bound, {@code l} and {@code r}) via a pivot.
     * It is assumed that the pivot is already at the leftmost position of the (sub){@code array}, i.e. at index {@code l}. After
     * partitioning, the pivot is at its final position, and all smaller (greater) elements are to the left (right) of the pivot.
     *
     * @param array (sub)array to partition
     * @param l left bound of the (sub)array
     * @param r right bound of the (sub)array
     * @return index of the pivot
     */
    int partition(int @NotNull [] array, int l, int r);
}
