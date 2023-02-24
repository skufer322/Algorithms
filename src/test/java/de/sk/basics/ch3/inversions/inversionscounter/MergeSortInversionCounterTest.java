package de.sk.basics.ch3.inversions.inversionscounter;

import de.sk.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Only test cases from: <a href="https://www.algorithmsilluminated.org/">https://www.algorithmsilluminated.org</a>
 */
class MergeSortInversionCounterTest {

    private MergeSortInversionCounter underTest;

    @BeforeEach
    void setUp() {
        underTest = new MergeSortInversionCounter();
    }

    @ParameterizedTest(name = "array = {0}, expectedNumberOfInversions = {1}")
    @MethodSource("sortedArraysForTest")
    void shouldSolveSanityCheck_andCountZeroInversions_forSortedArrays(int[] array, long expectedNumberOfInversions) {
        long inversions = underTest.countInversions(array);
        assertThat(inversions).isEqualTo(expectedNumberOfInversions);
    }

    @ParameterizedTest(name = "array = {0}, expectedNumberOfInversions = {1}")
    @MethodSource("reverseSortedArraysForTest")
    void shouldSolveSanityCheck_andCountGaussianSumNumberOfInversions_forReverseSortedArrays(int[] array, long expectedNumberOfInversions) {
        long inversions = underTest.countInversions(array);
        assertThat(inversions).isEqualTo(expectedNumberOfInversions);
    }

    @Test
    void shouldSolveBasicTestCase() {
        int[] array = new int[]{54044, 14108, 79294, 29649, 25260, 60660, 2995, 53777, 49689, 9083};
        long inversions = underTest.countInversions(array);
        assertThat(inversions).isEqualTo(28);
    }

    @ParameterizedTest(name = "[{index}] expectedNumberOfInversions={1}")
    @MethodSource("readChallengeProblemTestData")
    void shouldSolveChallengeProblemTest(int[] array, long expectedNumberOfInversions) {
        long inversions = underTest.countInversions(array);
        assertThat(inversions).isEqualTo(expectedNumberOfInversions);
    }

    private static Stream<Arguments> sortedArraysForTest() {
        return Stream.of(
                Arguments.of(new int[0], 0),
                Arguments.of(new int[]{1}, 0),
                Arguments.of(new int[]{1, 2}, 0),
                Arguments.of(new int[]{1, 2, 3}, 0),
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6}, 0),
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 0)
        );
    }

    private static Stream<Arguments> reverseSortedArraysForTest() {
        return Stream.of(
                Arguments.of(new int[0], 0),
                Arguments.of(new int[]{1}, 0),
                Arguments.of(new int[]{2, 1}, 1),
                Arguments.of(new int[]{3, 2, 1}, 3 * (3 - 1) / 2),
                Arguments.of(new int[]{6, 5, 4, 3, 2, 1}, 6 * (6 - 1) / 2),
                Arguments.of(new int[]{8, 7, 6, 5, 4, 3, 2, 1}, 8 * (8 - 1) / 2)
        );
    }

    private static Stream<Arguments> readChallengeProblemTestData() throws IOException {
        int[] array = TestUtils.readIntsFromResourceFile("integer_1_to_100_00_random_order.txt");
        return Stream.of(
                Arguments.of(array, 2407905288L)
        );
    }
}
