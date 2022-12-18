package de.sk.basics.ch3.closestpair.alg;

import de.sk.basics.ch3.closestpair.distance.Distance;
import de.sk.basics.ch3.closestpair.point.Point2D;
import de.sk.basics.ch3.closestpair.util.Point2DUtil;
import de.sk.basics.injection.InjectionConstants;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * O(n log n) divide & conquer implementation of {@link ClosestPairFinder}.
 */
public class FastClosestPairFinder extends ArgumentValidatingClosestPairFinder {

    // simple closest pair finder for conducting exhaustive searches (for small numbers of elements)
    @Inject @Named(InjectionConstants.SIMPLE_CLOSEST_PAIR_FINDER)
    private ClosestPairFinder simpleClosestPairFinder;

    @Inject
    private Distance distance;

    @Override
    public @NotNull Pair<Point2D, Point2D> determineClosestPair(@NotNull Point2D[] points) {
        Point2D[] px = Point2DUtil.sortByXCoordinates(Point2DUtil.clone(points));
        Point2D[] py = Point2DUtil.sortByYCoordinates(Point2DUtil.clone(points));
        return recClosestPairDetermination(px, py);
    }

    private @NotNull Pair<Point2D, Point2D> recClosestPairDetermination(@NotNull Point2D[] px, @NotNull Point2D[] py) {
        //// base case
        if (px.length <= 3) {
            // exhaustive search to determine closest pair
            return simpleClosestPairFinder.determineClosestPair(px);
        }

        //// recursion case
        // determine lx, ly, rx, ry
        Pair<Point2D[], Point2D[]> lxAndRx = Point2DUtil.splitIntoHalves(px);
        int xMedian = lxAndRx.getLeft()[lxAndRx.getLeft().length - 1].x(); // last element of lx has median x coordinate
        Pair<Point2D[], Point2D[]> lyAndRy = createLyAndRy(py, xMedian);
        Point2D[] lx = lxAndRx.getLeft();
        Point2D[] ly = lyAndRy.getLeft();
        Point2D[] rx = lxAndRx.getRight();
        Point2D[] ry = lyAndRy.getRight();

        // recursive calls -> determine the closest pair in left and right halves, each
        Pair<Point2D, Point2D> leftPair = recClosestPairDetermination(lx, ly);
        Pair<Point2D, Point2D> rightPair = recClosestPairDetermination(rx, ry);

        // determine closest non-split pair and delta
        double distanceLeftPair = distance.calcDist(leftPair);
        double distanceRightPair = distance.calcDist(rightPair);
        Pair<Point2D, Point2D> closestNonSplitPair = distanceLeftPair <= distanceRightPair ? leftPair : rightPair;
        double delta = Math.min(distanceLeftPair, distanceRightPair);

        // determine split pair
        Pair<Point2D, Point2D> splitPair = determineClosestSplitPair(py, delta, xMedian);
        double distanceSplitPair = splitPair.getLeft() != null ? distance.calcDist(splitPair) : Double.MAX_VALUE;

        // determine and return closest pair
        return delta <= distanceSplitPair ? closestNonSplitPair : splitPair;
    }

    private @NotNull Pair<Point2D[], Point2D[]> createLyAndRy(@NotNull Point2D[] py, int xMedian) {
        Point2D[] tmp = new Point2D[py.length];
        Pair<Point2D[], Point2D[]> lyAndRy = Point2DUtil.splitIntoHalves(tmp);
        // fill ly and ry by iterating through py once (-> O(n))
        int l = 0, r = 0;
        for (Point2D currentPoint : py) {
            if (currentPoint.x() <= xMedian) {
                // add current point to ly
                lyAndRy.getLeft()[l] = currentPoint;
                l++;
            } else {
                // add current point to ry
                lyAndRy.getRight()[r] = currentPoint;
                r++;
            }
        }
        return lyAndRy;
    }

    private @NotNull Pair<Point2D, Point2D> determineClosestSplitPair(@NotNull Point2D[] py, double delta, int xMedian) {
        Point2D[] sy = createSy(py, delta, xMedian);
        double minDist = delta;
        Point2D closestPoint1 = null, closestPoint2 = null;
        for (int i = 0; i < sy.length; i++) {
            Point2D currentP1 = sy[i];
            for (int j = i + 1; j < Math.min(sy.length, i + 7); j++) {
                Point2D currentP2 = sy[j];
                double currentDistance = distance.calcDist(currentP1, currentP2);
                if (currentDistance < minDist) {
                    closestPoint1 = currentP1;
                    closestPoint2 = currentP2;
                    minDist = currentDistance;
                }
            }
        }
        return new ImmutablePair<>(closestPoint1, closestPoint2);
    }

    private @NotNull Point2D[] createSy(@NotNull Point2D[] py, double delta, int xMedian) {
        List<Point2D> sy = new ArrayList<>();
        // determine all points which are in the window around x median
        for (Point2D point : py) {
            if ((point.x() >= xMedian - delta) && (point.x() <= xMedian + delta)) {
                sy.add(point);
            }
        }
        return sy.toArray(Point2D[]::new);
    }
}
