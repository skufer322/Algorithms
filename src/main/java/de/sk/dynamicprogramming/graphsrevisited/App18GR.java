package de.sk.dynamicprogramming.graphsrevisited;

import de.sk.dynamicprogramming.graphsrevisited.floydwarshall.FloydWarshallAlg;
import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class App18GR {

    public static void main(String[] args) {
        DiAdjacencyList graph = createGraphWithoutNegativeCycle();
        DiVertex s = graph.vertices().get(0);
//        BellmanFordAlg bellmanFordAlg = new BellmanFordAlg();
//        Map<DiVertex, Pair<List<DiVertex>, Integer>> shortestPaths = bellmanFordAlg.determineShortestPathsFromS(graph, s);
//        for (Map.Entry<DiVertex, Pair<List<DiVertex>, Integer>> shortestPathEntry : shortestPaths.entrySet()) {
//            System.out.printf("shortest path for '%s' (%d): %s%s", shortestPathEntry.getKey().getName(), shortestPathEntry.getValue().getRight(),
//                    shortestPathEntry.getValue().getLeft().stream().map(Vertex::getName).collect(Collectors.joining(", ")), System.lineSeparator());
//        }
        FloydWarshallAlg floydWarshallAlg = new FloydWarshallAlg();
        Map<DiVertex, Map<DiVertex, Pair<List<DiVertex>, Integer>>> shortestPathForAllPairs = floydWarshallAlg.determineShortestPathForAllPairs(graph);
        System.out.println("xxx");
    }

    private static @NotNull DiAdjacencyList createGraphWithNegativeCycle() {
        DiVertex s = new DiVertex("s");
        DiVertex u = new DiVertex("u");
        DiVertex v = new DiVertex("v");
        DiVertex w = new DiVertex("w");
        DiVertex x = new DiVertex("x");
        List<DiVertex> vertices = List.of(s, u, v, w, x);
        DiEdge su = new DiEdge("su", s, u, 1);
        DiEdge uv = new DiEdge("uv", u, v, -2);
        DiEdge ux = new DiEdge("ux", u, x, 4);
        DiEdge vw = new DiEdge("vw", v, w, -3);
        DiEdge wu = new DiEdge("wu", w, u, -1);
        List<DiEdge> edges = List.of(su, uv, ux, vw, wu);
        return new DiAdjacencyList(vertices, edges);
    }

    private static @NotNull DiAdjacencyList createGraphWithoutNegativeCycle() {
        DiVertex s = new DiVertex("s");
        DiVertex u = new DiVertex("u");
        DiVertex v = new DiVertex("v");
        DiVertex w = new DiVertex("w");
        DiVertex t = new DiVertex("t");
        List<DiVertex> vertices = List.of(s, u, v, w, t);
        DiEdge su = new DiEdge("su", s, u, 2);
        DiEdge sv = new DiEdge("sv", s, v, 4);
        DiEdge uv = new DiEdge("uv", u, v, -1);
        DiEdge uw = new DiEdge("uw", u, w, 2);
        DiEdge vt = new DiEdge("vt", v, t, 4);
        DiEdge wt = new DiEdge("wt", w, t, 2);
        List<DiEdge> edges = List.of(su, sv, uv, uw, vt, wt);
        return new DiAdjacencyList(vertices, edges);
    }
}
