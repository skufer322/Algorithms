package de.sk.basics.ch5.quicksort.pivot;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of {@link PivotChooser} selecting an element of the given (sub)array uniformly at random as pivot.
 */
public class RandomPivotChooser implements PivotChooser {

    private final Random rnd;

    /**
     * Constructor.
     *
     * TODO: Zusätzliche Konstruktor, der den Random entgegennimmt.
     *
     * @param isSeeded whether a seeded selector shall be used for the random selection process
     * @param seed seed for the seeded selector
     */
    public RandomPivotChooser(boolean isSeeded, long seed) {
        rnd = isSeeded ? new Random(seed) : ThreadLocalRandom.current();
    }

    @Override
    public int getPivotIndex(int @NotNull [] array, int l, int r) {
        return rnd.nextInt(r - l) + l;
    }
}
