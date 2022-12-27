package de.sk.greedy.benchmark.executionplans;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import de.sk.greedy.GreedyConstants;
import de.sk.greedy.GreedyInjectionModule;
import de.sk.greedy.mst.MstAlg;

public class CommonExecutionPlanObjects {

    private static final Injector INJECTOR = Guice.createInjector(new GreedyInjectionModule());

    public static final MstAlg primHeapBased = INJECTOR.getInstance(Key.get(MstAlg.class, Names.named(GreedyConstants.IN_MST_PRIMS_ALG_HEAP_BASED)));
    public static final MstAlg primSimple = INJECTOR.getInstance(Key.get(MstAlg.class, Names.named(GreedyConstants.IN_MST_PRIMS_ALG_SIMPLE)));
    public static final MstAlg kruskalSimple = INJECTOR.getInstance(Key.get(MstAlg.class, Names.named(GreedyConstants.IN_MST_KRUSKAL_ALG_SIMPLE)));
    public static final MstAlg kruskalUnionFindBased = INJECTOR.getInstance(Key.get(MstAlg.class, Names.named(GreedyConstants.IN_MST_KRUSKAL_ALG_UNION_FIND_BASED)));
}
