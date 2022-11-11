package de.sk.basics.ch3.closestpair.point;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Full test coverage.
 */
class UniqueXAndYCoordinatesPoint2DGeneratorTest {

    private static final long SEED = 64496546541L;

    private final int minX = 0;
    private final int maxX = 10;
    private final int minY = 0;
    private final int maxY = 10;
    private final int n = 5;

    private Random random;

    private UniqueXAndYCoordinatesPoint2DGenerator underTest;

    @BeforeEach
    void setUp() {
        random = new Random(SEED);
        underTest = new UniqueXAndYCoordinatesPoint2DGenerator();
    }

    @ParameterizedTest(name = "[{index}] newMinXForTest={0}, useClassRandom={1}")
    @CsvSource({
            "10, false",
            "1_000_000, false",
            "10, true",
            "1_000_000, true",
    })
    void shouldThrowException_ifMinXIsNotSmallerThanMaxX(int newMinXForTest, boolean useClassRandom) {
        // @formatter:off
        String expectedExceptionMsg = String.format(UniqueXAndYCoordinatesPoint2DGenerator.MIN_GREATER_EQUAL_MAX_EXCEPTION_TXT_FORMAT,
                                                        "minX", newMinXForTest, "maxX", maxX); //@formatter:on
        assertThatThrownBy(() -> runCreatePointsMethodDependentOnUseClassRandom(newMinXForTest, maxX, minY, maxY, n, useClassRandom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedExceptionMsg);
    }

    @ParameterizedTest(name = "[{index}] newMinYForTest={0}, useClassRandom={1}")
    @CsvSource({
            "10, false",
            "1_000_000, false",
            "10, true",
            "1_000_000, true",
    })
    void shouldThrowException_ifMinYIsNotSmallerThanMaxY(int newMinYForTest, boolean useClassRandom) {
        // @formatter:off
        String expectedExceptionMsg = String.format(UniqueXAndYCoordinatesPoint2DGenerator.MIN_GREATER_EQUAL_MAX_EXCEPTION_TXT_FORMAT,
                "minY", newMinYForTest, "maxY", maxY); //@formatter:on
        assertThatThrownBy(() -> runCreatePointsMethodDependentOnUseClassRandom(minX, maxX, newMinYForTest, maxY, n, useClassRandom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedExceptionMsg);
    }

    @ParameterizedTest(name = "[{index}] newMinXForTest={0}, useClassRandom={1}")
    @CsvSource({
            "6, false",
            "9, false",
            "6, true",
            "9, true"
    })
    void shouldThrowException_ifXRangeIsSmallerThanN(int newMinXForTest, boolean useClassRandom) {
        // @formatter:off
        String expectedExceptionMsg = String.format(UniqueXAndYCoordinatesPoint2DGenerator.RANGE_TOO_SMALL_EXCEPTION_TXT_FORMAT,
                newMinXForTest, maxX, (maxX - newMinXForTest), minY, maxY, (maxY - minY), n); //@formatter:on
        assertThatThrownBy(() -> runCreatePointsMethodDependentOnUseClassRandom(newMinXForTest, maxX, minY, maxY, n, useClassRandom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedExceptionMsg);
    }

    @ParameterizedTest(name = "[{index}] newMinYForTest={0}, useClassRandom={1}")
    @CsvSource({
            "6, false",
            "9, false",
            "6, true",
            "9, true"
    })
    void shouldThrowException_ifYRangeIsSmallerThanN(int newMinYForTest, boolean useClassRandom) {
        // @formatter:off
        String expectedExceptionMsg = String.format(UniqueXAndYCoordinatesPoint2DGenerator.RANGE_TOO_SMALL_EXCEPTION_TXT_FORMAT,
                minX, maxX, (maxX - minX), newMinYForTest, maxY, (maxY - newMinYForTest), n); //@formatter:on
        assertThatThrownBy(() -> runCreatePointsMethodDependentOnUseClassRandom(minX, maxX, newMinYForTest, maxY, n, useClassRandom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedExceptionMsg);
    }

    @ParameterizedTest(name = "[{index}] maxCoordinateValue={0}, newNForTest={1}, useClassRandom={2}")
    @CsvSource({
            "10, 10, false",
            "1_000, 100, false",
            "1_000, 1_000, false",
            "1_000_000, 100_000, false",
            "1_000_000, 1_000_000, false",
            "10, 10, true",
            "1_000, 100, true",
            "1_000, 1_000, true",
            "1_000_000, 100_000, true",
            "1_000_000, 1_000_000, true"
    })
    void shouldCreatePointsWithUniqueXCoordinateValues(int maxCoordinateValue, int newNForTest, boolean useClassRandom) {
        List<Point2D> points = runCreatePointsMethodDependentOnUseClassRandom(minX, maxCoordinateValue, minY, maxCoordinateValue,
                newNForTest, useClassRandom);

        Set<Integer> uniqueXCoordinates = getUniqueCoordinateValues(points, true);
        assertThat(uniqueXCoordinates.size()).isEqualTo(newNForTest);
    }

    @ParameterizedTest(name = "[{index}] maxCoordinateValue={0}, newNForTest={1}, useClassRandom={2}")
    @CsvSource({
            "10, 10, false",
            "1_000, 100, false",
            "1_000, 1_000, false",
            "1_000_000, 100_000, false",
            "1_000_000, 1_000_000, false",
            "10, 10, true",
            "1_000, 100, true",
            "1_000, 1_000, true",
            "1_000_000, 100_000, true",
            "1_000_000, 1_000_000, true"
    })
    void shouldCreatePointsWithUniqueYCoordinateValues(int maxCoordinateValue, int newNForTest, boolean useClassRandom) {
        List<Point2D> points = runCreatePointsMethodDependentOnUseClassRandom(minX, maxCoordinateValue, minY, maxCoordinateValue,
                newNForTest, useClassRandom);

        Set<Integer> uniqueYCoordinates = getUniqueCoordinateValues(points, false);
        assertThat(uniqueYCoordinates.size()).isEqualTo(newNForTest);
    }

    @ParameterizedTest(name = "[{index}] maxCoordinateValue={0}, newNForTest={1}, useClassRandom={2}")
    @CsvSource({
            "100, 20, false", // make newNForTest large enough such that the chance of an exact match will be equal to 0
            "1_000, 100, false",
            "1_0000, 1_000, false",
            "100, 20, true",
            "1_000, 100, true",
            "1_0000, 1_000, true"
    })
    void shouldCreateDifferentCoordinateSetsForXAndY(int maxCoordinateValue, int newNForTest, boolean useClassRandom) {
        List<Point2D> points = runCreatePointsMethodDependentOnUseClassRandom(minX, maxCoordinateValue, minY, maxCoordinateValue,
                newNForTest, useClassRandom);

        Set<Integer> uniqueXCoordinates = getUniqueCoordinateValues(points, true);
        Set<Integer> uniqueYCoordinates = getUniqueCoordinateValues(points, false);
        // if the two sets contain the exact same elements, their sequence will match
        assertThat(uniqueXCoordinates).doesNotContainSequence(uniqueYCoordinates);
    }

    @Test
    void shouldProduceEqualPointLists_whenEquivalentRandomIsPassedForPointCreation() {
        // create first point list
        List<Point2D> firstList = underTest.createPoints(minX, maxX, minY, maxY, n, random);
        // create second list with newly instantiated random which was seeded with same number
        random = new Random(SEED);
        List<Point2D> secondList = underTest.createPoints(minX, maxX, minY, maxY, n, random);
        // create third list in the same way
        random = new Random(SEED);
        List<Point2D> thirdList = underTest.createPoints(minX, maxX, minY, maxY, n, random);

        // assert that the lists are exactly the same
        assertThat(firstList).containsExactlyElementsOf(secondList);
        assertThat(firstList).containsExactlyElementsOf(thirdList);
    }

    private @NotNull List<Point2D> runCreatePointsMethodDependentOnUseClassRandom(int minX, int maxX, int minY, int maxY, int n, boolean useClassRandom) {
        if (useClassRandom) {
            return underTest.createPoints(minX, maxX, minY, maxY, n, random);
        } else {
            return underTest.createPoints(minX, maxX, minY, maxY, n);
        }
    }

    private static @NotNull Set<Integer> getUniqueCoordinateValues(@NotNull List<Point2D> points, boolean getXCoordinates) {
        Set<Integer> uniqueCoordinates = new HashSet<>();
        for (Point2D point : points) {
            int coordinate = getXCoordinates ? point.x() : point.y();
            uniqueCoordinates.add(coordinate);
        }
        return uniqueCoordinates;
    }
}
