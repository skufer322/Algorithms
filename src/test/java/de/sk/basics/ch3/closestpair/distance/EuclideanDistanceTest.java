package de.sk.basics.ch3.closestpair.distance;

import de.sk.basics.ch3.closestpair.point.Point2D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Full test coverage.
 */
class EuclideanDistanceTest {

    private Point2D p1;
    private Point2D p2;

    private EuclideanDistance underTest;

    @BeforeEach
    void setUp() {
        underTest = new EuclideanDistance();
    }

    @ParameterizedTest(name = "[{index}] x1={0} y1={1}, x2={2}, y2={3}")
    @CsvSource({
            "0, 0, 0, 0",
            "10, 10, 10, 10",
            "0, 10, 0, 10"
    })
    void shouldCorrectlyCalculateZeroDistance_for2Points(int x1, int y1, int x2, int y2) {
        p1 = new Point2D(x1, y1);
        p2 = new Point2D(x2, y2);

        double distance = underTest.calcDist(p1, p2);

        assertThat(distance).isZero();
    }

    @ParameterizedTest(name = "[{index}] x1={0} y1={1}, x2={2}, y2={3}, distance: {4}")
    @CsvSource({
            "0, 0, 1, 0, 1",
            "0, 0, 5, 0, 5",
            "10, 10, 10, 0, 10",
            "10, 10, 5, 10, 5"
    })
    void shouldCorrectlyCalculateAxisAlignedLineDistances_for2Points(int x1, int y1, int x2, int y2, double expectedDistance) {
        p1 = new Point2D(x1, y1);
        p2 = new Point2D(x2, y2);

        double distance = underTest.calcDist(p1, p2);

        assertThat(distance).isEqualTo(expectedDistance);
    }

    @ParameterizedTest(name = "[{index}] x1={0} y1={1}, x2={2}, y2={3}, squared distance: {4}")
    @CsvSource({
            "0, 0, 1, 1, 2",
            "1, 8, 2, 5, 10",
            "1, 8, 6, 3, 50",
            "2, 5, 6, 3, 20"
    })
    void shouldCorrectlyCalculateArbitraryDistances_for2Points(int x1, int y1, int x2, int y2, double squaredDistance) {
        p1 = new Point2D(x1, y1);
        p2 = new Point2D(x2, y2);

        double distance = underTest.calcDist(p1, p2);

        assertThat(distance).isEqualTo(Math.sqrt(squaredDistance));
    }

    @Test
    void shouldCalculateDistance_forPairs_byCallingPointDistanceCalculationMethod() {
        p1 = new Point2D(0, 0);
        p2 = new Point2D(0, 0);
        Pair<Point2D, Point2D> pointPair = new ImmutablePair<>(p1, p2);
        // create spy to be able to verify interactions of underTest with itself
        EuclideanDistance spyUnderTest = spy(underTest);

        spyUnderTest.calcDist(pointPair);

        verify(spyUnderTest).calcDist(p1, p2);
    }

    @Test
    void shouldReturnResultOfPointDistanceCalculationMethod_forPairs() {
        double resultToReturn = 123.456d;
        p1 = new Point2D(0, 0);
        p2 = new Point2D(0, 0);
        Pair<Point2D, Point2D> pointPair = new ImmutablePair<>(p1, p2);
        EuclideanDistance spyUnderTest = spy(underTest);
        // mock spy to return resultToReturn when calling the point distance calculation method with passing p1 and p2
        when(spyUnderTest.calcDist(p1, p2)).thenReturn(resultToReturn);

        double distance = spyUnderTest.calcDist(pointPair);

        assertThat(distance).isEqualTo(resultToReturn);
    }
}
