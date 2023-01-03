package de.sk.dynamicprogramming.sequencealignment;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

/**
 * Utility holding the penalty information for all possible (mis)matches/alignments of base symbols with each other/with gaps.
 * Also provides methods that allow to use the held information in a simple way.
 */
public class SequenceAlignmentUtils {

    static final String GAPS_CAN_BE_REDUCED_NO_PENALTY_EXCEPTION_MSG = "There cannot be a penalty score for the alignment of two " +
            "gaps, as the alignment of two gaps can always be reduced.";

    // alphabet of base symbols
    public static final Character ADENINE = 'A';
    public static final Character CYTOSINE = 'C';
    public static final Character GUANINE = 'G';
    public static final Character THYMINE = 'T';
    // symbol for gap
    public static final Character GAP = '-';
    // set of valid base symbols a sequence string can be made up
    private static final Set<Character> VALID_SYMBOLS = Set.of(ADENINE, CYTOSINE, GUANINE, THYMINE);

    // penalties
    private static final double PENALTY_MATCH = 0;
    private static final double PENALTY_ADENINE_CYTOSINE = 2;
    private static final double PENALTY_ADENINE_GUANINE = 2;
    private static final double PENALTY_ADENINE_THYMINE = 2;
    private static final double PENALTY_CYTOSINE_GUANINE = 2;
    private static final double PENALTY_CYTOSINE_THYMINE = 2;
    private static final double PENALTY_GUANINE_THYMINE = 2;
    public static final double PENALTY_GAP = 1;
    // marker value indicating to look up the value in the opposite field of the mismatch penalty matrix
    private static final double MARKER = Integer.MIN_VALUE;

    // penalty matrix with symmetric penalties (i.e. the penalty for e.g. a mismatch A-C is the same as for C-A)
    private static final double[][] MISMATCH_PENALTIES = new double[][]{
            new double[]{PENALTY_MATCH, PENALTY_ADENINE_CYTOSINE, PENALTY_ADENINE_GUANINE, PENALTY_ADENINE_THYMINE},
            new double[]{MARKER, PENALTY_MATCH, PENALTY_CYTOSINE_GUANINE, PENALTY_CYTOSINE_THYMINE},
            new double[]{MARKER, MARKER, PENALTY_MATCH, PENALTY_GUANINE_THYMINE},
            new double[]{MARKER, MARKER, MARKER, PENALTY_MATCH}
    };

    // map to connect the base symbols with the mismatch penalty matrix
    private static final Map<Character, Integer> BASE_INDEX_MAP = Map.ofEntries(
            Map.entry(ADENINE, 0),
            Map.entry(CYTOSINE, 1),
            Map.entry(GUANINE, 2),
            Map.entry(THYMINE, 3)
    );

    /**
     * Returns whether the given {@code string} of characters consists solely of valid base symbols (i.e. {A, C, G, T}), or not.
     *
     * @param string string of symbols which is to be checked if it consists solely of valid vase symbols
     * @return true if {@code string} consists solely of valid base symbols, false else
     */
    public static boolean consistsOfValidBaseSymbols(char @NotNull [] string) {
        for (char symbol : string) {
            if (!VALID_SYMBOLS.contains(symbol)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the penalty score for the alignment of the two given symbols {@code first} and {@code second}. Uses the
     * information held in the class {@link SequenceAlignmentUtils} to determine the penalty score.
     *
     * @param first  symbol for which the penalty score of an alignment with {@code second} is to be determined
     * @param second symbol for which the penalty score of an alignment with {@code first} is to be determined
     * @return the penalty score for an alignment of {@code first} and {@code second}
     */
    public static double getPenalty(@NotNull Character first, @NotNull Character second) {
        if (first == GAP && second == GAP) {
            throw new IllegalArgumentException(GAPS_CAN_BE_REDUCED_NO_PENALTY_EXCEPTION_MSG);
        }
        if (first == GAP || second == GAP) {
            return PENALTY_GAP;
        }
        int indexOfFirst = BASE_INDEX_MAP.get(first);
        int indexOfSecond = BASE_INDEX_MAP.get(second);
        double penalty = MISMATCH_PENALTIES[indexOfFirst][indexOfSecond];
        return penalty != MARKER ? penalty : MISMATCH_PENALTIES[indexOfSecond][indexOfFirst];
    }
}
