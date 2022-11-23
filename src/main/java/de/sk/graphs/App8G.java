package de.sk.graphs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import de.sk.graphs.algorithm.bfs.GraphBfs;
import de.sk.graphs.algorithm.cc.UnConnectedComponents;
import de.sk.graphs.algorithm.dfs.DiSccDfs;
import de.sk.graphs.algorithm.dfs.DiTopSort;
import de.sk.graphs.algorithm.dfs.UnGraphDfs;
import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.injection.GraphsInjectionModule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class App8G {

    public static void main(String[] args) {
        // setup guice dependency injection
        Injector injector = Guice.createInjector(new GraphsInjectionModule());

        // BFS
//        GraphBfs bfs = injector.getInstance(Key.get(GraphBfs.class, Names.named(GraphConstants.INJECTION_NAME_GRAPH_BFS_SIMPLE)));
//        UnAdjacencyList adjacencyList = createSimpleAdjacencyListUndirectedGraph();
//        System.out.println(adjacencyList);
//        List<UnVertex> bfsOrder = bfs.conductBfs(adjacencyList, adjacencyList.vertices().get(0));
//        System.out.println(bfsOrder);

        // Augmented-BFS
//        GraphBfs bfs = injector.getInstance(Key.get(GraphBfs.class, Names.named(GraphConstants.INJECTION_NAME_AUGMENTED_BFS)));
//        UnAdjacencyList adjacencyList = createSimpleAdjacencyListUndirectedGraph();
//        System.out.println(adjacencyList);
//        List<UnVertex> bfsOrderAugmented = bfs.conductBfs(adjacencyList, adjacencyList.vertices().get(0));
//        System.out.println(bfsOrderAugmented);

        // Connected Components, Undirected Graph
//        UnConnectedComponents unCc = injector.getInstance(UnConnectedComponents.class);
//        UnAdjacencyList adjacencyList = createSimpleAdjacencyListUndirectedGraph();
//        System.out.println(adjacencyList);
//        List<Set<UnVertex>> connectedComponents = unCc.determineConnectedComponents(adjacencyList);
//        for (Set<UnVertex> connectedComponent : connectedComponents) {
//            System.out.println(connectedComponent);
//        }

        // Iterative DFS
//        UnGraphDfs dfs = injector.getInstance(Key.get(UnGraphDfs.class, Names.named(GraphConstants.INJECTION_NAME_ITERATIVE_DFS)));
//        UnAdjacencyList adjacencyList = createSimpleAdjacencyListUndirectedGraph();
//        System.out.println(adjacencyList);
//        List<UnVertex> dfsOrderIterative = dfs.conductDfs(adjacencyList, adjacencyList.vertices().get(0));
//        System.out.println(dfsOrderIterative);

        // Recursive DFS
//        UnGraphDfs dfs = injector.getInstance(Key.get(UnGraphDfs.class, Names.named(GraphConstants.INJECTION_NAME_RECURSIVE_DFS)));
//        UnAdjacencyList adjacencyList = createSimpleAdjacencyListUndirectedGraph();
//        System.out.println(adjacencyList);
//        List<UnVertex> dfsOrderRecursive = dfs.conductDfs(adjacencyList, adjacencyList.vertices().get(0));
//        System.out.println(dfsOrderRecursive);

        // TopSort
//        DiTopSort topSort = injector.getInstance(DiTopSort.class);
//        DiAdjacencyList diAdjacencyList = createComplexTopSortAdjacencyList();
//        System.out.println(diAdjacencyList);
//        List<DiVertex> topologicalOrdering = topSort.determineTopologicalOrdering(diAdjacencyList, true);
//        System.out.println(topologicalOrdering);

        // SCC
        DiSccDfs sccDfs = injector.getInstance(DiSccDfs.class);
        DiAdjacencyList diAdjacencyList = createSccAdjacencyList();
        System.out.println(diAdjacencyList);
        Map<Integer, List<DiVertex>> scc = sccDfs.determineScc(diAdjacencyList);
        System.out.println(scc);
    }

    private static @NotNull UnAdjacencyList createSimpleAdjacencyListUndirectedGraph() {
        UnVertex v1 = new UnVertex("v1");
        UnVertex v2 = new UnVertex("v2");
        UnVertex v3 = new UnVertex("v3");

        UnEdge e12 = new UnEdge("e12", v1, v2);
        UnEdge e13 = new UnEdge("e13", v1, v3);
        UnEdge e23 = new UnEdge("e23", v2, v3);

        List<UnVertex> vertices = List.of(v1, v2, v3);
        List<UnEdge> edges = List.of(e12, e13, e23);

        return new UnAdjacencyList(vertices, edges);
    }

    private static @NotNull UnAdjacencyList createAdjacencyListUndirectedGraph() {
        UnVertex vs = new UnVertex("s");
        UnVertex va = new UnVertex("a");
        UnVertex vb = new UnVertex("b");
        UnVertex vc = new UnVertex("c");
        UnVertex vd = new UnVertex("d");
        UnVertex ve = new UnVertex("e");
        List<UnVertex> vertices = List.of(vs, va, vb, vc, vd, ve);
        UnEdge sa = new UnEdge("sa", vs, va);
        UnEdge sb = new UnEdge("sb", vs, vb);
        UnEdge ac = new UnEdge("ac", va, vc);
        UnEdge bc = new UnEdge("bc", vb, vc);
        UnEdge bd = new UnEdge("bd", vb, vd);
        UnEdge cd = new UnEdge("cd", vc, vd);
        UnEdge ce = new UnEdge("ce", vc, ve);
        UnEdge de = new UnEdge("de", vd, ve);
        List<UnEdge> edges = List.of(sa, sb, ac, bc, bd, cd, ce, de);
        return new UnAdjacencyList(vertices, edges);
    }

    private static @NotNull UnAdjacencyList createCcAdjacencyListUndirectedGraph() {
        // cc1
        UnVertex v1 = new UnVertex("1");
        UnVertex v3 = new UnVertex("3");
        UnVertex v5 = new UnVertex("5");
        UnVertex v7 = new UnVertex("7");
        UnVertex v9 = new UnVertex("9");
        UnEdge e13 = new UnEdge("13", v1, v3);
        UnEdge e15 = new UnEdge("15", v1, v5);
        UnEdge e35 = new UnEdge("35", v3, v5);
        UnEdge e57 = new UnEdge("57", v5, v7);
        UnEdge e59 = new UnEdge("59", v5, v9);
        // cc2
        UnVertex v2 = new UnVertex("2");
        UnVertex v4 = new UnVertex("4");
        UnEdge e24 = new UnEdge("24", v2, v4);
        // cc3
        UnVertex v6 = new UnVertex("6");
        UnVertex v8 = new UnVertex("8");
        UnVertex v10 = new UnVertex("10");
        UnEdge e68 = new UnEdge("68", v6, v8);
        UnEdge e6_10 = new UnEdge("6_10", v6, v10);
        List<UnVertex> vertices = List.of(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10);
        List<UnEdge> edges = List.of(e13, e15, e35, e57, e59, e24, e68, e6_10);
        return new UnAdjacencyList(vertices, edges);
    }

    private static @NotNull DiAdjacencyList createSimpleTopSortAdjacencyList() {
        DiVertex s = new DiVertex("s");
        DiVertex v = new DiVertex("v");
        DiVertex w = new DiVertex("w");
        DiVertex t = new DiVertex("t");
        DiEdge sv = new DiEdge("sv", s, v);
        DiEdge sw = new DiEdge("sw", s, w);
        DiEdge vt = new DiEdge("vt", v, t);
        DiEdge wt = new DiEdge("wt", w, t);
        List<DiVertex> vertices = List.of(s, v, w, t);
        List<DiEdge> edges = List.of(sv, sw, vt, wt);
        return new DiAdjacencyList(vertices, edges);
    }

    private static @NotNull DiAdjacencyList createComplexTopSortAdjacencyList() {
        DiVertex a = new DiVertex("a");
        DiVertex b = new DiVertex("b");
        DiVertex c = new DiVertex("c");
        DiVertex d = new DiVertex("d");
        DiVertex e = new DiVertex("e");
        DiVertex f = new DiVertex("f");
        DiVertex g = new DiVertex("g");
        DiVertex h = new DiVertex("h");
        DiVertex i = new DiVertex("i");
        // edge config 1
        DiEdge ab = new DiEdge("ab", a, b);
        DiEdge ac = new DiEdge("ac", a, c);
        DiEdge cb = new DiEdge("cb", c, b);
        DiEdge bd = new DiEdge("bd", b, d);
        DiEdge ce = new DiEdge("ce", c, e);
        DiEdge cf = new DiEdge("cf", c, f);
        DiEdge ed = new DiEdge("ed", e, d);
        DiEdge eh = new DiEdge("eh", e, h);
        DiEdge fh = new DiEdge("fh", f, h);
        DiEdge dg = new DiEdge("dg", d, g);
        DiEdge gi = new DiEdge("gi", g, i);
        DiEdge hi = new DiEdge("hi", h, i);

        // edge config 2
//        DiEdge ab = new DiEdge("ab", a, b);
//        DiEdge bc = new DiEdge("bc", b, c);
//        DiEdge bd = new DiEdge("bd", b, d);
//        DiEdge de = new DiEdge("de", d, e);
//        DiEdge ef = new DiEdge("ef", e, f);
//        DiEdge dg = new DiEdge("dg", d, g);
//        DiEdge gh = new DiEdge("gh", g, h);
//        DiEdge gi = new DiEdge("gi", g, i);
//        DiEdge hi = new DiEdge("hi", h, i);

        List<DiVertex> vertices = List.of(a, b, c, d, e, f, g, h, i);
        List<DiEdge> edges = List.of(ab, ac, cb, bd, ce, cf, ed, eh, fh, dg, gi, hi); // config 1
//        List<DiEdge> edges = List.of(ab, bc, bd, de, ef, dg, gh, gi, hi); // config 2
        return new DiAdjacencyList(vertices, edges);
    }

    private static @NotNull DiAdjacencyList createSccAdjacencyList() {
        DiVertex v01 = new DiVertex("1");
        DiVertex v02 = new DiVertex("2");
        DiVertex v03 = new DiVertex("3");
        DiVertex v04 = new DiVertex("4");
        DiVertex v05 = new DiVertex("5");
        DiVertex v06 = new DiVertex("6");
        DiVertex v07 = new DiVertex("7");
        DiVertex v08 = new DiVertex("8");
        DiVertex v09 = new DiVertex("9");
        DiVertex v10 = new DiVertex("10");
        DiVertex v11 = new DiVertex("11");

        DiEdge e_3_1 = new DiEdge("3_1", v03, v01);
        DiEdge e_1_5 = new DiEdge("1_5", v01, v05);
        DiEdge e_5_3 = new DiEdge("5_3", v05, v03);
        DiEdge e_11_3 = new DiEdge("11_3", v11, v03);
        DiEdge e_6_11 = new DiEdge("6_11", v06, v11);
        DiEdge e_8_11 = new DiEdge("8_11", v08, v11);
        DiEdge e_7_5 = new DiEdge("7_5", v07, v05);
        DiEdge e_9_5 = new DiEdge("9_5", v09, v05);
        DiEdge e_9_7 = new DiEdge("9_7", v09, v07);
        DiEdge e_7_4 = new DiEdge("7_4", v07, v04);
        DiEdge e_4_9 = new DiEdge("4_9", v04, v09);
        DiEdge e_4_2 = new DiEdge("4_2", v04, v02);
        DiEdge e_2_9 = new DiEdge("2_9", v02, v09);
        DiEdge e_8_9 = new DiEdge("8_9", v08, v09);
        DiEdge e_6_8 = new DiEdge("6_8", v06, v08);
        DiEdge e_8_10 = new DiEdge("8_10", v08, v10);
        DiEdge e_10_2 = new DiEdge("10_2", v10, v02);
        DiEdge e_10_6 = new DiEdge("10_6", v10, v06);

        List<DiVertex> vertices = List.of(v01, v02, v03, v04, v05, v06, v07, v08, v09, v10, v11);
        List<DiEdge> edges = List.of(e_3_1, e_1_5, e_5_3, e_11_3, e_6_11, e_8_11, e_7_5, e_9_5, e_9_7, e_7_4, e_4_9, e_4_2, e_2_9, e_8_9, e_6_8, e_8_10, e_10_2, e_10_6);
        return new DiAdjacencyList(vertices, edges);
    }
}
