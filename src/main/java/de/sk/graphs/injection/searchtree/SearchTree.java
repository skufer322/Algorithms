package de.sk.graphs.injection.searchtree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SearchTree {

    @Nullable SearchTreeNode search(int key);

    @Nullable SearchTreeNode min();

    @Nullable SearchTreeNode max();

    @Nullable SearchTreeNode predecessor(int key);

    @Nullable SearchTreeNode successor(int key);

    @NotNull List<SearchTreeNode> outputSorted();

    @NotNull SearchTreeNode insert(int key);

    @Nullable SearchTreeNode delete(int key);

    @Nullable SearchTreeNode select(int rank);

    int rank(int key);
}
