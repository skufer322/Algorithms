package de.sk.nphard.kpaths.colorize;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.nphard.NpHardnessConstants;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Implementation of {@link GraphColorist} assigning color codes uniformly at random to the vertices of a graph.
 */
public class UniformGraphColorist implements GraphColorist {

    @Inject
    @Named(NpHardnessConstants.IN_SEED_FOR_RANDOM_IN_UNIFORM_GRAPH_COLORIST)
    private long seed;
    private final Random random;

    public UniformGraphColorist() {
        this.random = new Random(this.seed);
    }

    /**
     * Colorizes the given graph (represented as adjacency list) with {@code k} different colors. The first color index
     * starts at {@code smallestIdx}, the last color index is {@code smallestIdx} + {@code k}.
     * <br>
     * In the returned map, a vertex is the key and the index of the color assigned to the very vertex is its corresponding
     * value.
     * <br>
     * The distribution of the color codes is uniformly at random.
     *
     * @param adjacencyList graph for which the vertices are to be colored
     * @param k             number of different colors for the color coding
     * @param smallestIdx   smallest index of a color
     * @return map with the graph's vertices as keys and the index of their assigned color as values
     */
    @Override
    public @NotNull Map<UnVertex, Integer> colorizeGraph(@NotNull UnAdjacencyList adjacencyList, int k, int smallestIdx) {
        Map<UnVertex, Integer> verticesAndColors = new HashMap<>();
        Queue<Integer> colors = this.createAndShuffleUniformlyDistributedColors(adjacencyList, k, smallestIdx);
        adjacencyList.vertices().forEach(vertex -> verticesAndColors.put(vertex, colors.poll()));
        return verticesAndColors;
    }

    private @NotNull Queue<Integer> createAndShuffleUniformlyDistributedColors(@NotNull UnAdjacencyList adjacencyList, int k, int smallestIdx) {
        LinkedList<Integer> colors = new LinkedList<>();
        int idxOfNextColor = smallestIdx;
        while (colors.size() < adjacencyList.vertices().size()) {
            colors.add(idxOfNextColor);
            idxOfNextColor = idxOfNextColor != (smallestIdx + k) ? idxOfNextColor + 1 : smallestIdx;
        }
        Collections.shuffle(colors, this.random);
        return colors;
    }
}
