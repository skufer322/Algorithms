package de.sk.greedy.huffman;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Representation of a node in a binary Huffman tree used to create Huffman codes. Is a recursive data structure, i.e. each node
 * has pointers to its possibly existing parent, left child, and right child nodes.
 */
public class HuffmanTreeNode {

    static final String CHILDREN_BOTH_MUST_BE_NULL_OR_NON_NULL_TF = "The left and the right child have to be either both null or " +
            "both non-null, but not a mix. left: %s, right: %s";
    static final String PROBABILITY_MUST_BE_BETWEEN_0_AND_1_EXCEPTION_MSG_TF = "Probability must be between 0 and 1. Given: %s.";
    static final String NAME_IS_BLANK_EXCEPTION_MSG_TF = "Name must not be blank. Name: '%s', probability: '%s'.";

    private final String name;
    private final double probability;
    private HuffmanTreeNode parent;
    private final HuffmanTreeNode left;
    private final HuffmanTreeNode right;

    /**
     * Constructor for creating a leaf node in the Huffman tree. Has no point pointers to child nodes and the {@code name}
     * is passed as a character since the {@code name} of a leaf node corresponds to the symbol it represents.
     *
     * @param name symbol the leaf node to create is to represent in the Huffman tree
     * @param probability probability that the given symbol occurs in the sequence of symbols to encode
     */
    public HuffmanTreeNode(@NotNull Character name, double probability) {
        this(name.toString(), probability, null, null);
    }

    /**
     * Constructor for creating a node the Huffman tree. Is primarily used for creating internal nodes of the Huffman tree
     * (featuring both a {@code left} and a {@code right} child), but can also be used for creating leaf nodes (by passing
     * null for both {@code left} and {@code right}).
     *
     * @param name name of the node (made up of the symbol(s) the node is to represent)
     * @param probability probability that the symbol(s) represented by this node occur(s) in the sequence of symbols to encode
     * @param left left child node
     * @param right right child node
     */
    public HuffmanTreeNode(@NotNull String name, double probability, @Nullable HuffmanTreeNode left, @Nullable HuffmanTreeNode right) {
        if (Stream.of(left, right).filter(Objects::nonNull).count() == 1) {
            throw new IllegalArgumentException(String.format(CHILDREN_BOTH_MUST_BE_NULL_OR_NON_NULL_TF, left, right));
        }
        if (probability < 0d || probability > 1d) {
            throw new IllegalArgumentException(String.format(PROBABILITY_MUST_BE_BETWEEN_0_AND_1_EXCEPTION_MSG_TF, probability));
        }
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(String.format(NAME_IS_BLANK_EXCEPTION_MSG_TF, name, probability));
        }
        this.name = name;
        this.probability = probability;
        this.left = left;
        if (this.left != null) {
            this.left.setParent(this);
        }
        this.right = right;
        if (this.right != null) {
            this.right.setParent(this);
        }
    }

    /**
     * Returns the name of the node (i.e. either the single symbol or the concatenated set of symbols the node represents).
     *
     * @return name of the node
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Returns the probability with which the single symbol or the set of symbols occur(s) in the sequence of symbols to encode.
     * @return probability of the symbol or set of symbols the node represents
     */
    public double getProbability() {
        return probability;
    }

    /**
     * Returns the parent node of the current node, or null if no parent node exists.
     *
     * @return parent node of the current node, or null if no parent node exists.
     */
    public @Nullable HuffmanTreeNode getParent() {
        return this.parent;
    }

    /**
     * Sets the {@code parent} node of the current node.
     *
     * @param parent new parent node for the current node
     */
    public void setParent(@Nullable HuffmanTreeNode parent) {
        this.parent = parent;
    }

    /**
     * Returns the left child node of the current node, or null if no left child node exists.
     *
     * @return left child node of the current node, or null if no left child node exists.
     */
    public @Nullable HuffmanTreeNode getLeft() {
        return left;
    }

    /**
     * Returns the right child node of the current node, or null if no right child node exists.
     *
     * @return right child node of the current node, or null if no right child node exists.
     */
    public @Nullable HuffmanTreeNode getRight() {
        return right;
    }

    /**
     * Returns whether the current node is a left child node, or not (i.e. it is a right child node or the root node of
     * the Huffman tree).
     *
     * @return true if the current node is a left child node, false else
     */
    public boolean isLeftChild() {
        return this.parent != null && this.parent.getLeft() == this;
    }

    /**
     * Returns whether the current node is a leaf node in the Huffman tree (i.e. both its left and right child nodes
     * are null). Note the method returns true if the current node is the root node and there are no other nodes in the tree.
     *
     * @return true if the current node is a leaf node, false else
     */
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
}
