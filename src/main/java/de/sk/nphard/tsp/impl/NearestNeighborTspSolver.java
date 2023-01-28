package de.sk.nphard.tsp.impl;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Greedy instantiable implementation of {@link AbstractTspSolver}. Is a heuristic algorithm, i.e. the solution will be
 * suboptimal in the overwhelming majority of cases.
 * <br><br>
 * Time complexity: O(nm log m).
 */
public class NearestNeighborTspSolver extends AbstractTspSolver {

    static final String NOT_ALL_VERTICES_IN_TOUR_EXCEPTION_MSG_TF = "Internal error: Tour only contains %d vertices, but there are %d vertices in the graph";
    static final String NO_UNUSED_VERTEX_FOUND_EXCEPTION_MSG_TF = "Internal error: No unused vertex found which could serve as the next nearest neighbor for vertex %s in the tour.";

    private final Set<UnVertex> verticesInTour = new HashSet<>();

    @Override
    @NotNull Pair<List<UnEdge>, Integer> solveShortestTourProblemForCommonCases(@NotNull UnAdjacencyList adjacencyList) {
        this.clearDatastructures();
        List<UnVertex> vertices = adjacencyList.vertices();
        // select starting vertex and add it to verticesInTour (selection could also be randomized)
        UnVertex startingVertex = vertices.get(0);
        this.verticesInTour.add(startingVertex);
        // from the starting vertex, determine approximate shortest tour by greedily going for the nearest unused neighbor as next target vertex
        List<UnEdge> tour = new ArrayList<>();
        UnVertex currentVertex = startingVertex;
        while (tour.size() != vertices.size() - 1) {
            // determine the nearest neighbor and the edge connecting it with current vertex
            List<UnEdge> edgesSortedByLength = UndirectedGraphUtils.getEdgesSortedByWeight(currentVertex);
            Pair<UnVertex, UnEdge> nearestUnusedNeighborAndItsEdge = this.determineNearestUnusedNeighborAndItsEdge(edgesSortedByLength, currentVertex);
            // add the nearest neighbor to the vertices contained in the tour and the connecting edge to the tour
            this.verticesInTour.add(nearestUnusedNeighborAndItsEdge.getLeft());
            tour.add(nearestUnusedNeighborAndItsEdge.getRight());
            // update current vertex to the nearest neighbor
            currentVertex = nearestUnusedNeighborAndItsEdge.getLeft();
        }
        // go back to starting vertex to complete the tour
        UnEdge edgeBackToStartingVertex = UndirectedGraphUtils.getEdgeConnectingVAndW(currentVertex, startingVertex);
        this.validateEdgeBackToStartingVertexIsNotNull(startingVertex, edgeBackToStartingVertex);
        tour.add(edgeBackToStartingVertex);
        // check integrity of the solution
        if (this.verticesInTour.size() != vertices.size()) {
            throw new IllegalStateException(String.format(NOT_ALL_VERTICES_IN_TOUR_EXCEPTION_MSG_TF, this.verticesInTour.size(), vertices.size()));
        }
        // return tour information
        int lengthOfTour = tour.stream().map(UnEdge::getWeight).mapToInt(Integer::intValue).sum();
        return new ImmutablePair<>(tour, lengthOfTour);
    }

    private @NotNull Pair<UnVertex, UnEdge> determineNearestUnusedNeighborAndItsEdge(List<UnEdge> edgesSortedByLength, UnVertex vertex) {
        for (UnEdge edge : edgesSortedByLength) {
            UnVertex nextNearestNeighbor = UndirectedGraphUtils.getOtherVertexOfEdge(edge, vertex);
            if (!this.verticesInTour.contains(nextNearestNeighbor)) {
                return new ImmutablePair<>(nextNearestNeighbor, edge);
            }
        }
        throw new IllegalStateException(String.format(NO_UNUSED_VERTEX_FOUND_EXCEPTION_MSG_TF, vertex.getName()));
    }

    private void clearDatastructures() {
        this.verticesInTour.clear();
    }
}
