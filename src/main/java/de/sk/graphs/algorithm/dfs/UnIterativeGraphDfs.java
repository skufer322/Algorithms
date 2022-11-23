package de.sk.graphs.algorithm.dfs;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.GraphUtils;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Stack;

/**
 * Iterative {@link UnGraphDfs} implementation utilizing a stack.
 */
public class UnIterativeGraphDfs implements UnGraphDfs {

    private final Stack<UnVertex> stack;

    public UnIterativeGraphDfs() {
        this.stack = new Stack<>();
    }

    @Override
    public @NotNull List<UnVertex> conductDfs(@NotNull UnAdjacencyList adjacencyList, @NotNull UnVertex s) {
        List<UnVertex> verticesByDfs = adjacencyList.vertices();
        stack.add(s);
        this.processStack();
        verticesByDfs.sort(GraphConstants.COMPARE_VERTICES_BY_GRAPH_SEARCH_POSITION);
        return verticesByDfs;
    }

    private void processStack() {
        int nextGraphSearchPosition = GraphConstants.INITIAL_GRAPH_SEARCH_POSITION;
        while (!stack.isEmpty()) {
            UnVertex v = stack.pop();
            if (!v.isExplored()) {
                v.setGraphSearchPosition(nextGraphSearchPosition);
                v.setExplored(true);
                for (UnEdge edge : v.getEdges()) {
                    UnVertex w = GraphUtils.getOtherVertexOfEdge(edge, v);
                    stack.push(w);
                }
                nextGraphSearchPosition++;
            }
        }
    }
}
