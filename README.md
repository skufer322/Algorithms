# Algorithms

This repository contains Java implementations of almost all the algorithms presented in the book series "Algorithms Illuminated" by Tim Roughgarden (https://www.algorithmsilluminated.org/). See "*Packages and implemented algorithms*" below for a list of packages and the concrete algorithm implementations they contain.

Please consider the following:
- After studying the corresponding chapters, I tried to implement the algorithms from memory the next day or so (*not* with open book next to me, simply copying the pseudocode). Therefore, the implementations do not necessarily correspond to the pseudocode in the books.
- There are not many unit tests due to their high time effort and because I implemented all this in my spare time. Basically, the only considerably complete unit test is the one for the **Longest Processing Time First** algorithm (see package `src/test/java/de/sk/nphard/makespan`).
- However, there are Javadoc comments for at least the very most classes, public methods, and public constants.
- Usually, for each subpackage, there are classes named `App18GR` or the like. The subpackages are organized by chapters in the book, so e.g. `App18GR` belongs to chapter 18, "Graphs Revisited". These classes contain `main` methods which instantiate the algorithm implementations, create and pass them some input arguments, run the algorithms , and finally print the determined solutions to the console. This way, I tested the implementations reasonably thoroughly, but of course it is not comparable to covering them with unit tests.
- Google Guice is used for dependeny injection. There are multiple classes named `GraphsInjectionModule`, `GreedyInjectionModule`, or the like which handle the wiring.
- If I had to unit test the various algorithms, I would probably split the algorithm implementations into more classes.


## Packages and implemented algorithms
Below package `src/main/java/de/sk/`, there are the following packages:
- `basics`
  
  Contains the algorithms from the first book of the series. The subpackages are organized in chapters. Contains implementations of the following *Divide & Conquer* algorithms:
  - **Karatsuba algorithm** (subpackage `ch1/multiply`)
  - an algorithm for finding the **Closest Pair** of a set of 2D points (`ch3`)
  - an algorithm counting the **Inversions** in an array (piggybacking on the **Mergesort** algorithm, `ch3`)
  - **Quicksort** and **Insertionsort** (the latter is not a *Divide & Conquer* algorithm, `ch5`)
  - **RSelect** and **DSelect** for solving the **Selection** problem (ch6)
  
- `dynamicprogramming`
  
  Contains various *Dynamic Programming* algorithms presented in the books:
  - the **Bellman-Ford algorithm** for solving the **Single-Source Shortest-Path** problem for graphs with negative edge weights (`graphsrevisited/bellmanford`)
  - the **Floyd-Warshall algorithm** for solving the **All-Source Shortest-Path** problem for graphs (`graphsrevisited/floydwarshall`)
  - an algorithm to solve the **Knapsack** problem (`knapsack`)
  - an algorithm to determine the **Maximum Weighted Independent Set for a Path Graph** (`mwis`)
  - a **Sequence Alignment** algorithm to determine an optimal *Needleman-Wunsch score* for two strings of genomic symbols
  
- `graphs`

  Various *Graph Search* algorithms for directed as well as undirected graphs based-on *Breadth-First Search* (bfs) or *Depth-First Search* (dfs):
  - **Simple BFS** for an undirected graph (`bfs`)
  - **Augmented BFS** for an undirected graph, determining the level of each vertex (`bfs`)
  - an algorithm determining the **Connected Components** of an undirected graph (based on bfs, `cc`)
  - **Iterative DFS** for an undirected graph (`dfs`)
  - **Recursive DFS** for an undirected graph (`dfs`)
  - **Topology Sort** for a directed graph (`dfs`)
  - **Kosaraju's algorithm** to find the strongly connected components in a directed graph (`dfs`)
  
- `greedy`

  Collection of *greedy algorithms* presented thoughout the books:
  - an implementation of the **Huffman Coding** for entropy encoding (`huffman`)
  - a **naïve** implementation of **Prim's algorithm** to determine the **Minimum Spanning Tree** (MST) of an undirected graph (`mst`)
  - a **Heap-based** implementation of **Prim's algorithm** to determine the **MST** of an undirected graph (`mst`)
  - a **naïve** implementation of **Kruskal's algorithm** to determine the **MST** of an undirected graph (`mst`)
  - an implementation of **Kruskal's algorithm** based on the **Union-Find** data structure to determine the **MST** of an undirected graph (`mst`)
  
  There is also a `benchmark` subpackage in which the various MST implementations are benchmarked against each other via utilizing the *Java Microbenchmark Harness* (JMH) framework.
  
- `nphard`

  Collection of various algorithms for *NP-hard* problems:
  - a greedy sampling algorithm for the **Influence Maximization** problem (`influencemaximization`)
  - an algorithm determining the **Shortest k-Path** in graph based on **Color cCding** (`kpaths`)
  - **Graham's algorithm** (greedy) for the **Make Span Minimization** problem (`makespan`)
  - the **Longest Processing Time First algorithm** for the **Make Span Minimization** problem, improving *Graham's algorithm* (`makespan`)
  - a greedy algorithm for the **Maximum Coverage** problem (`maxcoverage`)
  - the **Bellman-Held-Karp algorithm** for optimal solutions of the **Travelling Salesman Problem** (TSP) (`tsp`)
  - two different **Exhaustive Search algorithms** for optimally solving the **TSP** (one very naïve implementation, one *somewhat* optimizing memory usage) (`tsp`)
  - a simple **Nearest-Neighbor-based algorithm** for (most likely) suboptimal solutions to the **TSP** (`tsp`)
  - a simple **Randomization-based algorithm** for (most likely) suboptimal solutions to the **TSP** (`tsp`)
  - an implementation of the **2-Opt Heuristic algorithm**, a **Local Search Algorithm** iteratively improving base solutions to the **TSP** (`tsp`)
  
  The subpackage `piggyback` contains implementations of two algorithms required by for diverse TSP algorithms:
  - an implementation of **Gosper's hack** to generate all *k subsets of an n set*
  - an implementation of **Heap's algorithm** to generate all *permutations of an array of elements*
