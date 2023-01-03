package de.sk.greedy.mst;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.greedy.GreedyInjectionModule;
import de.sk.greedy.mst.kruskal.KruskalAlgSimple;
import de.sk.greedy.mst.prim.PrimsAlgSimple;

import java.util.List;

// TODO: string in Javadoc mit {@link} versehen? ebenso true/false?
// TODO: ergebnisse der 4 mst varianten miteinander vergleichen
public class App15Mst {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new GreedyInjectionModule());

        UnVertex a = new UnVertex("a");
        UnVertex b = new UnVertex("b");
        UnVertex c = new UnVertex("c");
        UnVertex d = new UnVertex("d");
        List<UnVertex> vertices = List.of(a, b, c, d);
        UnEdge ab = new UnEdge("ab", 1, a, b);
        UnEdge ac = new UnEdge("ac", 4, a, c);
        UnEdge ad = new UnEdge("ad", 3, a, d);
        UnEdge bd = new UnEdge("bd", 2, b, d);
        UnEdge cd = new UnEdge("cd", 5, c, d);
        List<UnEdge> edges = List.of(ab, ac, ad, bd, cd);
//
        UnAdjacencyList graph = new UnAdjacencyList(vertices, edges);
//        MstAlg mstAlg = injector.getInstance(MstAlg.class);
//        MstAlg mstAlg = injector.getInstance(Key.get(MstAlg.class, Names.named(GreedyConstants.INJECTION_NAME_MST_PRIMS_ALG_HEAP_BASED)));
//        MstAlg mstAlg = injector.getInstance(Key.get(MstAlg.class, Names.named(GreedyConstants.INJECTION_NAME_MST_KRUSKAL_ALG_UNION_FIND_BASED)));
        MstAlg mstAlg = new PrimsAlgSimple();
//        MstAlg mstAlg = new KruskalAlgSimple();
        System.out.println(mstAlg.determineMst(graph));

        // test UnionFind impl
//        UnVertex elemA = new UnVertex("a");
//        UnVertex elemB = new UnVertex("b");
//        UnVertex elemC = new UnVertex("c");
//        UnionFind<UnVertex> unionFind = new UnionFindSizeBased<>(List.of(elemA, elemB, elemC));
//        System.out.println(unionFind);
//        unionFind.union(elemA, elemB);
//        System.out.println(unionFind);
//        unionFind.union(elemC, elemA);
//        System.out.println(unionFind);
    }
}
