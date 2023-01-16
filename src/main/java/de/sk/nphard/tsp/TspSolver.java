package de.sk.nphard.tsp;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Interface defining the methods for implementations solving the Traveling Salesman Problem (TSP) for complete graphs.
 * The solutions determined by the implementations might be approximate solutions since the TSP problem is NP-hard.
 */
public interface TspSolver {

    /**
     * Determines the shortest tour for the given graph (represented as adjacency list). Throws an {@link IllegalArgumentException}
     * if the given graph is not a complete graph (i.e. there is an edge between each pair of vertices v,w ∈ V).
     * <br>
     * Returns a {@link Pair} representing the shortest tour. The left of the {@link Pair} is a list of the edges
     * compromising the shortest tour, the right of the {@link Pair} is length of the shortest tour.
     *
     * @param adjacencyList graph for which the shortest tour shall be determined.
     * @return shortest tour information, compromising of the edges of the shortest tour (left) and its length (right)
     */
    @NotNull Pair<List<UnEdge>, Integer> determineShortestTour(@NotNull UnAdjacencyList adjacencyList);
}
