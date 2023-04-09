# Algorithms

This repository contains Java implementations of almost all the algorithms presented in the book series "Algorithms Illuminated" by Tim Roughgarden (see https://www.algorithmsilluminated.org/)

Below 'src/main/java/de/sk/', there are the following packages:
- basics
  Contains the algorithms from the first book of the series. The subpackages are organized in chapters. Contains implementations of the following Divide & Conquer algorithms:
  - Karatsuba algorithm (ch1/multiply)
  - an algorithm for finding the closest pair of a set of 2D points (ch3)
  - an algorithm counting the inversions in an array (piggybacking on the mergesort algorithm, ch3)
  - quicksort and insertionsort (the latter is not a Divide & Conquer algorithm, ch5)
  - RSelect and DSelect (ch6)
- dynamicprogramming
- graphs
- greedy
- nphard
