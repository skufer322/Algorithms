package de.sk.basics.ch1.multiply.multiplier;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 * Interface for multiplier classes which multiply two arbitrarily large, non-negative integers.
 */
public interface Multiplier {

    /**
     * Multiplies two non-negative integers {@code x} and {@code y} and returns the result.
     * @param x first non-negative integer
     * @param y second non-negative integer
     * @return result of {@code x} multiplied with {@code y}
     */
    @NotNull BigInteger multiply(@NotNull BigInteger x, @NotNull BigInteger y);
}
