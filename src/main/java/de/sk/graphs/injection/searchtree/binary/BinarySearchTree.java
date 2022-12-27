package de.sk.graphs.injection.searchtree.binary;

import de.sk.graphs.injection.searchtree.SearchTree;
import de.sk.graphs.injection.searchtree.SearchTreeNode;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements {@link SearchTree} in the form of a binary tree.
 */
public class BinarySearchTree implements SearchTree {

    static final String DUPLICATE_KEY_EXCEPTION_MSG_TF = "Duplicate keys are not allowed. Key does already exist in search tree: %d.";
    static final String NO_PREDECESSOR_FOUND_EXCEPTION_MSG_TF = "Internal error. No predecessor found for node to delete %d even though the node has two children.";
    static final String ONLY_NULL_CHILDREN_FOUND_EXCEPTION_MSG_TF = "Internal error. Both children of node to delete %d are null, even " +
            "though the node should have one non-null child.";
    static final String TREE_IS_EMPTY_EXCEPTION_MSG_TF = "The tree is empty, key %d not contained";
    static final String KEY_NOT_CONTAINED_IN_TREE_EXCEPTION_MSG_TF = "Key %d is not contained in the tree";


    private boolean isEmpty;
    private BinarySearchTreeNode root;
    private final BinaryTreeChildCounter treeChildCounter;

    private BinarySearchTree() {
        this.isEmpty = true;
        this.root = new BinarySearchTreeNode(Integer.MIN_VALUE);
        this.treeChildCounter = new BinaryTreeChildCounter();
    }

    /**
     * Creates a new, empty binary search tree.
     *
     * @return newly created, empty binary search tree
     */
    public static @NotNull BinarySearchTree createEmptyBinarySearchTree() {
        return new BinarySearchTree();
    }

    @Override
    public @Nullable SearchTreeNode search(int key) {
        return this.isEmpty ? null : this.searchNode(key, this.root);
    }

    private @Nullable BinarySearchTreeNode searchNode(int key, @NotNull BinarySearchTreeNode subtreeToSearch) {
        if (key == subtreeToSearch.getKey()) return subtreeToSearch;
        if (key < subtreeToSearch.getKey()) {
            return subtreeToSearch.getLeftChild() != null ? this.searchNode(key, subtreeToSearch.getLeftChild()) : null;
        } else {
            return subtreeToSearch.getRightChild() != null ? this.searchNode(key, subtreeToSearch.getRightChild()) : null;
        }
    }

    @Override
    public @Nullable SearchTreeNode min() {
        return this.isEmpty ? null : this.minNode(this.root);
    }

    private @NotNull BinarySearchTreeNode minNode(@NotNull BinarySearchTreeNode subtreeToSearch) {
        BinarySearchTreeNode leftChild = subtreeToSearch.getLeftChild();
        return leftChild == null ? subtreeToSearch : this.minNode(leftChild);
    }

    @Override
    public @Nullable SearchTreeNode max() {
        return this.isEmpty ? null : this.maxNode(this.root);
    }

    private @NotNull BinarySearchTreeNode maxNode(@NotNull BinarySearchTreeNode subtreeToSearch) {
        BinarySearchTreeNode rightChild = subtreeToSearch.getRightChild();
        return rightChild == null ? subtreeToSearch : this.maxNode(rightChild);
    }

    @Override
    public @Nullable SearchTreeNode predecessor(int key) {
        BinarySearchTreeNode treeWithKeyAtRoot = this.useSearchToGetStartNodeForAnotherOperation(key);
        if (treeWithKeyAtRoot == null) {
            return null;
        }
        BinarySearchTreeNode leftChild = treeWithKeyAtRoot.getLeftChild();
        BinarySearchTreeNode parent = treeWithKeyAtRoot.getParent();
        return leftChild != null ? this.maxNode(leftChild) : BinarySearchTreeOperations.getFirstAncestorWithSmallerKey(key, parent);
    }

    @Override
    public @Nullable SearchTreeNode successor(int key) {
        BinarySearchTreeNode treeWithKeyAtRoot = this.useSearchToGetStartNodeForAnotherOperation(key);
        if (treeWithKeyAtRoot == null) {
            return null;
        }
        BinarySearchTreeNode rightChild = treeWithKeyAtRoot.getRightChild();
        BinarySearchTreeNode parent = treeWithKeyAtRoot.getParent();
        return rightChild != null ? this.minNode(rightChild) : BinarySearchTreeOperations.getFirstAncestorWithGreaterKey(key, parent);
    }

    private @Nullable BinarySearchTreeNode useSearchToGetStartNodeForAnotherOperation(int key) {
        if (this.isEmpty) {
            return null;
        }
        return this.searchNode(key, this.root);
    }

    @Override
    public @NotNull List<SearchTreeNode> outputSorted() {
        List<SearchTreeNode> sortedOutput = new ArrayList<>();
        if (this.isEmpty) {
            return Collections.emptyList();
        }
        BinarySearchTreeOperations.traverseInInorder(this.root, sortedOutput);
        return sortedOutput;
    }

