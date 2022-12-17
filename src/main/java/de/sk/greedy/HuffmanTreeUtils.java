package de.sk.greedy;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

public final class HuffmanTreeUtils {

    static final String NODE_NAME_DELIMITER = "|";
    static final char ZERO = '0';
    static final char ONE = '1';
    static final boolean BOOLEAN_FOR_LEFT_CHILD = false;
    static final char BINARY_DIGIT_LEFT_CHILD = BOOLEAN_FOR_LEFT_CHILD ? ZERO : ONE;

    private HuffmanTreeUtils() {
        // only utilities
    }

    public static @NotNull List<HuffmanTreeNode> getLeafNodes(@NotNull HuffmanTreeNode root) {
        List<HuffmanTreeNode> leafNodes = new ArrayList<>();
        HuffmanTreeUtils.dfsToRetrieveLeafNodes(root, leafNodes);
        return leafNodes;
    }

    private static void dfsToRetrieveLeafNodes(@NotNull HuffmanTreeNode node, @NotNull List<HuffmanTreeNode> leafNodes) {
        if (node.isLeaf()) {
            leafNodes.add(node);
        } else {
            if (node.getLeft() != null) HuffmanTreeUtils.dfsToRetrieveLeafNodes(node.getLeft(), leafNodes);
            if (node.getRight() != null) HuffmanTreeUtils.dfsToRetrieveLeafNodes(node.getRight(), leafNodes);
        }
    }

    public static @NotNull String getBinaryCode(@NotNull HuffmanTreeNode leafNode) {
        BitSet bitSet = new BitSet();
        HuffmanTreeNode currentChild = leafNode;
        HuffmanTreeNode currentParent = leafNode.getParent();
        if (currentParent == null) {
            throw new IllegalArgumentException("TODO given leafnode is a actually a root node;");
        }
        int idx = 0;
        while (currentParent != null) {
            bitSet.set(idx, currentChild.isLeftChild() == BOOLEAN_FOR_LEFT_CHILD);
            currentChild = currentParent;
            currentParent = currentParent.getParent();
            idx++;
        }
        String codeBuiltBottomUp = HuffmanTreeUtils.convertToBinaryString(bitSet, idx);
        // reverse codeBuiltBottomUp as it represents a binary code built bottom-up, but huffman codes are built top-down (to be prefix-free)
        return new StringBuilder(codeBuiltBottomUp).reverse().toString();
    }

    public static @NotNull String convertToBinaryString(@NotNull BitSet bitSet, int nBits) {
        final StringBuilder buffer = new StringBuilder(nBits);
        IntStream.range(0, nBits).mapToObj(i -> bitSet.get(i) ? ONE : ZERO).forEach(buffer::append);
        return buffer.toString();
    }
}
