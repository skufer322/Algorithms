package de.sk.graphs.algorithm.dijkstra.edgeselection;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Straight-forward implementation of {@link EdgeSelector}.
 */
public class SimpleEdgeSelector extends AbstractEdgeSelector {

    @Override
    public @NotNull Pair<DiEdge, Integer> selectEligibleEdgeWithLowestDijkstraScore(@NotNull DiAdjacencyList adjacencyList,
                                                                                    @NotNull Set<DiVertex> alreadySelectedVertices) {
        int lowestDijkstraScore = Integer.MAX_VALUE;
        DiEdge edgeWithLowestDijkstraScore = null;
        for (DiEdge edge : adjacencyList.edges()) {
            if (this.isEligibleEdge(edge, alreadySelectedVertices)) {
                int dijkstraScoreOfCurrentEdge = this.calculateDijkstraScore(edge);
                if (dijkstraScoreOfCurrentEdge < lowestDijkstraScore) {
                    lowestDijkstraScore = dijkstraScoreOfCurrentEdge;
                    edgeWithLowestDijkstraScore = edge;
                }
            }
        }
        return new ImmutablePair<>(edgeWithLowestDijkstraScore, lowestDijkstraScore);
    }
}
