package de.sk.graphs.injection.searchtree.binary;

import de.sk.graphs.injection.searchtree.SearchTreeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of {@link SearchTreeNode} for binary search trees.
 */
public class BinarySearchTreeNode implements SearchTreeNode {

    private final int key;
    private BinarySearchTreeNode parent;
    private BinarySearchTreeNode leftChild;
    private BinarySearchTreeNode rightChild;

    /**
     * Constructor.
     *
     * @param key key of the binary search tree node to create
     */
    public BinarySearchTreeNode(int key) {
        this(key, null, false);
    }

    /**
     * Constructor.
     *
     * @param key         key of the binary search tree node to create
     * @param parent      parent of the newly created node
     * @param isLeftChild whether the newly created child is the left child of its parent, or not
     */
    public BinarySearchTreeNode(int key, @Nullable BinarySearchTreeNode parent, boolean isLeftChild) {
        this.key = key;
        this.setParentAndRelationship(parent, isLeftChild);
    }

    public void setParentAndRelationship(@Nullable BinarySearchTreeNode parent, boolean isLeftChild) {
        this.parent = parent;
        if (this.parent != null) {
            if (isLeftChild) {
                // TODO. zweite methode 'setParentAndRelationship', die zusätzlich einen boolean suspendIntegrityCheck entgegen nimmt,
                //  und die von dieser methode mit fix 'false' aufgerufen wird?
                this.parent.setLeftChild(this);
            } else {
                this.parent.setRightChild(this);
            }
        }
    }

    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * Returns the parent node of this node (null if node is root).
     *
     * @return parent node of this node, or null if node is root
     */
    public @Nullable BinarySearchTreeNode getParent() {
        return this.parent;
    }

    /**
     * Returns the left child node of this node, or null if there is no left child node.
     *
     * @return left child node of this node, or null if there is no left child node
     */
    public @Nullable BinarySearchTreeNode getLeftChild() {
        return this.leftChild;
    }

    /**
     * Sets the left child node of this node.
     *
     * @param leftChild left child node for this node
     */
    public void setLeftChild(@Nullable BinarySearchTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Returns the right child node of this node, or null if there is no right child node.
     *
     * @return right child node of this node, or null if there is no right child node
     */
    public @Nullable BinarySearchTreeNode getRightChild() {
        return this.rightChild;
    }

    /**
     * Sets the right child node of this node.
     *
     * @param rightChild right child node for this node
     */
    public void setRightChild(@Nullable BinarySearchTreeNode rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Returns whether this node is the left child node of its parent, or not (i.e. is either right child node or has no parent).
     *
     * @return true if this node is the left child node of its parent, false else (is right child or has no parent)
     */
    public boolean isLeftChild() {
        return this.parent != null && this.parent.getLeftChild() == this;
    }

    /**
     * Returns the number of direct children of this node (is 2 at a max because there can only be one left child and one right child).
     *
     * @return number of direct direct children of this node
     */
    public int getNumberOfDirectChildren() {
        if (this.leftChild == null && this.rightChild == null) {
            return 0;
        }
        return (this.leftChild == null || this.rightChild == null) ? 1 : 2;
    }

    /**
     * Returns a list of the child nodes which are not null (list of 2 at a max).
     *
     * @return list of non-null children of this node
     */
    public @NotNull List<BinarySearchTreeNode> getNonNullChildren() {
        return Stream.of(this.leftChild, this.rightChild).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public @NotNull String toString() {
        return Integer.toString(this.key);
    }
}
