package de.sk.basics.ch3.closestpair.alg;

import de.sk.basics.ch3.closestpair.distance.Distance;
import de.sk.basics.ch3.closestpair.point.Point2D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Brute force implementation of {@link ClosestPairFinder}, time complexity of O(n²).
 */
public class SimpleClosestPairFinder extends ArgumentValidatingClosestPairFinder {

    static final String CLOSEST_PAIR_CONTAINS_NULL_TXT_FORMAT = "Internal Error! At least one of the closest pair points is null: %s!";

    @Inject
    private Distance distance;

    @Override
    public @NotNull Pair<Point2D, Point2D> determineClosestPair(@NotNull Point2D[] points) {
        this.validateDetermineClosestPairMethodArguments(points);
        double minDist = Double.MAX_VALUE;
        Point2D[] closestPair = new Point2D[2];
        for (int i = 0; i < points.length; i++) {
            Point2D p1 = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point2D p2 = points[j];
                double currentDist = distance.calcDist(p1, p2);
                if (currentDist < minDist) {
                    closestPair[0] = p1;
                    closestPair[1] = p2;
                    minDist = currentDist;
                }
            }
        }

        if (closestPair[0] == null || closestPair[1] == null) {
            throw new IllegalStateException(String.format(CLOSEST_PAIR_CONTAINS_NULL_TXT_FORMAT, Arrays.toString(closestPair)));
        }
        return new ImmutablePair<>(closestPair[0], closestPair[1]);
    }
}
