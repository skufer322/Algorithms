package de.sk.greedy;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HuffmanTreeNode {

    static final String PROBABILITY_MUST_BE_BETWEEN_0_AND_1_EXCEPTION_MSG_TEXT_FORMAT = "Probability must be between 0 and 1. Given: %s.";
    static final String NAME_IS_BLANK_EXCEPTION_MSG_TEXT_FORMAT = "Name must not be blank. Name: '%s', probability: '%s'.";

    private final String name;
    private final double probability;
    private HuffmanTreeNode parent;
    private final HuffmanTreeNode left;
    private final HuffmanTreeNode right;

    public HuffmanTreeNode(@NotNull Character name, double probability) {
        this(name.toString(), probability, null, null);
    }

    public HuffmanTreeNode(@NotNull String name, double probability, @Nullable HuffmanTreeNode left, @Nullable HuffmanTreeNode right) {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException(String.format(PROBABILITY_MUST_BE_BETWEEN_0_AND_1_EXCEPTION_MSG_TEXT_FORMAT, probability));
        }
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(String.format(NAME_IS_BLANK_EXCEPTION_MSG_TEXT_FORMAT, name, probability));
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

    public @NotNull String getName() {
        return name;
    }

    public double getProbability() {
        return probability;
    }

    public @Nullable HuffmanTreeNode getParent() {
        return this.parent;
    }

    public void setParent(@Nullable HuffmanTreeNode parent) {
        this.parent = parent;
    }

    public @Nullable HuffmanTreeNode getLeft() {
        return left;
    }

    public @Nullable HuffmanTreeNode getRight() {
        return right;
    }

    public boolean isLeftChild() {
        return this.parent != null && this.parent.getLeft() == this;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
}
