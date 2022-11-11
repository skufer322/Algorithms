package de.sk.basics.ch3.inversions;

import de.sk.basics.ch3.inversions.inversionscounter.InversionCounter;
import de.sk.basics.ch3.inversions.inversionscounter.MergeSortInversionCounter;

import java.util.Arrays;

public class App2I {

    public static void main(String[] args) {
        // create data for inversion counting
        int[] array = new int[]{1, 3, 5, 2, 4, 6};
//        int[] array = new int[]{2, 1};
        // create inversion counter
        InversionCounter inversionCounter = new MergeSortInversionCounter();
        // count inversions
        System.out.println("array to count inversions for: " + Arrays.toString(array));
        long inversions = inversionCounter.countInversions(array);

        // print results
        System.out.println("sorted array: " + Arrays.toString(array));
        System.out.println("number of inversions: " + inversions);
    }
}
