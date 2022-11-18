package de.sk.graphs;

import de.sk.graphs.datastructure.Edge;
import de.sk.graphs.datastructure.Vertex;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.Set;

/**
 * Utility class supplying various graph-related utility methods.
 */
public final class GraphUtils {

    static final String EDGE_IS_NOT_INCIDENT_TO_VERTEX_EXCEPTION_MSG_TEXT_FORMAT = "Edge %s is not incident to vertex %s.";
    static final String EDGE_STARTS_AND_ENDS_AT_VERTEX_EXCEPTION_MSG_TEXT_FORMAT = "Edge %s starts and ends at vertex %s.";

    private GraphUtils() {
        // only utility methods
    }

    /**
     * Given an undirected edge with two endpoints v and w, returns endpoint w if v is passed to the method.
     * Throws an IllegalArgumentException if either none of the edge's endpoints is v or both endpoints are v.
     *
     * @param edge edge with endpoints v and w, for which w shall be returned
     * @param v    endpoint v which shall not be returned
     * @return endpoint w
     */
    public static @NotNull Vertex getOtherVertexOfEdge(@NotNull Edge edge, @NotNull Vertex v) {
        Set<Vertex> verticesOfEdge = edge.getVertices();
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
     * Sets the status of the given vertex as explored and adds the vertex to the given queue.
     *
     * @param vertex vertex whose status is to be set true and which is to be added to the queue
     * @param queue  queue to which the vertex shall be added
     */
    public static void exploreVertexAndAddToQueue(@NotNull Vertex vertex, @NotNull Queue<Vertex> queue) {
        vertex.setExplored(true);
        queue.add(vertex);
    }
}
