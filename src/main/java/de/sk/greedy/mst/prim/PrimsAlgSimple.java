package de.sk.greedy.mst.prim;

import de.sk.graphs.datastructure.undirected.UnAdjacencyList;
import de.sk.graphs.datastructure.undirected.UnEdge;
import de.sk.graphs.datastructure.undirected.UnVertex;
import de.sk.greedy.mst.MstAlg;
import de.sk.greedy.mst.MstUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PrimsAlgSimple implements MstAlg {

    static final String NO_ELIGIBLE_EDGE_EXISTS_EXCEPTION_MSG_TF = "Internal error! No eligible edge exists to be selected " +
            "in the next iteration of Prim's algorithm. Number of vertices connected by the MST: %d.";
    static final String NO_EDGE_WAS_SELECTED_EXCEPTION_MSG_TF = "Internal error!. None of the %d eligible edges has been selected " +
            "for the next iteration of Prim's algorithm.";

    private final List<UnEdge> mst = new ArrayList<>();
    private final Set<UnVertex> connectedByMst = new HashSet<>();

    @Override
    public @NotNull List<UnEdge> determineMst(@NotNull UnAdjacencyList undirectedGraph) {
        // verify integrity of graph
        MstUtils.verifyIntegrityOfGraph(undirectedGraph);
        this.clearDatastructures();
        // start actual algorithm
        this.connectedByMst.add(undirectedGraph.vertices().get(0));
        while (this.connectedByMst.size() != undirectedGraph.vertices().size()) {
            List<UnEdge> eligibleEdges = this.getEligibleEdges(undirectedGraph.edges());
            UnEdge selectedEdge = this.getEdgeWithLowestLength(eligibleEdges);
            this.mst.add(selectedEdge);
            this.connectedByMst.addAll(selectedEdge.getVertices());
        }
        return Collections.unmodifiableList(this.mst);
    }

    private @NotNull List<UnEdge> getEligibleEdges(@NotNull List<UnEdge> edges) {
        List<UnEdge> eligibleEdges = new ArrayList<>();
        for (UnEdge edge : edges) {
            if (this.getNumberOfVerticesNotConnectedByMstForGivenEdge(edge) == 1) {
                eligibleEdges.add(edge);
            }
        }
        return eligibleEdges;
    }

    private int getNumberOfVerticesNotConnectedByMstForGivenEdge(@NotNull UnEdge edge) {
        // @formatter:off
        return (int) edge.getVertices().stream()
                        .filter(vertex -> !this.connectedByMst.contains(vertex))
                        .count(); // @formatter:on
    }

    private @NotNull UnEdge getEdgeWithLowestLength(@NotNull List<UnEdge> edges) {
        if (edges.isEmpty()) {
            throw new IllegalArgumentException(String.format(NO_ELIGIBLE_EDGE_EXISTS_EXCEPTION_MSG_TF, this.connectedByMst.size()));
        }
        UnEdge selectedEdge = null;
        int minLength = Integer.MAX_VALUE;
        for (UnEdge edge : edges) {
            if (edge.getWeight() < minLength) {
                selectedEdge = edge;
                minLength = edge.getWeight();
            }
        }
        return Optional.ofNullable(selectedEdge).orElseThrow(() -> new IllegalStateException(String.format(NO_EDGE_WAS_SELECTED_EXCEPTION_MSG_TF, edges.size())));
    }

    private void clearDatastructures() {
        this.mst.clear();
        this.connectedByMst.clear();
    }
}
