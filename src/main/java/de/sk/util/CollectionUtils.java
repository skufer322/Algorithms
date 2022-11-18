package de.sk.util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Utility class supplying utility methods which are not generally offered in other common collection libraries.
 */
public final class CollectionUtils {

    private CollectionUtils() {
        // only utility methods
    }

    /**
     * Checks if at least one element in the given collection is null
     * @param collection collection to check for null values
     * @return true, if at least one element is null, else false
     * @param <T> generic collection type
     */
    public static <T> boolean containsNullElement(@NotNull Collection<T> collection) {
        for (T element : collection) {
            if (element == null) return true;
        }
        return false;
    }
}
