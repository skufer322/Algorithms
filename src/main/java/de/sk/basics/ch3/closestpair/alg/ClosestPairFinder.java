package de.sk.basics.ch3.closestpair.alg;

import de.sk.basics.ch3.closestpair.point.Point2D;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

/**
 * Interface defining the methods for implementations of a closest pair finder retrieving the closest pair from a set of 2D points.
 */
public interface ClosestPairFinder {

    /**
     * Determines the closest pair for the given array of 2D {@code points}.
     *
     * @param points array of 2D points for which the closest pair is to be determined
     * @return closest pair of 2D {@code points}
     */
    @NotNull Pair<Point2D, Point2D> determineClosestPair(@NotNull Point2D[] points);
}
