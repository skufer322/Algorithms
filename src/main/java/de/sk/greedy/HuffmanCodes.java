package de.sk.greedy;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface HuffmanCodes {

    @NotNull Map<Character, Double> determineSymbolsAndProbabilities(@NotNull String toEncode);

    @NotNull HuffmanTreeNode createHuffmanTree(@NotNull Map<Character, Double> symbolsAndProbabilities);

    @NotNull String encode(@NotNull String toEncode, @NotNull Map<Character, Double> symbolsAndProbabilities);

    @NotNull String decode(@NotNull String binaryString, @NotNull HuffmanTreeNode huffmanTreeRoot);
}
