package de.sk.basics.ch5.quicksort;

import de.sk.basics.ch5.Sorter;
import de.sk.basics.ch5.quicksort.partition.Partitioner;
import de.sk.basics.ch5.quicksort.pivot.PivotChooser;
import org.jetbrains.annotations.NotNull;
import util.SortingUtils;

import javax.inject.Inject;

/**
 * Implementation using QuickSort to sort the array.
 */
public class QuickSort implements Sorter {

    @Inject
    private PivotChooser pivotChooser;

    @Inject
    private Partitioner partitioner;

    @Override
    public int @NotNull [] sort(int @NotNull [] array) {
        recQuickSort(array, 0, array.length - 1);
        return array;
    }

    private void recQuickSort(int[] array, int l, int r) {
        //// base case
        if (l >= r) {
            return;
        }
        //// recursive case
        // get pivot
        int chosenPivotIdx = pivotChooser.getPivotIndex(array, l, r);
        // swap chosen pivot with leftmost element, such that the chosen pivot is now the leftmost element
        SortingUtils.swapElements(array, l, chosenPivotIdx);
        // partition array based on chosen pivot
        int pivotFinalIdx = partitioner.partition(array, l, r);
        recQuickSort(array, l, pivotFinalIdx - 1);
        recQuickSort(array, pivotFinalIdx + 1, r);
    }
}
