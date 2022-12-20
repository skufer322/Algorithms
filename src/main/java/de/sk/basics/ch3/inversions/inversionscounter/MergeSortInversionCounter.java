package de.sk.basics.ch3.inversions.inversionscounter;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * O(n log n) implementation of {@link InversionCounter}, piggybacking the MergeSort algorithm.
 */
public class MergeSortInversionCounter implements InversionCounter {

    @Override
    public long countInversions(int @NotNull [] array) {
        Pair<int[], Long> arrayAndInversions = new ImmutablePair<>(array, 0L);
        int n = array.length;
        arrayAndInversions = recCountInversions(arrayAndInversions, n);
        return arrayAndInversions.getRight();
    }

    private @NotNull Pair<int[], Long> recCountInversions(@NotNull Pair<int[], Long> arrayAndInversions, int n) {
        // check base case
        if (n < 2) {
            return arrayAndInversions;
        }
        // divide: get array and split into left and right halves
        int[] array = arrayAndInversions.getKey();
        int mid = n / 2;
        int[] leftHalf = Arrays.copyOfRange(array, 0, mid);
        int[] rightHalf = Arrays.copyOfRange(array, mid, n);
        // conquer: solve problem recursively
        Pair<int[], Long> leftHalfAndInversions = recCountInversions(new ImmutablePair<>(leftHalf, 0L), leftHalf.length);
        Pair<int[], Long> rightHalfAndInversions = recCountInversions(new ImmutablePair<>(rightHalf, 0L), rightHalf.length);
        // merge: unify partial results
        return mergeAndCountInversions(leftHalfAndInversions, rightHalfAndInversions);
    }

    private @NotNull Pair<int[], Long> mergeAndCountInversions(Pair<int[], Long> leftHalfAndInversions, Pair<int[], Long> rightHalfAndInversions) {
        // get the number of inversions which occurred so far
        long inversions = leftHalfAndInversions.getValue() + rightHalfAndInversions.getValue();
        // get halves which shall be merged
        int[] leftHalf = leftHalfAndInversions.getKey();
        int[] rightHalf = rightHalfAndInversions.getKey();

        // create array to return
        int k = leftHalf.length + rightHalf.length;
        int[] whole = new int[k];
        // merge and sort halves into array while counting inversions
        int l = 0, r = 0;
        for (int i = 0; i < k; i++) {
            // no half has reached its end yet
            if (l < leftHalf.length && r < rightHalf.length) {
                if (leftHalf[l] <= rightHalf[r]) {
                    whole[i] = leftHalf[l];
                    l++;
                } else {
                    whole[i] = rightHalf[r];
                    inversions += leftHalf.length - l;
                    r++;
                }
            // left half has reached its end
            } else if (l == leftHalf.length) {
                whole[i] = rightHalf[r];
                r++;
            // right half has reached its end
            } else {
                whole[i] = leftHalf[l];
                l++;
            }
        }

        return new ImmutablePair<>(whole, inversions);
    }
}
