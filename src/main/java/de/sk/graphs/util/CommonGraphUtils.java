package de.sk.graphs.util;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.datastructure.Edge;
import de.sk.graphs.datastructure.Vertex;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class supplying various utility methods for all kinds of graphs (both directed and undirected).
 */
public final class CommonGraphUtils {

    static final String EDGE_ENDPOINTS_NOT_IN_GRAPH_EXCEPTION_MSG_TF = "At least one endpoint of edge %s ([%s]) is incident to " +
            "a vertex not in the adjacency list ([%s]).";

    private CommonGraphUtils() {
        // only utility methods
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
                throw new IllegalArgumentException(String.format(EDGE_ENDPOINTS_NOT_IN_GRAPH_EXCEPTION_MSG_TF,
                        edge.getName(), nameOfVerticesOfTheEdge, nameOfVerticesInGraph));
            }
        }
    }
}
