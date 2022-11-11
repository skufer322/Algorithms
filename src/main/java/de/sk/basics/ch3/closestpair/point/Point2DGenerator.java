package de.sk.basics.ch3.closestpair.point;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

/**
 * Interface defining methods for implementations generating sets of 2d point data..
 */
public interface Point2DGenerator {

    /**
     * Creates a list of 2D points which are all located within the specified bounds.
     *
     * @param minX lower bound for the x-coordinate of created points
     * @param maxX upper bound for the x-coordinate of created points
     * @param minY lower bound for the y-coordinate of created points
     * @param maxY upper bound for the y-coordinate of created points
     * @param n number of points to create
     * @return list of n 2D points
     */
    @NotNull List<Point2D> createPoints(int minX, int maxX, int minY, int maxY, int n);

    /**
     * Creates a list of 2D points which are all located within the specified bounds.
     *
     * @param minX lower bound for the x-coordinate of created points
     * @param maxX upper bound for the x-coordinate of created points
     * @param minY lower bound for the y-coordinate of created points
     * @param maxY upper bound for the y-coordinate of created points
     * @param n number of points to create
     * @param random Random used in the shuffling
     * @return list of n 2D points
     */
    @NotNull List<Point2D> createPoints(int minX, int maxX, int minY, int maxY, int n, @NotNull Random random);
}