    @Override
    public @NotNull SearchTreeNode insert(int key) {
        if (this.isEmpty) {
            this.isEmpty = false;
            this.root = createNewNodeAndAddToChildCounts(key, null, false);
            return this.root;
        }
        return this.insertNode(key, this.root);
    }

    private @NotNull BinarySearchTreeNode insertNode(int key, @NotNull BinarySearchTreeNode node) {
        if (key == node.getKey()) {
            throw new IllegalArgumentException(String.format(DUPLICATE_KEY_EXCEPTION_MSG_TF, node.getKey()));
        }
        boolean belongsInLeftSubtree = key < node.getKey();
        this.treeChildCounter.incrementChildCount(node, belongsInLeftSubtree);
        if (belongsInLeftSubtree) {
            return node.getLeftChild() == null ? this.createNewNodeAndAddToChildCounts(key, node, true) : this.insertNode(key, node.getLeftChild());
        } else {
            return node.getRightChild() == null ? this.createNewNodeAndAddToChildCounts(key, node, false) : this.insertNode(key, node.getRightChild());
        }
    }

    @Override
    public @Nullable SearchTreeNode delete(int key) {
        BinarySearchTreeNode nodeToDelete = useSearchToGetStartNodeForAnotherOperation(key);
        if (nodeToDelete == null) {
            return null;
        }
        // only path to root is affected by delete operation -> adequately decrement child counts on path from nodeToDelete to root
        this.decrementChildCountsOnPathToRoot(nodeToDelete.getParent(), nodeToDelete.isLeftChild());
        // actual deletion
        int numberOfDirectChildren = nodeToDelete.getNumberOfDirectChildren();
        if (numberOfDirectChildren == 0) {
            return this.deleteWith0DirectChildren(nodeToDelete);
        }
        return numberOfDirectChildren == 1 ? this.deleteWith1DirectChild(nodeToDelete) : this.deleteWith2DirectChildren(nodeToDelete);
    }

    private void decrementChildCountsOnPathToRoot(@Nullable BinarySearchTreeNode ancestor, boolean isLeftChildCountToDecrement) {
        if (ancestor != null) {
            this.treeChildCounter.decrementChildCount(ancestor, isLeftChildCountToDecrement);
            boolean isLeftChild = ancestor.isLeftChild();
            this.decrementChildCountsOnPathToRoot(ancestor.getParent(), isLeftChild);
        }
    }

    private @NotNull BinarySearchTreeNode deleteWith0DirectChildren(@NotNull BinarySearchTreeNode nodeToDelete) {
        BinarySearchTreeNode parent = nodeToDelete.getParent();
        if (parent == null) {
            // deleted node is root
            this.isEmpty = true;
        } else {
            // deleted node is leaf
            if (nodeToDelete.isLeftChild()) {
                parent.setLeftChild(null);
            } else {
                parent.setRightChild(null);
            }
        }
        this.cleanUpReferences(nodeToDelete);
        return nodeToDelete;
    }

    private @NotNull BinarySearchTreeNode deleteWith1DirectChild(@NotNull BinarySearchTreeNode nodeToDelete) {
        BinarySearchTreeNode parent = nodeToDelete.getParent();
        BinarySearchTreeNode child = nodeToDelete.getLeftChild() != null ? nodeToDelete.getLeftChild() : nodeToDelete.getRightChild();
        if (child == null) {
            throw new IllegalStateException(String.format(ONLY_NULL_CHILDREN_FOUND_EXCEPTION_MSG_TF, nodeToDelete.getKey()));
        }
        if (parent == null) {
            // nodeToDelete is root -> make child the new root
            child.setParentAndRelationship(null, false);
            this.root = child;
        } else {
            // nodeToDelete is inner node -> splice out
            boolean isLeftChild = nodeToDelete.isLeftChild();
            child.setParentAndRelationship(parent, isLeftChild);
        }
        this.cleanUpReferences(nodeToDelete);
        return nodeToDelete;
    }

    private @NotNull BinarySearchTreeNode deleteWith2DirectChildren(@NotNull BinarySearchTreeNode nodeToDelete) {
        BinarySearchTreeNode predecessor = (BinarySearchTreeNode) this.predecessor(nodeToDelete.getKey());
        if (predecessor == null) {
            throw new IllegalStateException(NO_PREDECESSOR_FOUND_EXCEPTION_MSG_TF);
        }
        // if nodeToDelete has 2 children, one of its left children is its predecessor
        // -> decrement left child count of nodeToDelete, as it will also swap its child counts with its predecessor (and after swapping, nodeToDelete is deleted)
        this.treeChildCounter.decrementChildCount(nodeToDelete, true);
        BinarySearchTreeNode leftChildOfPredecessor = predecessor.getLeftChild(); // predecessor can only have a left child, never a right child
        // swap nodeToDelete and its predecessor
        BinarySearchTreeOperations.swapFirstAndSecondNode(nodeToDelete, predecessor, this.treeChildCounter);
        // take care in case nodeToDelete or predecessor were root before swap
        if (this.root == nodeToDelete || this.root == predecessor) {
            if (this.root == nodeToDelete) {
                this.root = predecessor;
            } else {
                this.root = nodeToDelete;
            }
        }
        // proceed with actual deletion of nodeToDelete
        if (leftChildOfPredecessor == null) {
            // after swap, nodeToDelete is now a leaf -> simply remove it
            this.deleteWith0DirectChildren(nodeToDelete);
        } else {
            // after swap nodeToDelete is now an internal node with one child -> splice out
            this.deleteWith1DirectChild(nodeToDelete);
        }
        return nodeToDelete;
    }

