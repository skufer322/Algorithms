package de.sk.dynamicprogramming.sequencealignment;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Implementation of a dynamic programming algorithm to calculate an optimal sequence alignment for two strings of
 * genomic symbols (alphabet: {A, C, G, T}).
 */
public class SequenceAligner {

    static final String STRINGS_ARE_NOT_VALID_GENOMIC_SEQUENCES_EXCEPTION_MSG_TF = "The given strings %s and %s must be valid " +
            "genomic sequences, i.e. are only allowed to contain the following symbols: %s, %s, %s, %s.";

    /**
     * Determines an optimal sequence alignment for the given strings {@code x} and {@code y}. An optimal sequence alignment
     * is one with a minimal Needleman-Wunsch score.}. The alphabet for the given strings must be {A, C, G, T}.
     *
     * @param x string for which an optimal sequence alignment with {@code y} is to be determined
     * @param y string for which an optimal sequence alignment with {@code x} is to be determined
     * @return triple of the minimum Needleman-Wunsch score (left), and an optimal alignment for {@code x} (middle) and {@code y} (right)
     */
    public @NotNull Triple<Double, String, String> determineMinimumPenaltyAlignment(@NotNull String x, @NotNull String y) {
        char[] symbolsOfX = x.toCharArray();
        char[] symbolsOfY = y.toCharArray();
        Pair<Double, double[][]> minPenaltyAndPenaltyMatrix = this.calculatePenalties(symbolsOfX, symbolsOfY);
        Pair<String, String> alignments = this.reconstructAlignedStrings(minPenaltyAndPenaltyMatrix.getRight(), symbolsOfX, symbolsOfY);
        return new ImmutableTriple<>(minPenaltyAndPenaltyMatrix.getLeft(), alignments.getLeft(), alignments.getRight());
    }

    private @NotNull Pair<Double, double[][]> calculatePenalties(char @NotNull [] x, char @NotNull [] y) {
        if (!(SequenceAlignmentUtils.consistsOfValidBaseSymbols(x) && SequenceAlignmentUtils.consistsOfValidBaseSymbols(y))) {
            throw new IllegalArgumentException(String.format(STRINGS_ARE_NOT_VALID_GENOMIC_SEQUENCES_EXCEPTION_MSG_TF, Arrays.toString(x),
                    Arrays.toString(y), SequenceAlignmentUtils.ADENINE, SequenceAlignmentUtils.CYTOSINE, SequenceAlignmentUtils.GUANINE,
                    SequenceAlignmentUtils.THYMINE));
        }
        double[][] penalties = new double[x.length + 1][y.length + 1];
        // base case #1 (j = 0)
        for (int i = 0; i <= x.length; i++) {
            penalties[i][0] = i * SequenceAlignmentUtils.PENALTY_GAP;
        }
        // base case #2 (i = 0)
        for (int j = 0; j <= y.length; j++) {
            penalties[0][j] = j * SequenceAlignmentUtils.PENALTY_GAP;
        }
        // systematically solve all subproblems
        for (int i = 1; i <= x.length; i++) {
            for (int j = 1; j <= y.length; j++) {
                double penaltyXWithY = penalties[i - 1][j - 1] + SequenceAlignmentUtils.getPenalty(x[i - 1], y[j - 1]); // case 1
                double penaltyYWithGap = penalties[i - 1][j] + SequenceAlignmentUtils.PENALTY_GAP; // case 2
                double penaltyXWithGap = penalties[i][j - 1] + SequenceAlignmentUtils.PENALTY_GAP; // case 3
                penalties[i][j] = Math.min(penaltyXWithGap, Math.min(penaltyYWithGap, penaltyXWithY));
            }
        }
        return new ImmutablePair<>(penalties[x.length][y.length], penalties);
    }

    private @NotNull Pair<String, String> reconstructAlignedStrings(double[] @NotNull [] penalties, char @NotNull [] x, char @NotNull [] y) {
        StringBuilder reconstructX = new StringBuilder();
        StringBuilder reconstructY = new StringBuilder();
        int i = penalties.length - 1;
        int j = penalties[0].length - 1;
        while (i > 0 && j > 0) {
            double case1 = penalties[i - 1][j - 1] + SequenceAlignmentUtils.getPenalty(x[i - 1], y[j - 1]);
            double case2 = penalties[i - 1][j] + SequenceAlignmentUtils.PENALTY_GAP;
            double case3 = penalties[i][j - 1] + SequenceAlignmentUtils.PENALTY_GAP;
            if (case1 <= case2 && case1 <= case3) { // check if case1 is the smallest
                reconstructX.append(x[i - 1]);
                reconstructY.append(y[j - 1]);
                i--;
                j--;
            } else if (case2 <= case3) { // case1 is not the smallest -> check if case2 is the smallest
                reconstructX.append(x[i - 1]);
                reconstructY.append(SequenceAlignmentUtils.GAP);
                i--;
            } else { // neither case1 nor case2 is the smallest -> only case3 left
                reconstructX.append(SequenceAlignmentUtils.GAP);
                reconstructY.append(y[j - 1]);
                j--;
            }
        }
        // handle bases cases
        if (i == 0) {
            reconstructX.append(SequenceAlignmentUtils.GAP.toString().repeat(j)); // fill x with gaps
            reconstructY.append(new StringBuilder(new String(y, 0, j)).reverse()); // remaining string of y
        }
        if (j == 0) {
            reconstructY.append(SequenceAlignmentUtils.GAP.toString().repeat(i)); // fill y with gaps
            reconstructX.append(new StringBuilder(new String(x, 0, i)).reverse()); // remaining string of x
        }
        return new ImmutablePair<>(reconstructX.reverse().toString(), reconstructY.reverse().toString());
    }
}
