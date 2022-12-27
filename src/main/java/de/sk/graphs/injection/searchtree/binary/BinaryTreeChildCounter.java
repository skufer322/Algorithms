package de.sk.graphs.injection.searchtree.binary;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Class encapsulating the left and right child counts for each node maintained in a  binary search tree. The counts
 * are encapsulated in a separate class such that the count information does not have to be maintained with the nodes
 * themselves.
 */
public class BinaryTreeChildCounter {

    static final String NO_COUNTER_FOR_NODE_EXCEPTION_MSG_TF = "Internal error: No counter exists for node with key %d.";
    static final String CHILD_COUNTS_ARE_NEGATIVE_EXCEPTION_MSG_TF = "Child counts must not be negative. leftChildCount: %d, "
            + "rightChildCount: %d.";

    private final Map<BinarySearchTreeNode, Counter> childCounts;

    /**
     * Default constructor.
     */
    public BinaryTreeChildCounter() {
        this.childCounts = new HashMap<>();
    }

    /**
     * Returns the number of nodes in the left subtree of the given {@code node} (i.e. the number of nodes in the subtree which
     * has the left child of {@code node} as a root).
     *
     * @param node node for which the number of nodes in its left subtree is to be returned
     * @return number of nodes in the left subtree of {@code node}
     */
    public int getLeftChildCount(@NotNull BinarySearchTreeNode node) {
        return this.childCounts.get(node).getLeftChildCount();
    }

    /**
     * Returns the number of nodes in the right subtree of the given {@code node} (i.e. the number of nodes in the subtree which
     * has the right child of {@code node} as a root).
     *
     * @param node node for which the number of nodes in its right subtree is to be returned
     * @return number of nodes in the right subtree of {@code node}
     */
    public int getRightChildCount(@NotNull BinarySearchTreeNode node) {
        return this.childCounts.get(node).getRightChildCount();
    }

    /**
     * Returns the total number of nodes in the left and right subtrees of the given {@code node}.
     *
     * @param node node for which the number of nodes in its subtrees is to be returned
     * @return number of nodes in the subtrees of {@code node}
     */
    public int getTotalChildCount(@NotNull BinarySearchTreeNode node) {
        Counter counter = this.childCounts.get(node);
        return counter.getLeftChildCount() + counter.getRightChildCount();
    }

    /**
     * Adds a new {@code node} and its left and right child counts.
     *
     * @param node            new node for which the child counts are to be maintained
     * @param leftChildCount  number of nodes in the left subtree of {@code node}
     * @param rightChildCount number of nodes in the right subtree of {@code node}
     */
    public void put(@NotNull BinarySearchTreeNode node, int leftChildCount, int rightChildCount) {
        if (leftChildCount < 0 || rightChildCount < 0) {
            throw new IllegalArgumentException(String.format(CHILD_COUNTS_ARE_NEGATIVE_EXCEPTION_MSG_TF, leftChildCount, rightChildCount));
        }
        this.childCounts.put(node, new Counter(leftChildCount, rightChildCount));
    }

    /**
     * Adds a new {@code node} whose left and right child counters are both set to 0.
     *
     * @param node new node for which the child counts are to be maintained
     */
    public void putWithNewCounter(@NotNull BinarySearchTreeNode node) {
        this.put(node, 0, 0);
    }

    public void incrementChildCount(@NotNull BinarySearchTreeNode node, boolean isLeftChildToIncrement) {
        if (isLeftChildToIncrement) {
            this.incrementLeftChildCount(node);
        } else {
            this.incrementRightChildCount(node);
        }
    }

    private void incrementLeftChildCount(@NotNull BinarySearchTreeNode node) {
        this.childCounts.get(node).incrementLeftChildCount();
    }

    private void incrementRightChildCount(@NotNull BinarySearchTreeNode node) {
        this.childCounts.get(node).incrementRightChildCount();
    }

    /**
     * Decrements the left or the right child count of the given {@code node}, depending on {@code isLeftChildToDecrement}.
     *
     * @param node                   node for which the left or right child count are to be decremented
     * @param isLeftChildToDecrement whether the left child count is to be decremented, or not (i.e. the right child count is to be decremented)
     */
    public void decrementChildCount(@NotNull BinarySearchTreeNode node, boolean isLeftChildToDecrement) {
        if (isLeftChildToDecrement) {
            this.decrementLeftChildCount(node);
        } else {
            this.decrementRightChildCount(node);
        }
    }

    /**
     * Decrements the left child count of the given {@code node}.
     *
     * @param node node for which the left child count is to be decremented
     */
    public void decrementLeftChildCount(@NotNull BinarySearchTreeNode node) {
        this.childCounts.get(node).decrementLeftChildCount();
    }

    /**
     * Decrements the right child count of the given {@code node}.
     *
     * @param node node for which the right child count is to be decremented
     */
    public void decrementRightChildCount(@NotNull BinarySearchTreeNode node) {
        this.childCounts.get(node).decrementRightChildCount();
    }

    /**
     * Removes the given {@code node} from the binary tree child counter.
     *
     * @param node node which is to be removed from the binary tree child counter
     */
    public void remove(@NotNull BinarySearchTreeNode node) {
        this.childCounts.remove(node);
    }

    /**
     * Returns a string depicting the left and right child counter information of the given {@code node}.
     *
     * @param node node for which the left and right child counter information are to be depicted in the returned string
     * @return string depicting the left and right child counter information of the given {@code node}
     */
    public @NotNull String getCounterInformation(@NotNull BinarySearchTreeNode node) {
        Counter counter = this.childCounts.get(node);
        if (counter == null) {
            throw new IllegalStateException(String.format(NO_COUNTER_FOR_NODE_EXCEPTION_MSG_TF, node.getKey()));
        }
        return counter.toString();
    }

    private static final class Counter {

        private int leftChildCount;
        private int rightChildCount;

        private Counter(int leftChildCount, int rightChildCount) {
            this.leftChildCount = leftChildCount;
            this.rightChildCount = rightChildCount;
        }

        private int getLeftChildCount() {
            return this.leftChildCount;
        }

        private int getRightChildCount() {
            return this.rightChildCount;
        }

        private void incrementLeftChildCount() {
            this.leftChildCount++;
        }

        private void decrementLeftChildCount() {
            this.leftChildCount--;
        }

        private void incrementRightChildCount() {
            this.rightChildCount++;
        }

        private void decrementRightChildCount() {
            this.rightChildCount--;
        }

        @Override
        public @NotNull String toString() {
            return "" + this.leftChildCount + ";" + this.rightChildCount + "";
        }
    }
}
