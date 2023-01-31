package de.sk.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for array-related methods which are not featured in common array utility classes.
 */
public final class AdditionalArrayUtils {

    static final String CANNOT_DETERMINE_IDX_OF_SMALLEST_ELEMENT_OF_EMPTY_ARRAY_EXCEPTION_MSG = "Cannot determine the index of the smallest element of an empty array.";

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

    /**
     * Sets all elements in the given {@code matrix} to the given {@code value}.
     *
     * @param matrix matrix for which all elements are to bet set to {@code value}
     * @param value  value to set all elements of the {@code matrix} to
     */
    public static void setAllElementsOfMatrixToValue(int[] @NotNull [] matrix, int value) {
        for (int[] row : matrix) {
            Arrays.fill(row, value);
        }
    }

    /**
     * Determines and returns the index of the smallest element in the given {@code array}. If there are multiple
     * smallest elements, the index of the first of these elements is returned.
     *
     * @param array array for which the index of its smallest element is to be determined
     * @return index of the smallest element in the {@code array}
     */
    public static int idxOfSmallestElement(int @NotNull [] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException(CANNOT_DETERMINE_IDX_OF_SMALLEST_ELEMENT_OF_EMPTY_ARRAY_EXCEPTION_MSG);
        }
        int min = Integer.MAX_VALUE;
        int idxOfMin = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
                idxOfMin = i;
            }
        }
        return idxOfMin;
    }
}