    private void cleanUpReferences(@NotNull BinarySearchTreeNode nodeToCleanUp) {
        this.treeChildCounter.remove(nodeToCleanUp);
        // clean up references of node to clean up
        nodeToCleanUp.setParentAndRelationship(null, false);
        nodeToCleanUp.setLeftChild(null);
        nodeToCleanUp.setRightChild(null);
    }

    private @NotNull BinarySearchTreeNode createNewNodeAndAddToChildCounts(int key, @Nullable BinarySearchTreeNode parent, boolean isLeftChild) {
        BinarySearchTreeNode newNode = new BinarySearchTreeNode(key, parent, isLeftChild);
        this.treeChildCounter.putWithNewCounter(newNode);
        return newNode;
    }

    public @Nullable SearchTreeNode select(int rank) {
        if (this.isEmpty || (rank < 1) || (rank > this.treeChildCounter.getTotalChildCount(this.root) + 1)) {
            return null;
        }
        return this.selectNode(this.root, rank);
    }

    private @Nullable BinarySearchTreeNode selectNode(@NotNull BinarySearchTreeNode node, int rank) {
        int rankOfNode = this.treeChildCounter.getLeftChildCount(node) + 1;
        if (rankOfNode == rank) {
            return node;
        }
        if (rankOfNode > rank) {
            return node.getLeftChild() != null ? this.selectNode(node.getLeftChild(), rank) : null;
        } else {
            int adjustedRank = rank - rankOfNode;
            return node.getRightChild() != null ? this.selectNode(node.getRightChild(), adjustedRank) : null;
        }
    }

    public int rank(int key) {
        if (this.isEmpty) {
            throw new IllegalArgumentException(String.format(TREE_IS_EMPTY_EXCEPTION_MSG_TF, key));
        }
        return this.rankNode(this.root, key, 1);
    }

    private int rankNode(@Nullable BinarySearchTreeNode node, int key, int rank) {
        if (node == null) {
            throw new IllegalArgumentException(String.format(KEY_NOT_CONTAINED_IN_TREE_EXCEPTION_MSG_TF, key));
        }
        if (node.getKey() == key) {
            return rank + this.treeChildCounter.getLeftChildCount(node);
        }
        if (node.getKey() > key) {
            return this.rankNode(node.getLeftChild(), key, rank);
        } else {
            // rank increases by the number of left children of the current node and 1 for the current node
            rank += this.treeChildCounter.getLeftChildCount(node) + 1;
            return this.rankNode(node.getRightChild(), key, rank);
        }
    }

    /**
     * Creates a string depicting the hierarchical structure of the binary search tree.
     *
     * @param doPrintCounts whether the left child and right child counts is to be printed with each node, or not
     * @return string depicting the hierarchical structure of the binary search tree
     */
    public @NotNull String getTreeStructure(boolean doPrintCounts) {
        if (this.isEmpty) {
            return StringUtils.EMPTY;
        }
        StringBuilder builder = new StringBuilder().append(System.lineSeparator());
        this.createTreeStructureString(this.root, builder, StringUtils.EMPTY, StringUtils.EMPTY, doPrintCounts);
        return builder.toString();
    }

    private void createTreeStructureString(@NotNull BinarySearchTreeNode node, @NotNull StringBuilder buffer, @NotNull String prefix,
                                           @NotNull String childrenPrefix, boolean doPrintCounts) {
        buffer.append(prefix);
        buffer.append(doPrintCounts ? this.childCountEntryToString(node) : node.getKey());
        buffer.append(System.lineSeparator());
        List<BinarySearchTreeNode> children = node.getNonNullChildren();
        for (int i = 0; i < children.size(); i++) {
            BinarySearchTreeNode child = children.get(i);
            String direction = child.isLeftChild() ? "l" : "r";
            if (i < children.size() - 1) {
                this.createTreeStructureString(child, buffer, childrenPrefix + "├──" + direction + "── ", childrenPrefix + "│      ", doPrintCounts);
            } else {
                this.createTreeStructureString(child, buffer, childrenPrefix + "└──" + direction + "── ", childrenPrefix + "       ", doPrintCounts);
            }
        }
    }

    private @NotNull String childCountEntryToString(@NotNull BinarySearchTreeNode node) {
        return node.getKey() + " {" + this.treeChildCounter.getCounterInformation(node) + "}";
    }
}
