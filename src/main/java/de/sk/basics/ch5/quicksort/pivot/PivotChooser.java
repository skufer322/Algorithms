package de.sk.basics.ch5.quicksort.pivot;

import org.jetbrains.annotations.NotNull;

/**
 * Interface defining the methods for implementations of different strategies for choosing pivot elements from a (sub)array.
 */
public interface PivotChooser {

    /**
     * Returns the index of the element from the (sub){@code array} which is selected to be the pivot. Only elements between
     * the left and right bound ({@code l} and {@code r}) are considered.
     *
     * @param array array to select the pivot from
     * @param l left bound for considerable elements
     * @param r right bound for considerable elements
     * @return index of the selected pivot element
     */
    int getPivotIndex(int @NotNull [] array, int l, int r);
}
