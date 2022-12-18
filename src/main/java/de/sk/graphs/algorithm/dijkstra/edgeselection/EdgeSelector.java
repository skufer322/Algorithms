package de.sk.graphs.algorithm.dijkstra.edgeselection;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Interface defining the methods for implementations selecting the next eligible edge for the straight-forward implementation of Dijkstra's algorithm.
 */
public interface EdgeSelector {

    /**
     * For the graph represented as adjacency list, selects the lowest-cost eligible edge considering the set of already selected vertices X. An edge (v,w)
     * is eligible if v ∈ X and w ∈ V - X (V is the set of all vertices).
     *
     * @param adjacencyList           graph
     * @param alreadySelectedVertices set of already selected vertices
     * @return next eligible edge with the lowest cost (i.e. the lowest Dijkstra score)
     */
    @NotNull Pair<DiEdge, Integer> selectEligibleEdgeWithLowestDijkstraScore(@NotNull DiAdjacencyList adjacencyList, @NotNull Set<DiVertex> alreadySelectedVertices);
}
