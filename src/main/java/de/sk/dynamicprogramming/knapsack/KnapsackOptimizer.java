package de.sk.dynamicprogramming.knapsack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of a dynamic programming algorithm to solve the Knapsack problem for a set of values with sizes and values.
 */
public class KnapsackOptimizer {

    /**
     * Determines the optimal sub-selection of items from the given list of {@code items}, considering the given {@code capacity}.
     * Each item has an associated size and value. The optimal sub-selection of items is the collection of items with the
     * maximum possible value fitting into a knapsack of the given {@code capacity}.
     *
     * @param items list of items for which the optimal sub-selection of item is to be determined
     * @param capacity maximum capacity of the knapsack
     * @return optimal sub-selection of items maximising the value for the given knapsack {@code capacity}
     */
    public @NotNull Collection<KnapsackItem> determineOptimumItems(@NotNull List<KnapsackItem> items, int capacity) {
        int[][] optimumSolutions = this.calculateOptimumValueForEachSubProblems(items, capacity);
        return this.reconstructOptimumSolutionMembers(optimumSolutions, items);
    }

    private int @NotNull [][] calculateOptimumValueForEachSubProblems(@NotNull List<KnapsackItem> items, int capacity) {
        int[][] optimumSolutions = new int[items.size() + 1][capacity + 1];
        // base case
        for (int c = 0; c < capacity; c++) {
            optimumSolutions[0][c] = 0;
        }
        // solve sub problems, from smaller to larger
        for (int c = 0; c <= capacity; c++) {
            for (int i = 1; i <= items.size(); i++) {
                KnapsackItem currentItem = items.get(i - 1);
                int sizeOfCurrentItem = currentItem.size();
                if (sizeOfCurrentItem > c) {
                    optimumSolutions[i][c] = optimumSolutions[i - 1][c];
                } else {
                    optimumSolutions[i][c] = Math.max(optimumSolutions[i - 1][c], optimumSolutions[i - 1][c - sizeOfCurrentItem] + currentItem.value());
                }
            }
        }
        return optimumSolutions;
    }

    private @NotNull Collection<KnapsackItem> reconstructOptimumSolutionMembers(int @NotNull [][] optimumSolutions, @NotNull List<KnapsackItem> items) {
        List<KnapsackItem> solution = new ArrayList<>();
        int i = items.size();
        int residualC = optimumSolutions[0].length - 1;
        while (i >= 1) {
            KnapsackItem currentItem = items.get(i - 1);
            int sizeOfCurrentItem = currentItem.size();
            if (sizeOfCurrentItem <= residualC &&
                    optimumSolutions[i - 1][residualC - sizeOfCurrentItem] + currentItem.value() >= optimumSolutions[i - 1][residualC]
            ) {
                solution.add(currentItem); // include current item
                residualC -= sizeOfCurrentItem;
            } // else skip current item
            i--;
        }
        return solution;
    }
}
