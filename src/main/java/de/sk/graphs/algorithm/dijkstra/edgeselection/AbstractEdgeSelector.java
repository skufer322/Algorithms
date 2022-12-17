package de.sk.graphs.algorithm.dijkstra.edgeselection;

import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public abstract class AbstractEdgeSelector implements EdgeSelector {

    boolean isEligibleEdge(@NotNull DiEdge edge, @NotNull Set<DiVertex> processedVertices) {
        return processedVertices.contains(edge.tail()) && !processedVertices.contains(edge.head());
    }

    int calculateDijkstraScore(@NotNull DiEdge edge) {
        return edge.tail().getLen() + edge.getWeight();
    }
}
