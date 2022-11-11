package de.sk.basics.ch3.closestpair.util;

import de.sk.basics.ch3.closestpair.point.Point2D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class for methods and constants for handling instances of {@link Point2D}.
 */
public final class Point2DUtil {

    static final Comparator<Point2D> xSorter = Comparator.comparing(Point2D::x, Comparator.naturalOrder());
    static final Comparator<Point2D> ySorter = Comparator.comparing(Point2D::y, Comparator.naturalOrder());

    private Point2DUtil() {
        // only utility methods
    }

    /**
     * Sorts the given array of points in ascending order w.r.t. the x-coordinates of the points.
     * @param points points to sort
     * @return sorted array (ascending order) w.r.t. the x-coordinates of the points
     */
    public static @NotNull Point2D[] sortByXCoordinates(@NotNull Point2D[] points) {
        return sort(points, xSorter);
    }

    /**
     * Sorts the given array of points in ascending order w.r.t. the y-coordinates of the points.
     * @param points points to sort
     * @return sorted array (ascending order) w.r.t. the y-coordinates of the points
     */
    public static @NotNull Point2D[] sortByYCoordinates(@NotNull Point2D[] points) {
        return sort(points, ySorter);
    }

    private static @NotNull Point2D[] sort(@NotNull Point2D[] points, @NotNull Comparator<Point2D> comparator) {
        Arrays.sort(points, comparator);
        return points;
    }

    /**
     * Creates a duplicate of the given array of points. Is only a shallow copy, i.e. the points within the array are not copied.
     * @param points array of points to copy
     * @return shallow copy of the given array
     */
    public static @NotNull Point2D[] clone(@NotNull Point2D[] points) {
        return Arrays.copyOf(points, points.length);
    }

    /**
     * Splits the given array of points into two equally-sized halves. If the array size is odd, the right half has one
     * point more than the left half.
     * @param points array of points to split in halves
     * @return original array split into a left and a right half
     */
    public static @NotNull Pair<Point2D[], Point2D[]> splitIntoHalves(@NotNull Point2D[] points) {
        int mid = points.length / 2;
        Point2D[] firstHalf = Arrays.copyOfRange(points, 0, mid);
        Point2D[] secondHalf = Arrays.copyOfRange(points, mid, points.length);
        return new ImmutablePair<>(firstHalf, secondHalf);
    }
}
