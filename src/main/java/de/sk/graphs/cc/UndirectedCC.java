package de.sk.graphs.cc;

import de.sk.graphs.GraphUtils;
import de.sk.graphs.datastructure.AdjacencyList;
import de.sk.graphs.datastructure.Edge;
import de.sk.graphs.datastructure.Vertex;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Determines the connected components of a graph. Utilizes a queue-based breadth-first search.
 */
public class UndirectedCC {

    private final Queue<Vertex> queue;

    public UndirectedCC() {
        this.queue = new LinkedList<>();
    }

    /**
     * Determines the connected components of the given undirected graph and returns them. Each connected component
     * is the set of its vertices in the returned list.
     *
     * @param adjacencyList adjacency list representation of the graph
     * @return list of connected components
     */
    public List<Set<Vertex>> determineConnectedComponents(@NotNull AdjacencyList adjacencyList) {
        List<Set<Vertex>> connectedComponents = new ArrayList<>();
        int cc = 0;
        for (Vertex vertex : adjacencyList.vertices()) {
            if (!vertex.isExplored()) {
                vertex.setCc(cc);
                GraphUtils.exploreVertexAndAddToQueue(vertex, this.queue);
                List<Vertex> connectedComponent = this.processQueue(cc);
                connectedComponents.add(Set.copyOf(connectedComponent));
                cc++;
            }
        }
        return connectedComponents;
    }

    private @NotNull List<Vertex> processQueue(int cc) {
        List<Vertex> connectedComponent = new ArrayList<>();
        while (!this.queue.isEmpty()) {
            Vertex v = this.queue.remove();
            connectedComponent.add(v);
            for (Edge edge : v.getEdges()) {
                Vertex w = GraphUtils.getOtherVertexOfEdge(edge, v);
                if (!w.isExplored()) {
                    w.setCc(cc);
                    GraphUtils.exploreVertexAndAddToQueue(w, this.queue);
                }
            }
        }
        return connectedComponent;
    }
}
