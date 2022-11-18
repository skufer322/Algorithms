package de.sk.graphs.bfs;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.GraphUtils;
import de.sk.graphs.datastructure.AdjacencyList;
import de.sk.graphs.datastructure.Edge;
import de.sk.graphs.datastructure.Vertex;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * {@link GraphBfs} implementation utilizing a stack which additionally determines the levels of the graph's vertices
 * (the level of a vertex is also the minimum distance between the very vertex and the start vertex s).
 */
public class AugmentedBfs implements GraphBfs {

    private final Queue<Vertex> queue;

    public AugmentedBfs() {
        this.queue = new LinkedList<>();
    }

    @Override
    public @NotNull List<Vertex> conductBfs(@NotNull AdjacencyList adjacencyList, @NotNull Vertex s) {
        List<Vertex> verticesByBfs = new ArrayList<>(adjacencyList.vertices());
        s.setLevel(GraphConstants.INITIAL_GRAPH_SEARCH_LEVEL);
        s.setGraphSearchPosition(GraphConstants.INITIAL_GRAPH_SEARCH_POSITION);
        GraphUtils.exploreVertexAndAddToQueue(s, this.queue);
        this.processQueue();
        verticesByBfs.sort(GraphConstants.COMPARE_VERTICES_BY_LEVEL.thenComparing(GraphConstants.COMPARE_VERTICES_BY_POSITION));
        return verticesByBfs;
    }

    private void processQueue() {
        int nextGraphSearchPosition = GraphConstants.INITIAL_GRAPH_SEARCH_POSITION + 1;
        while (!this.queue.isEmpty()) {
            Vertex v = this.queue.remove();
            List<Edge> edges = v.getEdges();
            for (Edge edge : edges) {
                Vertex w = GraphUtils.getOtherVertexOfEdge(edge, v);
                if (!w.isExplored()) {
                    int levelOfW = v.getLevel() + 1;
                    w.setLevel(levelOfW);
                    w.setGraphSearchPosition(nextGraphSearchPosition);
                    GraphUtils.exploreVertexAndAddToQueue(w, this.queue);
                    nextGraphSearchPosition++;
                }
            }
        }
    }
}
