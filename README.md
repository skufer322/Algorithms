# Algorithms

This repository contains Java implementations of almost all the algorithms presented in the book series "Algorithms Illuminated" by Tim Roughgarden (see https://www.algorithmsilluminated.org/)

Below 'src/main/java/de/sk/', there are the following packages:
- basics
  
  Contains the algorithms from the first book of the series. The subpackages are organized in chapters. Contains implementations of the following Divide & Conquer algorithms:
  - Karatsuba algorithm (subpackage ch1/multiply)
  - an algorithm for finding the closest pair of a set of 2D points (ch3)
  - an algorithm counting the inversions in an array (piggybacking on the mergesort algorithm, ch3)
  - quicksort and insertionsort (the latter is not a Divide & Conquer algorithm, ch5)
  - RSelect and DSelect (ch6)
  
- dynamicprogramming
  
  Contains the various DynamicProgramming algorithms presented in the books:
  - the Bellman-Ford algorithm for solving the single-source shortest-path problem for graphs with negative edge weights (graphsrevisited/bellmanford)
  - the Floyd-Warshall algorithm for solving the all-source shortest-path problem for graphs (graphsrevisited/floydwarshall)
  - an algorithm to solve the Knpack problem (knapsack)
  - an algorithm to determine the Maximum Weighted Independent Set for a path graph (mwis)
  - a Sequence Alignment alhorithm to determine an optimal Needleman-Wunsch score for two strings of genomic symbols
  
- graphs

  Various graph search algorithms for directed as well as undirected graphs based-on breadth-first-search (bfs) or depth-first-search (dfs):
  - simple bfs for an undirected graph (bfs)
  - augmented bfs for an undirected graph, determining the level of each vertex (bfs)
  - an algorithm determining the connected components of a graph (based on bfs, cc)
  - iterative dfs for an undirected graph (dfs)
  - recursive dfs for an undirected graph (dfs)
  - topology sort for a directed graph (dfs)
  - Kosaraju's algorithm to find the strongly connected components in a directedgraph (dfs)
  
- greedy

  Collection of greedy algorithms presented thoughout the books:
  - an implementation of the Huffman coding for entropy encoding (huffman)
  - a naive implementation of Prim's algorithm to determine the Minimum Spanning Tree (MST) of an undirected graph (mst)
  - a heap-based implementation of Prim's algorithm to determine the MST of an undirected graph (mst)
  - a naive implementation of Kruskal's algorithm to determine the MST of an undirected graph (mst)
  - an implementation of Kruskal's algorithm based on the union-find data structure to determine the MST of an undirected graph (mst)
  
  There is also a benchmark subpackage in which the various MST implementations are benchmarked against each other via utilizing the Java Microbenchmark Harness (JMH) framework.
  
- nphard

  Collection of various algorithms for NP-hard problems:
  - a greedy smapling algorithm for the Influence Maximization problem (influencemaximization)
  - an algorithm determining the shortest k-path in graph based on color coding (kpaths)
  - Graham's greedy algorithm for the Make Span Minimization problem (makespan)
  - the Longest Processing Time First algorithm for the Make Span Minimization problem, improving Graham's algorithm (makespan)
  - a greedy algorithm for the Maximum Coverage problem (maxcoverage)
  - the Bellman-Held-Karp algorithm for optimal solutions of the Travelling Salesman Problem (TSP) (tsp)
  - two different exhaustive search algorithms for optimally  solving the TSP (one very naive implementation, one somewhat optimizing memory usage) (tsp)
  - a simple nearest-neighbor-based algorithm for (most likely) suboptimal solutions to the TSP (tsp)
  - a simple randomization-based algorithm for (most likely) suboptimal solutions to the TSP
  - an implementation of the 2-Opt Heuristic Algorithm, a local search algorithm iteratively improving base solutions to the TSP (tsp)
  
  The subpackage piggyback contains implementations of two algorithms required by for diverse TSP algorithms:
  - an implementation of Gosper's hack to generate all k subsets of an n set
  - an implementation of Heap's algorithm to generate all permutations of an array of elements
