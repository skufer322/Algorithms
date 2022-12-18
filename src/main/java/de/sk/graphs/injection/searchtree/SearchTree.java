package de.sk.graphs.injection.searchtree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Interface defining the search tree operations.
 */
public interface SearchTree {

    /**
     * Searches and returns the node with the given key. If no such node exists, null is returned.
     *
     * @param key key to search for
     * @return the node with the given key, or null if no such key exists
     */
    @Nullable SearchTreeNode search(int key);

    /**
     * Returns the node with the smallest key in the search tree. If the search tree is empty, null is returned.
     *
     * @return node with the smallest key, or null if the search tree is empty
     */
    @Nullable SearchTreeNode min();

    /**
     * Returns the node with the greatest key in the search tree. If the search tree is empty, null is returned.
     *
     * @return node with the greatest key, or null if the search tree is empty
     */
    @Nullable SearchTreeNode max();

    /**
     * Returns the predecessor for the given key, i.e. the node with the next smaller key (w.r.t. the given key). Returns
     * null if no smaller key exists in the search tree.
     *
     * @param key key for which the predecessor shall be returned.
     * @return node with the next smaller key (w.r.t. the given key), or null if no smaller key exists
     */
    @Nullable SearchTreeNode predecessor(int key);

    /**
     * Returns the successor for the given key, i.e. the node with the next greater key (w.r.t. the given key). Returns
     * null if no greater key exists in the search tree.
     *
     * @param key key for which the successor shall be returned.
     * @return node with the next greater key (w.r.t. the given key), or null if no greater key exists
     */
    @Nullable SearchTreeNode successor(int key);

    /**
     * Returns all nodes of the search tree in ascending order of their respective keys.
     *
     * @return list of the search tree's nodes, in ascending order of their keys
     */
    @NotNull List<SearchTreeNode> outputSorted();

    /**
     * Inserts a new key into the search tree, creating a corresponding node associated with the key.
     *
     * @param key the new key to insert
     * @return the created node associated with the new key
     */
    @NotNull SearchTreeNode insert(int key);

    /**
     * Deletes the node with the given key from the search tree. Returns the deleted node or null if there is given key
     * (respectively its associated node) does not exist in the search tree.
     *
     * @param key key which, together which its associated node, shall be deleted from the search tree
     * @return the deleted node, or null if the given key does not exist
     */
    @Nullable SearchTreeNode delete(int key);

    /**
     * Returns the node with the <code>rank</code>-greatest key from the search tree. Returns null if the given rank is
     * greater than the number of nodes in the search tree.
     *
     * @param rank the rank of the key/node which shall be returned (i.e. the node with the <code>rank</code>-greatest key)
     * @return node with the <code>rank</code>-greatest key, or null if rank is greater than the number of nodes in the search tree
     */
    @Nullable SearchTreeNode select(int rank);

    /**
     * Returns the rank of the given key, considering all keys maintained in the search tree. Throws an <code>IllegalArgumentException</code>
     * if there is no node with the given key.
     *
     * @param key key whose rank shall be determined (considering all keys maintained in the search tree)
     * @return the rank of the given key
     */
    int rank(int key);
}
