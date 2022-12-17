package de.sk.graphs.searchtree.binary;

import de.sk.graphs.searchtree.SearchTreeNode;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class BinarySearchTreeOperations {

    static final String WRONG_PARENT_EXCEPTION_MSG_TEXT_FORMAT = "Internal error. Parent node %d of given child %d of given parent %d.";

    private BinarySearchTreeOperations() {
        // only utilities
    }

    public static @Nullable SearchTreeNode getFirstAncestorWithGreaterKey(int key, @Nullable BinarySearchTreeNode ancestor) {
        if (ancestor == null) {
            return null;
        }
        return ancestor.getKey() > key ? ancestor : BinarySearchTreeOperations.getFirstAncestorWithGreaterKey(key, ancestor.getParent());
    }

    public static @Nullable SearchTreeNode getFirstAncestorWithSmallerKey(int key, @Nullable BinarySearchTreeNode ancestor) {
        if (ancestor == null) {
            return null;
        }
        return ancestor.getKey() < key ? ancestor : BinarySearchTreeOperations.getFirstAncestorWithSmallerKey(key, ancestor.getParent());
    }

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
            throw new IllegalArgumentException(WRONG_PARENT_EXCEPTION_MSG_TEXT_FORMAT);
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
