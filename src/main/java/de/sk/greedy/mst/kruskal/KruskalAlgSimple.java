package de.sk.greedy.mst.kruskal;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.greedy.GreedyConstants;
import de.sk.greedy.mst.MstAlg;
import de.sk.greedy.mst.MstUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class KruskalAlgSimple implements MstAlg {

    static final String NO_PARTITION_EXISTS_EXCEPTION_MSG = "Internal Error! No partition exists even though at least should exist.";

    private final List<UnEdge> mst = new ArrayList<>();
    private final Set<Set<UnVertex>> partitions = new HashSet<>();

    @Override
    public @NotNull List<UnEdge> determineMst(@NotNull UnAdjacencyList undirectedGraph) {
        // verify integrity of graph
        MstUtils.verifyIntegrityOfGraph(undirectedGraph);
        this.clearDatastructures();
        // start actual algorithm -> sort edges by weight (corresponds to length) @formatter:off
        PriorityQueue<UnEdge> edgesSortedByWeight = undirectedGraph.edges().stream().sorted(GreedyConstants.EDGE_WEIGHT_SORTER)
                                                    .collect(Collectors.toCollection(PriorityQueue::new)); // @formatter:on
        while (this.getSizeOfLargestPartition() != undirectedGraph.vertices().size()) {
            // unprocessed edge with the lowest weight si the next candidate to be selected for the MST
            UnEdge nextCandidate = edgesSortedByWeight.poll();
            Objects.requireNonNull(nextCandidate);
            // get vertices v and w of nextCandidate
            List<UnVertex> verticesOfEdge = nextCandidate.getVertices().stream().toList();
            UnVertex v = verticesOfEdge.get(0);
            UnVertex w = verticesOfEdge.get(1);
            // determine partitions of v and w
            Pair<Set<UnVertex>, Set<UnVertex>> partitionsOfVAndW = this.getPartitionsContainingVerticesOfEdge(v, w);
            // handle different cases
            if (partitionsOfVAndW == null) {
                // v and w not contained in any partition -> select nextCandidate for MST
                this.mst.add(nextCandidate);
                // create new partition containing both v and w
                Set<UnVertex> newPartition = new HashSet<>(Set.of(v, w));
                this.partitions.add(newPartition);
            } else {
                Set<UnVertex> partitionOfV = partitionsOfVAndW.getLeft();
                Set<UnVertex> partitionOfW = partitionsOfVAndW.getRight();
                if (partitionOfV == null) {
                    // v is not in any partition, but w is -> select nextCandidate for MST
                    this.mst.add(nextCandidate);
                    // add v to the partition of w
                    partitionOfW.add(v);
                } else if (partitionOfW == null) {
                    // w is not in any partition, but v is -> select nextCandidate for MST
                    this.mst.add(nextCandidate);
                    // add w to the partition of v
                    partitionOfV.add(w);
                } else if (partitionOfV != partitionOfW) {
                    // v and w are in different partitions -> select nextCandidate for MST
                    this.mst.add(nextCandidate);
                    // remove partitions of v and w from the partitions, merge partitions of v and w, and add merged partition to the partitions
                    this.partitions.remove(partitionOfV);
                    this.partitions.remove(partitionOfW);
                    partitionOfV.addAll(partitionOfW);
                    this.partitions.add(partitionOfV);
                } else {
                    // v and w already in same partition -> discard nextCandidate, do nothing
                }
            }
        }
        return Collections.unmodifiableList(this.mst);
    }

    private int getSizeOfLargestPartition() {
        // @formatter:off
        return this.partitions.isEmpty() ? 0 : this.partitions.stream().map(Set::size)
                                                .max(Integer::compare).orElseThrow(() -> new IllegalStateException(NO_PARTITION_EXISTS_EXCEPTION_MSG)); // @formatter:on
    }

    private @Nullable Pair<Set<UnVertex>, Set<UnVertex>> getPartitionsContainingVerticesOfEdge(@NotNull UnVertex v, @NotNull UnVertex w) {
        Set<UnVertex> partitionOfV = null;
        Set<UnVertex> partitionOfW = null;
        for (Set<UnVertex> partition : this.partitions) {
            if (partitionOfV == null && partition.contains(v)) {
                partitionOfV = partition;
            }
            if (partitionOfW == null && partition.contains(w)) {
                partitionOfW = partition;
            }
            if (partitionOfV != null && partitionOfW != null) {
                break;
            }
        }
        return partitionOfV == null && partitionOfW == null ? null : new ImmutablePair<>(partitionOfV, partitionOfW);
    }

    private void clearDatastructures() {
        this.mst.clear();
        this.partitions.clear();
    }
}
