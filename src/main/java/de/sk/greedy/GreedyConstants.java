package de.sk.greedy;

import de.sk.graphs.datastructure.undirected.UnEdge;

import java.util.Comparator;

/**
 * Class holding general constants related to greedy algorithms.
 */
public final class GreedyConstants {

    private GreedyConstants() {
        // only constants
    }

    // injection names (in) for mst variants
    public static final String IN_MST_PRIMS_ALG_HEAP_BASED = "Prims-Alg-Heap-Based";
    public static final String IN_MST_PRIMS_ALG_SIMPLE = "Prims-Alg-Simple";
    public static final String IN_MST_KRUSKAL_ALG_SIMPLE = "Kruskal-Alg-Simple";
    public static final String IN_MST_KRUSKAL_ALG_UNION_FIND_BASED = "Kruskal-Alg-Union-Find-Based";

    // injection names (in) for union-find variants
    public static final String IN_UNION_FIND_BY_SIZE = "Union-Find-By-Size";

    // comparators
    public static final Comparator<UnEdge> EDGE_WEIGHT_SORTER = Comparator.comparing(UnEdge::getWeight, Comparator.naturalOrder());
}
