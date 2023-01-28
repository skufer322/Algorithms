package de.sk.nphard.tsp.piggyback;

import de.sk.util.SortingUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of {@link PermutationAlg} using Heaps algorithm.
 */
public class HeapsPermutationAlg implements PermutationAlg {

    @Override
    public @NotNull List<int[]> createAllPermutations(int @NotNull [] elements) {
        List<int[]> permutations = new ArrayList<>();
        this.createAllPermutationsRec(elements.length, elements, permutations);
        return permutations;
    }

    private void createAllPermutationsRec(int n, int @NotNull [] elements, @NotNull List<int[]> permutations) {
        if (n == 1) {
            permutations.add(Arrays.copyOf(elements, elements.length));
        } else {
            for (int i = 0; i < n - 1; i++) {
                createAllPermutationsRec(n - 1, elements, permutations);
                if (n % 2 == 0) {
                    SortingUtils.swapElements(elements, i, n - 1);
                } else {
                    SortingUtils.swapElements(elements, 0, n - 1);
                }
            }
            createAllPermutationsRec(n - 1, elements, permutations);
        }
    }
}
