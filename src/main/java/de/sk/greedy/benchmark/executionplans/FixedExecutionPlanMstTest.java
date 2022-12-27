package de.sk.greedy.benchmark.executionplans;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.greedy.mst.MstAlg;
import org.openjdk.jmh.annotations.*;

import java.util.List;

@State(Scope.Benchmark)
public class FixedExecutionPlanMstTest {

    public final MstAlg prim = CommonExecutionPlanObjects.primHeapBased;
    public final MstAlg kruskal = CommonExecutionPlanObjects.kruskalUnionFindBased;

    public UnAdjacencyList smallGraph;

    @Setup(Level.Iteration)
    public void createGraphForMst() {
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
        this.smallGraph = new UnAdjacencyList(vertices, edges);
    }

    @TearDown(Level.Invocation)
    public void resetGraph() {
        // nothing to do here right now
    }
}
