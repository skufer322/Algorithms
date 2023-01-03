package de.sk.dynamicprogramming.mwis;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class for methods required for solving the Maximum Weighted Independent Set (MWIS) problem for path graphs.
 */
public final class MWISUtils {

    static final String NOT_2_VERTICES_WITH_1_INCIDENT_EDGE_EXCEPTION_MSG_TF = "Given graph is not a path graph because there " +
            "are not exactly 2 vertices with 1 incident edge, bot %d.";
    static final String EDGE_WITH_MORE_THAN_2_INCIDENT_EDGES_EXCEPTION_MSG_TF = "Vertex %s has not 2 but %d incident edges. Hence, " +
            "the given graph is not a path graph.";

    private MWISUtils() {
        // only utility methods
    }

    /**
     * Returns the vertices of the given path graph (represented as adjacency list) in the order of traversal, starting at
     * one of the two endpoints. Throws an {@link IllegalArgumentException} if the given graph is not a path graph.
     *
     * @param adjacencyList path graph
     * @return vertices of the path graph in the order of traversal (starting at one of the two endpoints)
     */
    public static List<UnVertex> getVerticesOfPathGraphInOrderOfPath(@NotNull UnAdjacencyList adjacencyList) {
        // try to find one of the two endpoints of the path graph, and set it as current vertex
        List<UnVertex> endpointsOfPathGraph = UndirectedGraphUtils.findVerticesWithNIncidentEdges(adjacencyList, 1);
        if (endpointsOfPathGraph.size() != 2) {
            throw new IllegalArgumentException(String.format(NOT_2_VERTICES_WITH_1_INCIDENT_EDGE_EXCEPTION_MSG_TF, endpointsOfPathGraph.size()));
        }
        Set<UnVertex> alreadyVisitedVertices = new LinkedHashSet<>();
        UnVertex currentVertex = endpointsOfPathGraph.get(0); // pick any of the two endpoints as starting point
        List<UnEdge> incidentEdgesToConsider = currentVertex.getEdges();
        UnEdge nextEdge;
        while (incidentEdgesToConsider.size() == 1) {
            alreadyVisitedVertices.add(currentVertex);
            nextEdge = incidentEdgesToConsider.get(0);
            currentVertex = UndirectedGraphUtils.getOtherVertexOfEdge(nextEdge, currentVertex);
            incidentEdgesToConsider = UndirectedGraphUtils.getOtherIncidentEdgesOfVertex(currentVertex, nextEdge);
        }
        if (incidentEdgesToConsider.size() != 0) {
            throw new IllegalArgumentException(String.format(EDGE_WITH_MORE_THAN_2_INCIDENT_EDGES_EXCEPTION_MSG_TF, currentVertex, currentVertex.getEdges().size()));
        }
        return new ArrayList<>(alreadyVisitedVertices);
    }
}
