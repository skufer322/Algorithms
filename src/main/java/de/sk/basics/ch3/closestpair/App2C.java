package de.sk.basics.ch3.closestpair;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.basics.ch3.closestpair.alg.ClosestPairFinder;
import de.sk.basics.ch3.closestpair.alg.SimpleClosestPairFinder;
import de.sk.basics.ch3.closestpair.point.Point2D;
import de.sk.basics.injection.BasicsInjectionModule;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class App2C {

    public static void main(String[] args) {
        // set up guice dependency injection
        Injector injector = Guice.createInjector(new BasicsInjectionModule());
        ClosestPairFinder closestPairFinder = injector.getInstance(SimpleClosestPairFinder.class);
//        ClosestPairFinder closestPairFinder = injector.getInstance(FastClosestPairFinder.class);

        // set up parameters for creating set of n points
        int minX = 0, maxX = 10, minY = 0, maxY = 10;
        int n = 5;

        // create points and find closest pair
//        Point2DGenerator pointGenerator = new UniqueXAndYCoordinatesPoint2DGenerator();
//        List<Point2D> points = pointGenerator.createPoints(minX, maxX, minY, maxY, n);
        List<Point2D> points = getPointsForTest();
        System.out.println("Points: " + points);
        Point2D[] pointsArray = points.toArray(Point2D[]::new);

        Pair<Point2D, Point2D> closestPair = closestPairFinder.determineClosestPair(pointsArray);
        System.out.println("Closest Pair: " + closestPair);

        // test sort & clone
//        Point2D[] sortedByX = Point2DUtil.sortByXCoordinates(Point2DUtil.clone(pointsArray));
//        System.out.println("sorted by x: " + Arrays.toString(sortedByX));
//        Point2D[] sortedByY = Point2DUtil.sortByYCoordinates(Point2DUtil.clone(pointsArray));
//        System.out.println("sorted by y: " + Arrays.toString(sortedByY));
//        System.out.println("original array: " + Arrays.toString(pointsArray));
    }

    private static List<Point2D> getPointsForTest() {
        Point2D firstPoint = new Point2D(1, 8);
        Point2D secondPoint = new Point2D(2, 5);
        Point2D thirdPoint = new Point2D(4, 7);
        Point2D fourthPoint = new Point2D(6, 3);
        return List.of(firstPoint, secondPoint, thirdPoint, fourthPoint);
    }
}
