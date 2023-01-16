package de.sk.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for array-related methods which are not featured in common array utility classes.
 */
public final class AdditionalArrayUtils {

    private AdditionalArrayUtils() {
        // only utilities
    }

    /**
     * Determines and returns the indices of the largest element contained in {@code values}. If there are no ties
     * for the largest element, the returned list contains only one index.
     *
     * @param values array of values for which the indices of the largest element are to be determined
     * @return list with the indices of the largest element in {@code values} (list of one if there are no ties for the largest element)
     */
    public static @NotNull List<Integer> indicesOfLargestElement(long @NotNull [] values) {
        long largestValue = Long.MIN_VALUE;
        List<Integer> indicesOfLargestElements = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            long value = values[i];
            if (value > largestValue) {
                largestValue = value;
                indicesOfLargestElements.clear();
                indicesOfLargestElements.add(i);
            } else if (value == largestValue) {
                indicesOfLargestElements.add(i);
            }
        }
        return indicesOfLargestElements;
    }
}
