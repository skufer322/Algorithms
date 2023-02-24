package de.sk.basics.ch3.closestpair.alg;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.TestUtils;
import de.sk.basics.ch3.closestpair.point.Point2D;
import de.sk.basics.ch3.closestpair.point.Point2DGenerator;
import de.sk.basics.ch3.closestpair.point.UniqueXAndYCoordinatesPoint2DGenerator;
import de.sk.basics.injection.BasicsInjectionModule;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FastClosestPairFinderTest {

    private FastClosestPairFinder underTest;

    @BeforeEach
    void setUp() {
        Injector injector = Guice.createInjector(new BasicsInjectionModule());
        underTest = injector.getInstance(FastClosestPairFinder.class);
    }

    @ParameterizedTest(name = "[{index}] points={0}, closest pair point 1 = {1}, closest pair point 2 = {2}")
    @MethodSource("createManuallyControlledTestData")
    void shouldFindClosestPair_supervised(Point2D[] points, Point2D closestPairPoint1, Point2D closestPairPoint2) {
        Pair<Point2D, Point2D> closestPair = underTest.determineClosestPair(points);

        // convert to list for more "elegant" assert
        List<Point2D> closestPoints = TestUtils.convertPairToList(closestPair);
        assertThat(closestPoints).containsExactlyInAnyOrder(closestPairPoint1, closestPairPoint2);
    }

    private static Stream<Arguments> createManuallyControlledTestData() {
        Point2D p1 = new Point2D(1, 8);
        Point2D p2 = new Point2D(2, 5);
        Point2D p3 = new Point2D(4, 7);
        Point2D p4 = new Point2D(6, 3);
        return Stream.of(
                // each Argument consists of the array and two closest points (closest points in any order)
                Arguments.of(new Point2D[]{p1, p4}, p1, p4),
                Arguments.of(new Point2D[]{p1, p2, p4}, p1, p2),
                Arguments.of(new Point2D[]{p1, p2, p3, p4}, p2, p3)
        );
    }

    private static Stream<Arguments> createProgrammaticallyControlledTestData() {
        Point2DGenerator generator = new UniqueXAndYCoordinatesPoint2DGenerator();
        return Stream.of(
                // TODO test against results determined by brute force finder
                // each Argument consists of the array and two closest points (the closest points in any order)
        );
    }
}
