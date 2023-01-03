package de.sk.basics.ch5;

import de.sk.basics.ch5.insertionsort.InsertionSort;

import java.util.Arrays;

public class App5QS {

    public static void main(String[] args) {
        // setup guice dependency injection
//        Injector injector = Guice.createInjector(new BasicsInjectionModule());
//        QuickSort qs = injector.getInstance(QuickSort.class);
//
//        int n = 10;
//        Random random = new Random(1337L);
//        int[] array = SortingUtils.createArrayWithoutDuplicates(n, random);
//        System.out.println("unsorted: " + Arrays.toString(array));
//        qs.sort(array);
//        System.out.println("sorted: " + Arrays.toString(array));

        InsertionSort insertionSort = new InsertionSort();
        int[] array = new int[]{5, 7, 1, 4, 9, 2};
        System.out.println(Arrays.toString(insertionSort.sort(array)));
    }
}
