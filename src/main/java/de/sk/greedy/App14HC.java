package de.sk.greedy;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class App14HC {

    public static void main(String[] args) {
        HuffmanCodesImpl huffmanCodes = new HuffmanCodesImpl();
        // create symbols and probabilities
//        Map<Character, Double> symbolsAndProbabilities = createSymbolsAndProbabilities();
        String toEncode = "AAAAAABBCD";
        Map<Character, Double> symbolsAndProbabilities = huffmanCodes.determineSymbolsAndProbabilities(toEncode);
        // create Huffman encoding
        HuffmanTreeNode huffmanTree = huffmanCodes.createHuffmanTree(symbolsAndProbabilities);
        System.out.println(huffmanTree);
        String binaryCode = huffmanCodes.encode(toEncode, symbolsAndProbabilities);
        System.out.println("encoded: " + binaryCode);
        System.out.println("decoded: " + huffmanCodes.decode(binaryCode, huffmanTree));
    }

    private static @NotNull Map<Character, Double> createSymbolsAndProbabilities() {
        Character symbolA = 'A';
        double probabilitySymbolA = 0.6d;
        Character symbolB = 'B';
        double probabilitySymbolB = 0.25d;
        Character symbolC = 'C';
        double probabilitySymbolC = 0.1d;
        Character symbolD = 'D';
        double probabilitySymbolD = 0.05d;
        return Map.ofEntries(
                Map.entry(symbolA, probabilitySymbolA),
                Map.entry(symbolB, probabilitySymbolB),
                Map.entry(symbolC, probabilitySymbolC),
                Map.entry(symbolD, probabilitySymbolD)
        );
    }
}
