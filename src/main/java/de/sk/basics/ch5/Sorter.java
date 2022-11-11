package de.sk.basics.ch5;

import org.jetbrains.annotations.NotNull;

public interface Sorter {

    // Apparently, this is the way to use @NotNull with primitive arrays:
    // 'int @NotNull []' instead of '@NotNull int[]'.
    //
    // see: https://youtrack.jetbrains.com/issue/IDEA-253963
    // TODO: in README.md unterbringen?

    /**
     * Sorts the given array (ascending order).
     * @param array array to sort
     * @return sorted array (ascending order)
     */
    int @NotNull [] sort(int @NotNull [] array);
}
