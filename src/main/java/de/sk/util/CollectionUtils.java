package de.sk.util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utility class supplying utility methods which are not generally offered in other common collection libraries.
 */
public final class CollectionUtils {

    private CollectionUtils() {
        // only utility methods
    }

    /**
     * Checks if at least one element in the given collection is null
     *
     * @param collection collection to check for null values
     * @param <T>        generic type of the collection
     * @return true, if at least one element is null, else false
     */
    public static <T> boolean containsNullElement(@NotNull Collection<T> collection) {
        for (T element : collection) {
            if (element == null) return true;
        }
        return false;
    }

    /**
     * For the given {@code key}, puts the given {@code element} into the set-based value of the given {@code map}.
     * <br>
     * This means that if the value for {@code key} in the {@code map} is {@code null}, a new set is created and
     * {@code element} is inserted as first element into this newly created set.
     * <br>
     * Else, if the set as value for {@code key} is already initialized, {@code element} is simply added to this set as another element .
     *
     * @param key     key for which {@code element} is to be inserted into the corresponding set-based value of the {@code map}
     * @param element element to be added to the set-based value for {@code key}
     * @param map     map which maintains a set-based value of elements for each of its for {@code key}s
     * @param <T>     generic type of {@code key}, the elements in the set-based value of the {@code map}, and {@code element}
     */
    public static <T> void putIntoMapWithSetBasedValue(@NotNull T key, @NotNull T element, @NotNull Map<T, Set<T>> map) {
        map.computeIfPresent(key, (k, v) -> {
            v.add(element);
            return v;
        });
        map.computeIfAbsent(key, v -> new HashSet<>()).add(element);
    }
}
