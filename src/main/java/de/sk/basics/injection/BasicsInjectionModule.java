package de.sk.basics.injection;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import de.sk.basics.ch1.multiply.multiplier.KaratsubaMultiplier;
import de.sk.basics.ch1.multiply.multiplier.Multiplier;
import de.sk.basics.ch1.multiply.splitter.IntegerSplitter;
import de.sk.basics.ch1.multiply.splitter.SimpleIntegerSplitter;
import de.sk.basics.ch3.closestpair.alg.ClosestPairFinder;
import de.sk.basics.ch3.closestpair.alg.FastClosestPairFinder;
import de.sk.basics.ch3.closestpair.alg.SimpleClosestPairFinder;
import de.sk.basics.ch3.closestpair.distance.Distance;
import de.sk.basics.ch3.closestpair.distance.EuclideanDistance;
import de.sk.basics.ch5.Sorter;
import de.sk.basics.ch5.insertionsort.InsertionSort;
import de.sk.basics.ch5.quicksort.partition.Partitioner;
import de.sk.basics.ch5.quicksort.partition.StandardPartitioner;
import de.sk.basics.ch5.quicksort.pivot.PivotChooser;
import de.sk.basics.ch5.quicksort.pivot.RandomPivotChooser;
import de.sk.basics.ch5.quicksort.pivot.SimplePivotChooser;
import de.sk.basics.ch6.selection.DSelect;
import de.sk.basics.ch6.selection.RSelect;
import de.sk.basics.ch6.selection.Selector;

/**
 * Injection module specifying the guice binding for the dependency injection (for "Part 1: THE BASICS").
 */
public class BasicsInjectionModule extends AbstractModule {

    @Override
    protected void configure() {
        //// chapter 1
        bind(Multiplier.class).to(KaratsubaMultiplier.class);
        bind(IntegerSplitter.class).to(SimpleIntegerSplitter.class);
        //// chapter 3
        bind(Distance.class).to(EuclideanDistance.class);
        bind(ClosestPairFinder.class).to(FastClosestPairFinder.class);
        bind(ClosestPairFinder.class).annotatedWith(Names.named(InjectionConstants.SIMPLE_CLOSEST_PAIR_FINDER)).to(SimpleClosestPairFinder.class);
        //// chapter 5
        bind(Partitioner.class).to(StandardPartitioner.class);
        bind(PivotChooser.class).to(SimplePivotChooser.class);
        bind(Sorter.class).annotatedWith(Names.named(InjectionConstants.INSERTION_SORT)).to(InsertionSort.class);
        // RandomPivotChooser config
        bind(Boolean.class).toInstance(true); // bind RandomPivotChooser to either seeded or non-seeded instance
        bind(Long.class).toInstance(1337L); // seed for if bound to seeded instance
        try {
            bind(RandomPivotChooser.class).toConstructor(RandomPivotChooser.class.getConstructor(Boolean.TYPE, Long.TYPE));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        bind(PivotChooser.class).annotatedWith(Names.named(InjectionConstants.RANDOM_PIVOT_CHOOSER)).to(RandomPivotChooser.class);
        //// chapter 6
        bind(Selector.class).to(RSelect.class);
        bind(Selector.class).annotatedWith(Names.named(InjectionConstants.D_SELECT)).to(DSelect.class);
    }
}
