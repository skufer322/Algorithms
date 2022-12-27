package de.sk.dynamicprogramming.knapsack;

public record KnapsackItem(int value, int size) {

    static final String SIZE_TOO_SMALL_EXCEPTION_MSG_TF = "size must be greater than 0. Given: %d.";

    public KnapsackItem {
        if (size < 0) {
            throw new IllegalArgumentException(String.format(SIZE_TOO_SMALL_EXCEPTION_MSG_TF, size));
        }
    }
}
