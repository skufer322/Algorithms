package de.sk.graphs.util;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class supplying various utility methods for directed graphs.
 */
public final class DirectedGraphUtils {

    static final String EDGE_WITH_TOO_SMALL_WEIGHT_EXCEPTION_MSG_TF = "Edge %s has a weight smaller than %d.";

    private DirectedGraphUtils() {
        // only utility methods
    }

    /**
     * Checks whether all the edges of the graph represented by an adjacency list have a weight of at least {@code minWeight}.
     * Throws an {@link IllegalArgumentException} if any edge has a smaller weight.
     *
     * @param adjacencyList graph for which the edge weights are checked
     * @param minWeight     min weight each edge must have
     */
    public static void assertAllEdgesHaveWeightGreaterThan(@NotNull DiAdjacencyList adjacencyList, int minWeight) {
        // could be improved to only account for edges reachable from a specific starting vertex
        for (DiEdge edge : adjacencyList.edges()) {
            if (edge.getWeight() < minWeight) {
                throw new IllegalArgumentException(String.format(EDGE_WITH_TOO_SMALL_WEIGHT_EXCEPTION_MSG_TF, edge, minWeight));
            }
        }
    }
}
