package de.sk.basics.ch5.insertionsort;

import de.sk.basics.ch5.Sorter;
import de.sk.util.SortingUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Implementation using InsertionSort to sort the array (O(n) best case, O(n²) avg and worst case).
 */
public class InsertionSort implements Sorter {

    @Override
    public int @NotNull [] sort(int @NotNull [] array) {
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j >= 1; j--) {
                if (array[j] < array[j - 1]) {
                    SortingUtils.swapElements(array, j, j - 1);
                } else {
                    break;
                }
            }
        }
        return array;
    }
}
