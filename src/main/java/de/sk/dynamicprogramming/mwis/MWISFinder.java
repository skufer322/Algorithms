package de.sk.dynamicprogramming.mwis;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of a dynamic programming algorithm to determine the maximum weighted independent set for a given path
 * graph.
 */
public class MWISFinder {

    /**
     * Returns the set of vertices which form the maximum weighted independent set for the given path graph (represented
     * as adjacency list). In an independent set, there are no two vertices which are directly connected via an edge
     * (i.e. an independent set does not contain both endpoints of any edge). The maximum weighted independent set is,
     * of all possible independent set, the independent set whose vertices have the largest sum of weights.
     *
     * @param adjacencyList path graph for which the maximum weighted independent set is to be determined
     * @return the set of vertices which form the maximum weighted independent for the given path graph
     */
    public @NotNull Collection<UnVertex> determineMWIS(@NotNull UnAdjacencyList adjacencyList) {
        List<UnVertex> verticesInPathOrder = MWISUtils.getVerticesOfPathGraphInOrderOfPath(adjacencyList);
        double[] optimumMWISValues = this.calculateMWISForEachSubProblem(verticesInPathOrder);
        return this.reconstructMWISMembers(optimumMWISValues, verticesInPathOrder);
    }

    private double @NotNull [] calculateMWISForEachSubProblem(@NotNull List<UnVertex> verticesInPathOrder) {
        double[] optimumMWISValues = new double[verticesInPathOrder.size() + 1];
        // base case
        optimumMWISValues[0] = 0;
        optimumMWISValues[1] = verticesInPathOrder.get(0).getWeight();
        for (int i = 2; i < optimumMWISValues.length; i++) {
            UnVertex nextVertex = verticesInPathOrder.get(i - 1);
            optimumMWISValues[i] = Math.max(optimumMWISValues[i - 1], optimumMWISValues[i - 2] + nextVertex.getWeight());
        }
        return optimumMWISValues;
    }

    private @NotNull Collection<UnVertex> reconstructMWISMembers(double @NotNull [] optimumMWISValues,
                                                                 @NotNull List<UnVertex> verticesInPathOrder) {
        List<UnVertex> solution = new ArrayList<>();
        int i = optimumMWISValues.length - 1;
        while (i >= 2) {
            UnVertex currentVertex = verticesInPathOrder.get(i - 1);
            if (optimumMWISValues[i - 2] + currentVertex.getWeight() >= optimumMWISValues[i - 1]) {
                solution.add(currentVertex); // include current vertex
                i -= 2; // exclude predecessor of current vertex
            } else {
                i--; // exclude current vertex
            }
        }
        if (i == 1) {
            solution.add(verticesInPathOrder.get(0)); // include first vertex
        }
        return solution;
    }
}
