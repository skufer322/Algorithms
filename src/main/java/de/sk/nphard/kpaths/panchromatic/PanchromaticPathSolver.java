package de.sk.nphard.kpaths.panchromatic;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Interface defining the methods for implementations determining the shortest panchromatic k-path for a graph, given a
 * specific color coding for the vertices. A panchromatic k-path is a path in which each color is represented exactly once.
 */
public interface PanchromaticPathSolver {

    /**
     * Determines the shortest panchromatic {@code k}-path for the given graph (represented as adjacency list) and the
     * given {@code colorCoding}.
     * <br>
     * Returns the shortest panchromatic {@code k}-path information as a {@link Pair}, with left being the list of edges
     * compromising the shortest panchromatic {@code k}-path and right being the length of the shortest panchromatic
     * {@code k}-path.
     *
     * @param adjacencyList graph for which the shortest panchromatic {@code k}-path is to be determined
     * @param k             number of different colors in the color coding/number of vertices in the path to determine
     * @param colorCoding   color coding of the graph's vertices (one of {@code k} different colors is assigned to each vertex)
     * @return list of edges of the shortest panchromatic {@code k}-path (left) and the length of the shortest panchromatic {@code k}-path (right).
     */
    @NotNull Pair<List<UnEdge>, Integer> determineShortestPanchromaticKPath(@NotNull UnAdjacencyList adjacencyList, int k, @NotNull Map<UnVertex, Integer> colorCoding);
}
