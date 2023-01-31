package de.sk.nphard.kpaths;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Interface defining the methods for implementations solving the shortest k-path problem in a graph.
 */
public interface ShortestKPathSolver {

    /**
     * Determine the shortest {@code k}-path for the given graph (represented as adjacency list). A {@code k}-path is
     * a path visiting {@code k} different vertices of the graph.
     * <br>
     * Returns the shortest {@code k}-path information as a {@link Pair}, with left being the list of edges compromising
     * the shortest {@code k}-path and right being the length of the shortest {@code k}-path.
     *
     * @param adjacencyList graph for which the shortest {@code k}-path is to be determined
     * @param k             number of different vertices the shortest path to determine has to visit
     * @return list of edges of the shortest {@code k}-path (left) and the length of the shortest {@code k}-path (right).
     */
    @NotNull Pair<List<UnEdge>, Integer> shortestKPath(@NotNull UnAdjacencyList adjacencyList, int k);
}
