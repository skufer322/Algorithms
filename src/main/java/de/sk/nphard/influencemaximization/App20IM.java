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
        double p = 0.7d;
        int k = 3;
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
}
