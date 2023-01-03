package de.sk.dynamicprogramming.knapsack;

/**
 * Class to represent the items that are the subject of the Knapsack problem.
 *
 * @param value the item's value
 * @param size the item's size
 */
public record KnapsackItem(int value, int size) {

    static final String SIZE_TOO_SMALL_EXCEPTION_MSG_TF = "size must be greater than 0. Given: %d.";

    public KnapsackItem {
        if (size < 0) {
            throw new IllegalArgumentException(String.format(SIZE_TOO_SMALL_EXCEPTION_MSG_TF, size));
        }
    }
}
