package de.sk.basics.ch3.closestpair.distance;

import de.sk.basics.ch3.closestpair.point.Point2D;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

/**
 * Interface defining the methods for distance measures.
 */
public interface Distance {

    /**
     * Calculates the distance between the given two points {@code p1} and {@code p2}.
     *
     * @param p1 first point
     * @param p2 second point
     * @return distance between {@code p1} and {@code p2}
     */
    double calcDist(@NotNull Point2D p1, @NotNull Point2D p2);

    /**
     * Calculates the distance between the given pair of {@code points}.
     *
     * @param pair pair of points
     * @return distance between the given pair of {@code points}.
     */
    double calcDist(@NotNull Pair<Point2D, Point2D> pair);
}
