package de.sk.basics.ch6;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.util.SortingUtils;
import de.sk.basics.ch6.selection.Selector;
import de.sk.basics.injection.BasicsInjectionModule;

public class App6SP {

    public static void main(String[] args) {
        // setup guice dependency injection
        Injector injector = Guice.createInjector(new BasicsInjectionModule());

        Selector rSelect = injector.getInstance(Selector.class);
        int n = 11;
        int ithOrderStatistics = 11;
        int[] array = SortingUtils.createArrayWithoutDuplicates(n);
//        System.out.println("array before RSelect: " + Arrays.toString(array));
        int foundIthOrderStatistics = rSelect.select(array, ithOrderStatistics);
//        System.out.println("array after RSelect: " + Arrays.toString(array));
        System.out.println("foundIthOrderStatistics: " + foundIthOrderStatistics);
//        Selector sortBasedSelect = new SortBasedSelect();
//        int foundIthOrderStatistics = sortBasedSelect.select(array, ithOrderStatistics);
//        System.out.println("foundIthOrderStatistics: " + foundIthOrderStatistics);
//        Selector dSelect = injector.getInstance(Key.get(Selector.class, Names.named(InjectionConstants.D_SELECT)));
//        System.out.println(dSelect.select(array, ithOrderStatistics));
    }
}
