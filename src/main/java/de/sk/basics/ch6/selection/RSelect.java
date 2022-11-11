package de.sk.basics.ch6.selection;

import util.SortingUtils;
import de.sk.basics.ch5.quicksort.partition.Partitioner;
import de.sk.basics.ch5.quicksort.pivot.PivotChooser;
import de.sk.basics.injection.InjectionConstants;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Implementation of {@link Selector} applying the RSelect algorithm (O(n) best and avg case, O(n²) worst case).
 */
public class RSelect extends ArgumentValidatingSelector {

    @Inject @Named(InjectionConstants.RANDOM_PIVOT_CHOOSER)
    private PivotChooser pivotChooser;

    @Inject
    private Partitioner partitioner;

    @Override
    public int select(int @NotNull [] array, int ithOrderStatistics) {
        validateSelectMethodArguments(array, ithOrderStatistics);
        ithOrderStatistics -= 1; // adjust ithOrderStatistics (= i-th-smallest element) for 0-based array index
        return recRSelect(array, ithOrderStatistics, 0, array.length - 1);
    }

    private int recRSelect(int @NotNull [] array, int ithOrderStatistics, int l, int r) {
        //// base case
        if (r - l <= 0) {
            return array[l];
        }
        //// recursive case
        // choose random pivot and determine its final position
        int chosenPivotIdx = pivotChooser.getPivotIndex(array, l, r);
        SortingUtils.swapElements(array, l, chosenPivotIdx); // put pivot as 1st element of (sub)array
        int idxOfPivot = partitioner.partition(array, l, r);

        int numOfElementsLeftPart = idxOfPivot - l;
        // check if we got lucky or further recursion is needed
        if (numOfElementsLeftPart == ithOrderStatistics) {
            // lucky case -> searched-for element was chosen pivot
            return array[idxOfPivot];
        } else if (numOfElementsLeftPart > ithOrderStatistics) {
            // else, recursively search first part of the array ...
            return recRSelect(array, ithOrderStatistics, l, idxOfPivot - 1);
        } else {
            // ... or second part of the array
            ithOrderStatistics -= (numOfElementsLeftPart + 1); // -> adjust ithOrderStatistics to account for pruned elements (= elements in left part + pivot)
            return recRSelect(array, ithOrderStatistics, idxOfPivot + 1, r);
        }
    }
}
