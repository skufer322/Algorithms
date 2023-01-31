package de.sk.nphard.kpaths.colorize;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Interface defining the methods for implementations "coloring" a graph (to be used by color coding algorithms, later).
 */
public interface GraphColorist {

    /**
     * Colorizes the given graph (represented as adjacency list) with {@code k} different colors. The first color index
     * starts at {@code smallestIdx}, the last color index is {@code smallestIdx} + {@code k}.
     * <br>
     * In the returned map, a vertex is the key and the index of the color assigned to the very vertex is its corresponding
     * value.
     * <br>
     * The distribution of the color codes is up to the concrete implementation.
     *
     * @param adjacencyList graph for which the vertices are to be colored
     * @param k             number of different colors for the color coding
     * @param smallestIdx   smallest index of a color
     * @return map with the graph's vertices as keys and the index of their assigned color as values
     */
    @NotNull Map<UnVertex, Integer> colorizeGraph(@NotNull UnAdjacencyList adjacencyList, int k, int smallestIdx);
}
