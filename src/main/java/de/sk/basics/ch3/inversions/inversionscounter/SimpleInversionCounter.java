package de.sk.basics.ch3.inversions.inversionscounter;

import org.jetbrains.annotations.NotNull;

/**
 * Brute force implementation of {@link InversionCounter}, runtime O(n²).
 */
public class SimpleInversionCounter implements InversionCounter {

    @Override
    public long countInversions(int @NotNull [] array) {
        long inversions = 0;

        for (int i = 0; i < array.length; i++) {
            int elementToCheckInversionsFor = array[i];
            for (int j = i + 1; j < array.length; j++) {
                int currentElement = array[j];
                if (elementToCheckInversionsFor > currentElement) {
                    inversions++;
                }
            }
        }
        return inversions;
    }
}
