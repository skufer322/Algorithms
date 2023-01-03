package de.sk.graphs.injection.searchtree.binary;

import de.sk.graphs.injection.searchtree.SearchTreeNode;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Utility class bundling general methods on binary search trees which are not operations defined by
 * {@link de.sk.graphs.injection.searchtree.SearchTree}, but are required for implementing these operations.
 */
public final class BinarySearchTreeOperations {

    static final String WRONG_PARENT_EXCEPTION_MSG_TF = "Internal error. Parent node %d of given child %d of given parent %d.";

    private BinarySearchTreeOperations() {
        // only utilities
    }

    /**
     * Returns the first ancestor which has an associated key greater than the given key. Returns null if there is no such ancestor.
     *
     * @param key      key for which the first ancestor with a greater key is to be returned
     * @param ancestor current ancestor which is checked if its key is greater than the given key
     * @return first ancestor with a greater key than the given key, or null if there is no such ancestor
     */
    public static @Nullable SearchTreeNode getFirstAncestorWithGreaterKey(int key, @Nullable BinarySearchTreeNode ancestor) {
        if (ancestor == null) {
            return null;
        }
        return ancestor.getKey() > key ? ancestor : BinarySearchTreeOperations.getFirstAncestorWithGreaterKey(key, ancestor.getParent());
    }

    /**
     * Returns the first ancestor which has an associated key smaller than the given key. Returns null if there is no such ancestor.
     * Recursively ascends the search tree.
     *
     * @param key      key for which the first ancestor with a smaller key is to be returned
     * @param ancestor current ancestor which is checked if its key is smaller than the given key
     * @return first ancestor with a smaller key than the given key, or null if there is no such ancestor
     */
    public static @Nullable SearchTreeNode getFirstAncestorWithSmallerKey(int key, @Nullable BinarySearchTreeNode ancestor) {
        if (ancestor == null) {
            return null;
        }
        return ancestor.getKey() < key ? ancestor : BinarySearchTreeOperations.getFirstAncestorWithSmallerKey(key, ancestor.getParent());
    }

    /**
     * Traverses the binary search tree in inorder and returns the nodes of the tree in the order of encounter.
     *
     * @param currentNode  current node in the inorder traversal
     * @param sortedOutput list which, after traversal is complete, holds the tree's nodes in the order of encounter
     */
    public static void traverseInInorder(@NotNull BinarySearchTreeNode currentNode, @NotNull List<SearchTreeNode> sortedOutput) {
        // left first
        if (currentNode.getLeftChild() != null) {
            BinarySearchTreeOperations.traverseInInorder(currentNode.getLeftChild(), sortedOutput);
        }
        // root second
        sortedOutput.add(currentNode);
        // right last
        if (currentNode.getRightChild() != null) {
            BinarySearchTreeOperations.traverseInInorder(currentNode.getRightChild(), sortedOutput);
        }
    }

    /**
     * Swaps the positions of the first and the second given nodes in the binary search tree. Also, accordingly adjusts
     * the child tree counters associated with both nodes.
     *
     * @param first            node whose position is to be swapped with the second node
     * @param second           node whose position is to be swapped with the first node
     * @param treeChildCounter child tree counter maintaining the left and right child counts for all nodes in the search trees
     */
    public static void swapFirstAndSecondNode(@NotNull BinarySearchTreeNode first, @NotNull BinarySearchTreeNode second,
                                              @NotNull BinaryTreeChildCounter treeChildCounter) {
        boolean areDirectNeighbors = first.getNonNullChildren().contains(second) || second.getNonNullChildren().contains(first);
        Pair<Integer, Integer> formerCounterOfFirst = new ImmutablePair<>(treeChildCounter.getLeftChildCount(first), treeChildCounter.getRightChildCount(first));
        Pair<Integer, Integer> formerCounterOfSecond = new ImmutablePair<>(treeChildCounter.getLeftChildCount(second), treeChildCounter.getRightChildCount(second));
        if (areDirectNeighbors) {
            BinarySearchTreeOperations.swapDirectlyNeighboredNodes(first, second);
        } else {
            BinarySearchTreeOperations.swapNonNeighboredNodes(first, second);
        }
        // swap counters of the swapped nodes
        treeChildCounter.put(first, formerCounterOfSecond.getLeft(), formerCounterOfSecond.getRight());
        treeChildCounter.put(second, formerCounterOfFirst.getLeft(), formerCounterOfFirst.getRight());
    }

    private static void swapDirectlyNeighboredNodes(@NotNull BinarySearchTreeNode first, @NotNull BinarySearchTreeNode second) {
        BinarySearchTreeNode formerParent = first.getParent() == second ? second : first;
        BinarySearchTreeNode formerChild = formerParent == first ? second : first;
        // integrity check
        if (formerChild.getParent() != formerParent) {
            throw new IllegalArgumentException(WRONG_PARENT_EXCEPTION_MSG_TF);
        }
        // temporary variables for subsequent swap
        boolean wasFormerChildLeftChild = formerChild.isLeftChild();
        BinarySearchTreeNode leftChildOfFormerChild = formerChild.getLeftChild();
        BinarySearchTreeNode rightChildOfFormerChild = formerChild.getRightChild();
        boolean wasFormerParentLeftChild = formerParent.isLeftChild();
        // swap parent-child relationship
        formerChild.setParentAndRelationship(formerParent.getParent(), wasFormerParentLeftChild);
        if (wasFormerChildLeftChild) {
            formerChild.setLeftChild(formerParent);
            formerChild.setRightChild(formerParent.getRightChild());
        } else {
            formerChild.setLeftChild(formerParent.getLeftChild());
            formerChild.setRightChild(formerParent);
        }
        formerParent.setParentAndRelationship(formerChild, wasFormerChildLeftChild);
        formerParent.setLeftChild(leftChildOfFormerChild);
        formerParent.setRightChild(rightChildOfFormerChild);
    }

    private static void swapNonNeighboredNodes(@NotNull BinarySearchTreeNode first, @NotNull BinarySearchTreeNode second) {
        // temporary variables for subsequent swap
        boolean wasFirstLeftChild = first.isLeftChild();
        BinarySearchTreeNode formerParentOfFirst = first.getParent();
        BinarySearchTreeNode formerLeftChildOfFirst = first.getLeftChild();
        BinarySearchTreeNode formerRightChildOfFirst = first.getRightChild();
        boolean wasSecondLeftChild = second.isLeftChild();
        // rewire first
        first.setParentAndRelationship(second.getParent(), wasSecondLeftChild);
        first.setLeftChild(second.getLeftChild());
        first.setRightChild(second.getRightChild());
        // rewire second
        second.setParentAndRelationship(formerParentOfFirst, wasFirstLeftChild);
        second.setLeftChild(formerLeftChildOfFirst);
        second.setRightChild(formerRightChildOfFirst);
    }
}
