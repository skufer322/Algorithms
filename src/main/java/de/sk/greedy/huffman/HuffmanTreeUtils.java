package de.sk.greedy.huffman;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Utility class for Huffman-encoding-related methods. Some methods traverse the tree structure of Huffman trees,
 * others are for the creation of binary Huffman codes from a Huffman tree.
 */
public final class HuffmanTreeUtils {

    static final String NODE_NAME_DELIMITER = "|";
    static final char ZERO = '0';
    static final char ONE = '1';
    static final boolean BOOLEAN_FOR_LEFT_CHILD = false;
    static final char BINARY_DIGIT_LEFT_CHILD = BOOLEAN_FOR_LEFT_CHILD ? ZERO : ONE;

    private HuffmanTreeUtils() {
        // only utilities
    }

    /**
     * Traverses the Huffman tree anchored at the given root node and returns a list of the tree's leaf nodes.
     *
     * @param root root of the Huffman tree for which the leaf nodes shall be retrieved
     * @return list of the tree's leaf nodes
     */
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

    /**
     * Creates a binary code (in the form of a string) for the given leaf node of a Huffman tree. The code is created
     * in a top-down fashion, i.e. starting at the root and ending at the leaf node level (required to create prefix-free
     * codes) .Throws an {@link IllegalArgumentException} in case the given node is not a leaf node or a root node.
     *
     * @param leafNode leaf node for which the binary code shall be determined
     * @return binary code for the given leaf node (in the form of a string)
     */
    public static @NotNull String getBinaryCode(@NotNull HuffmanTreeNode leafNode) {
        if (!leafNode.isLeaf()) {
            throw new IllegalArgumentException("TODO given node is not a leaf node");
        }
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

    /**
     * Converts a {@link BitSet} to a string representation consisting of '0's (for false) and '1's (for true).
     *
     * @param bitSet BitSet to convert to a string representation
     * @param nBits  number of bits to consider for creating the string representation (since BitSets have only an implicit length)
     * @return string representation of the given BitSet, depicting false as '0' and true as '1'
     */
    public static @NotNull String convertToBinaryString(@NotNull BitSet bitSet, int nBits) {
        final StringBuilder buffer = new StringBuilder(nBits);
        IntStream.range(0, nBits).mapToObj(i -> bitSet.get(i) ? ONE : ZERO).forEach(buffer::append);
        return buffer.toString();
    }
}
