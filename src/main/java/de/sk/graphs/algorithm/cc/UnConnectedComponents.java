package de.sk.graphs.algorithm.cc;

import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Determines the connected components of a graph. Utilizes a queue-based breadth-first search.
 */
public class UnConnectedComponents {

    private final Queue<UnVertex> queue;

    public UnConnectedComponents() {
        this.queue = new LinkedList<>();
    }

    /**
     * Determines the connected components of the given undirected graph and returns them. Each connected component
     * is the set of its vertices in the returned list.
     *
     * @param adjacencyList adjacency list representation of the graph
     * @return list of connected components
     */
    public List<Set<UnVertex>> determineConnectedComponents(@NotNull UnAdjacencyList adjacencyList) {
        List<Set<UnVertex>> connectedComponents = new ArrayList<>();
        int cc = 0;
        for (UnVertex vertex : adjacencyList.vertices()) {
            if (!vertex.isExplored()) {
                vertex.setCc(cc);
                UndirectedGraphUtils.exploreVertexAndAddToQueue(vertex, this.queue);
                List<UnVertex> connectedComponent = this.processQueue(cc);
                connectedComponents.add(Set.copyOf(connectedComponent));
                cc++;
            }
        }
        return connectedComponents;
    }

    private @NotNull List<UnVertex> processQueue(int cc) {
        List<UnVertex> connectedComponent = new ArrayList<>();
        while (!this.queue.isEmpty()) {
            UnVertex v = this.queue.remove();
            connectedComponent.add(v);
            for (UnEdge edge : v.getEdges()) {
                UnVertex w = UndirectedGraphUtils.getOtherVertexOfEdge(edge, v);
                if (!w.isExplored()) {
                    w.setCc(cc);
                    UndirectedGraphUtils.exploreVertexAndAddToQueue(w, this.queue);
                }
            }
        }
        return connectedComponent;
    }
}
