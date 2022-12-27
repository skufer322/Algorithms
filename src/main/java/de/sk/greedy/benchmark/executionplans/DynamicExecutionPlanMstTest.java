package de.sk.greedy.benchmark.executionplans;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.util.UndirectedGraphUtils;
import de.sk.greedy.mst.MstAlg;
import org.openjdk.jmh.annotations.*;

import java.util.Random;

@State(Scope.Benchmark)
public class DynamicExecutionPlanMstTest {

    @Param({"100", "1000"})
//    @Param({"100", "1000", "10000"})
    private int numberOfVertices;

    public final MstAlg primHeapBased = CommonExecutionPlanObjects.primHeapBased;
    public final MstAlg primSimple = CommonExecutionPlanObjects.primSimple;
    public final MstAlg kruskalSimple = CommonExecutionPlanObjects.kruskalSimple;
    public final MstAlg kruskalUnionFindBased = CommonExecutionPlanObjects.kruskalUnionFindBased;

    public UnAdjacencyList randomGraph;

    @Setup(Level.Trial)
    public void createRandomGraph() {
        int numberOfEdges = this.numberOfVertices * 30;
        int maxWeight = 1_000;
        Random random = new Random(578347734907L);
        this.randomGraph = UndirectedGraphUtils.createRandomGraph(this.numberOfVertices, numberOfEdges, maxWeight, random);
    }

    @TearDown(Level.Invocation)
    public void resetGraph() {
        // nothing to do here right now
    }
}
