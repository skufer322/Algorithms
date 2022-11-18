package de.sk.graphs;

import de.sk.graphs.datastructure.AdjacencyList;
import de.sk.graphs.datastructure.AdjacencyListImpl;
import de.sk.graphs.datastructure.Edge;
import de.sk.graphs.datastructure.Vertex;
import de.sk.graphs.dfs.GraphDfs;
import de.sk.graphs.dfs.IterativeGraphDfs;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class App8G {

    public static void main(String[] args) {
//        Vertex v1 = new Vertex("v1");
//        Vertex v2 = new Vertex("v2");
//        Vertex v3 = new Vertex("v3");
//
//        Edge e12 = new Edge("e12", v1, v2);
//        Edge e13 = new Edge("e13", v1, v3);
//        Edge e23 = new Edge("e23", v2, v3);
//
//        List<Vertex> vertices = List.of(v1, v2, v3);
//        List<Edge> edges = List.of(e12, e13, e23);
//
//        AdjacencyList adjacencyList = new AdjacencyListImpl(vertices, edges);
//        System.out.println(adjacencyList);
//
//        GraphBfs bfs = new GraphBfsImpl();
//        bfs.conductBfs(adjacencyList);
//        System.out.println(adjacencyList);
        AdjacencyList adjacencyList = createAdjacencyList();
        System.out.println(adjacencyList);
        GraphDfs dfs = new IterativeGraphDfs();
//        GraphDfs dfs = new RecursiveGraphDfs();
        List<Vertex> verticesByDfs = dfs.conductDfs(adjacencyList, adjacencyList.vertices().get(0));
        System.out.println(verticesByDfs);
////        GraphBfs bfs = new GraphBfsImpl();
//        GraphBfs bfs = new AugmentedBfs();
//        List<Vertex> verticesByBfs = bfs.conductBfs(adjacencyList, vs);
//        String out = verticesByBfs.stream()
//                .map(vertex -> vertex.getName() + " [" + vertex.getGraphSearchPosition() + "] + {" + vertex.getLevel() + "}")
////                .map(vertex -> vertex.getName() + " [" + vertex.getGraphSearchPosition() + "]")
//                .collect(Collectors.joining(" | "));
//        System.out.println(out);
//        UndirectedCC ucc = new UndirectedCC();
//        List<Set<Vertex>> ccs = ucc.determineConnectedComponents(adjacencyList);
//        for (Set<Vertex> cc : ccs) {
//            System.out.println(cc);
//        }
    }

    private static @NotNull AdjacencyList createAdjacencyList() {
        Vertex vs = new Vertex("s");
        Vertex va = new Vertex("a");
        Vertex vb = new Vertex("b");
        Vertex vc = new Vertex("c");
        Vertex vd = new Vertex("d");
        Vertex ve = new Vertex("e");
        List<Vertex> vertices = List.of(vs, va, vb, vc, vd, ve);
        Edge sa = new Edge("sa", vs, va);
        Edge sb = new Edge("sb", vs, vb);
        Edge ac = new Edge("ac", va, vc);
        Edge bc = new Edge("bc", vb, vc);
        Edge bd = new Edge("bd", vb, vd);
        Edge cd = new Edge("cd", vc, vd);
        Edge ce = new Edge("ce", vc, ve);
        Edge de = new Edge("de", vd, ve);
        List<Edge> edges = List.of(sa, sb, ac, bc, bd, cd, ce, de);
        return new AdjacencyListImpl(vertices, edges);
    }

    private static @NotNull AdjacencyList createCcAdjacencyList() {
        // cc1
        Vertex v1 = new Vertex("1");
        Vertex v3 = new Vertex("3");
        Vertex v5 = new Vertex("5");
        Vertex v7 = new Vertex("7");
        Vertex v9 = new Vertex("9");
        Edge e13 = new Edge("13", v1, v3);
        Edge e15 = new Edge("15", v1, v5);
        Edge e35 = new Edge("35", v3, v5);
        Edge e57 = new Edge("57", v5, v7);
        Edge e59 = new Edge("59", v5, v9);
        // cc2
        Vertex v2 = new Vertex("2");
        Vertex v4 = new Vertex("4");
        Edge e24 = new Edge("24", v2, v4);
        // cc3
        Vertex v6 = new Vertex("6");
        Vertex v8 = new Vertex("8");
        Vertex v10 = new Vertex("10");
        Edge e68 = new Edge("68", v6, v8);
        Edge e6_10 = new Edge("6_10", v6, v10);
        List<Vertex> vertices = List.of(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10);
        List<Edge> edges = List.of(e13, e15, e35, e57, e59, e24, e68, e6_10);
        return new AdjacencyListImpl(vertices, edges);
    }
}
