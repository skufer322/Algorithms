package de.sk.dynamicprogramming.mwis;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class App16WIS {

    public static void main(String[] args) {
        UnAdjacencyList graph = createPathGraphForTest();
        MWISFinder finder = new MWISFinder();
        Collection<UnVertex> mWIS = finder.determineMWIS(graph);
        System.out.println(mWIS);
    }

    private static @NotNull UnAdjacencyList createPathGraphForTest() {
        UnVertex a = new UnVertex("a=3", 3);
        UnVertex b = new UnVertex("b=2", 2);
        UnVertex c = new UnVertex("c=1", 1);
        UnVertex d = new UnVertex("d=6", 6);
        UnVertex e = new UnVertex("e=4", 4);
        UnVertex f = new UnVertex("f=5", 5);
        List<UnVertex> vertices = List.of(a, b, c, d, e, f);
        UnEdge ab = new UnEdge("ab", a, b);
        UnEdge bc = new UnEdge("bc", b, c);
        UnEdge cd = new UnEdge("cd", c, d);
        UnEdge de = new UnEdge("de", d, e);
        UnEdge ef = new UnEdge("ef", e, f);
        List<UnEdge> edges = List.of(ab, bc, cd, de, ef);
        return new UnAdjacencyList(vertices, edges);
    }
}
