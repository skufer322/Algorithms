package de.sk.graphs.searchtree;

import de.sk.graphs.searchtree.binary.BinarySearchTree;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App11ST {

    public static void main(String[] args) {
        BinarySearchTree bst = BinarySearchTree.createEmptyBinarySearchTree();
        int[] keys = new int[]{50, 30, 10, 40, 70, 80, 90};
        Arrays.stream(keys).forEach(bst::insert);

        // test search
        SearchTreeNode existing1 = bst.search(keys[0]);
        System.out.println("existing1: " + (existing1 != null ? existing1.getKey() : "<null>"));
        SearchTreeNode existing2 = bst.search(keys[keys.length - 1]);
        System.out.println("existing2: " + (existing2 != null ? existing2.getKey() : "<null>"));
        SearchTreeNode nonExisting1 = bst.search(120);
        System.out.println("nonExisting1: " + (nonExisting1 != null ? nonExisting1.getKey() : "<null>"));
        SearchTreeNode nonExisting2 = bst.search(45);
        System.out.println("nonExisting2: " + (nonExisting2 != null ? nonExisting2.getKey() : "<null>"));
        // test min
        SearchTreeNode min = bst.min();
        System.out.println("min: " + (min != null ? min.getKey() : "<null>"));
        // test max
        SearchTreeNode max = bst.max();
        System.out.println("max: " + (max != null ? max.getKey() : "<null>"));
        // test predecessor
        SearchTreeNode predecessor1 = bst.predecessor(keys[0]);
        System.out.println("predecessor1: " + (predecessor1 != null ? predecessor1.getKey() : "<null>"));
        SearchTreeNode predecessor2 = bst.predecessor(Arrays.stream(keys).min().orElseThrow());
        System.out.println("predecessor2: " + (predecessor2 != null ? predecessor2.getKey() : "<null>"));
        SearchTreeNode predecessor3 = bst.predecessor(Arrays.stream(keys).max().orElseThrow());
        System.out.println("predecessor3: " + (predecessor3 != null ? predecessor3.getKey() : "<null>"));
        // test successor
        SearchTreeNode successor1 = bst.successor(keys[0]);
        System.out.println("successor1: " + (successor1 != null ? successor1.getKey() : "<null>"));
        SearchTreeNode successor2 = bst.successor(Arrays.stream(keys).min().orElseThrow());
        System.out.println("successor2: " + (successor2 != null ? successor2.getKey() : "<null>"));
        SearchTreeNode successor3 = bst.successor(Arrays.stream(keys).max().orElseThrow());
        System.out.println("successor3: " + (successor3 != null ? successor3.getKey() : "<null>"));
        // test output sorted
        List<SearchTreeNode> sortedOutput = bst.outputSorted();
        // @formatter:off
        String sortedOutputAsString = sortedOutput.stream().map(SearchTreeNode::getKey)
                                                        .map(i -> Integer.toString(i))
                                                        .collect(Collectors.joining(",", "[", "]")); // @formatter:on
        System.out.println("sorted output: " + sortedOutputAsString);
        // test select
        int rank = 0;
        SearchTreeNode selectedByRank = bst.select(rank);
        System.out.println("selectedByRank[" + rank + "]: " + (selectedByRank != null ? selectedByRank.getKey() : "<null>"));
        // test rank
        for (int key : keys) {
            int rankOfKey = bst.rank(key);
            System.out.println("rankOfKey[" + key + "]: " + rankOfKey);
        }
        // test delete
        System.out.println("bst: " + bst.getTreeStructure(false));
        SearchTreeNode deleted = bst.delete(30);
        System.out.println("deleted node: " + (deleted != null ? deleted.getKey() : "<null>"));
        System.out.println("bst: " + bst.getTreeStructure(true));
        System.out.println("bst: " + bst.getTreeStructure(false));
    }
}
