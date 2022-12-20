package de.sk.basics.ch3.closestpair.alg;

import de.sk.basics.ch3.closestpair.point.Point2D;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract class defining common methods for instantiable {@link ClosestPairFinder} implementations which validate the
 * arguments passed to the methods.
 */
public abstract class ArgumentValidatingClosestPairFinder implements ClosestPairFinder {

    static final String TOO_FEW_POINTS_EXCEPTION_MSG_TEXT_STRING = "Point2D array must have at least 2 elements. Number of elements in array: %d";

    protected void validateDetermineClosestPairMethodArguments(@NotNull Point2D[] points) {
        if (points.length < 2) {
            throw new IllegalArgumentException(String.format(TOO_FEW_POINTS_EXCEPTION_MSG_TEXT_STRING, points.length));
        }
    }
}
