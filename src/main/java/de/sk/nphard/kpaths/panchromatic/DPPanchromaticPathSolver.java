package de.sk.nphard.kpaths.panchromatic;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.nphard.tsp.TspUtils;
import de.sk.nphard.tsp.piggyback.SubsetGenerator;
import de.sk.util.AdditionalArrayUtils;
import de.sk.util.IntegerUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.*;

/**
 * Dynamic programming algorithm implementing {@link PanchromaticPathSolver}.
 */
public class DPPanchromaticPathSolver implements PanchromaticPathSolver {

    static final String INTERNAL_ERROR_COULD_NOT_DETERMINE_LENGTH_OF_SHORTEST_K_PATH_EXCEPTION_MSG_TF = "Internal error! Could not find a shortest k-path for graph %s.";
    static final String INTERNAL_ERROR_COULD_NOT_RECONSTRUCT_IDX_OF_NEXT_VERTEX_EXCEPTION_MSG_TF = "Internal error! Could not reconstruct index of the next vertex after the one with index %d.";

    static final int POSITIVE_INFINITY = Integer.MAX_VALUE;

    @Inject
    private SubsetGenerator subsetGenerator;

    private final Map<UnVertex, Integer> indexLookUpForVertices = new HashMap<>();
    private final Map<UnVertex, Integer> colorizedGraph = new HashMap<>();

    @Override
    public @NotNull Pair<List<UnEdge>, Integer> determineShortestPanchromaticKPath(@NotNull UnAdjacencyList adjacencyList, int k, @NotNull Map<UnVertex, Integer> colorCoding) {
        List<UnVertex> vertices = adjacencyList.vertices();
        this.clearDatastructures();
        this.indexLookUpForVertices.putAll(UndirectedGraphUtils.createIndexLookupForVertices(adjacencyList));
        this.colorizedGraph.putAll(colorCoding);
        int numberOfSubProblems = (int) (Math.pow(2, k) - 1);
        // the number of rows in the array of optimal solutions is 'numberOfSubProblems + 1' for convenience/clearer and easier implementation (row 0 will be left empty)
        int[][] optimalSolutions = new int[numberOfSubProblems + 1][vertices.size()];
        AdditionalArrayUtils.setAllElementsOfMatrixToValue(optimalSolutions, POSITIVE_INFINITY);
        // base cases
        List<Integer> indicesOfBaseCases = this.subsetGenerator.generateAllKSubsetsOfAnNSetAsInts(1, k);
        for (int ithOfKBaseCases = 0; ithOfKBaseCases < indicesOfBaseCases.size(); ithOfKBaseCases++) {
            int idxOfBaseCase = indicesOfBaseCases.get(ithOfKBaseCases);
            for (int idxOfVertex = 0; idxOfVertex < vertices.size(); idxOfVertex++) {
                UnVertex vertex = vertices.get(idxOfVertex);
                if (this.colorizedGraph.get(vertex) == ithOfKBaseCases) {
                    optimalSolutions[idxOfBaseCase][idxOfVertex] = 0;
                }
            }
        }
        // systematically solve all sub problems
        for (int s = 2; s <= k; s++) { // s is the sub problem size
            List<Integer> indicesOfSubProblemsOfCurrentSize = this.subsetGenerator.generateAllKSubsetsOfAnNSetAsInts(s, k);
            for (int idxOfCurrentSubProblem : indicesOfSubProblemsOfCurrentSize) {
                for (UnVertex currentVertex : vertices) {
                    int colorOfCurrentVertex = this.colorizedGraph.get(currentVertex);
                    if (IntegerUtils.isNthBitSetForInteger(idxOfCurrentSubProblem, colorOfCurrentVertex)) { // verify the current vertex has a color which is relevant for the current sub problem
                        int lengthOfShortestSPathToCurrentVertex = this.determineShortestSPathToCurrentVertex(currentVertex, colorOfCurrentVertex, idxOfCurrentSubProblem, optimalSolutions);
                        optimalSolutions[idxOfCurrentSubProblem][this.indexLookUpForVertices.get(currentVertex)] = lengthOfShortestSPathToCurrentVertex;
                    }
                }
            }
        }
        int lengthOfShortestKPath = Arrays.stream(optimalSolutions[numberOfSubProblems]).min()
                .orElseThrow(() -> new IllegalStateException(String.format(INTERNAL_ERROR_COULD_NOT_DETERMINE_LENGTH_OF_SHORTEST_K_PATH_EXCEPTION_MSG_TF, adjacencyList)));
        List<UnEdge> edgesOfShortestKPath = lengthOfShortestKPath != POSITIVE_INFINITY ? this.reconstructEdgesOfShortestKPath(optimalSolutions, k, adjacencyList) : Collections.emptyList();
        return new ImmutablePair<>(edgesOfShortestKPath, lengthOfShortestKPath);
    }

