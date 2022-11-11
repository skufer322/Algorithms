package de.sk.basics.ch1.multiply.multiplier;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 * Interface for multiplier classes which multiply two arbitrarily large, non-negative integers.
 */
public interface Multiplier {

    /**
     * Multiplies two non-negative integers x and y and returns the result.
     * @param x first non-negative integer
     * @param y second non-negative integer
     * @return result of x multiplied with y
     */
    @NotNull BigInteger multiply(@NotNull BigInteger x, @NotNull BigInteger y);
}
