package de.sk.basics.ch3.closestpair.point;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of {@link Point2DGenerator} creating sets of points. All the x- and y-coordinates of the points are
 * unique, i.e. no two points share a common x- or y-coordinate value.
 */
public class UniqueXAndYCoordinatesPoint2DGenerator implements Point2DGenerator {

    static final String MIN_GREATER_EQUAL_MAX_EXCEPTION_TXT_FORMAT = "%s (%d) must be smaller than %s (%d)!";
    static final String RANGE_TOO_SMALL_EXCEPTION_TXT_FORMAT = "Range of both x (%d-%d => %d) and y (%d-%d => %d) must be greater than n (%d)!";

    // class random to use if no random is passed for the method call
    private static final Random RANDOM = ThreadLocalRandom.current();

    @Override
    public @NotNull List<Point2D> createPoints(int minX, int maxX, int minY, int maxY, int n) {
        return conductPointCreation(minX, maxX, minY, maxY, n, RANDOM);
    }

    @Override
    public @NotNull List<Point2D> createPoints(int minX, int maxX, int minY, int maxY, int n, @NotNull Random random) {
        return conductPointCreation(minX, maxX, minY, maxY, n, random);
    }

    private @NotNull List<Point2D> conductPointCreation(int minX, int maxX, int minY, int maxY, int n, @NotNull Random random) {
        validateIntegrityOfArguments(minX, maxX, minY, maxY, n);

        // create point list to return
        List<Point2D> points = new ArrayList<>();
        // ensure that for the n points, each x- and y-coordinate is unique
        List<Integer> xCoordinates = getRandomListOfRangeBoundIntegers(minX, maxX, n, random);
        List<Integer> yCoordinates = getRandomListOfRangeBoundIntegers(minY, maxY, n, random);
        for (int i = 0; i < xCoordinates.size(); i++) {
            Point2D point = new Point2D(xCoordinates.get(i), yCoordinates.get(i));
            points.add(point);
        }

        return points;
    }

    private void validateIntegrityOfArguments(int minX, int maxX, int minY, int maxY, int n) {
        // check integrity of arguments
        if (minX >= maxX) {
            throw new IllegalArgumentException(String.format(MIN_GREATER_EQUAL_MAX_EXCEPTION_TXT_FORMAT, "minX", minX, "maxX", maxX));
        }
        if (minY >= maxY) {
            throw new IllegalArgumentException(String.format(MIN_GREATER_EQUAL_MAX_EXCEPTION_TXT_FORMAT, "minY", minY, "maxY", maxY));
        }
        if ((maxX - minX < n) || (maxY - minY < n)) {
            throw new IllegalArgumentException(String.format(RANGE_TOO_SMALL_EXCEPTION_TXT_FORMAT, minX, maxX, (maxX - minX),
                    minY, maxY, (maxY - minY), n));
        }
    }

    private @NotNull List<Integer> getRandomListOfRangeBoundIntegers(int min, int max, int n, @NotNull Random random) {
        List<Integer> rangeBoundIntegers = IntStream.range(min, max).boxed().collect(Collectors.toList());
        Collections.shuffle(rangeBoundIntegers, random);
        return rangeBoundIntegers.stream().limit(n).toList();
    }
}
