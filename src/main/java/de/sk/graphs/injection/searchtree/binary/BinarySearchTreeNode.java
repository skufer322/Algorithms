package de.sk.graphs.injection.searchtree.binary;

import de.sk.graphs.injection.searchtree.SearchTreeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BinarySearchTreeNode implements SearchTreeNode {

    private final int key;
    private BinarySearchTreeNode parent;
    private BinarySearchTreeNode leftChild;
    private BinarySearchTreeNode rightChild;

    public BinarySearchTreeNode(int key) {
        this(key, null, false);
    }

    public BinarySearchTreeNode(int key, @Nullable BinarySearchTreeNode parent, boolean isLeftChild) {
        this.key = key;
        this.setParentAndRelationship(parent, isLeftChild);
    }

    public void setParentAndRelationship(@Nullable BinarySearchTreeNode parent, boolean isLeftChild) {
        this.parent = parent;
        if (this.parent != null) {
            if (isLeftChild) {
//            if (this.key > this.parent.getKey()) {
//                throw new IllegalArgumentException("TODOleft child must be smaller parent");
//            }
                this.parent.setLeftChild(this);
            } else {
//            if (this.key < this.parent.getKey()) {
//                throw new IllegalArgumentException("TODOright child must be greater parent");
//            }
                this.parent.setRightChild(this);
            }
        }
    }

    @Override
    public int getKey() {
        return this.key;
    }

    public @Nullable BinarySearchTreeNode getParent() {
        return this.parent;
    }

    public @Nullable BinarySearchTreeNode getLeftChild() {
        return this.leftChild;
    }

    public void setLeftChild(@Nullable BinarySearchTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public @Nullable BinarySearchTreeNode getRightChild() {
        return this.rightChild;
    }

    public void setRightChild(@Nullable BinarySearchTreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public boolean isLeftChild() {
        return this.parent != null && this.parent.getLeftChild() == this;
    }

    public boolean isRightChild() {
        return this.parent != null && this.parent.getRightChild() == this;
    }

    public int getNumberOfDirectChildren() {
        if (this.leftChild == null && this.rightChild == null) {
            return 0;
        }
        return (this.leftChild == null || this.rightChild == null) ? 1 : 2;
    }

    public @NotNull List<BinarySearchTreeNode> getNonNullChildren() {
        return Stream.of(this.leftChild, this.rightChild).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public @NotNull String toString() {
        return Integer.toString(this.key);
    }
}
