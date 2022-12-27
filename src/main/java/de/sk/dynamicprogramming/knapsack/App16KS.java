package de.sk.dynamicprogramming.knapsack;

import java.util.Collection;
import java.util.List;

public class App16KS {

    public static void main(String[] args) {
        KnapsackItem i1 = new KnapsackItem(3, 4);
        KnapsackItem i2 = new KnapsackItem(2, 3);
        KnapsackItem i3 = new KnapsackItem(4, 2);
        KnapsackItem i4 = new KnapsackItem(4, 3);
        List<KnapsackItem> items = List.of(i1, i2, i3, i4);
        int capacity = 6;

        KnapsackOptimizer ks = new KnapsackOptimizer();
        Collection<KnapsackItem> optimumSolution = ks.determineOptimumItems(items, capacity);
        System.out.println(optimumSolution);
    }
}
