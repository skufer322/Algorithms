package de.sk.basics.ch1.multiply.multiplier;

import de.sk.basics.ch1.multiply.MultiplierUtils;
import de.sk.basics.ch1.multiply.splitter.IntegerSplitter;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.math.BigInteger;

/**
 * O(n log n) Implementation of {@link Multiplier} utilizing the Karatsuba method.
 */
public class KaratsubaMultiplier implements Multiplier {

    static final String NEGATIVE_INTEGER_PARAMETER_MSG_TEXT_FORMAT = "At least one of x/y is negative. Only non-negative integers allowed. x:%s, y:%s";

    static final BigInteger TEN = BigInteger.TEN;
    static final BigInteger TWO = BigInteger.TWO;

    @Inject
    private IntegerSplitter splitter;

    @Override
    public @NotNull BigInteger multiply(@NotNull BigInteger x, @NotNull BigInteger y) {
        // integrity check
        if (x.signum() == -1 || y.signum() == -1) {
            throw new IllegalArgumentException(String.format(NEGATIVE_INTEGER_PARAMETER_MSG_TEXT_FORMAT, x, y));
        }

        // check base case
        int numberOfDigitsX = MultiplierUtils.getIntegerLength(x);
        int numberOfDigitsY = MultiplierUtils.getIntegerLength(y);
        int maxNumberOfDigits = Math.max(numberOfDigitsX, numberOfDigitsY);
        if (maxNumberOfDigits == 1) {
            return x.multiply(y);
        }

        // create halves of both x (-> a,b) and y (-> c,d)
        int n = MultiplierUtils.getNextGreaterPowerOfTwo(maxNumberOfDigits);
        Pair<Pair<BigInteger, BigInteger>, Pair<BigInteger, BigInteger>> abcd = splitter.splitIntoHalves(x, y, n);
        BigInteger a = abcd.getLeft().getLeft();
        BigInteger b = abcd.getLeft().getRight();
        BigInteger c = abcd.getRight().getLeft();
        BigInteger d = abcd.getRight().getRight();

        // calculate frontCoefficient
        BigInteger aTimesC = multiply(a, c); // = frontCoefficient
        // calculate endCoefficient (also thirdSummand)
        BigInteger bTimesD = multiply(b, d); // = backCoefficient & thirdSummand
        // calculate middleCoefficient
        BigInteger aPlusB = a.add(b);
        BigInteger cPlusD = c.add(d);
        BigInteger aPlusB_Times_cPlusD = multiply(aPlusB, cPlusD);
        BigInteger middleCoefficient = aPlusB_Times_cPlusD.subtract(aTimesC).subtract(bTimesD);

        // calculate front, middle, and end summand, sum them up, and return the sum
        BigInteger firstSummand = TEN.pow(n).multiply(aTimesC);
        BigInteger halfN = BigInteger.valueOf(n).divide(TWO);
        BigInteger secondSummand = TEN.pow(halfN.intValue()).multiply(middleCoefficient);
        return firstSummand.add(secondSummand).add(bTimesD);
    }
}
