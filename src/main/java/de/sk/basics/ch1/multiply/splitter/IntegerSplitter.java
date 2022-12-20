package de.sk.basics.ch1.multiply.splitter;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 * Interface defining the methods for implementations of an integer splitter to split integers into their halves based on the single digits, not the value.
 */
public interface IntegerSplitter {

    /**
     * Splits both given integers {@code x} and {@code y} uniformly into halves and returns the respective resulting pairs (x->a|b, y-> c|d).
     * For example, if
     * x = 1234 -> a = 12, b = 34 (= first pair returned)
     * y = 5678 -> c = 56, d = 78 (= second pair returned)
     * <br>
     * Integer n denotes the amount of digits which are considered during the split.
     * For example, if n=4 and
     * x = 123  -> a = 01, b = 23
     * y = 5678 -> c = 56, d = 78
     * <br>
     * Integer n must be >= max{|digits x|, |digits y|}. It ensures a uniform split of {@code x} and {@code y}, i.e. the resulting split
     * integers a, b, c, d have the same amount of digits (if leading 0 are added if required).
     *
     * @param x first integer to split uniformly
     * @param y second integer to split uniformly
     * @param n amount of digits considered during the split
     * @return integer {@code x} split into the pair (a|b) and integer {@code y} split into the pair (c|d), i.e. a pair of ( (a|b) | (c|d) )
     *
     */
    @NotNull Pair<Pair<BigInteger, BigInteger>, Pair<BigInteger, BigInteger>> splitIntoHalves(@NotNull BigInteger x, @NotNull BigInteger y, int n);
}
