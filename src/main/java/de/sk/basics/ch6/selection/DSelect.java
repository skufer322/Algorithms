package de.sk.basics.ch6.selection;

import de.sk.basics.ch5.Sorter;
import de.sk.basics.ch5.quicksort.partition.Partitioner;
import de.sk.basics.injection.InjectionConstants;
import de.sk.util.MathUtils;
import de.sk.util.SortingUtils;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

/**
 * Implementation of {@link Selector} applying the O(n) DSelect algorithm.
 */
public class DSelect extends ArgumentValidatingSelector {

    static final String ELEMENT_NOT_CONTAINED_EXCEPTION_MSG_TEXT_FORMAT = "Element %d is not contained within the (sub)array bounds [%d;%d] of array %s.";

    static final int GROUP_SIZE = 5;

    @Inject
    @Named(InjectionConstants.INSERTION_SORT)
    private Sorter sorter;

    @Inject
    private Partitioner partitioner;

    @Override
    public int select(int @NotNull [] array, int ithOrderStatistics) {
        this.validateSelectMethodArguments(array, ithOrderStatistics);
        ithOrderStatistics -= 1; // adjust ithOrderStatistics (= i-th-smallest element) for 0-based array index
        return recDSelect(array, ithOrderStatistics, 0, array.length - 1);
    }

    private int recDSelect(int @NotNull [] array, int ithOrderStatistics, int l, int r) {
        //// base case
        if (r - l <= 0) {
            return array[l];
        }
        //// recursive case
        // determine median of medians
        int[] medians = determineMedians(array, l, r);
        int ithOrderStatisticsOfMedians = MathUtils.getCeilInteger(medians.length, 2);
        int medianOfMedians = recDSelect(medians, ithOrderStatisticsOfMedians, 0, medians.length - 1);
        int idxMedianOfMedians = getIndexOfElement(array, medianOfMedians, l, r);
        SortingUtils.swapElements(array, l, idxMedianOfMedians); // put median of medians as 1st element of (sub)array
        int idxOfPivot = partitioner.partition(array, l, r);

        int numOfElementsLeftPart = idxOfPivot - l;
        // check if we got lucky or further recursion is needed
        if (numOfElementsLeftPart == ithOrderStatistics) {
            // lucky case -> searched-for element was chosen pivot
            return array[idxOfPivot];
        } else if (numOfElementsLeftPart > ithOrderStatistics) {
            // recursively search first part of the array ...
            return recDSelect(array, ithOrderStatistics, l, idxOfPivot - 1);
        } else {
            // ... or second part of the array
            ithOrderStatistics -= numOfElementsLeftPart + 1; // -> adjust ithOrderStatistics to account for pruned elements (= elements in left part + pivot)
            return recDSelect(array, ithOrderStatistics, idxOfPivot + 1, r);
        }
    }

    private int @NotNull [] determineMedians(int @NotNull [] array, int l, int r) { // TODO auslagern in DSelectSubroutines?
        int numberOfElementsWithinBounds = r - l + 1;
        int numberOfGroups = MathUtils.getCeilInteger(numberOfElementsWithinBounds, GROUP_SIZE);
        int[] medians = new int[numberOfGroups];
        for (int groupIdx = 0; groupIdx < numberOfGroups; groupIdx++) {
            int subArrayStartIndex = l + groupIdx * GROUP_SIZE;
            int subArrayEndIndex = Math.min(subArrayStartIndex + GROUP_SIZE, r + 1);
            int[] currentSubArray = Arrays.copyOfRange(array, subArrayStartIndex, subArrayEndIndex);
            sorter.sort(currentSubArray);
            medians[groupIdx] = currentSubArray[currentSubArray.length / 2];
        }
        return medians;
    }

    private int getIndexOfElement(int @NotNull [] array, int element, int l, int r) { // TODO auslagern in DSelectSubroutines?
        for (int i = l; i <= r; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        throw new IllegalStateException(String.format(ELEMENT_NOT_CONTAINED_EXCEPTION_MSG_TEXT_FORMAT, element, l, r, Arrays.toString(array)));
    }
}
