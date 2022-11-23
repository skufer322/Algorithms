package de.sk.graphs.algorithm.dfs;

import de.sk.graphs.GraphUtils;
import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DiSccDfs {

    private int numScc;

    @Inject
    private DiTopSort topSort;
//    private DiTopSort topSort = new DiTopSort();

    public @NotNull Map<Integer, List<DiVertex>> determineScc(@NotNull DiAdjacencyList adjacencyList) {
        List<DiVertex> vertices = adjacencyList.vertices();
        if (!vertices.isEmpty()) {
            // determine magic order
            List<DiVertex> reverseTopOrder = this.topSort.determineTopologicalOrdering(adjacencyList, true);
            GraphUtils.resetAttributesOfVertices(reverseTopOrder);
            this.numScc = 0;
            for (DiVertex v : reverseTopOrder) {
                if (!v.isExplored()) {
                    this.recursiveDfs(v);
                    this.numScc++;
                }
            }
        }
        return this.createSccFromProcessedVertices(adjacencyList);
    }

    private void recursiveDfs(@NotNull DiVertex v) {
        v.setExplored(true);
        v.setNumScc(this.numScc);
        for (DiEdge edge : v.getOutgoingEdges()) {
            DiVertex w = edge.head();
            if (!w.isExplored()) {
                this.recursiveDfs(w);
            }
        }
    }

    private @NotNull Map<Integer, List<DiVertex>> createSccFromProcessedVertices(@NotNull DiAdjacencyList adjacencyList){
        List<List<DiVertex>> scc = new ArrayList<>();
        return adjacencyList.vertices().stream().collect(Collectors.groupingBy(DiVertex::getNumScc));
    }
}
