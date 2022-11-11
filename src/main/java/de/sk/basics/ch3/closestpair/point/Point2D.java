package de.sk.basics.ch3.closestpair.point;

import org.jetbrains.annotations.NotNull;

/**
 * Immutable 2D point data class.
 *
 * @param x x-coordinate of the point
 * @param y y-coordinate of the point
 */
public record Point2D(int x, int y) {

    static final String TO_STRING_TXT_FORMAT = "(%d;%d)";

    @Override
    public @NotNull String toString() {
        return String.format(TO_STRING_TXT_FORMAT, x, y);
    }
}
