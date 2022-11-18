package de.sk.graphs.dfs;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.GraphUtils;
import de.sk.graphs.datastructure.AdjacencyList;
import de.sk.graphs.datastructure.Edge;
import de.sk.graphs.datastructure.Vertex;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Recursive {@link GraphDfs} implementation.
 */
public class RecursiveGraphDfs implements GraphDfs {

    private int nextGraphSearchPosition;

    @Override
    public @NotNull List<Vertex> conductDfs(@NotNull AdjacencyList adjacencyList, @NotNull Vertex s) {
        this.nextGraphSearchPosition = GraphConstants.INITIAL_GRAPH_SEARCH_POSITION;
        List<Vertex> verticesByDfs = new ArrayList<>(adjacencyList.vertices());
        this.recursiveDfs(s);
        verticesByDfs.sort(GraphConstants.COMPARE_VERTICES_BY_POSITION);
        return verticesByDfs;
    }

    private void recursiveDfs(@NotNull Vertex v) {
        if (!v.isExplored()) {
            v.setGraphSearchPosition(this.nextGraphSearchPosition++);
            v.setExplored(true);
            for (Edge edge : v.getEdges()) {
                Vertex w = GraphUtils.getOtherVertexOfEdge(edge, v);
                this.recursiveDfs(w);
            }
        }
    }
}