    private int determineShortestSPathToCurrentVertex(@NotNull UnVertex currentVertex, int colorOfCurrentVertex, int idxOfCurrentSubProblem, int[] @NotNull [] optimalSolutions) {
        int lenghtOfShortestPath = Integer.MAX_VALUE;
        for (UnEdge incidentEdge : currentVertex.getEdges()) {
            UnVertex adjacentVertex = UndirectedGraphUtils.getOtherVertexOfEdge(incidentEdge, currentVertex);
            int colorOfAdjacentVertex = this.colorizedGraph.get(adjacentVertex);
            // an adjacent vertex is only to consider if it has a different color than the current vertex AND its color is relevant for the current sub problem
            if (colorOfAdjacentVertex != colorOfCurrentVertex && IntegerUtils.isNthBitSetForInteger(idxOfCurrentSubProblem, colorOfAdjacentVertex)) {
                int idxOfRelevantSmallerSubProblem = idxOfCurrentSubProblem - (int) Math.pow(2, colorOfCurrentVertex);
                int lengthOfShortestSMinus1PathToAdjacentVertex = optimalSolutions[idxOfRelevantSmallerSubProblem][this.indexLookUpForVertices.get(adjacentVertex)];
                if (lengthOfShortestSMinus1PathToAdjacentVertex != POSITIVE_INFINITY) { // verify there exists an s-1 path to the adjacent vertex
                    int currentCosts = lengthOfShortestSMinus1PathToAdjacentVertex + incidentEdge.getWeight();
                    lenghtOfShortestPath = Math.min(currentCosts, lenghtOfShortestPath);
                }
            }
        }
        return lenghtOfShortestPath;
    }

    private @NotNull List<UnEdge> reconstructEdgesOfShortestKPath(int[] @NotNull [] optimalSolutions, int k, @NotNull UnAdjacencyList adjacencyList) {
        List<Pair<Integer, Integer>> edgesAsIndicesOfTheirVertices = new ArrayList<>();
        int idxOfCurrentVertex = AdditionalArrayUtils.idxOfSmallestElement(optimalSolutions[optimalSolutions.length - 1]);
        int idxOfCurrentSubProblem = optimalSolutions.length - 1;
        while (edgesAsIndicesOfTheirVertices.size() < k - 1) { // a k-path has k-1 edges
            int colorOfCurrentVertex = this.colorizedGraph.get(adjacencyList.vertices().get(idxOfCurrentVertex));
            int idxOfRelevantSmallerSubProblem = idxOfCurrentSubProblem - (int) Math.pow(2, colorOfCurrentVertex);
            int idxOfNextVertex = this.getIdxOfNextVertex(idxOfCurrentSubProblem, idxOfCurrentVertex, idxOfRelevantSmallerSubProblem, optimalSolutions, adjacencyList.vertices());
            edgesAsIndicesOfTheirVertices.add(new ImmutablePair<>(idxOfCurrentVertex, idxOfNextVertex));
            idxOfCurrentVertex = idxOfNextVertex;
            idxOfCurrentSubProblem = idxOfRelevantSmallerSubProblem;
        }
        Collections.reverse(edgesAsIndicesOfTheirVertices); // flip the order of the edges since the solution is reconstructed backwards (could also be omitted)
        return TspUtils.determineTourFromRepresentationFittingAnAdjacencyMatrix(edgesAsIndicesOfTheirVertices, adjacencyList);
    }

    private int getIdxOfNextVertex(int idxOfCurrentSubProblem, int idxOfCurrentVertex, int idxOfRelevantSmallerSubProblem, int[] @NotNull [] optimalSolutions, @NotNull List<UnVertex> vertices) {
        UnVertex currentVertex = vertices.get(idxOfCurrentVertex);
        int lengthOfShortestSPath = optimalSolutions[idxOfCurrentSubProblem][idxOfCurrentVertex];
        for (UnEdge incidentEdge : currentVertex.getEdges()) {
            UnVertex adjacentVertex = UndirectedGraphUtils.getOtherVertexOfEdge(incidentEdge, currentVertex);
            int colorOfAdjacentVertex = this.colorizedGraph.get(adjacentVertex);
            if (colorOfAdjacentVertex != this.colorizedGraph.get(currentVertex) && IntegerUtils.isNthBitSetForInteger(idxOfRelevantSmallerSubProblem, colorOfAdjacentVertex)) {
                int idxOfAdjacentVertex = this.indexLookUpForVertices.get(adjacentVertex);
                int lengthOfShortestSMinus1PathToAdjacentVertex = optimalSolutions[idxOfRelevantSmallerSubProblem][idxOfAdjacentVertex];
                if (lengthOfShortestSMinus1PathToAdjacentVertex + incidentEdge.getWeight() == lengthOfShortestSPath) {
                    return idxOfAdjacentVertex;
                }
            }
        }
        throw new IllegalStateException(String.format(INTERNAL_ERROR_COULD_NOT_RECONSTRUCT_IDX_OF_NEXT_VERTEX_EXCEPTION_MSG_TF, idxOfCurrentVertex));
    }

    private void clearDatastructures() {
        this.indexLookUpForVertices.clear();
        this.colorizedGraph.clear();
    }
}
