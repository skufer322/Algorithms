package de.sk.graphs.util;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class supplying various utility methods for undirected graphs.
 */
public final class UndirectedGraphUtils {

    static final String EDGE_IS_NOT_INCIDENT_TO_VERTEX_EXCEPTION_MSG_TF = "Edge %s is not incident to vertex %s.";
    static final String EDGE_STARTS_AND_ENDS_AT_VERTEX_EXCEPTION_MSG_TF = "Edge %s starts and ends at vertex %s.";
    static final String TOO_MANY_EDGES_FOR_UNDIRECTED_GRAPH_EXCEPTION_MSG_TF = "An undirected graph of %d vertices can only have %d edges at a max " +
            "(if there cannot be more than 1 edge between each pair of vertices). Number of edges was specified as %d.";

    private static final String VERTEX_NAME_TF = "v%d";
    private static final String EDGE_NAME_TF = "e_%s_%s";

    private UndirectedGraphUtils() {
        // only utility methods
    }

    /**
     * Given an undirected edge with two endpoints {@code v} and w, returns endpoint w if {@code v} is passed to the method.
     * Throws an {@link IllegalArgumentException} if either none of the edge's endpoints is {@code v} or both endpoints are {@code v}.
     *
     * @param edge edge with endpoints {@code v} and w, for which w is to be returned
     * @param v    endpoint v which is not to be returned
     * @return endpoint w
     */
    public static @NotNull UnVertex getOtherVertexOfEdge(@NotNull UnEdge edge, @NotNull UnVertex v) {
        Set<UnVertex> verticesOfEdge = edge.getVertices();
        if (!verticesOfEdge.contains(v)) {
            throw new IllegalArgumentException(String.format(EDGE_IS_NOT_INCIDENT_TO_VERTEX_EXCEPTION_MSG_TF,
                    edge, v.toStringOnlyWithNameAndEdges()));
        }

        return verticesOfEdge.stream()
                .filter(w -> !w.equals(v))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(EDGE_STARTS_AND_ENDS_AT_VERTEX_EXCEPTION_MSG_TF,
                        edge, v.toStringOnlyWithNameAndEdges())));
    }

    /**
     * TODO
     *
     * @param vertex
     * @param incidentEdge
     * @return
     */
    public static @NotNull List<UnEdge> getOtherIncidentEdgesOfVertex(@NotNull UnVertex vertex, @NotNull UnEdge incidentEdge) {
        if (!incidentEdge.getVertices().contains(vertex)) {
            throw new IllegalArgumentException("TODO vertex is not part of the given incident edfge!");
        }
        return vertex.getEdges().stream()
                .filter(edge -> edge != incidentEdge)
                .collect(Collectors.toList());
    }

    /**
     * Sets the status of the given {@code vertex} as explored and adds {@code vertex} to the given queue.
     *
     * @param vertex vertex whose status is to be set true and which is to be added to the queue
     * @param queue  queue to which the {@code vertex} is to be added
     */
    public static void exploreVertexAndAddToQueue(@NotNull UnVertex vertex, @NotNull Queue<UnVertex> queue) {
        vertex.setExplored(true);
        queue.add(vertex);
    }

    /**
     * TODO
     *
     * @param adjacencyList
     * @param n
     * @return
     */
    public static @NotNull List<UnVertex> findVerticesWithNIncidentEdges(@NotNull UnAdjacencyList adjacencyList, int n) {
        return adjacencyList.vertices().stream()
                .filter(vertex -> vertex.getEdges().size() == n)
                .collect(Collectors.toList());
    }

    /**
     * TODO
     *
     * @param vertex
     * @return
     */
    public static @NotNull Set<UnVertex> getAdjacentVertices(@NotNull UnVertex vertex) {
        Set<UnVertex> adjacentVertices = new HashSet<>();
        vertex.getEdges().forEach(edge -> adjacentVertices.addAll(edge.getVertices()));
        adjacentVertices.remove(vertex);
        return adjacentVertices;
    }

    public static @NotNull UnAdjacencyList createRandomGraph(int numberOfVertices, int numberOfEdges, int maxWeight, @NotNull Random random) {
        List<UnVertex> vertices = new ArrayList<>();
        for (int i = 1; i <= numberOfVertices; i++) {
            UnVertex vertex = new UnVertex(String.format(VERTEX_NAME_TF, i), random.nextInt(maxWeight));
            vertices.add(vertex);
        }
        int maxPossibleNumberOfEdges = (numberOfVertices * (numberOfVertices - 1)) / 2; // (n * (n-1)) / 2
        if (numberOfEdges > maxPossibleNumberOfEdges) {
            throw new IllegalArgumentException(String.format(TOO_MANY_EDGES_FOR_UNDIRECTED_GRAPH_EXCEPTION_MSG_TF, numberOfVertices,
                    maxPossibleNumberOfEdges, numberOfEdges));
        }
        List<UnEdge> edges = new ArrayList<>();
        for (int i = 0; i < numberOfEdges; i++) {
            UnVertex v = vertices.get(random.nextInt(vertices.size()));
            Set<UnVertex> adjacentVerticesOfV = UndirectedGraphUtils.getAdjacentVertices(v);
            if (adjacentVerticesOfV.size() == vertices.size() - 1) {
                i--;
                continue; // v already has the maximum possible number of edges
            }
            UnVertex w = vertices.get(random.nextInt(vertices.size()));
            while (v == w || adjacentVerticesOfV.contains(w)) {
                w = vertices.get(random.nextInt(vertices.size()));
            }
            UnEdge edge = new UnEdge(String.format(EDGE_NAME_TF, v.getName(), w.getName()), v, w);
            edges.add(edge);
        }
        return new UnAdjacencyList(vertices, edges);
    }
}
