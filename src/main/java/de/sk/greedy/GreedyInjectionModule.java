package de.sk.greedy;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.greedy.mst.MstAlg;
import de.sk.greedy.mst.datastructure.unionfind.UnionFind;
import de.sk.greedy.mst.datastructure.unionfind.UnionFindSizeBased;
import de.sk.greedy.mst.kruskal.KruskalAlgSimple;
import de.sk.greedy.mst.kruskal.KruskalAlgUnionFindBased;
import de.sk.greedy.mst.prim.PrimsAlgHeapBased;
import de.sk.greedy.mst.prim.PrimsAlgSimple;

public class GreedyInjectionModule extends AbstractModule {

    @Override
    protected void configure() {
        // bind mst implementations
        bind(MstAlg.class).to(PrimsAlgHeapBased.class);
        bind(MstAlg.class).annotatedWith(Names.named(GreedyConstants.IN_MST_PRIMS_ALG_HEAP_BASED)).to(PrimsAlgHeapBased.class);
        bind(MstAlg.class).annotatedWith(Names.named(GreedyConstants.IN_MST_PRIMS_ALG_SIMPLE)).to(PrimsAlgSimple.class);
        bind(MstAlg.class).annotatedWith(Names.named(GreedyConstants.IN_MST_KRUSKAL_ALG_SIMPLE)).to(KruskalAlgSimple.class);
        bind(MstAlg.class).annotatedWith(Names.named(GreedyConstants.IN_MST_KRUSKAL_ALG_UNION_FIND_BASED)).to(KruskalAlgUnionFindBased.class);

        // bind union-find implementations
        bind(UnionFind.class).to(UnionFindSizeBased.class);
        bind(UnionFind.class).annotatedWith(Names.named(GreedyConstants.IN_UNION_FIND_BY_SIZE)).to(UnionFindSizeBased.class);
        bind(new TypeLiteral<UnionFind<UnVertex>>(){}).to(new TypeLiteral<UnionFindSizeBased<UnVertex>>(){});
    }
}
