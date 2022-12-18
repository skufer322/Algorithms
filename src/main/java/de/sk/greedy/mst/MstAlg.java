package de.sk.greedy.mst;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface MstAlg {

    @NotNull List<UnEdge> determineMst(@NotNull UnAdjacencyList undirectedGraph);
}
