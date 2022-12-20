package de.sk.greedy.mst.kruskal;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.greedy.mst.MstAlg;
import de.sk.greedy.mst.MstUtils;
import de.sk.greedy.mst.datastructure.unionfind.UnionFind;
import de.sk.greedy.mst.datastructure.unionfind.UnionFindSizeBased;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@link UnionFind}-based implementation of Kruskal's algorithm for determining the Minimum Spanning Tree of a given
 * connected, undirected graph {@link MstAlg}. Time complexity: O(m log n).
 */
public class KruskalAlgUnionFindBased implements MstAlg {

    static final Comparator<UnEdge> EDGE_WEIGHT_SORTER = Comparator.comparing(UnEdge::getWeight, Comparator.naturalOrder());

    private final List<UnEdge> mst = new ArrayList<>();
    @Inject
    private UnionFind<UnVertex> unionFind;

    @Override
    public @NotNull List<UnEdge> determineMst(@NotNull UnAdjacencyList undirectedGraph) {
        MstUtils.verifyIntegrityOfGraph(undirectedGraph);
        this.clearDatastructures();
        PriorityQueue<UnEdge> edgesSortedByWeight = undirectedGraph.edges().stream().sorted(EDGE_WEIGHT_SORTER).collect(Collectors.toCollection(PriorityQueue::new));
        this.unionFind = new UnionFindSizeBased<>();
        this.unionFind.initialize(undirectedGraph.vertices());
        while (this.mst.size() != undirectedGraph.vertices().size() - 1) {
            UnEdge nextEdge = edgesSortedByWeight.poll();
            Objects.requireNonNull(nextEdge);
            List<UnVertex> rootsOfParentTrees = nextEdge.getVertices().stream().map(vertex -> this.unionFind.find(vertex)).toList();
            UnVertex rootOf1stGroup = rootsOfParentTrees.get(0);
            UnVertex rootOf2ndGroup = rootsOfParentTrees.get(1);
            if (rootOf1stGroup != rootOf2ndGroup) {
                this.mst.add(nextEdge);
                this.unionFind.union(rootOf1stGroup, rootOf2ndGroup);
            }
        }
        return this.mst;
    }

    private void clearDatastructures() {
        this.mst.clear();
        this.unionFind.clear();
    }
}
