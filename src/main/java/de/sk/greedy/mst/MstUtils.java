package de.sk.greedy.mst;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.graphs.algorithm.cc.UnConnectedComponents;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.greedy.GreedyInjectionModule;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class supplying various 'Minimum Spanning Tree'-related utility methods.
 */
public final class MstUtils {

    static final String NOT_EXACTLY_1_CC_EXCEPTION_MSG_TF = "Undirected graph must consist of exactly 1 connected component. Number of connected components: %d.";
    static final String MORE_THAN_1_EDGE_BETWEEN_2_VERTICES_EXCEPTION_MSG_TF = "Vertex %s has %d incident edges and %d adjacent vertices. Only one edge is allowed " +
            "between any pair of vertices.";

    private static final Injector INJECTOR = Guice.createInjector(new GreedyInjectionModule());
    public static final UnConnectedComponents UCC = INJECTOR.getInstance(UnConnectedComponents.class);

    private MstUtils() {
        // only utils
    }

    /**
     * Verifies the integrity of the graph for which the Minimum Spanning Tree (MST) is to be determined. It only makes sense
     * to determine the minimum spanning tree for undirected, connected graphs. Also, it is checked that at a max, there is
     * only 1 edge between each pair of vertices.
     *
     * @param undirectedGraph undirected graph which is to be verified for its integrity to apply MST algorithms on it
     */
    public static void verifyIntegrityOfGraph(@NotNull UnAdjacencyList undirectedGraph) {
        // verify that graph consists of exactly one connected component
        List<Set<UnVertex>> connectedComponents = UCC.determineConnectedComponents(undirectedGraph);
        if (connectedComponents.size() != 1) {
            throw new IllegalArgumentException(String.format(NOT_EXACTLY_1_CC_EXCEPTION_MSG_TF, connectedComponents.size()));
        }
        // verify that between any pair of vertices, max. 1 edge exists
        Set<UnVertex> adjacentVertices = new HashSet<>();
        for (UnVertex vertex : undirectedGraph.vertices()) {
            adjacentVertices.clear();
            List<UnEdge> incidentEdges = vertex.getEdges();
            incidentEdges.forEach(edge -> adjacentVertices.add(UndirectedGraphUtils.getOtherVertexOfEdge(edge, vertex)));
            if (adjacentVertices.size() != incidentEdges.size()) {
                throw new IllegalArgumentException(String.format(MORE_THAN_1_EDGE_BETWEEN_2_VERTICES_EXCEPTION_MSG_TF,
                        vertex.getName(), incidentEdges.size(), adjacentVertices.size()));
            }
        }
        undirectedGraph.vertices().forEach(UnVertex::resetAttributeValuesModifiableByAlgorithms);
    }
}
