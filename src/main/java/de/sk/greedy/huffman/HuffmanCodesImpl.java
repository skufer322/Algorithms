package de.sk.greedy.huffman;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class HuffmanCodesImpl implements HuffmanCodes {

    // exception message text formats for validation errors
    static final String TOO_FEW_SYMBOLS_EXCEPTION_MSG_TEXT_FORMAT = "At least 2 symbols must be given. Given %d symbol(s).";
    static final String PROBABILITIES_SUM_IS_NOT_1_EXCEPTION_MSG_TEXT_FORMAT = "Probabilities do not add up to 1. Sum of probabilities: %s.";
    static final String SYMBOL_IS_NOT_UNIQUE_EXCEPTION_MSG_TEXT_FORMAT = "Symbol '%s' exists at least twice in input map. Symbols must be unique.";
    // exception message text formats for implementation errors
    static final String COULD_NOT_FIND_LOWEST_PROBABILITY_NODE_EXCEPTION_MSG_TEXT_FORMAT = "Internal error: Could not find the node with the lowest probability.";
    static final String FAILED_TO_CREATE_HUFFMAN_TREE_EXCEPTION_MSG_TEXT_FORMAT = "Internal error: Failed to create Huffman tree for symbols and probabilities: %s.";

    static final Comparator<HuffmanTreeNode> HUFFMAN_TREE_NODE_COMPARATOR = Comparator.comparingDouble(HuffmanTreeNode::getProbability);

    private final Queue<HuffmanTreeNode> oneSymbolTrees;
    private final Queue<HuffmanTreeNode> multipleSymbolsTrees;

    public HuffmanCodesImpl() {
        this.oneSymbolTrees = new LinkedList<>();
        this.multipleSymbolsTrees = new LinkedList<>();
    }

    @Override
    public @NotNull Map<Character, Double> determineSymbolsAndProbabilities(@NotNull String toEncode) {
        Map<Character, Double> symbolsAndProbabilities = new HashMap<>();
        // determine symbols and their frequencies
        for (int i = 0; i < toEncode.length(); i++) {
            char currentSymbol = toEncode.charAt(i);
            symbolsAndProbabilities.put(currentSymbol, symbolsAndProbabilities.getOrDefault(currentSymbol, 0d) + 1d);
        }
        // normalize frequencies to probabilities between 0 and 1
        for (Map.Entry<Character, Double> entry : symbolsAndProbabilities.entrySet()) {
            double frequency = entry.getValue();
            symbolsAndProbabilities.put(entry.getKey(), frequency / toEncode.length());
        }
        return symbolsAndProbabilities;
    }

    @Override
    public @NotNull HuffmanTreeNode createHuffmanTree(@NotNull Map<Character, Double> symbolsAndProbabilities) {
        this.validate(symbolsAndProbabilities);
        this.clearDatastructures();
        this.initOneSymbolTrees(symbolsAndProbabilities);
        // merge the trees until the forest is a single tree
        while (this.oneSymbolTrees.size() + this.multipleSymbolsTrees.size() > 1) {
            HuffmanTreeNode lowestProbabilityNode = this.getNodeWithLowestProbability();
            HuffmanTreeNode secondLowestProbabilityNode = this.getNodeWithLowestProbability();
            HuffmanTreeNode mergedNode = this.createMergedNode(lowestProbabilityNode, secondLowestProbabilityNode);
            this.multipleSymbolsTrees.add(mergedNode);
        }
        return Optional.ofNullable(this.multipleSymbolsTrees.poll())
                .orElseThrow(() -> new IllegalStateException(String.format(FAILED_TO_CREATE_HUFFMAN_TREE_EXCEPTION_MSG_TEXT_FORMAT, symbolsAndProbabilities)));
    }

    private void validate(@NotNull Map<Character, Double> symbolsAndProbabilities) {
        if (symbolsAndProbabilities.size() < 2) {
            throw new IllegalArgumentException(String.format(TOO_FEW_SYMBOLS_EXCEPTION_MSG_TEXT_FORMAT, symbolsAndProbabilities.size()));
        }
        double sumOfProbabilities = symbolsAndProbabilities.values().stream().mapToDouble(Double::doubleValue).sum();
        if (sumOfProbabilities != 1d) {
            throw new IllegalArgumentException(String.format(PROBABILITIES_SUM_IS_NOT_1_EXCEPTION_MSG_TEXT_FORMAT, sumOfProbabilities));
        }
        Set<Character> symbols = new HashSet<>();
        for (Character symbol : symbolsAndProbabilities.keySet()) {
            if (symbols.contains(symbol)) {
                throw new IllegalArgumentException(String.format(SYMBOL_IS_NOT_UNIQUE_EXCEPTION_MSG_TEXT_FORMAT, symbol));
            } else {
                symbols.add(symbol);
            }
        }
    }

    private void clearDatastructures() {
        this.oneSymbolTrees.clear();
        this.multipleSymbolsTrees.clear();
    }

    private void initOneSymbolTrees(@NotNull Map<Character, Double> symbolsAndProbabilities) {
        List<HuffmanTreeNode> symbols = new ArrayList<>();
        for (Map.Entry<Character, Double> entry : symbolsAndProbabilities.entrySet()) {
            HuffmanTreeNode currentTree = new HuffmanTreeNode(entry.getKey(), entry.getValue());
            symbols.add(currentTree);
        }
        // sort by probability in ascending order
        symbols.sort(HUFFMAN_TREE_NODE_COMPARATOR);
        this.oneSymbolTrees.addAll(symbols);
    }

    private @NotNull HuffmanTreeNode getNodeWithLowestProbability() {
        HuffmanTreeNode firstCandidate = this.oneSymbolTrees.peek();
        HuffmanTreeNode secondCandidate = this.multipleSymbolsTrees.peek();
        double firstCandidateProbability = firstCandidate != null ? firstCandidate.getProbability() : Double.MAX_VALUE;
        double secondCandidateProbability = secondCandidate != null ? secondCandidate.getProbability() : Double.MAX_VALUE;

        HuffmanTreeNode lowestProbabilityTreeRoot = firstCandidateProbability <= secondCandidateProbability ? this.oneSymbolTrees.poll() : this.multipleSymbolsTrees.poll();
        return Optional.ofNullable(lowestProbabilityTreeRoot)
                .orElseThrow(() -> new IllegalStateException(COULD_NOT_FIND_LOWEST_PROBABILITY_NODE_EXCEPTION_MSG_TEXT_FORMAT));
    }

    private @NotNull HuffmanTreeNode createMergedNode(@NotNull HuffmanTreeNode n1, @NotNull HuffmanTreeNode n2) {
        String name = n1.getName() + HuffmanTreeUtils.NODE_NAME_DELIMITER + n2.getName();
        double probability = n1.getProbability() + n2.getProbability();
        return new HuffmanTreeNode(name, probability, n1, n2);
    }

    @Override
    public @NotNull String encode(@NotNull String toEncode, @NotNull Map<Character, Double> symbolsAndProbabilities) {
        Map<Character, HuffmanTreeNode> symbolToNodeMap = this.createSymbolToNodeMap(symbolsAndProbabilities);
        Map<Character, String> symbolToBinaryCodeCache = new HashMap<>(); // cache for already encoded symbols, e.g. 'A' -> '1101'
        StringBuilder binaryCode = new StringBuilder();
        for (char symbol : toEncode.toCharArray()) {
            String binaryCodeOfSymbol = symbolToBinaryCodeCache.get(symbol);
            if (binaryCodeOfSymbol == null) {
                // binary code for symbol is not in cache -> create binary code and put in cache
                HuffmanTreeNode leafNode = symbolToNodeMap.get(symbol);
                binaryCodeOfSymbol = HuffmanTreeUtils.getBinaryCode(leafNode);
                symbolToBinaryCodeCache.put(symbol, binaryCodeOfSymbol);
            }
            binaryCode.append(binaryCodeOfSymbol);
        }
        return binaryCode.toString();
    }

    private @NotNull Map<Character, HuffmanTreeNode> createSymbolToNodeMap(@NotNull Map<Character, Double> symbolsAndProbabilities) {
        HuffmanTreeNode huffmanTree = this.createHuffmanTree(symbolsAndProbabilities);
        List<HuffmanTreeNode> leafNodes = HuffmanTreeUtils.getLeafNodes(huffmanTree);
        return this.buildSymbolNodeMapFromLeafNodes(leafNodes);
    }

    private @NotNull Map<Character, HuffmanTreeNode> buildSymbolNodeMapFromLeafNodes(@NotNull List<HuffmanTreeNode> symbolNodes) {
        Map<Character, HuffmanTreeNode> symbolNodeMap = new HashMap<>();
        for (HuffmanTreeNode symbolNode : symbolNodes) {
            if (symbolNode.getName().length() != 1) {
                throw new IllegalStateException("TODO not a valid leaf node symbol");
            }
            Character symbol = symbolNode.getName().charAt(0);
            if (symbolNodeMap.containsKey(symbol)) {
                throw new IllegalStateException("TODO more than 1 node with symbol contained in huffman tree");
            }
            symbolNodeMap.put(symbol, symbolNode);
        }
        return symbolNodeMap;
    }

    @Override
    public @NotNull String decode(@NotNull String binaryString, @NotNull HuffmanTreeNode huffmanTreeRoot) {
        StringBuilder decoded = new StringBuilder();
        HuffmanTreeNode currentNode = huffmanTreeRoot;
        for (char binaryDigit : binaryString.toCharArray()) {
            currentNode = binaryDigit == HuffmanTreeUtils.BINARY_DIGIT_LEFT_CHILD ? currentNode.getLeft() : currentNode.getRight();
            if (Objects.requireNonNull(currentNode).isLeaf()) {
                decoded.append(currentNode.getName());
                currentNode = huffmanTreeRoot; // set current node back to root
            }
        }
        return decoded.toString();
    }
}
