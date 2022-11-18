package de.sk.graphs.dfs;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.GraphUtils;
import de.sk.graphs.datastructure.AdjacencyList;
import de.sk.graphs.datastructure.Edge;
import de.sk.graphs.datastructure.Vertex;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Iterative {@link GraphDfs} implementation utilizing a stack.
 */
public class IterativeGraphDfs implements GraphDfs {

    private final Stack<Vertex> stack;

    public IterativeGraphDfs() {
        this.stack = new Stack<>();
    }

    @Override
    public @NotNull List<Vertex> conductDfs(@NotNull AdjacencyList adjacencyList, @NotNull Vertex s) {
        List<Vertex> verticesByDfs = new ArrayList<>(adjacencyList.vertices());
        stack.add(s);
        this.processStack();
        verticesByDfs.sort(GraphConstants.COMPARE_VERTICES_BY_POSITION);
        return verticesByDfs;
    }

    private void processStack() {
        int nextGraphSearchPosition = GraphConstants.INITIAL_GRAPH_SEARCH_POSITION;
        while (!stack.isEmpty()) {
            Vertex v = stack.pop();
            if (!v.isExplored()) {
                v.setGraphSearchPosition(nextGraphSearchPosition);
                v.setExplored(true);
                for (Edge edge : v.getEdges()) {
                    Vertex w = GraphUtils.getOtherVertexOfEdge(edge, v);
                    stack.push(w);
                }
                nextGraphSearchPosition++;
            }
        }
    }
}
