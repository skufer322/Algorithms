package de.sk.graphs.algorithm.bfs;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Implementation of {@link GraphBfs} utilizing a stack which additionally determines the levels of the graph's vertices
 * (the level of a vertex is also the minimum distance between the very vertex and the starting vertex {@code s}).
 * Time complexity: O(m+n), m=|E|, n=|V|.
 */
public class AugmentedBfs implements GraphBfs {

    private final Queue<UnVertex> queue;

    public AugmentedBfs() {
        this.queue = new LinkedList<>();
    }

    @Override
    public @NotNull List<UnVertex> conductBfs(@NotNull UnAdjacencyList adjacencyList, @NotNull UnVertex s) {
        List<UnVertex> verticesByBfs = adjacencyList.vertices();
        s.setLevel(GraphConstants.INITIAL_GRAPH_SEARCH_LEVEL);
        s.setGraphSearchPosition(GraphConstants.INITIAL_GRAPH_SEARCH_POSITION);
        UndirectedGraphUtils.exploreVertexAndAddToQueue(s, this.queue);
        this.processQueue();
        verticesByBfs.sort(GraphConstants.COMPARE_VERTICES_BY_LEVEL.thenComparing(GraphConstants.COMPARE_VERTICES_BY_GRAPH_SEARCH_POSITION));
        return verticesByBfs;
    }

    private void processQueue() {
        int nextGraphSearchPosition = GraphConstants.INITIAL_GRAPH_SEARCH_POSITION + 1;
        while (!this.queue.isEmpty()) {
            UnVertex v = this.queue.remove();
            List<UnEdge> edges = v.getEdges();
            for (UnEdge edge : edges) {
                UnVertex w = UndirectedGraphUtils.getOtherVertexOfEdge(edge, v);
                if (!w.isExplored()) {
                    int levelOfW = v.getLevel() + 1;
                    w.setLevel(levelOfW);
                    w.setGraphSearchPosition(nextGraphSearchPosition);
                    UndirectedGraphUtils.exploreVertexAndAddToQueue(w, this.queue);
                    nextGraphSearchPosition++;
                }
            }
        }
    }
}
