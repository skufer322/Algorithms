package de.sk.greedy.mst.datastructure.unionfind;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UnionFind<T> {

    void initialize(@NotNull List<T> elements);

    @NotNull T find(@NotNull T x);

    @NotNull T union(@NotNull T x, @NotNull T y);

    void clear();
}
