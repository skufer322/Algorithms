package de.sk.basics.ch1.multiply.splitter;

import de.sk.basics.ch1.multiply.MultiplierUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 * Straight-forward implementation of {@link IntegerSplitter}.
 */
public class SimpleIntegerSplitter implements IntegerSplitter {

    static final String N_IS_SHORTER_THAN_MAX_DIGITS_XY_EXCEPTION_TEXT_FORMAT = "n (%d) must be >= max {number of digits of x (%s); number of digits of y (%s)}";

    public @NotNull Pair<Pair<BigInteger, BigInteger>, Pair<BigInteger, BigInteger>> splitIntoHalves(@NotNull BigInteger x, @NotNull BigInteger y, int n) {
        // check integrity
        if (Math.max(MultiplierUtils.getIntegerLength(x), MultiplierUtils.getIntegerLength(y)) > n) {
            throw new IllegalArgumentException(String.format(N_IS_SHORTER_THAN_MAX_DIGITS_XY_EXCEPTION_TEXT_FORMAT, n, x, y));
        }

        // split x in halves
        String xAsString = getIntegerAsString(x, n);
        Pair<BigInteger, BigInteger> xHalves = createHalves(xAsString);
        // split y in halves
        String yAsString = getIntegerAsString(y, n);
        Pair<BigInteger, BigInteger> yHalves = createHalves(yAsString);

        // return halves of x and y as pair
        return new ImmutablePair<>(xHalves, yHalves);
    }

    private @NotNull String getIntegerAsString(@NotNull BigInteger integer, int n) {
        String formatString = MultiplierUtils.getIntegerFormatString(n);
        return String.format(formatString, integer);
    }

    private @NotNull Pair<BigInteger, BigInteger> createHalves(@NotNull String integerAsString) {
        int mid = integerAsString.length() / 2;
        String[] halvesAsString = {integerAsString.substring(0, mid), integerAsString.substring(mid)};
        BigInteger firstHalf = new BigInteger(halvesAsString[0]);
        BigInteger secondHalf = new BigInteger(halvesAsString[1]);
        return new ImmutablePair<>(firstHalf, secondHalf);
    }
}
