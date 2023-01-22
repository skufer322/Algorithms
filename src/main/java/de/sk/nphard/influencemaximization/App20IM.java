package de.sk.nphard.influencemaximization;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.graphs.datastructure.directed.DiAdjacencyList;
import de.sk.graphs.datastructure.directed.DiEdge;
import de.sk.graphs.datastructure.directed.DiVertex;
import de.sk.nphard.NpHardInjectionModule;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class App20IM {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new NpHardInjectionModule());
        DiAdjacencyList graph = createGraphFromExample20_3_2();
//        DiAdjacencyList graph = createSlightlyMoreComplexExample();
        double p = 0.5d;
        int k = 2;
        InfMaxSolver infMaxSolver = injector.getInstance(InfMaxSolver.class);
        Set<DiVertex> seedsWithMaxInfluence = infMaxSolver.maximizeInfluence(graph, p, k);
        System.out.println(seedsWithMaxInfluence);
    }

    private static @NotNull DiAdjacencyList createGraphFromExample20_3_2() {
        DiVertex a = new DiVertex("a");
        DiVertex b = new DiVertex("b");
        DiVertex c = new DiVertex("c");
        DiVertex d = new DiVertex("d");
        List<DiVertex> vertices = List.of(a, b, c, d);
        DiEdge ab = new DiEdge("ab", a, b);
        DiEdge ac = new DiEdge("ac", a, c);
        DiEdge ad = new DiEdge("ad", a, d);
        DiEdge bd = new DiEdge("bd", b, d);
        DiEdge cd = new DiEdge("cd", c, d);
        List<DiEdge> edges = List.of(ab, ac, ad, bd, cd);
        return new DiAdjacencyList(vertices, edges);
    }

    private static @NotNull DiAdjacencyList createSlightlyMoreComplexExample() {
        DiVertex a = new DiVertex("a");
        DiVertex b = new DiVertex("b");
        DiVertex c = new DiVertex("c");
        DiVertex d = new DiVertex("d");
        DiVertex e = new DiVertex("e");
        List<DiVertex> vertices = List.of(a, b, c, d, e);
        DiEdge ab = new DiEdge("ab", a, b);
        DiEdge ad = new DiEdge("ad", a, d);
        DiEdge be = new DiEdge("be", b, e);
        DiEdge ce = new DiEdge("ce", c, e);
        DiEdge db = new DiEdge("db", d, b);
        DiEdge dc = new DiEdge("dc", d, c);
        DiEdge de = new DiEdge("de", d, e);
        List<DiEdge> edges = List.of(ab, ad, be, ce, db, dc, de);
        return new DiAdjacencyList(vertices, edges);
    }
}
