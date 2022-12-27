package de.sk.graphs.algorithm.dijkstra;

import de.sk.graphs.util.DirectedGraphUtils;
import de.sk.graphs.algorithm.dijkstra.edgeselection.EdgeSelector;
import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Straight-forward implementation of {@link Dijkstra}'s algorithm for the single-source shortest path problem which runs in O(mn).
 */
public class StraightForwardDijkstra extends AbstractDijkstra {

    static final int ZERO = 0;

    @Inject
    private EdgeSelector edgeSelector;

    private final Set<DiVertex> processedVertices;

    /**
     * Constructor.
     */
    public StraightForwardDijkstra() {
        this.processedVertices = new HashSet<>();
    }

    @Override
    public void determineSingleSourceShortestPaths(@NotNull DiAdjacencyList adjacencyList, @NotNull DiVertex s) {
        DirectedGraphUtils.assertAllEdgesHaveWeightGreaterThan(adjacencyList, ZERO);
        this.processedVertices.clear();
        this.initializeLenValues(adjacencyList, s);
        this.processedVertices.add(s);
        Pair<DiEdge, Integer> eligibleEdgeWithLowestDijkstraScore = edgeSelector.selectEligibleEdgeWithLowestDijkstraScore(adjacencyList, processedVertices);
        while (eligibleEdgeWithLowestDijkstraScore.getLeft() != null) {
            DiVertex nextVertexToAdd = eligibleEdgeWithLowestDijkstraScore.getLeft().head();
            nextVertexToAdd.setLen(eligibleEdgeWithLowestDijkstraScore.getRight());
            processedVertices.add(nextVertexToAdd);
            eligibleEdgeWithLowestDijkstraScore = edgeSelector.selectEligibleEdgeWithLowestDijkstraScore(adjacencyList, processedVertices);
        }
    }
}
