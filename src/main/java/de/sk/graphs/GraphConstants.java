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

    public static final String INJECTION_NAME_GRAPH_BFS_SIMPLE = "Graph-BFS-Simple";
    public static final String INJECTION_NAME_AUGMENTED_BFS = "Augmented-BFS";
    public static final String INJECTION_NAME_ITERATIVE_DFS = "Iterative-BFS";
    public static final String INJECTION_NAME_RECURSIVE_DFS = "Recursive-BFS";
    public static final String INJECTION_NAME_SIMPLE_EDGE_SELECTOR = "SimpleEdgeSelector";

    public static final Comparator<UnVertex> COMPARE_VERTICES_BY_LEVEL = Comparator.comparingInt(UnVertex::getLevel);
    public static final Comparator<UnVertex> COMPARE_VERTICES_BY_GRAPH_SEARCH_POSITION = Comparator.comparingInt(UnVertex::getGraphSearchPosition);
    public static final Comparator<DiVertex> COMPARE_VERTICES_BY_TOP_SORT_POSITION = Comparator.comparingInt(DiVertex::getTopSortPosition);

    public static final int INITIAL_GRAPH_SEARCH_POSITION = 1;
    public static final int INITIAL_GRAPH_SEARCH_LEVEL = 0;

    public static final String STRING_JOIN_DELIMITER = ",";
}
