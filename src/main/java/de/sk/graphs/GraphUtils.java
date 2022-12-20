package de.sk.graphs;

import de.sk.graphs.datastructure.Edge;
import de.sk.graphs.datastructure.Vertex;
import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class supplying various graph-related utility methods.
 */
public final class GraphUtils {

    static final String EDGE_IS_NOT_INCIDENT_TO_VERTEX_EXCEPTION_MSG_TEXT_FORMAT = "Edge %s is not incident to vertex %s.";
    static final String EDGE_STARTS_AND_ENDS_AT_VERTEX_EXCEPTION_MSG_TEXT_FORMAT = "Edge %s starts and ends at vertex %s.";
    static final String EDGE_ENDPOINTS_NOT_IN_GRAPH_EXCEPTION_MSG_TEXT_FORMAT = "At least one endpoint of edge %s ([%s]) is incident to " +
            "a vertex not in the adjacency list ([%s]).";
    static final String EDGE_WITH_TOO_SMALL_WEIGHT_EXCEPTION_MSG_TEXT_FORMAT = "Edge %s has a weight smaller than %d.";

    private GraphUtils() {
        // only utility methods
    }

    /**
     * Given an undirected edge with two endpoints {@code v} and w, returns endpoint w if {@code v} is passed to the method.
     * Throws an {@link IllegalArgumentException} if either none of the edge's endpoints is {@code v} or both endpoints are {@code v}.
     *
     * @param edge edge with endpoints {@code v} and w, for which w shall be returned
     * @param v    endpoint v which shall not be returned
     * @return endpoint w
     */
    public static @NotNull UnVertex getOtherVertexOfEdge(@NotNull UnEdge edge, @NotNull UnVertex v) {
        Set<UnVertex> verticesOfEdge = edge.getVertices();
        if (!verticesOfEdge.contains(v)) {
            throw new IllegalArgumentException(String.format(EDGE_IS_NOT_INCIDENT_TO_VERTEX_EXCEPTION_MSG_TEXT_FORMAT,
                    edge, v.toStringOnlyWithNameAndEdges()));
        }

        return verticesOfEdge.stream()
                .filter(w -> !w.equals(v))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(EDGE_STARTS_AND_ENDS_AT_VERTEX_EXCEPTION_MSG_TEXT_FORMAT,
                        edge, v.toStringOnlyWithNameAndEdges())));
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
     * Validates that all endpoints of the given {@code edges} are contained in the {@code vertices}.
     *
     * @param vertices list of vertices which are potential endpoints for the given {@code edges}
     * @param edges    list of edges whose endpoints must be contained in the list of {@code vertices}.
     */
    public static void validateEdgesAreInGraph(@NotNull List<? extends Vertex> vertices, @NotNull List<? extends Edge> edges) {
        Set<Vertex> verticesForValidation = new HashSet<>(vertices); // create set to speed up containment checks
        for (Edge edge : edges) {
            if (!verticesForValidation.containsAll(edge.getVertices())) {
                String nameOfVerticesOfTheEdge = edge.getVertices().stream().map(Vertex::getName)
                        .collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
                String nameOfVerticesInGraph = vertices.stream().map(Vertex::getName)
                        .collect(Collectors.joining(GraphConstants.STRING_JOIN_DELIMITER));
                throw new IllegalArgumentException(String.format(EDGE_ENDPOINTS_NOT_IN_GRAPH_EXCEPTION_MSG_TEXT_FORMAT,
                        edge.getName(), nameOfVerticesOfTheEdge, nameOfVerticesInGraph));
            }
        }
    }

    /**
     * Resets the primitive type attributes of all {@code vertices} in the list to their default values.
     *
     * @param vertices vertices whose primitive type attributes are to be reset to their default values
     */
    public static void resetAttributesOfVertices(@NotNull List<DiVertex> vertices) {
        for (DiVertex vertex : vertices) {
            resetAttributesOfVertex(vertex);
        }
    }

    /**
     * Resets the primitive type attributes of the {@code vertex} to their default values.
     *
     * @param vertex vertex whose primitive type attributes are to be reset to their default values
     */
    public static void resetAttributesOfVertex(@NotNull DiVertex vertex) {
        vertex.setExplored(false);
        vertex.setTopSortPosition(-1);
        vertex.setCc(-1);
        vertex.setNumScc(-1);
    }

    /**
     * Checks whether all the edges of the graph represented by an adjacency list have a weight of at least {@code minWeight}.
     * Throws an {@link IllegalArgumentException} if any edge has a smaller weight.
     *
     * @param adjacencyList graph for which the edge weights are checked
     * @param minWeight min weight each edge must have
     */
    public static void assertAllEdgesHaveWeightGreaterThan(@NotNull DiAdjacencyList adjacencyList, int minWeight) {
        // could be improved to only account for edges reachable from a specific starting vertex
        for (DiEdge edge : adjacencyList.edges()) {
            if (edge.getWeight() < minWeight) {
                throw new IllegalArgumentException(String.format(EDGE_WITH_TOO_SMALL_WEIGHT_EXCEPTION_MSG_TEXT_FORMAT, edge, minWeight));
            }
        }
    }
}
