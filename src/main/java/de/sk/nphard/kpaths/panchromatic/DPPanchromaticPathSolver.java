package de.sk.nphard.kpaths.panchromatic;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.nphard.GraphPathUtils;
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
    static final String INTERNAL_ERROR_COULD_NOT_RECONSTRUCT_IDX_OF_PREVIOUS_VERTEX_EXCEPTION_MSG_TF = "Internal error! Could not reconstruct the index of the previous vertex after the one with index %d.";

    static final int POSITIVE_INFINITY = Integer.MAX_VALUE;

    @Inject
    private SubsetGenerator subsetGenerator;

    private final Map<UnVertex, Integer> indexLookUpForVertices = new HashMap<>();
    private final Map<UnVertex, Integer> colorizedGraph = new HashMap<>();

    @Override
    public @NotNull Pair<List<UnEdge>, Integer> determineShortestPanchromaticKPath(@NotNull UnAdjacencyList adjacencyList, int k, @NotNull Map<UnVertex, Integer> colorCoding) {
        this.clearDatastructures();
        List<UnVertex> vertices = adjacencyList.vertices();
        this.indexLookUpForVertices.putAll(UndirectedGraphUtils.createIndexLookupForVertices(adjacencyList));
        this.colorizedGraph.putAll(colorCoding);
        int numberOfSubProblems = (int) (Math.pow(2, k) - 1);
        // the number of rows in the array of optimal solutions is 'numberOfSubProblems + 1' for convenience/clearer and easier implementation (row 0 will be left empty)
        int[][] optimalSolutions = new int[numberOfSubProblems + 1][vertices.size()];
        AdditionalArrayUtils.setAllElementsOfMatrixToValue(optimalSolutions, POSITIVE_INFINITY);
        // base cases -> there are k base cases (since all 1-subsets out of a k-set are created)
        List<Integer> indicesOfTheKBaseCases = this.subsetGenerator.generateAllKSubsetsOfAnNSetAsInts(1, k);
        for (int ithOfTheKBaseCases = 0; ithOfTheKBaseCases < indicesOfTheKBaseCases.size(); ithOfTheKBaseCases++) { // each of the k base case corresponds to a color ({0, 1, ..., k-1})
            int idxOfTheIthBaseCase = indicesOfTheKBaseCases.get(ithOfTheKBaseCases); // connection of the idx of the base case with the color the base case represents
            for (int idxOfCurrentVertex = 0; idxOfCurrentVertex < vertices.size(); idxOfCurrentVertex++) {
                UnVertex currentVertex = vertices.get(idxOfCurrentVertex);
                if (this.colorizedGraph.get(currentVertex) == ithOfTheKBaseCases) {
                    // if the color of currentVertex is the same as the color the current base case represents -> add empty path ending at currentVertex
                    optimalSolutions[idxOfTheIthBaseCase][idxOfCurrentVertex] = 0;
                }
            }
        }
        // systematically solve all sub problems
        for (int s = 2; s <= k; s++) { // s is the sub problem size
            List<Integer> indicesOfSubProblemsOfSizeS = this.subsetGenerator.generateAllKSubsetsOfAnNSetAsInts(s, k);
            for (int idxOfCurrentSubProblem : indicesOfSubProblemsOfSizeS) {
                for (UnVertex currentVertex : vertices) {
                    int colorOfCurrentVertex = this.colorizedGraph.get(currentVertex);
                    if (IntegerUtils.isNthBitSetForInteger(idxOfCurrentSubProblem, colorOfCurrentVertex)) { // verify the current vertex has a color which is relevant for the current sub problem
                        int lengthOfShortestSPathToCurrentVertex = this.determineShortestSPathToCurrentVertex(currentVertex, colorOfCurrentVertex, idxOfCurrentSubProblem, optimalSolutions);
                        int idxOfCurrentVertex = this.indexLookUpForVertices.get(currentVertex);
                        optimalSolutions[idxOfCurrentSubProblem][idxOfCurrentVertex] = lengthOfShortestSPathToCurrentVertex;
                    }
                }
            }
        }
        // @formatter:off
        int lengthOfShortestKPath = Arrays.stream(optimalSolutions[numberOfSubProblems]).min()
                                        .orElseThrow(() -> new IllegalStateException(String.format(INTERNAL_ERROR_COULD_NOT_DETERMINE_LENGTH_OF_SHORTEST_K_PATH_EXCEPTION_MSG_TF, adjacencyList))); // @formatter:on
        List<UnEdge> edgesOfShortestKPath = lengthOfShortestKPath != POSITIVE_INFINITY ? this.reconstructEdgesOfShortestKPath(optimalSolutions, k, adjacencyList) : Collections.emptyList();
        return new ImmutablePair<>(edgesOfShortestKPath, lengthOfShortestKPath);
    }

    private int determineShortestSPathToCurrentVertex(@NotNull UnVertex currentVertex, int colorOfCurrentVertex, int idxOfCurrentSubProblem, int[] @NotNull [] optimalSolutions) {
        int lenghtOfShortestSPath = POSITIVE_INFINITY;
        for (UnEdge incidentEdge : currentVertex.getEdges()) {
            UnVertex adjacentVertex = UndirectedGraphUtils.getOtherVertexOfEdge(incidentEdge, currentVertex);
            int colorOfAdjacentVertex = this.colorizedGraph.get(adjacentVertex);
            // an adjacent vertex is only to consider if it has a different color than the current vertex AND if its color is also relevant for the current sub problem
            if ((colorOfAdjacentVertex != colorOfCurrentVertex) && IntegerUtils.isNthBitSetForInteger(idxOfCurrentSubProblem, colorOfAdjacentVertex)) {
                int idxOfRelevantSmallerSubProblem = idxOfCurrentSubProblem - (int) Math.pow(2, colorOfCurrentVertex);
                int lengthOfShortestSMinus1PathToAdjacentVertex = optimalSolutions[idxOfRelevantSmallerSubProblem][this.indexLookUpForVertices.get(adjacentVertex)];
                if (lengthOfShortestSMinus1PathToAdjacentVertex != POSITIVE_INFINITY) { // verify there exists an (s-1)-path to the adjacent vertex
                    int lengthOfShortestSPathOverAdjacentVertex = lengthOfShortestSMinus1PathToAdjacentVertex + incidentEdge.getWeight();
                    lenghtOfShortestSPath = Math.min(lengthOfShortestSPathOverAdjacentVertex, lenghtOfShortestSPath);
                }
            }
        }
        // returns length of shortest s-path or POSITIVE_INFINITY if no such path exists
        return lenghtOfShortestSPath;
    }

    private @NotNull List<UnEdge> reconstructEdgesOfShortestKPath(int[] @NotNull [] optimalSolutions, int k, @NotNull UnAdjacencyList adjacencyList) {
        List<Pair<Integer, Integer>> edgesAsIndicesOfTheirVertices = new ArrayList<>();
        int idxOfCurrentVertex = AdditionalArrayUtils.idxOfSmallestElement(optimalSolutions[optimalSolutions.length - 1]);
        int idxOfCurrentSubProblem = optimalSolutions.length - 1;
        while (edgesAsIndicesOfTheirVertices.size() < k - 1) { // a k-path has k-1 edges
            int colorOfCurrentVertex = this.colorizedGraph.get(adjacencyList.vertices().get(idxOfCurrentVertex));
            int idxOfRelevantSmallerSubProblem = idxOfCurrentSubProblem - (int) Math.pow(2, colorOfCurrentVertex);
            int idxOfPreviousVertex = this.getIdxOfPreviousVertex(idxOfCurrentVertex, idxOfCurrentSubProblem, idxOfRelevantSmallerSubProblem, optimalSolutions, adjacencyList.vertices());
            edgesAsIndicesOfTheirVertices.add(new ImmutablePair<>(idxOfCurrentVertex, idxOfPreviousVertex));
            idxOfCurrentVertex = idxOfPreviousVertex;
            idxOfCurrentSubProblem = idxOfRelevantSmallerSubProblem;
        }
        Collections.reverse(edgesAsIndicesOfTheirVertices); // flip the order of the edges since the solution is reconstructed backwards (could also be omitted)
        return GraphPathUtils.convertPathToRepresentationFittingAnAdjacencyList(edgesAsIndicesOfTheirVertices, adjacencyList);
    }

    private int getIdxOfPreviousVertex(int idxOfCurrentVertex, int idxOfCurrentSubProblem, int idxOfRelevantSmallerSubProblem, int[] @NotNull [] optimalSolutions, @NotNull List<UnVertex> vertices) {
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
        throw new IllegalStateException(String.format(INTERNAL_ERROR_COULD_NOT_RECONSTRUCT_IDX_OF_PREVIOUS_VERTEX_EXCEPTION_MSG_TF, idxOfCurrentVertex));
    }

    private void clearDatastructures() {
        this.indexLookUpForVertices.clear();
        this.colorizedGraph.clear();
    }
}
