package de.sk.basics.ch5.quicksort.pivot;

import org.jetbrains.annotations.NotNull;

/**
 * Implementation of {@link PivotChooser} simply selecting the leftmost element of the given (sub)array.
 */
public class SimplePivotChooser implements PivotChooser {

    @Override
    public int getPivotIndex(int @NotNull [] array, int l, int r) {
        return l;
    }
}
