package de.sk.util;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Utility class for elementary operations related or required to solve the sorting problem.
 */
public final class SortingUtils {

    private static final Random RANDOM = ThreadLocalRandom.current();

    private SortingUtils() {
        // only utility methods
    }

    /**
     * Creates an integer array of size n with integers from 1 to n, no duplicates.
     * The returned array is in random order (by shuffling the array before returning it).
     *
     * @param n size of the array to create
     * @return randomly ordered integer array of size n with integers from 1 to n, no duplicates
     */
    public static int[] createArrayWithoutDuplicates(int n) {
        int[] array = IntStream.range(1, n + 1).toArray();
        SortingUtils.shuffleArray(array, RANDOM);
        return array;
    }

    /**
     * Creates an integer array of size n with integers from 1 to n, no duplicates.
     * The returned array is in random order (by shuffling the array before returning it).
     * The passed Random is used for shuffling.
     *
     * @param n size of the array to create
     * @param random Random to shuffle the array
     * @return randomly ordered integer array of size n with integers from 1 to n, no duplicates, shuffled by the passed Random
     */
    public static int[] createArrayWithoutDuplicates(int n, @NotNull Random random) {
        int[] array = IntStream.range(1, n + 1).toArray();
        SortingUtils.shuffleArray(array, random);
        return array;
    }

    // https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array -> Fisher–Yates shuffle
    private static void shuffleArray(int @NotNull [] array, @NotNull Random random) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // simple swap
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    /**
     * Swaps the two elements specified with the indexes i and j for the given array.
     *
     * @param array array for which the two specified elements shall be swapped
     * @param i     position of the first element to swap
     * @param j     position of the second element to swap
     */
    public static void swapElements(int @NotNull [] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
