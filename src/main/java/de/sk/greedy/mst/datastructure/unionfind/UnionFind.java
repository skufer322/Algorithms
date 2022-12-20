package de.sk.greedy.mst.datastructure.unionfind;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Interface defining the methods for implementations of the UnionFind data structure.
 *
 * @param <T> the type of the elements in the UnionFind data structure
 */
public interface UnionFind<T> {

    /**
     * Initializes the UnionFind data structure for the given list of {@code elements}. After initialization, each element is its own
     * one-element partition tree.
     *
     * @param elements elements to maintain in the partitions of the UnionFind data structure
     */
    void initialize(@NotNull List<T> elements);

    /**
     * Returns the root element of the partition tree the given element {@code x} is located in. Since each element is exactly in
     * one partition, the root element serves as identifier for its partition.
     *
     * @param x element for which it is to be determined in which partition it is located in
     * @return root element of the partition the given element is located in
     */
    @NotNull T find(@NotNull T x);

    /**
     * Merges the partitions in which the two given elements {@code x} and {@code y} are located in, and returns root
     * element of this new partition. If the two elements are located in the same location, no merge is conducted and
     * only the root element of this partition is returned.
     *
     * @param x element located in the first partition to be merged
     * @param y element located in the second partition to be merged
     * @return root element of the common partition both elements are located in (after the operation has been conducted,
     * the elements are guaranteed to be in the same partition - either because their respective partitions have been merged
     * or because they were in the same partition from the beginning)
     */
    @NotNull T union(@NotNull T x, @NotNull T y);

    /**
     * Clears all the auxiliary data structures the UnionFind data structure maintains. After it has been clear, a new
     * call of the {@link #initialize(List)} has to be conducted.
     */
    void clear();
}
