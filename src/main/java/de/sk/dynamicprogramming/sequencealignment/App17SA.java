package de.sk.dynamicprogramming.sequencealignment;

import org.apache.commons.lang3.tuple.Triple;

public class App17SA {

    public static void main(String[] args) {
        String x = "AGGGCT";
        String y = "AGGCA";

        SequenceAligner sequenceAligner = new SequenceAligner();
        Triple<Double, String, String> xxx = sequenceAligner.determineMinimumPenaltyAlignment(x, y);
        System.out.println(xxx.getLeft());
        System.out.println(xxx.getMiddle());
        System.out.println(xxx.getRight());
    }
}
