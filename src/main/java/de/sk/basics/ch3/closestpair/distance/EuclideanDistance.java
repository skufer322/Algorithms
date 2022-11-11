package de.sk.basics.ch3.closestpair.distance;

import de.sk.basics.ch3.closestpair.point.Point2D;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of the euclidean distance measure.
 */
public class EuclideanDistance implements Distance {

    @Override
    public double calcDist(@NotNull Point2D p1, @NotNull Point2D p2) {
        double squaredDistance = Math.pow(p1.x() - p2.x(), 2) + Math.pow(p1.y() - p2.y(), 2);
        return Math.sqrt(squaredDistance);
    }

    @Override
    public double calcDist(@NotNull Pair<Point2D, Point2D> pair) {
        return calcDist(pair.getLeft(), pair.getRight());
    }
}
