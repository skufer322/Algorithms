package de.sk.graphs.algorithm.bfs;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.GraphUtils;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * {@link GraphBfs} implementation utilizing a queue.
 */
public class GraphBfsImpl implements GraphBfs {

    private final Queue<UnVertex> queue;

    public GraphBfsImpl() {
        this.queue = new LinkedList<>();
    }

    @Override
    public @NotNull List<UnVertex> conductBfs(@NotNull UnAdjacencyList adjacencyList, @NotNull UnVertex s) {
        List<UnVertex> verticesByBfs = adjacencyList.vertices();
        s.setGraphSearchPosition(GraphConstants.INITIAL_GRAPH_SEARCH_POSITION);
        GraphUtils.exploreVertexAndAddToQueue(s, this.queue);
        this.processQueue();
        verticesByBfs.sort(GraphConstants.COMPARE_VERTICES_BY_GRAPH_SEARCH_POSITION);
        return verticesByBfs;
    }

    private void processQueue() {
        int nextGraphSearchPosition = GraphConstants.INITIAL_GRAPH_SEARCH_POSITION + 1;
        while (!this.queue.isEmpty()) {
            UnVertex v = this.queue.remove();
            List<UnEdge> edges = v.getEdges();
            for (UnEdge edge : edges) {
                UnVertex w = GraphUtils.getOtherVertexOfEdge(edge, v);
                if (!w.isExplored()) {
                    w.setGraphSearchPosition(nextGraphSearchPosition);
                    nextGraphSearchPosition++;
                }
            }
        }
    }
}
