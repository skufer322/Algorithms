package de.sk.basics.ch1.multiply;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 * General utility methods required for various integer multiplication implementations.
 */
public class MultiplierUtils {

    /**
     * Determines the number of digits the given big {@code integer} has.
     *
     * @param integer big integer for which the number of digits are to be determined
     * @return number of digits of the given big {@code integer}
     */
    public static int getIntegerLength(@NotNull BigInteger integer){
        String integerAsString = String.valueOf(integer);
        return integerAsString.length();
    }

    /**
     * Creates a format string required for formatting an arbitrary integer with the given number of digits.
     * For example, if the integer is '1' and the number of digits is 3, the integer should be formatted as '003'. With
     * the returned format string, an integer can be formatted adequately.
     *
     * @param numDigits number of digits for the integer which is to be formatted by use of the returned format string
     * @return adequate format string for the required formatting of an integer
     */
    public static @NotNull String getIntegerFormatString(int numDigits) {
        return "%0" + numDigits + "d";
    }

    /**
     * Calculates the next greater power of 2 for the given {@code integer} .
     * For example,
     * if {@code integer} is 3, the next greater power of 2 is 4 (2^2)
     * if {@code integer} is 6, the next greater power of 2 is 8 (2^3)
     * if {@code integer} is 30, the next greater power of 2 is 32 (2^5)
     *
     * @param integer integer for which the next greater power of 2 is to be calculated
     * @return next greater power of 2 for {@code integer}
     */
    public static int getNextGreaterPowerOfTwo(int integer){
        return (int) Math.pow(2, Math.ceil(Math.log(integer)/Math.log(2)));
    }
}
