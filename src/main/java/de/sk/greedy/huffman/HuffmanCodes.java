package de.sk.greedy.huffman;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Interface defining the methods for implementations of Huffman coders (capable of encoding as well as decoding).
 */
public interface HuffmanCodes {

    /**
     * For the given string {@code toEncode}, determines the occurring symbols and their probabilities.
     *
     * @param toEncode string for which the symbols and their occurrences is to be determined
     * @return map of occurring symbols (as key) and their probabilities (as double between ]0;1]).
     */
    @NotNull Map<Character, Double> determineSymbolsAndProbabilities(@NotNull String toEncode);

    /**
     * Creates a Huffman tree for the given symbols and their probabilities, leading to a prefix-free encoding with a
     * minimal average length.
     *
     * @param symbolsAndProbabilities map of symbols and their occurrences for which the Huffman tree is to be created
     * @return an optimal Huffman tree for the given symbols and their probabilities
     */
    @NotNull HuffmanTreeNode createHuffmanTree(@NotNull Map<Character, Double> symbolsAndProbabilities);

    /**
     * Encodes the given string {@code toEncode} with the given map of symbols and probabilities. Creates a Huffman tree
     * from the given symbols and probabilities, derives binary Huffman codes from this tree for each symbol, and finally
     * encodes the single symbols of the string with their binary code.
     *
     * @param toEncode                string to encode
     * @param symbolsAndProbabilities symbols and their probabilities for the encoding of the string to encode
     * @return a minimum average length encoding of the string to encode for the given symbols and probabilities
     */
    @NotNull String encode(@NotNull String toEncode, @NotNull Map<Character, Double> symbolsAndProbabilities);

    /**
     * Decodes a binary Huffman code into its original sequence of symbols. Uses the given Huffman tree for mapping the
     * binary code to the original sequence of symbol (by traversing the Huffman tree from root downwards until
     * a leaf node is found; rinse and repeat until the entire binary code has been processed).
     *
     * @param binaryString    binary Huffman code representing a string of symbols
     * @param huffmanTreeRoot Huffman tree to use for decoding the binary code
     * @return original string of symbols decoded from the binary code
     */
    @NotNull String decode(@NotNull String binaryString, @NotNull HuffmanTreeNode huffmanTreeRoot);
}
