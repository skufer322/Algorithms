package de.sk.nphard.tsp.impl;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.nphard.NpHardnessConstants;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Instantiable implementation of {@link AbstractTspSolver} creating the tour iteratively by selecting a random, not yet
 * visited vertex as the destination of the tour's next edge in each step.
 * <br>
 * Time complexity: O(n²).
 */
public class RandomTspSolver extends AbstractTspSolver {

    @Inject
    @Named(NpHardnessConstants.IN_SEED_FOR_RANDOM_IN_RANDOM_TSP_SOLVER)
    private long seed;

    private final Random random;

    private final Set<UnVertex> verticesInTour = new HashSet<>();

    /**
     * Constructor.
     */
    public RandomTspSolver() {
        this.random = new Random(this.seed);
    }

    @Override
    @NotNull Pair<List<UnEdge>, Integer> solveShortestTourProblemForCommonCases(@NotNull UnAdjacencyList adjacencyList) {
        this.clearDatastructures();
        List<UnVertex> vertices = adjacencyList.vertices();
        // select a random starting vertex
        UnVertex startingVertex = vertices.get(this.random.nextInt(vertices.size()));
        this.verticesInTour.add(startingVertex);
        UnVertex currentVertex = startingVertex;
        List<UnEdge> tour = new ArrayList<>();
        while (this.verticesInTour.size() < vertices.size()) {
            List<UnEdge> candidatesForNextEdge = this.getCandidatesForNextEdgeInTour(currentVertex);
            // randomly select one of the eligible candidates to be the next edge of the tour
            UnEdge nextEdge = candidatesForNextEdge.get(this.random.nextInt(candidatesForNextEdge.size()));
            tour.add(nextEdge);
            currentVertex = UndirectedGraphUtils.getOtherVertexOfEdge(nextEdge, currentVertex);
            this.verticesInTour.add(currentVertex);
        }
        // go back to starting vertex to complete the tour
        UnEdge edgeBackToStartingVertex = UndirectedGraphUtils.getEdgeConnectingVAndW(startingVertex, currentVertex);
        this.validateEdgeBackToStartingVertexIsNotNull(startingVertex, edgeBackToStartingVertex);
        tour.add(edgeBackToStartingVertex);
        // return tour information
        int lengthOfTour = tour.stream().map(UnEdge::getWeight).mapToInt(Integer::intValue).sum();
        return new ImmutablePair<>(tour, lengthOfTour);
    }

    private @NotNull List<UnEdge> getCandidatesForNextEdgeInTour(@NotNull UnVertex vertex) {
        return vertex.getEdges().stream()
                .filter(edge -> !this.verticesInTour.contains(UndirectedGraphUtils.getOtherVertexOfEdge(edge, vertex)))
                .toList();
    }

    private void clearDatastructures() {
        this.verticesInTour.clear();
    }
}
