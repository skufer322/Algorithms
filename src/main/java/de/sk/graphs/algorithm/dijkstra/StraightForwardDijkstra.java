package de.sk.graphs.algorithm.dijkstra;

import de.sk.graphs.algorithm.dijkstra.edgeselection.EdgeSelector;
import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class StraightForwardDijkstra extends AbstractDijkstra {

    @Inject
    private EdgeSelector edgeSelector;

    private final Set<DiVertex> processedVertices;

    public StraightForwardDijkstra() {
        this.processedVertices = new HashSet<>();
    }

    public void determineSingleSourceShortestPaths(@NotNull DiAdjacencyList adjacencyList, @NotNull DiVertex s) {
        // TODO: validität der ajdzenzliste prüfen -> nur kanten mit weight >= 0
        this.processedVertices.clear();
        this.setLenValues(adjacencyList, s);
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
