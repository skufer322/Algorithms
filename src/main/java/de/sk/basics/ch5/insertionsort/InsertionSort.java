package de.sk.basics.ch5.insertionsort;

import de.sk.basics.ch5.Sorter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Implementation using InsertionSort to sort the array.
 */
public class InsertionSort implements Sorter {

    @Override
    public int @NotNull [] sort(int @NotNull [] array) {
        // TODO: real implementation of InsertionSort
        Arrays.sort(array);
        return array;
    }
}
