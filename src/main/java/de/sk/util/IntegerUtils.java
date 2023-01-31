package de.sk.util;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Utility class for Integer-related utility methods.
 */
public final class IntegerUtils {

    private IntegerUtils() {
        // only utils
    }

    /**
     * Returns whether the {@code n}-th bit of the given {@code integer}'s the binary representation is set to {@code true}, or not.
     * The least significant bit (2^0) is at index 0.
     *
     * @param integer integer for which it is to be determined whether in its binary representation, the {@code n}-th is set to {@code true}
     * @param n       index of the bit to verify
     * @return {@code true}, if the {@code n}-th bit of {@code integer}'s binary representation is set to {@code true}, else {@code false}
     */
    public static boolean isNthBitSetForInteger(int integer, int n) {
        return ((integer & (1L << n)) != 0);
    }

    /**
     * Returns a random integer between the specified bounds [{@code min}; {@code max}].
     *
     * @param min    lower bound for the generated random integer (inclusive)
     * @param max    upper bound for the generated random integer (inclusive)
     * @param random {@link Random} used to generate the ranom number
     * @return random integer between [{@code min}; {@code max}]
     */
    public static int getRandomIntBetween(int min, int max, @NotNull Random random) {
        return random.nextInt(max - min + 1) + min;
    }
}
