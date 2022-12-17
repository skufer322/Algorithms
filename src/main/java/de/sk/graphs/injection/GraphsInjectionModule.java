package de.sk.graphs.injection;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import de.sk.graphs.GraphConstants;
import de.sk.graphs.algorithm.bfs.AugmentedBfs;
import de.sk.graphs.algorithm.bfs.GraphBfs;
import de.sk.graphs.algorithm.bfs.GraphBfsImpl;
import de.sk.graphs.algorithm.dfs.UnGraphDfs;
import de.sk.graphs.algorithm.dfs.UnIterativeGraphDfs;
import de.sk.graphs.algorithm.dfs.UnRecursiveGraphDfs;
import de.sk.graphs.algorithm.dijkstra.edgeselection.EdgeSelector;
import de.sk.graphs.algorithm.dijkstra.edgeselection.SimpleEdgeSelector;

public class GraphsInjectionModule extends AbstractModule {

    @Override
    protected void configure() {
        // BFS bindings
        bind(GraphBfs.class).annotatedWith(Names.named(GraphConstants.INJECTION_NAME_GRAPH_BFS_SIMPLE)).to(GraphBfsImpl.class);
        bind(GraphBfs.class).annotatedWith(Names.named(GraphConstants.INJECTION_NAME_AUGMENTED_BFS)).to(AugmentedBfs.class);

        // DFS bindings
        bind(UnGraphDfs.class).annotatedWith(Names.named(GraphConstants.INJECTION_NAME_ITERATIVE_DFS)).to(UnIterativeGraphDfs.class);
        bind(UnGraphDfs.class).annotatedWith(Names.named(GraphConstants.INJECTION_NAME_RECURSIVE_DFS)).to(UnRecursiveGraphDfs.class);

        // Dijkstra
        bind(EdgeSelector.class).to(SimpleEdgeSelector.class);
        bind(EdgeSelector.class).annotatedWith(Names.named(GraphConstants.INJECTION_NAME_SIMPLE_EDGE_SELECTOR)).to(SimpleEdgeSelector.class);
    }
}
