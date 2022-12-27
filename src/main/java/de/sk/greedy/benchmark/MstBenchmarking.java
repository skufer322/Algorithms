package de.sk.greedy.benchmark;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.greedy.benchmark.executionplans.DynamicExecutionPlanMstTest;
import de.sk.greedy.mst.MstAlg;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

// https://entwickler.de/java/wer-nicht-weiss-was-er-misst-misst-mist
// https://www.baeldung.com/java-microbenchmark-harness
// https://blog.avenuecode.com/java-microbenchmarks-with-jmh-part-1?hsLang=en-us
public class MstBenchmarking {

    private static final int FORK = 1;
    static final int WARMUP_ITERATIONS = 5;
    private static final int WARMUP_TIME = 10;
    static final int MEASUREMENT_ITERATIONS = 5;
    private static final int MEASUREMENT_TIME = 10;

//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    @Fork(FORK)
//    @Warmup(iterations = WARMUP_ITERATIONS, time = WARMUP_TIME, timeUnit = TimeUnit.SECONDS)
//    @Measurement(iterations = MEASUREMENT_ITERATIONS, time = MEASUREMENT_TIME, timeUnit = TimeUnit.SECONDS)
//    @BenchmarkMode(Mode.Throughput)
//    public void testPrim_smallGraph(FixedExecutionPlanMstTest fixedExecutionPlanMstTest, Blackhole blackhole) {
//        UnAdjacencyList graph = fixedExecutionPlanMstTest.smallGraph;
//        MstAlg prim = fixedExecutionPlanMstTest.prim;
//        // conduct actual test
//        blackhole.consume(prim.determineMst(graph));
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    @Fork(FORK)
//    @Warmup(iterations = WARMUP_ITERATIONS, time = WARMUP_TIME, timeUnit = TimeUnit.SECONDS)
//    @Measurement(iterations = MEASUREMENT_ITERATIONS, time = MEASUREMENT_TIME, timeUnit = TimeUnit.SECONDS)
//    @BenchmarkMode(Mode.Throughput)
//    public void testKruskal_smallGraph(FixedExecutionPlanMstTest fixedExecutionPlanMstTest, Blackhole blackhole) {
//        UnAdjacencyList graph = fixedExecutionPlanMstTest.smallGraph;
//        MstAlg kruskal = fixedExecutionPlanMstTest.kruskal;
//        // conduct actual test
//        blackhole.consume(kruskal.determineMst(graph));
//    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(FORK)
    @Warmup(iterations = WARMUP_ITERATIONS, time = WARMUP_TIME, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = MEASUREMENT_ITERATIONS, time = MEASUREMENT_TIME, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    public void testPrim_heapBased_randomGraphs(DynamicExecutionPlanMstTest executionPlan, Blackhole blackhole) {
        UnAdjacencyList graph = executionPlan.randomGraph;
        MstAlg prim = executionPlan.primHeapBased;
        // conduct actual test
        blackhole.consume(prim.determineMst(graph));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(FORK)
    @Warmup(iterations = WARMUP_ITERATIONS, time = WARMUP_TIME, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = MEASUREMENT_ITERATIONS, time = MEASUREMENT_TIME, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    public void testPrim_simple_randomGraphs(DynamicExecutionPlanMstTest executionPlan, Blackhole blackhole) {
        UnAdjacencyList graph = executionPlan.randomGraph;
        MstAlg prim = executionPlan.primSimple;
        // conduct actual test
        blackhole.consume(prim.determineMst(graph));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(FORK)
    @Warmup(iterations = WARMUP_ITERATIONS, time = WARMUP_TIME, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = MEASUREMENT_ITERATIONS, time = MEASUREMENT_TIME, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    public void testKruskal_simple_randomGraphs(DynamicExecutionPlanMstTest executionPlan, Blackhole blackhole) {
        UnAdjacencyList graph = executionPlan.randomGraph;
        MstAlg kruskal = executionPlan.kruskalSimple;
        // conduct actual test
        blackhole.consume(kruskal.determineMst(graph));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(FORK)
    @Warmup(iterations = WARMUP_ITERATIONS, time = WARMUP_TIME, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = MEASUREMENT_ITERATIONS, time = MEASUREMENT_TIME, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    public void testKruskal_unionFindBased_randomGraphs(DynamicExecutionPlanMstTest executionPlan, Blackhole blackhole) {
        UnAdjacencyList graph = executionPlan.randomGraph;
        MstAlg kruskal = executionPlan.kruskalUnionFindBased;
        // conduct actual test
        blackhole.consume(kruskal.determineMst(graph));
    }
}
