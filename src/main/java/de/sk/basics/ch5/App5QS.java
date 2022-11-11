package de.sk.basics.ch5;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.basics.ch5.quicksort.QuickSort;
import de.sk.basics.injection.BasicsInjectionModule;
import util.SortingUtils;

import java.util.Arrays;
import java.util.Random;

public class App5QS {

    public static void main(String[] args) {
        // setup guice dependency injection
        Injector injector = Guice.createInjector(new BasicsInjectionModule());
        QuickSort qs = injector.getInstance(QuickSort.class);

        int n = 10;
        Random random = new Random(1337L);
        int[] array = SortingUtils.createArrayWithoutDuplicates(n, random);
        System.out.println("unsorted: " + Arrays.toString(array));
        qs.sort(array);
        System.out.println("sorted: " + Arrays.toString(array));
    }
}
