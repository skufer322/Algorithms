package de.sk.graphs.injection.searchtree.binary;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BinaryTreeChildCounter {

    static final String NO_COUNTER_FOR_NODE_EXCEPTION_MSG_TEXT_FORMAT = "Internal error: No counter exists for node with key %d.";
    static final String CHILD_COUNTS_ARE_NEGATIVE_EXCEPTION_MSG_TEXT_FORMAT = "Child counts must not be negative. leftChildCount: %d, "
            + "rightChildCount: %d.";

    private final Map<BinarySearchTreeNode, Counter> childCounts;

    public BinaryTreeChildCounter() {
        this.childCounts = new HashMap<>();
    }

    public int getLeftChildCount(@NotNull BinarySearchTreeNode node) {
        return this.childCounts.get(node).getLeftChildCount();
    }

    public int getRightChildCount(@NotNull BinarySearchTreeNode node) {
        return this.childCounts.get(node).getRightChildCount();
    }

    public int getTotalChildCount(@NotNull BinarySearchTreeNode node) {
        Counter counter = this.childCounts.get(node);
        return counter.getLeftChildCount() + counter.getRightChildCount();
    }

    public void put(@NotNull BinarySearchTreeNode node, int leftChildCount, int rightChildCount) {
        if (leftChildCount < 0 || rightChildCount < 0) {
            throw new IllegalArgumentException(String.format(CHILD_COUNTS_ARE_NEGATIVE_EXCEPTION_MSG_TEXT_FORMAT, leftChildCount, rightChildCount));
        }
        this.childCounts.put(node, new Counter(leftChildCount, rightChildCount));
    }

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

    public void decrementChildCount(@NotNull BinarySearchTreeNode node, boolean isLeftChildToDecrement) {
        if (isLeftChildToDecrement) {
            this.decrementLeftChildCount(node);
        } else {
            this.decrementRightChildCount(node);
        }
    }

    public void decrementLeftChildCount(@NotNull BinarySearchTreeNode node) {
        this.childCounts.get(node).decrementLeftChildCount();
    }

    public void decrementRightChildCount(@NotNull BinarySearchTreeNode node) {
        this.childCounts.get(node).decrementRightChildCount();
    }

    public void remove(@NotNull BinarySearchTreeNode node) {
        this.childCounts.remove(node);
    }

    public @NotNull String printCounterInformation(@NotNull BinarySearchTreeNode node) {
        Counter counter = this.childCounts.get(node);
        if (counter == null) {
            throw new IllegalStateException(String.format(NO_COUNTER_FOR_NODE_EXCEPTION_MSG_TEXT_FORMAT, node.getKey()));
        }
        return counter.toString();
    }

    private static final class Counter {

        private int leftChildCount;
        private int rightChildCount;

        private Counter() {
            this(0, 0);
        }

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
