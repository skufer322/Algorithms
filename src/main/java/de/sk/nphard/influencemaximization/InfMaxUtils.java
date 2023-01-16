package de.sk.nphard.influencemaximization;

import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Utility class supplying utility methods for operations in the context of the influence maximization problem.
 */
public class InfMaxUtils {

    private InfMaxUtils() {
        // only utils
    }

    /**
     * Throws a coin with probability {@code p} for all edges in the given graph (which is represented as adjacency list).<br>
     * If "heads" comes up (with probability {@code p}), the edge is "active". <br>
     * If "tail" comes up (with probability {@code 1-p}), the edge is "inactive".<br>
     * The given {@code random} is used for the simulated coin flips.
     * <br><br>
     * Returns a map with the edges as key and their respective status as value {@code true} for "active", {@code false}
     * for "inactive").
     *
     * @param adjacencyList graph whose edges shall be flipped
     * @param p             probability of an edge being in status "active" after being flipped
     * @param random        random number generator used in the simulated coin flips
     * @return map of edges (as keys) alongside their respective activation status (as value; {@code true} for "active",
     * {@code false}  for "inactive"
     */
    public static @NotNull Map<DiEdge, Boolean> flipEdges(@NotNull DiAdjacencyList adjacencyList, double p, @NotNull Random random) {
        Map<DiEdge, Boolean> statusesOfEdges = new HashMap<>();
        List<DiEdge> edges = adjacencyList.edges();
        for (DiEdge edge : edges) {
            // coin toss if edge is active, or not
            boolean isActive = random.nextDouble() <= p;
            statusesOfEdges.put(edge, isActive);
        }
        return statusesOfEdges;
    }

    /**
     * Determines the vertices which are reachable from vertex {@code candidate} (including {@code candidate}  itself) while considering the {@code edgeStatuses}
     * of the vertices' outgoing edges. Only "active" edges can be pursued.
     * <br>
     * In {@code edgeStatuses}, an edge (used as key) is "active" if its corresponding value is {@code true}.
     *
     * @param candidate    candidate vertex for which the reachable vertices are to be determined
     * @param edgeStatuses statuses of the edges
     * @return set of vertices which are reachable from {@code candidate} while considering the {@code edgeStatuses}
     */
    public static @NotNull Set<DiVertex> determineReachableVerticesConsideringEdgeStatuses(@NotNull DiVertex candidate, @NotNull Map<DiEdge, Boolean> edgeStatuses) {
        Set<DiVertex> reachableVertices = new HashSet<>();
        InfMaxUtils.dfsConsideringEdgeStatuses(candidate, edgeStatuses, reachableVertices);
        return reachableVertices;
    }

    static void dfsConsideringEdgeStatuses(@NotNull DiVertex v, @NotNull Map<DiEdge, Boolean> edgeStatuses, @NotNull Set<DiVertex> reachableVertices) {
        if (!reachableVertices.contains(v)) {
            reachableVertices.add(v);
            for (DiEdge edge : v.getOutgoingEdges()) {
                // only consider active edges
                if (edgeStatuses.get(edge)) {
                    DiVertex w = edge.head();
                    InfMaxUtils.dfsConsideringEdgeStatuses(w, edgeStatuses, reachableVertices);
                }
            }
        }
    }
}
