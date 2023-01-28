package de.sk.nphard.tsp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.nphard.NpHardInjectionModule;
import de.sk.nphard.NpHardnessConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class A20_2OPT_TSP {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new NpHardInjectionModule());
        Random random = new Random(322);
        UnAdjacencyList graphAsAdjacencyList = UndirectedGraphUtils.createCompleteGraph(9, 1, 30, random);
//        UnAdjacencyList graphAsAdjacencyList = createIncompleteGraph();
//        UnAdjacencyList graphAsAdjacencyList = createCompleteGraphFromQuiz20_7();

        TspSolver randomTspSolver = injector.getInstance(Key.get(TspSolver.class, Names.named(NpHardnessConstants.IN_RANDOM_TSP_SOLVER)));
        TspSolver nearestNeighborSolver = injector.getInstance(Key.get(TspSolver.class, Names.named(NpHardnessConstants.IN_NEAREST_NEIGHBOR_TSP_SOLVER)));
        TspSolver twoOptSolver = injector.getInstance(Key.get(TspSolver.class, Names.named(NpHardnessConstants.IN_2OPT_HEURISTIC_TSP_SOLVER)));
        TspSolver exhaustiveEfficientSolver = injector.getInstance(Key.get(TspSolver.class, Names.named(NpHardnessConstants.IN_EXHAUSTIVE_MEMORY_EFFICIENT_TSP_SOLVER)));
        TspSolver exhaustiveCachedPermutationsSolver = injector.getInstance(Key.get(TspSolver.class, Names.named(NpHardnessConstants.IN_EXHAUSTIVE_CACHED_PERMUTATIONS_TSP_SOLVER)));
        TspSolver bellmanHeldKarp = injector.getInstance(Key.get(TspSolver.class, Names.named(NpHardnessConstants.IN_BELLMAN_HELD_KARP_TSP_SOLVER)));
        Pair<List<UnEdge>, Integer> tourRandom = randomTspSolver.determineShortestTour(graphAsAdjacencyList);
        Pair<List<UnEdge>, Integer> tourNearestNeighbor = nearestNeighborSolver.determineShortestTour(graphAsAdjacencyList);
        Pair<List<UnEdge>, Integer> tour2Opt = twoOptSolver.determineShortestTour(graphAsAdjacencyList);
        Pair<List<UnEdge>, Integer> tourEfficientExhaustive = exhaustiveEfficientSolver.determineShortestTour(graphAsAdjacencyList);
        Pair<List<UnEdge>, Integer> tourCachedPermutationsExhaustive = exhaustiveCachedPermutationsSolver.determineShortestTour(graphAsAdjacencyList);
        Pair<List<UnEdge>, Integer> tourBellmanHeldKarp = bellmanHeldKarp.determineShortestTour(graphAsAdjacencyList);
        System.out.println(">>>");
        System.out.println(tourRandom.getRight());
        System.out.println(tourNearestNeighbor.getRight());
        System.out.println(tour2Opt.getRight());
        System.out.println(tourEfficientExhaustive.getRight());
        System.out.println(tourCachedPermutationsExhaustive.getRight());
        System.out.println(tourBellmanHeldKarp.getRight());
    }

    private static @NotNull UnAdjacencyList createIncompleteGraph() {
        UnVertex a = new UnVertex("a");
        UnVertex b = new UnVertex("b");
        UnVertex c = new UnVertex("c");
        List<UnVertex> vertices = List.of(a, b, c);
        UnEdge ab = new UnEdge("ab", 4, a, b);
        UnEdge ac = new UnEdge("ac", a, c);
        List<UnEdge> edges = List.of(ab, ac);
        return new UnAdjacencyList(vertices, edges);
    }

    private static @NotNull UnAdjacencyList createCompleteGraphFromQuiz20_7() {
        UnVertex a = new UnVertex("a");
        UnVertex b = new UnVertex("b");
        UnVertex c = new UnVertex("c");
        UnVertex d = new UnVertex("d");
        UnVertex e = new UnVertex("e");
        List<UnVertex> vertices = List.of(a, b, c, d, e);
        UnEdge ab = new UnEdge("ab", 1, a, b);
        UnEdge bc = new UnEdge("bc", 2, b, c);
        UnEdge be = new UnEdge("be", 3, b, e);
        UnEdge ac = new UnEdge("ac", 4, a, c);
        UnEdge ad = new UnEdge("ad", 5, a, d);
        UnEdge bd = new UnEdge("bd", 6, b, d);
        UnEdge cd = new UnEdge("cd", 7, c, d);
        UnEdge ce = new UnEdge("ce", 8, c, e);
        UnEdge de = new UnEdge("de", 9, d, e);
        UnEdge ae = new UnEdge("ae", 10, a, e);
        List<UnEdge> edges = List.of(ab, bc, be, ac, ad, bd, cd, ce, de, ae);
        return new UnAdjacencyList(vertices, edges);
    }
}
