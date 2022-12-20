package de.sk.basics.ch6.selection;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Implementation if {@link Selector} reducing the selection problem to sorting (-> O(n log n) runtime).
 */
public class SortBasedSelect extends ArgumentValidatingSelector {

    @Override
    public int select(int @NotNull [] array, int ithOrderStatistics) {
        this.validateSelectMethodArguments(array, ithOrderStatistics);
        Arrays.sort(array);
        // account for 0-based index in Java -> i-th-largest element is at index (ithOrderStatistics - 1)
        return array[ithOrderStatistics - 1];
    }
}
