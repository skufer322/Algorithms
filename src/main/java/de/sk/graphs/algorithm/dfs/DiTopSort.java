package de.sk.graphs.algorithm.dfs;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DiTopSort {

    private int nextTopSortPosition;

    public @NotNull List<DiVertex> determineTopologicalOrdering(@NotNull DiAdjacencyList adjacencyList, boolean isReverse) {
        List<DiVertex> vertices = adjacencyList.vertices();
        this.nextTopSortPosition = vertices.size();
        for (DiVertex s : vertices) {
            if (!s.isExplored()) {
                if (isReverse) {
                    this.reverseDfsTopSort(s);
                } else {
                    this.dfsTopSort(s);
                }
            }
        }
        vertices.sort(GraphConstants.COMPARE_VERTICES_BY_TOP_SORT_POSITION);
        return vertices;
    }

    private void dfsTopSort(@NotNull DiVertex v) {
        v.setExplored(true);
        for (DiEdge edge : v.getOutgoingEdges()) {
            DiVertex w = edge.head();
            if (!w.isExplored()) {
                this.dfsTopSort(w);
            }
        }
        v.setTopSortPosition(this.nextTopSortPosition--);
    }

    private void reverseDfsTopSort(@NotNull DiVertex v) {
        v.setExplored(true);
        for (DiEdge edge : v.getIncomingEdges()) {
            DiVertex w = edge.tail();
            if (!w.isExplored()) {
                this.reverseDfsTopSort(w);
            }
        }
        v.setTopSortPosition(this.nextTopSortPosition--);
    }
}
