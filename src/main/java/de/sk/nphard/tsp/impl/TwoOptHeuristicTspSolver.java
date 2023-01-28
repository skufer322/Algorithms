package de.sk.nphard.tsp.impl;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnAdjacencyMatrix;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.greedy.mst.datastructure.unionfind.UnionFind;
import de.sk.greedy.mst.datastructure.unionfind.UnionFindSizeBased;
import de.sk.nphard.NpHardnessConstants;
import de.sk.nphard.tsp.TspSolver;
import de.sk.nphard.tsp.TspUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Instantiable implementation of {@link AbstractTspSolver} utilizing the 2-OPT Heuristic Algorithm, a local search algorithm.
 * <br><br>
 * From an initial solution, iteratively explores the neighborhood of alternative solutions until it ends up at a local optimum.
 * Is not guaranteed to halt within a polynomial amount of iterations, but can be interrupted anytime.
 */
public class TwoOptHeuristicTspSolver extends AbstractTspSolver {

    @Inject
    @Named(NpHardnessConstants.IN_NEAREST_NEIGHBOR_TSP_SOLVER)
//    @Named(NpHardnessConstants.IN_RANDOM_TSP_SOLVER)
    private TspSolver initialSolutionProvider;

    @Override
    @NotNull Pair<List<UnEdge>, Integer> solveShortestTourProblemForCommonCases(@NotNull UnAdjacencyList adjacencyList) {
        // determine initial solution
        Pair<List<UnEdge>, Integer> initialSolution = this.initialSolutionProvider.determineShortestTour(adjacencyList);
        // convert graph and tour to adjacency matrix respectively adjacency-matrix-suitable representations
        UnAdjacencyMatrix adjacencyMatrix = UndirectedGraphUtils.convertToAdjacencyMatrix(adjacencyList);
        List<Pair<Integer, Integer>> tour = TspUtils.convertTourToRepresentationFittingAnAdjacencyMatrix(initialSolution.getLeft(), adjacencyList);
        boolean isStillImproving = true;
        while (isStillImproving) {
//            System.out.println(tour.stream().map(edge -> adjacencyMatrix.getEdgeWeight(edge.getLeft(), edge.getRight())).mapToInt(costs -> costs).sum());
            isStillImproving = false;
            for (int i = 0; i < tour.size() - 1; i++) {
                Pair<Integer, Integer> firstEdge = tour.get(i);
                for (int j = i + 1; j < tour.size(); j++) {
                    Pair<Integer, Integer> secondEdge = tour.get(j);
                    if (this.areFourDistinctEndpointsInvolved(firstEdge, secondEdge)) {
                        // determine the two possible 2changes
                        List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> possible2Changes = this.createPossible2Changes(firstEdge, secondEdge);
                        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> firstPossible2Change = possible2Changes.get(0);
                        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> secondPossible2Change = possible2Changes.get(1);
                        // determine which one creates a new tour
                        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> relevant2Change = this.verifyIfNewTourIsCreated(firstPossible2Change, tour, i, j) ? firstPossible2Change : secondPossible2Change;
                        // validate if the new tour is cheaper than the current tour
                        if (this.isImproving2Change(relevant2Change, tour.get(i), tour.get(j), adjacencyMatrix)) {
                            // TODO mögliche strategie in eigene klassen auslagern?
                            // 2change results in an improving tour -> conduct the 2change; an alternative strategy would e.g. collect all improving 2changes and then select the best of these
                            tour.set(i, relevant2Change.getLeft());
                            tour.set(j, relevant2Change.getRight());
                            isStillImproving = true;
                            break;
                        }
                    }
                }
                if (isStillImproving) {
                    break;
                }
            }
        }
        List<UnEdge> tourRepresentationFittingAdjacencyList = TspUtils.determineTourFromRepresentationFittingAnAdjacencyMatrix(tour, adjacencyList);
        int lengthOfShortestTour = tour.stream().map(edge -> adjacencyMatrix.getEdgeWeight(edge.getLeft(), edge.getRight())).mapToInt(costs -> costs).sum();
        return new ImmutablePair<>(tourRepresentationFittingAdjacencyList, lengthOfShortestTour);
    }

    private boolean areFourDistinctEndpointsInvolved(@NotNull Pair<Integer, Integer> firstEdge, @NotNull Pair<Integer, Integer> secondEdge) {
        Set<Integer> involvedVertices = new HashSet<>(List.of(firstEdge.getLeft(), firstEdge.getRight(), secondEdge.getLeft(), secondEdge.getRight()));
        return involvedVertices.size() == 4;
    }

    private @NotNull List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> createPossible2Changes(@NotNull Pair<Integer, Integer> firstEdge, @NotNull Pair<Integer, Integer> secondEdge) {
        Pair<Integer, Integer> edge1OfFirst2Change = new ImmutablePair<>(firstEdge.getLeft(), secondEdge.getLeft());
        Pair<Integer, Integer> edge2OfFirst2Change = new ImmutablePair<>(firstEdge.getRight(), secondEdge.getRight());
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> first2Change = new ImmutablePair<>(edge1OfFirst2Change, edge2OfFirst2Change);
        Pair<Integer, Integer> edge1OfSecond2Change = new ImmutablePair<>(firstEdge.getLeft(), secondEdge.getRight());
        Pair<Integer, Integer> edge2OfSecond2Change = new ImmutablePair<>(firstEdge.getRight(), secondEdge.getLeft());
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> second2Change = new ImmutablePair<>(edge1OfSecond2Change, edge2OfSecond2Change);
        return List.of(first2Change, second2Change);
    }

    private boolean verifyIfNewTourIsCreated(@NotNull Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> twoChange, @NotNull List<Pair<Integer, Integer>> tour, int idxOfEdge1ToReplace, int idxOfEdge2ToReplace) {
        List<Integer> indices = IntStream.range(0, tour.size()).boxed().toList();
        UnionFind<Integer> unionFind = new UnionFindSizeBased<>();
        unionFind.initialize(indices);
        for (int i = 0; i < tour.size(); i++) {
            Pair<Integer, Integer> currentEdge = tour.get(i);
            if (i == idxOfEdge1ToReplace || i == idxOfEdge2ToReplace) {
                currentEdge = i == idxOfEdge1ToReplace ? twoChange.getLeft() : twoChange.getRight();
            }
            unionFind.union(currentEdge.getLeft(), currentEdge.getRight());
        }
        // TODO could be faster if the unionFind data structure would offer a method to determine the size of the partition an element belongs to
        return indices.stream().map(unionFind::find).distinct().count() == 1;
    }

    private boolean isImproving2Change(@NotNull Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> twoChange, @NotNull Pair<Integer, Integer> edgeToReplace1,
                                       @NotNull Pair<Integer, Integer> edgeToReplace2, @NotNull UnAdjacencyMatrix graph) {
        int costsOfRemovedEdges = graph.getEdgeWeight(edgeToReplace1.getLeft(), edgeToReplace1.getRight()) + graph.getEdgeWeight(edgeToReplace2.getLeft(), edgeToReplace2.getRight());
        Pair<Integer, Integer> newEdge1 = twoChange.getLeft();
        Pair<Integer, Integer> newEdge2 = twoChange.getRight();
        int costsOfNewEdges = graph.getEdgeWeight(newEdge1.getLeft(), newEdge1.getRight()) + graph.getEdgeWeight(newEdge2.getLeft(), newEdge2.getRight());
        return costsOfNewEdges < costsOfRemovedEdges;
    }
}
