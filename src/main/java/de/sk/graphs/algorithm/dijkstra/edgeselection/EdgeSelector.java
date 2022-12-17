package de.sk.graphs.algorithm.dijkstra.edgeselection;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface EdgeSelector {

    @NotNull Pair<DiEdge, Integer> selectEligibleEdgeWithLowestDijkstraScore(@NotNull DiAdjacencyList adjacencyList, @NotNull Set<DiVertex> processedVertices);
}
