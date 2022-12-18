package de.sk.graphs.algorithm.dijkstra.edgeselection;

import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Abstract class implementing common methods required for all edge selector implementations.
 */
public abstract class AbstractEdgeSelector implements EdgeSelector {

    /**
     * Based on the set of already selected Vertices X, determines whether the given edge (v,w) is eligible for selection
     * in the next iteration of Dijkstra's algorithm, or not. An edge (v,w) is eligible if v ∈ X and w ∈ V - X (V is the
     * set of all vertices).
     *
     * @param edge                    edge whose eligibility shall be asserted
     * @param alreadySelectedVertices set of already selected vertices
     * @return true if the given edge is eligible, else false
     */
    boolean isEligibleEdge(@NotNull DiEdge edge, @NotNull Set<DiVertex> alreadySelectedVertices) {
        return alreadySelectedVertices.contains(edge.tail()) && !alreadySelectedVertices.contains(edge.head());
    }

    /**
     * Calculates the Dijkstra score for the given edge.
     *
     * @param edge edge for which the Dijkstra score shall be calculated
     * @return the edge's Dijkstra score
     */
    int calculateDijkstraScore(@NotNull DiEdge edge) {
        return edge.tail().getLen() + edge.getWeight();
    }
}
