package de.sk.nphard.tsp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.nphard.NpHardInjectionModule;
import de.sk.nphard.tsp.impl.ExhaustiveSearchLowMemoryTspSolver;
import de.sk.nphard.tsp.impl.NearestNeighborTspSolver;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class A19TSP {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new NpHardInjectionModule());
//        UnAdjacencyList graph = createCompleteGraphFromQuiz19_2();
        Random random = new Random(322);
        UnAdjacencyList graph = UndirectedGraphUtils.createCompleteGraph(11, 100, random);
        TspSolver tspSolver = injector.getInstance(ExhaustiveSearchLowMemoryTspSolver.class);
        Pair<List<UnEdge>, Integer> shortestTour = tspSolver.determineShortestTour(graph);
        System.out.printf("tspsolver: %d -> %s%n", shortestTour.getRight(), shortestTour);
        TspSolver tspSolver2 = injector.getInstance(NearestNeighborTspSolver.class);
        shortestTour = tspSolver2.determineShortestTour(graph);
        System.out.printf("tspsolver2: %d -> %s%n", shortestTour.getRight(), shortestTour);
    }

    private static @NotNull UnAdjacencyList createCompleteGraphFromQuiz19_2() {
        UnVertex a = new UnVertex("a", 1);
        UnVertex b = new UnVertex("b", 1);
        UnVertex c = new UnVertex("c", 1);
        UnVertex d = new UnVertex("d", 1);
        List<UnVertex> vertices = List.of(a, b, c, d);
        UnEdge ab = new UnEdge("ab", 1, a, b);
        UnEdge ac = new UnEdge("ac", 4, a, c);
        UnEdge ad = new UnEdge("ad", 3, a, d);
        UnEdge bc = new UnEdge("bc", 5, b, c);
        UnEdge bd = new UnEdge("bd", 2, b, d);
        UnEdge cd = new UnEdge("cd", 6, c, d);
        List<UnEdge> edges = List.of(ab, ac, ad, bc, bd, cd);
        return new UnAdjacencyList(vertices, edges);
    }
}
