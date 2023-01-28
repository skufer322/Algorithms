package de.sk.graphs.util;

import de.sk.graphs.GraphConstants;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnAdjacencyMatrix;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.util.IntegerUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class supplying various utility methods for undirected graphs.
 */
public final class UndirectedGraphUtils {

    static final String EDGE_IS_NOT_INCIDENT_TO_VERTEX_EXCEPTION_MSG_TF = "Edge %s is not incident to vertex %s.";
    static final String EDGE_STARTS_AND_ENDS_AT_VERTEX_EXCEPTION_MSG_TF = "Edge %s starts and ends at vertex %s.";
    static final String VERTEX_IS_NOT_PART_OF_GIVEN_EDGE_EXCEPTION_MSG_TF = "Vertex %s is not part of edge %s.";
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
     * Returns all the other edges incident of the given {@code vertex} except the given {@code incidentEdge}. Throws an
     * {@link IllegalArgumentException} if {@code incidentEdge} does not have {@code vertex} as one of its endpoints.
     *
     * @param vertex       vertex for which the incident edges except {@code incidentEdge} are to be returned
     * @param incidentEdge the single incident edge which is not to be returned
     * @return all incident edges of {@code vertex} except {@code incidentEdge}
     */
    public static @NotNull List<UnEdge> getOtherIncidentEdgesOfVertex(@NotNull UnVertex vertex, @NotNull UnEdge incidentEdge) {
        if (!incidentEdge.getVertices().contains(vertex)) {
            throw new IllegalArgumentException(String.format(VERTEX_IS_NOT_PART_OF_GIVEN_EDGE_EXCEPTION_MSG_TF, vertex, incidentEdge));
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
     * Returns all vertices of the graph (represented as adjacency list) which have exactly {@code n} incident edges.
     *
     * @param adjacencyList the graph
     * @param n             number of incident edges a vertex should have to be in the returned result
     * @return list of vertices which have exactly {@code n} incident edges
     */
    public static @NotNull List<UnVertex> findVerticesWithNIncidentEdges(@NotNull UnAdjacencyList adjacencyList, int n) {
        return adjacencyList.vertices().stream()
                .filter(vertex -> vertex.getEdges().size() == n)
                .collect(Collectors.toList());
    }

    /**
     * Returns all adjacent vertices for the given {@code vertex}, i.e. all vertices which are connected with
     * {@code vertex} by a common edge .
     *
     * @param vertex vertex for which the adjacent vertices are to be returned
     * @return set of the adjacent vertices for the given {@code vertex}
     */
    public static @NotNull Set<UnVertex> getAdjacentVertices(@NotNull UnVertex vertex) {
        Set<UnVertex> adjacentVertices = new HashSet<>();
        vertex.getEdges().forEach(edge -> adjacentVertices.addAll(edge.getVertices()));
        adjacentVertices.remove(vertex);
        return adjacentVertices;
    }

    /**
     * Creates a random, undirected graph with the given {@code numberOfVertices} and the given {@code numberOfEdges}.
     * The weight of an edge in the created graph is between 0 and {@code maxWeight}. The created graph is represented
     * as adjacency list.
     *
     * @param numberOfVertices number of vertices in the created graph
     * @param numberOfEdges    number of vertices in the created graph
     * @param maxWeight        maximum weight an edge can have in the given graph
     * @param random           {@link Random} to use in the creation process
     * @return random, undirected graph with {@code numberOfVertices} vertices and {@code numberOfEdges} edges
     */
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

    /**
     * Creates a complete graph with {@code numberOfVertices} vertices. A "complete" graph means that there is an edge
     * between each pair of vertices v,w ∈ V. The weight of an edge in the created graph is between [0;{@code maxWeight}].
     * The created graph is represented as adjacency list. Calls {@link UndirectedGraphUtils#createCompleteGraph(int, int, int, Random)}.
     *
     * @param numberOfVertices number of vertices in the graph
     * @param maxWeight        maximum weight an edge can have in the given graph
     * @param random           {@link Random} to use in the creation process
     * @return random, undirected, complete graph with {@code numberOfVertices} vertices
     */
    public static @NotNull UnAdjacencyList createCompleteGraph(int numberOfVertices, int maxWeight, @NotNull Random random) {
        return UndirectedGraphUtils.createCompleteGraph(numberOfVertices, 0, maxWeight, random);
    }

    /**
     * Creates a complete graph with {@code numberOfVertices} vertices. A "complete" graph means that there is an edge
     * between each pair of vertices v,w ∈ V. The weight of an edge in the created graph is between [{@code minWeight};{@code maxWeight}].
     * The created graph is represented as adjacency list.
     *
     * @param numberOfVertices number of vertices in the graph
     * @param minWeight        minimum weight an edge can have in the given graph
     * @param maxWeight        maximum weight an edge can have in the given graph
     * @param random           {@link Random} to use in the creation process
     * @return random, undirected, complete graph with {@code numberOfVertices} vertices of weight between [{@code minWeight};{@code maxWeight}]
     */
    public static @NotNull UnAdjacencyList createCompleteGraph(int numberOfVertices, int minWeight, int maxWeight, @NotNull Random random) {
        List<UnVertex> vertices = new ArrayList<>();
        for (int i = 1; i <= numberOfVertices; i++) {
            int vertexWeight = IntegerUtils.getRandomIntBetween(minWeight, maxWeight, random);
            UnVertex vertex = new UnVertex(String.format(VERTEX_NAME_TF, i), vertexWeight);
            vertices.add(vertex);
        }
        List<UnEdge> edges = new ArrayList<>();
        for (int i = 0; i < vertices.size() - 1; i++) {
            UnVertex v = vertices.get(i);
            for (int j = i + 1; j < vertices.size(); j++) {
                UnVertex w = vertices.get(j);
                int edgeWeight = IntegerUtils.getRandomIntBetween(minWeight, maxWeight, random);
                UnEdge edge = new UnEdge(String.format(EDGE_NAME_TF, v.getName(), w.getName()), edgeWeight, v, w);
                edges.add(edge);
            }
        }
        return new UnAdjacencyList(vertices, edges);
    }

    /**
     * Checks whether the given graph (represented as adjacency list) is a complete graph (i.e. there is an edge between
     * each pair of vertices v,w ∈ V).
     *
     * @param adjacencyList graph which is to check for its completeness
     * @return {@code true} if the graph is a complete graph, {@code false} else
     */
    public static boolean isCompleteGraph(@NotNull UnAdjacencyList adjacencyList) {
        List<UnVertex> vertices = adjacencyList.vertices();
        for (UnVertex vertex : vertices) {
            Set<UnVertex> verticesOfIncidentEdges = new HashSet<>();
            for (UnEdge edge : vertex.getEdges()) {
                verticesOfIncidentEdges.addAll(edge.getVertices());
            }
            if (verticesOfIncidentEdges.size() != vertices.size()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the edges of {@code vertex} sorted by weight (in ascending order).
     *
     * @param vertex vertex whose edges are to be returned
     * @return edges of {@code vertex} sorted by weight (in ascending order)
     */
    public static @NotNull List<UnEdge> getEdgesSortedByWeight(@NotNull UnVertex vertex) {
        List<UnEdge> edgesSortedByLength = new ArrayList<>(vertex.getEdges());
        edgesSortedByLength.sort(GraphConstants.COMPARE_VERTICES_BY_WEIGHT);
        return edgesSortedByLength;
    }

    /**
     * Returns the edge connecting the vertices {@code v} and {@code w}. Returns {@code null} if there is no such edge.
     *
     * @param v vertex whose connecting edge with {@code w} is to be returned
     * @param w vertex whose connecting edge with {@code v} is to be returned
     * @return edge connecting {@code v} and {@code w}, {@code null} if there is no such edge
     */
    public static @Nullable UnEdge getEdgeConnectingVAndW(@NotNull UnVertex v, @NotNull UnVertex w) {
        UnEdge connectingEdge = null;
        for (UnEdge currentEdge : v.getEdges()) {
            UnVertex otherVertex = UndirectedGraphUtils.getOtherVertexOfEdge(currentEdge, v);
            if (otherVertex == w) {
                connectingEdge = currentEdge;
                break;
            }
        }
        return connectingEdge;
    }

    /**
     * Converts the given {@code adjacencyList} into a corresponding adjacency matrix representation.
     * The indices of the vertices in the adjacency matrix are derived from the indices of the vertices in the
     * vertices' list of {@code adjacencyList}
     *
     * @param adjacencyList adjacency list to be converted into a corresponding adjacency matrix representation
     * @return adjacency matrix representation of {@code adjacencyList}
     */
    public static @NotNull UnAdjacencyMatrix convertToAdjacencyMatrix(@NotNull UnAdjacencyList adjacencyList) {
        List<UnVertex> vertices = adjacencyList.vertices();
        // create matrix
        UnAdjacencyMatrix adjacencyMatrix = new UnAdjacencyMatrix(vertices.size());
        // add edges
        Map<UnVertex, Integer> indexLookupForVertices = UndirectedGraphUtils.createIndexLookupForVertices(adjacencyList);
        // iterate over edges, add to adjacency matrix
        for (UnEdge edge : adjacencyList.edges()) {
            List<Integer> indicesOfEndpoints = edge.getVertices().stream().map(indexLookupForVertices::get).toList();
            adjacencyMatrix.addEdge(indicesOfEndpoints.get(0), indicesOfEndpoints.get(1), edge.getWeight());
        }
        return adjacencyMatrix;
    }

    /**
     * Creates a lookup table for the indices of the {@code adjacencyList}'s vertices, i.e. to the returned map, a vertex
     * can be passed as key and the returned value is the index of the vertex in the vertices' list of {@code adjacencyList}.
     *
     * @param adjacencyList adjacency list for which a lookup table for the indices of the vertices is to be created
     * @return lookup table as a map with the vertices as keys and their indices as corresponding values
     */
    public static @NotNull Map<UnVertex, Integer> createIndexLookupForVertices(@NotNull UnAdjacencyList adjacencyList) {
        List<UnVertex> vertices = adjacencyList.vertices();
        Map<UnVertex, Integer> indexLookupForVertices = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++) {
            UnVertex vertex = vertices.get(i);
            indexLookupForVertices.put(vertex, i);
        }
        return indexLookupForVertices;
    }
}
