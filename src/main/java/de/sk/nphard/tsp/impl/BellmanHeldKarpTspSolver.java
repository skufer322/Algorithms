package de.sk.nphard.tsp.impl;

import de.sk.basics.ch1.multiply.MultiplierUtils;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnAdjacencyMatrix;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.nphard.tsp.TspUtils;
import de.sk.nphard.tsp.piggyback.SubsetGenerator;
import de.sk.util.IntegerUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Instantiable implementation of {@link AbstractTspSolver} utilizing the Bellman-Held-Karp Algorithm, a dynamic programming algorithm.
 * <br><br>
 * Time complexity: O(n²2ⁿ ).
 */
public class BellmanHeldKarpTspSolver extends AbstractTspSolver {

    static final String NO_PATH_TO_VERTEX_COULD_BE_CALCULATED_EXCEPTION_MSG_TF = "Internal Error! Could not calculate a minimum length path to vertex '%d'.";

    static final int MARKER_NON_EXISTING_ENTRY = 0;

    @Inject
    private SubsetGenerator subsetGenerator;

    @Override
    @NotNull Pair<List<UnEdge>, Integer> solveShortestTourProblemForCommonCases(@NotNull UnAdjacencyList adjacencyList) {
        // convert graph to adjacency matrix representation
        UnAdjacencyMatrix adjacencyMatrix = UndirectedGraphUtils.convertToAdjacencyMatrix(adjacencyList);
        int numberOfSubProblems = (int) Math.pow(2, adjacencyMatrix.getNumberOfVertices() - 1) - 1;
        // the vertex at index 0 is fixed as starting vertex and contained in all subsets -> does not need to be considered with an own column in the array of optimal solutions
        int numberOfVerticesToConsider = adjacencyMatrix.getNumberOfVertices() - 1;
        // the number of rows in the array of optimal solutions is 'numberOfSubProblems + 1' for convenience/clearer and easier implementation (row 0 will be left empty)
        int[][] optimalSolutions = new int[numberOfSubProblems + 1][numberOfVerticesToConsider];
        // solve base cases
        List<Integer> indicesOfSubProblemsToConsider = this.subsetGenerator.generateAllKSubsetsOfAnNSetAsInts(1, numberOfVerticesToConsider);
        for (int j = 0; j < numberOfVerticesToConsider; j++) {
            int idxOfBaseCase = indicesOfSubProblemsToConsider.get(j);
            int idxOfVertexInAdjacencyMatrix = j + 1; // account for that the adjacency matrix contains the starting vertex while the optimal solutions do not
            optimalSolutions[idxOfBaseCase][j] = adjacencyMatrix.getEdgeWeight(0, idxOfVertexInAdjacencyMatrix);
        }
        // systematically solve all sub problems
        for (int s = 2; s < adjacencyMatrix.getNumberOfVertices(); s++) { // s is the sub problem size
            indicesOfSubProblemsToConsider = this.subsetGenerator.generateAllKSubsetsOfAnNSetAsInts(s, numberOfVerticesToConsider);
            for (int idxOfCurrentSubProblem : indicesOfSubProblemsToConsider) {
                //j is the index of the vertex which is considered to be the latest add to the current sub problem
//                for (int j = 0; j < Math.min(numberOfVerticesToConsider, idxOfCurrentSubProblem; j++) { // TODO: welches Math.min ist schneller?
                for (int j = 0; j < Math.min(numberOfVerticesToConsider, MultiplierUtils.getNextGreaterPowerOfTwo(idxOfCurrentSubProblem)); j++) {
                    if (IntegerUtils.isNthBitSetForInteger(idxOfCurrentSubProblem, j)) { // j + 1 = account for that the starting vertex does not have a column in optimalSolutions
                        int idxOfRelevantSmallerSubProblem = idxOfCurrentSubProblem - (int) Math.pow(2, j); // j + 1
                        optimalSolutions[idxOfCurrentSubProblem][j] = this.calculateMinimumPathLengthToVertexJ(idxOfRelevantSmallerSubProblem, j, optimalSolutions, adjacencyMatrix);
                    }
                }
            }
        }
        // summarize information of determined optimal tour
        int costOfOptimalTour = this.calculateMinimumPathLengthToVertexJ(numberOfSubProblems, -1, optimalSolutions, adjacencyMatrix);
        // reconstruct the edges of the optimal tour
        List<Integer> idxOfVerticesOfTour = this.reconstructVerticesOfOptimalTour(optimalSolutions, adjacencyMatrix);
        List<UnEdge> tourAsEdges = this.reconstructTourOfEdges(idxOfVerticesOfTour, adjacencyList);
        return new ImmutablePair<>(tourAsEdges, costOfOptimalTour);
    }

