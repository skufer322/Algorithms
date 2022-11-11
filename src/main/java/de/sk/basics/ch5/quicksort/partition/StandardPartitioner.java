package de.sk.basics.ch5.quicksort.partition;

import util.SortingUtils;
import org.jetbrains.annotations.NotNull;

public class StandardPartitioner implements Partitioner {

    @Override
    public int partition(int @NotNull [] array, int l, int r) {
        int pivot = array[l];
        int i = l + 1;

        for (int j = i; j <= r; j++) {
            int currentElement = array[j];
            if (currentElement < pivot) {
                SortingUtils.swapElements(array, i, j);
                i++;
            }
        }

        int pivotFinalIdx = i - 1;
        // swap chosen pivot from leftmost position to its final position
        SortingUtils.swapElements(array, l, pivotFinalIdx);
        return pivotFinalIdx;
    }
}
