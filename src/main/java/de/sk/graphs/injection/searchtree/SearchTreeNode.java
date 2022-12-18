package de.sk.graphs.injection.searchtree;

/**
 * Interface defining the common methods of all search tree nodes.
 * <p>
 * (Could be extended with some payload-related methods).
 */
public interface SearchTreeNode {

    /**
     * Returns the key associated with the search tree node.
     *
     * @return key of the search tree node
     */
    int getKey();
}
