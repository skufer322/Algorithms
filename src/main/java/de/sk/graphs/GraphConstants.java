package de.sk.graphs;

import de.sk.graphs.datastructure.Vertex;

import java.util.Comparator;

/**
 * Class holding some general graph-related constants.
 */
public final class GraphConstants {

    private GraphConstants() {
        // only constants
    }

    public static final Comparator<Vertex> COMPARE_VERTICES_BY_LEVEL = Comparator.comparingInt(Vertex::getLevel);
    public static final Comparator<Vertex> COMPARE_VERTICES_BY_POSITION = Comparator.comparingInt(Vertex::getGraphSearchPosition);

    public static final int INITIAL_GRAPH_SEARCH_POSITION = 1;
    public static final int INITIAL_GRAPH_SEARCH_LEVEL = 0;

    public static final String STRING_JOIN_DELIMITER = ",";
}
