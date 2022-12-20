package de.sk.graphs.algorithm.dfs;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.GraphUtils;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Recursive implementation of {@link UnGraphDfs}.
 */
public class UnRecursiveGraphDfs implements UnGraphDfs {

    private int nextGraphSearchPosition;

    @Override
    public @NotNull List<UnVertex> conductDfs(@NotNull UnAdjacencyList adjacencyList, @NotNull UnVertex s) {
        this.nextGraphSearchPosition = GraphConstants.INITIAL_GRAPH_SEARCH_POSITION;
        List<UnVertex> verticesByDfs = adjacencyList.vertices();
        this.recursiveDfs(s);
        verticesByDfs.sort(GraphConstants.COMPARE_VERTICES_BY_GRAPH_SEARCH_POSITION);
        return verticesByDfs;
    }

    private void recursiveDfs(@NotNull UnVertex v) {
        if (!v.isExplored()) {
            v.setGraphSearchPosition(this.nextGraphSearchPosition++);
            v.setExplored(true);
            for (UnEdge edge : v.getEdges()) {
                UnVertex w = GraphUtils.getOtherVertexOfEdge(edge, v);
                this.recursiveDfs(w);
            }
        }
    }
}
