package de.sk.greedy.mst.datastructure.unionfind;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Implementation of the {@link UnionFind} data structure applying the (rather simple) union-by-size strategy.
 * @param <T> the type of the elements in the UnionFind data structure
 */
public class UnionFindSizeBased<T> implements UnionFind<T> {

    static final String ALREADY_INITIALIZED_EXCEPTION_MSG = "UnionFind data structure is already initialized!";
    static final String NOT_INITIALIZED_EXCEPTION_MSG = "UnionFind data structure has not been initialized, yet!";
    static final String OBJECT_NOT_IN_PARENT_GRAPH_EXCEPTION_MSG_TEXT_FORMAT = "Object %s is not contained in the parent graph.";
    static final String OUT_OF_PARENT_GRAPH_BOUNDS_EXCEPTION_MSG_TEXT_FORMAT = "Index '%d' for %s out of parent graph bounds [0;%d].";

    private boolean isInitialized;
    private int numberOfElementsInParentGraph;
    private List<Element> parentGraph;
    private final Map<T, Integer> objectMap;

    /**
     * Default constructor.
     */
    public UnionFindSizeBased() {
        this.isInitialized = false;
        this.parentGraph = new ArrayList<>();
        this.objectMap = new HashMap<>();
    }

    @Override
    public void initialize(@NotNull List<T> elements) {
        if (this.isInitialized) {
            throw new IllegalStateException(ALREADY_INITIALIZED_EXCEPTION_MSG);
        }
        this.numberOfElementsInParentGraph = elements.size();
        for (int i = 0; i < this.numberOfElementsInParentGraph; i++) {
            T object = elements.get(i);
            this.parentGraph.add(new Element(object, i, i, 1));
            this.objectMap.put(object, i);
        }
        // make parent graph unmodifiable after creation
        this.parentGraph = Collections.unmodifiableList(this.parentGraph);
        this.isInitialized = true;
    }

    @Override
    public @NotNull T find(@NotNull T object) {
        this.throwExceptionIfNotInitialized();
        Integer idxOfElement = this.findIdx(object);
        return this.parentGraph.get(idxOfElement).object;
    }

    private @NotNull Integer findIdx(@NotNull T object) {
        Integer idxOfElement = this.objectMap.get(object);
        this.verifyFoundObjectIsNotNull(idxOfElement);
        Element foundObject = this.parentGraph.get(idxOfElement);
        while (foundObject.idx != foundObject.parent) {
            foundObject = this.parentGraph.get(foundObject.parent);
        }
        return foundObject.parent;
    }

    @Override
    public @NotNull T union(@NotNull T x, @NotNull T y) {
        this.throwExceptionIfNotInitialized();
        Integer idxOfParentOfX = this.findIdx(x);
        Integer idxOfParentOfY = this.findIdx(y);
        idxOfParentOfX = verifyFoundObjectIsNotNull(idxOfParentOfX);
        idxOfParentOfY = verifyFoundObjectIsNotNull(idxOfParentOfY);
        if (idxOfParentOfX.equals(idxOfParentOfY)) {
            // x and y are already in the same partition -> return their common parent
            return this.parentGraph.get(idxOfParentOfX).object;
        }
        // else, the partitions of x and y must be merged
        Element parentOfX = this.parentGraph.get(idxOfParentOfX);
        Element parentOfY = this.parentGraph.get(idxOfParentOfY);
        Element parentWithGreaterTree = parentOfX.size >= parentOfY.size ? parentOfX : parentOfY;
        Element parentWithSmallerTree = parentWithGreaterTree == parentOfX ? parentOfY : parentOfX;
        parentWithSmallerTree.setParent(parentWithGreaterTree.idx);
        parentWithGreaterTree.setSize(parentWithGreaterTree.size + parentWithSmallerTree.size);
        return parentWithGreaterTree.object;
    }

    private void throwExceptionIfNotInitialized() {
        if (!this.isInitialized) {
            throw new IllegalStateException(NOT_INITIALIZED_EXCEPTION_MSG);
        }
    }

    @Override
    public void clear() {
        this.isInitialized = false;
        this.numberOfElementsInParentGraph = Integer.MIN_VALUE;
        this.parentGraph = new ArrayList<>();
        this.objectMap.clear();
    }

    private @NotNull Integer verifyFoundObjectIsNotNull(@Nullable Integer object) {
        return Optional.ofNullable(object)
                .orElseThrow(() -> new IllegalArgumentException(String.format(OBJECT_NOT_IN_PARENT_GRAPH_EXCEPTION_MSG_TEXT_FORMAT, object)));
    }

    @Override
    public @NotNull String toString() {
        StringBuilder buffer = new StringBuilder("UnionFind").append(System.lineSeparator());
        for (Element element : this.parentGraph) {
            // @formatter:off
            buffer.append(element.object)
                    .append(", idx: ").append(element.idx)
                    .append(", parent: ").append(element.parent)
                    .append(", size: ").append(element.size).append(System.lineSeparator()); // @formatter:on
        }
        return buffer.toString();
    }

    /**
     * Class to represent elements and the required associated meta information to efficiently maintain the
     * UnionFind data structure.
     */
    private class Element {

        private final T object;
        private final int idx;
        private int parent;
        private int size;

        private Element(@NotNull T object, int idx, int parent, int size) {
            this.object = object;
            this.verifyIsWithinParentGraphBounds(idx, "idx (of object)");
            this.idx = idx;
            this.setParent(parent);
            this.setSize(size);
        }

        private void setParent(int parent) {
            this.verifyIsWithinParentGraphBounds(size, "parent");
            this.parent = parent;
        }

        private void setSize(int size) {
            // size is 1-based, the indices are 0-based -> same verification method usable if size is adjusted to size-1
            this.verifyIsWithinParentGraphBounds(size - 1, "size");
            this.size = size;
        }

        private void verifyIsWithinParentGraphBounds(int idxValueToCheck, @NotNull String parameterName) {
            if (idxValueToCheck < 0 || idxValueToCheck >= numberOfElementsInParentGraph) {
                throw new IllegalArgumentException(String.format(OUT_OF_PARENT_GRAPH_BOUNDS_EXCEPTION_MSG_TEXT_FORMAT,
                        idxValueToCheck, parameterName, parentGraph.size()));
            }
        }
    }
}
