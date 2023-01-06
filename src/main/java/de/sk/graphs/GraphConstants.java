package de.sk.graphs;

import de.sk.graphs.datastructure.directed.DiVertex;
import de.sk.graphs.datastructure.undirected.UnVertex;

import java.util.Comparator;

/**
 * Class holding general, graph-related constants.
 */
public final class GraphConstants {

    private GraphConstants() {
        // only constants
    }

    // injection names (in) for graph search algorithms
    public static final String IN_GRAPH_BFS_SIMPLE = "Graph-BFS-Simple";
    public static final String IN_AUGMENTED_BFS = "Augmented-BFS";
    public static final String IN_ITERATIVE_DFS = "Iterative-BFS";
    public static final String IN_RECURSIVE_DFS = "Recursive-BFS";
    public static final String IN_SIMPLE_EDGE_SELECTOR = "SimpleEdgeSelector";

    // comparators to compare vertices (in undirected graphs)
    public static final Comparator<UnVertex> COMPARE_VERTICES_BY_LEVEL = Comparator.comparingInt(UnVertex::getLevel);
    public static final Comparator<UnVertex> COMPARE_VERTICES_BY_GRAPH_SEARCH_POSITION = Comparator.comparingInt(UnVertex::getGraphSearchPosition);
    // comparators to compare vertices (in directed graphs)
    public static final Comparator<DiVertex> COMPARE_VERTICES_BY_TOP_SORT_POSITION = Comparator.comparingInt(DiVertex::getTopSortPosition);

    // default values for vertex attributes in algorithms
    public static final int INITIAL_GRAPH_SEARCH_POSITION = 1;
    public static final int INITIAL_GRAPH_SEARCH_LEVEL = 0;

    // delimiter separating information concatenated in toString methods
    public static final String STRING_JOIN_DELIMITER = ",";

    public static final int POSITIVE_INFINITY = Integer.MAX_VALUE;
}
