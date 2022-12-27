package de.sk.dynamicprogramming.knapsack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnapsackOptimizer {

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
