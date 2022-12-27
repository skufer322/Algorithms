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

public final class MWISUtils {

    private MWISUtils() {
        // only utility methods
    }

    /**
     * TODO
     *
     * @param adjacencyList
     * @return
     */
    public static List<UnVertex> getVerticesOfPathGraphInOrderOfPath(@NotNull UnAdjacencyList adjacencyList) {
        // try to find one of the two endpoints of the path graph, and set it as current vertex
        List<UnVertex> endpointsOfPathGraph = UndirectedGraphUtils.findVerticesWithNIncidentEdges(adjacencyList, 1);
        if (endpointsOfPathGraph.size() != 2) {
            throw new IllegalArgumentException("TODO is not a path graph because there are not exactly with vertices with " +
                    "1 incident edge");
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
            throw new IllegalArgumentException("TODO es gibt einen vertex mit mehr als zwei incident edges -> ist kein pfad graph ");
        }
        return new ArrayList<>(alreadyVisitedVertices);
    }
}
