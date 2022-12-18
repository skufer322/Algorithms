package de.sk.greedy.mst;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.graphs.GraphUtils;
import de.sk.graphs.algorithm.cc.UnConnectedComponents;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.greedy.GreedyInjectionModule;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class MstUtils {

    static final String NOT_EXACTLY_1_CC_EXCEPTION_MSG_TEXT_FORMAT = "Undirected graph must consist of exactly 1 connected component. Number of connected components: %d.";
    static final String MORE_THAN_1_EDGE_BETWEEN_2_VERTICES_EXCEPTION_MSG_TEXT_FORMAT = "Vertex %s has %d incident edges and %d adjacent vertices. Only one edge is allowed " +
            "between any pair of vertices.";

    private static final Injector INJECTOR = Guice.createInjector(new GreedyInjectionModule());
    private static final UnConnectedComponents UCC = INJECTOR.getInstance(UnConnectedComponents.class);

    private MstUtils() {
        // only utils
    }

    public static void verifyIntegrityOfGraph(@NotNull UnAdjacencyList undirectedGraph) {
        // verify that graph consists of exactly one connected component
        List<Set<UnVertex>> connectedComponents = UCC.determineConnectedComponents(undirectedGraph);
        if (connectedComponents.size() != 1) {
            throw new IllegalArgumentException(String.format(NOT_EXACTLY_1_CC_EXCEPTION_MSG_TEXT_FORMAT, connectedComponents.size()));
        }
        // verify that between any pair of vertices, max. 1 edge exists
        Set<UnVertex> adjacentVertices = new HashSet<>();
        for (UnVertex vertex : undirectedGraph.vertices()) {
            adjacentVertices.clear();
            List<UnEdge> incidentEdges = vertex.getEdges();
            incidentEdges.forEach(edge -> adjacentVertices.add(GraphUtils.getOtherVertexOfEdge(edge, vertex)));
            if (adjacentVertices.size() != incidentEdges.size()) {
                throw new IllegalArgumentException(String.format(MORE_THAN_1_EDGE_BETWEEN_2_VERTICES_EXCEPTION_MSG_TEXT_FORMAT,
                        vertex.getName(), incidentEdges.size(), adjacentVertices.size()));
            }
        }
    }
}
