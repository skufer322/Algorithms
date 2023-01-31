package de.sk.nphard.kpaths;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.nphard.NpHardInjectionModule;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App21KPaths {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new NpHardInjectionModule());
        UnAdjacencyList graph = createGraphFromProblem21_5();
        int k = 4;
        ShortestKPathSolver shortestKPathSolver = injector.getInstance(ShortestKPathSolver.class);
        Pair<List<UnEdge>, Integer> shortestKPath = shortestKPathSolver.shortestKPath(graph, k);
        System.out.println(shortestKPath.getLeft());
        System.out.println(shortestKPath.getRight());
    }

    private static @NotNull UnAdjacencyList createGraphFromProblem21_5() {
        UnVertex a = new UnVertex("a");
        UnVertex b = new UnVertex("b");
        UnVertex c = new UnVertex("c");
        UnVertex d = new UnVertex("d");
        UnVertex e = new UnVertex("e");
        UnVertex f = new UnVertex("f");
        UnVertex g = new UnVertex("g");
        UnVertex h = new UnVertex("h");
        List<UnVertex> vertices = List.of(a, b, c, d, e, f, g, h);
        UnEdge ac = new UnEdge("ac", 1, a, c);
        UnEdge ce = new UnEdge("ce", 7, c, e);
        UnEdge eg = new UnEdge("eg", 9, e, g);
        UnEdge af = new UnEdge("af", 2, a, f);
        UnEdge ch = new UnEdge("ch", 8, c, h);
        UnEdge be = new UnEdge("be", 6, b, e);
        UnEdge dg = new UnEdge("dg", 5, d, g);
        UnEdge bd = new UnEdge("bd", 4, b, d);
        UnEdge df = new UnEdge("df", 3, d, f);
        UnEdge fh = new UnEdge("fh", 10, f, h);
        List<UnEdge> edges = List.of(ac, ce, eg, af, ch, be, dg, bd, df, fh);
        return new UnAdjacencyList(vertices, edges);
    }

    public static @NotNull Map<UnVertex, Integer> colorizeGraphLikeInProblem21_5(@NotNull UnAdjacencyList graphFromProblem21_5) {
        Map<UnVertex, Integer> colorizedGraph = new HashMap<>();
        List<UnVertex> vertices = graphFromProblem21_5.vertices();
        colorizedGraph.put(vertices.get(0), 0);
        colorizedGraph.put(vertices.get(1), 0);
        colorizedGraph.put(vertices.get(2), 1);
        colorizedGraph.put(vertices.get(3), 1);
        colorizedGraph.put(vertices.get(4), 2);
        colorizedGraph.put(vertices.get(5), 2);
        colorizedGraph.put(vertices.get(6), 3);
        colorizedGraph.put(vertices.get(7), 3);
        return colorizedGraph;
    }
}
