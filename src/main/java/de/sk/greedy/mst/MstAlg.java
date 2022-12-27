package de.sk.greedy.mst;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Interface defining the methods for implementations of Minimum Spanning Tree (MST) algorithms.
 */
public interface MstAlg {

    /**
     * Determines the Minimum Spanning Tree (MST) for the given connected, undirected graph. Returns the list of edges which
     * are selected for the MST.
     *
     * @param undirectedGraph graph for which the MST is to be determined
     * @return list of edges which are selected for the MST
     */
    @NotNull List<UnEdge> determineMst(@NotNull UnAdjacencyList undirectedGraph);
}
