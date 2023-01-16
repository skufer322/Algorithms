package de.sk.nphard.maximumcoverage;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.nphard.NpHardInjectionModule;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class App20MC {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new NpHardInjectionModule());
        Set<BitSet> subsets = createSubsetsFromQuiz20_4();
        Random random = new Random(322);
        MaxCoverageSolver maxCoverageSolver = injector.getInstance(GreedyCoverage.class);
        Set<BitSet> selectedSubsets = maxCoverageSolver.determineMaximumCoverage(subsets, 4, random);
        System.out.println(selectedSubsets);
    }

    private static @NotNull Set<BitSet> createSubsetsFromQuiz20_4() {
        // @formatter:off
        BitSet a1 = new BitSet();
        a1.set(0, 2); a1.set(4, 6); a1.set(8, 10);
        BitSet a2 = new BitSet();
        a2.set(0, 3); a2.set(4, 7);
        BitSet a3 = new BitSet();
        a3.set(3);
        BitSet a4 = new BitSet();
        a4.set(4, 6); a4.set(8, 10); a4.set(12, 14);
        BitSet a5 = new BitSet();
        a5.set(7); a5.set(11);
        BitSet a6 = new BitSet();
        a6.set(10, 12); a6.set(14, 16);
        // @formatter:on

        return new LinkedHashSet<>(List.of(a1, a2, a3, a4, a5, a6));
    }
}
