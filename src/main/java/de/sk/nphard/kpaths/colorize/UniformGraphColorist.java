package de.sk.nphard.kpaths.colorize;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.nphard.NpHardnessConstants;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * TODO
 */
public class UniformGraphColorist implements GraphColorist {

    @Inject
    @Named(NpHardnessConstants.IN_SEED_FOR_RANDOM_IN_UNIFORM_GRAPH_COLORIST)
    private long seed;
    private final Random random;

    public UniformGraphColorist() {
        this.random = new Random(this.seed);
    }

    @Override
    public @NotNull Map<UnVertex, Integer> colorizeGraph(@NotNull UnAdjacencyList adjacencyList, int k) {
        Map<UnVertex, Integer> verticesAndColors = new HashMap<>();
        Queue<Integer> colors = this.createAndShuffleUniformlyDistributedColors(adjacencyList, k);
        adjacencyList.vertices().forEach(vertex -> verticesAndColors.put(vertex, colors.poll()));
        return verticesAndColors;
    }

    private @NotNull Queue<Integer> createAndShuffleUniformlyDistributedColors(@NotNull UnAdjacencyList adjacencyList, int k) {
        LinkedList<Integer> colors = new LinkedList<>();
        int idxOfNextColor = 1;
        while (colors.size() < adjacencyList.vertices().size()) {
            colors.add(idxOfNextColor);
            idxOfNextColor = idxOfNextColor != k ? idxOfNextColor + 1 : 1;
        }
        Collections.shuffle(colors, this.random);
        return colors;
    }
}