    private int calculateMinimumPathLengthToVertexJ(int idxOfRelevantSmallerSubProblem, int j, int[] @NotNull [] optimalSolutions, @NotNull UnAdjacencyMatrix adjacencyMatrix) {
        int minimumPathLength = Integer.MAX_VALUE;
        for (int k = 0; k < optimalSolutions[idxOfRelevantSmallerSubProblem].length; k++) {
            if (k != j && optimalSolutions[idxOfRelevantSmallerSubProblem][k] != MARKER_NON_EXISTING_ENTRY) {
                int currentPathLength = this.calculateCosts(optimalSolutions, idxOfRelevantSmallerSubProblem, k, j, adjacencyMatrix);
                if (currentPathLength < minimumPathLength) {
                    minimumPathLength = currentPathLength;
                }
            }
        }
        if (minimumPathLength == Integer.MAX_VALUE) {
            // j + 1 -> to account for adjacency matrix containing the starting vertex (while optimumSolutions does not)
            throw new IllegalStateException(String.format(NO_PATH_TO_VERTEX_COULD_BE_CALCULATED_EXCEPTION_MSG_TF, j + 1));
        }
        return minimumPathLength;
    }

    private @NotNull List<Integer> reconstructVerticesOfOptimalTour(int[] @NotNull [] optimalSolutions, @NotNull UnAdjacencyMatrix adjacencyMatrix) {
        List<Integer> idxOfVerticesOfTour = new ArrayList<>();
        // indices relate to the positions of the vertices in optimalSolutions (their respective position in the adjacency matrix are their index+1)
        int idxOfRelevantSubProblem = optimalSolutions.length - 1;
        int idxOfTargetVertex = -1; // init to -1 because the starting vertex has no index in optimalSolutions
        while (idxOfVerticesOfTour.size() != adjacencyMatrix.getNumberOfVertices() - 1) {
            int[] solutionsForCurrentSubProblem = optimalSolutions[idxOfRelevantSubProblem];
            int lowestCosts = Integer.MAX_VALUE;
            int idxOfPenultimateVertex = -1;
            for (int k = 0; k < solutionsForCurrentSubProblem.length; k++) {
                if (k != idxOfTargetVertex && optimalSolutions[idxOfRelevantSubProblem][k] != MARKER_NON_EXISTING_ENTRY) {
                    int currentCosts = this.calculateCosts(optimalSolutions, idxOfRelevantSubProblem, k, idxOfTargetVertex, adjacencyMatrix);
                    if (currentCosts < lowestCosts) {
                        lowestCosts = currentCosts;
                        idxOfPenultimateVertex = k;
                    }
                }
            }
            idxOfVerticesOfTour.add(idxOfPenultimateVertex + 1); // idxOfPenultimateVertex + 1 -> account for indices in adjacency matrix containing the starting vertex
            idxOfRelevantSubProblem -= (int) Math.pow(2, idxOfPenultimateVertex);
            idxOfTargetVertex = idxOfPenultimateVertex;
        }
        // add starting vertex
        idxOfVerticesOfTour.add(0);
        // reverse idxOfVerticesOfTour to start the tour at the starting vertex
        Collections.reverse(idxOfVerticesOfTour);
        return idxOfVerticesOfTour;
    }

    private int calculateCosts(int[] @NotNull [] optimalSolutions, int idxOfRelevantSubProblem, int penultimateVertex, int targetVertex, @NotNull UnAdjacencyMatrix adjacencyMatrix) {
        // penultimateVertex + 1 && targetVertex + 1 -> account for adjacency matrix containing the starting vertex (while optimumSolutions does not)
        return optimalSolutions[idxOfRelevantSubProblem][penultimateVertex] + adjacencyMatrix.getEdgeWeight(penultimateVertex + 1, targetVertex + 1);
    }

    private @NotNull List<UnEdge> reconstructTourOfEdges(@NotNull List<Integer> idxOfVerticesOfTour, @NotNull UnAdjacencyList adjacencyList) {
        List<Pair<Integer, Integer>> edgesAsIndicesOfTheirVertices = new ArrayList<>();
        for (int i = 0; i < idxOfVerticesOfTour.size(); i++) {
            int j = i == idxOfVerticesOfTour.size() - 1 ? 0 : i + 1;
            Pair<Integer, Integer> nextEdgeIndices = new ImmutablePair<>(idxOfVerticesOfTour.get(i), idxOfVerticesOfTour.get(j));
            edgesAsIndicesOfTheirVertices.add(nextEdgeIndices);
        }
        return TspUtils.determineTourFromRepresentationFittingAnAdjacencyMatrix(edgesAsIndicesOfTheirVertices, adjacencyList);
    }
}
