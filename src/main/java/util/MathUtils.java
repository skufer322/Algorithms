package util;

/**
 * Utility class for math-related utility methods.
 */
public final class MathUtils {

    private MathUtils() {
        // only utility methods
    }

    /**
     * Calculates the ceil (⌈dividend/divisor⌉) of dividend and divisor, i.e. rounds the result up to the next integer number.
     * E.g.:
     * 5/3 = ⌈1.666...⌉ = 2.
     * 6/2 = ⌈3⌉ = 3.
     *
     * @param dividend dividend integer
     * @param divisor divisor integer
     * @return result of dividend/divisor rounded up to the next integer
     */
    public static int getCeilInteger(int dividend, int divisor) {
        return dividend / divisor + ((dividend % divisor == 0) ? 0 : 1);
    }
}
