package de.sk.basics.ch5.quicksort;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.TestUtils;
import de.sk.basics.injection.BasicsInjectionModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Some test cases from: <a href="https://www.algorithmsilluminated.org/">https://www.algorithmsilluminated.org</a>
 * TODO: Maybe implement some additional test cases.
 */
class QuickSortTest {

    private QuickSort underTest;

    @BeforeEach
    void setUp() {
        Injector injector = Guice.createInjector(new BasicsInjectionModule());
        underTest = injector.getInstance(QuickSort.class);
    }

    @ParameterizedTest
    @MethodSource("readTestData")
    void shouldSolveTestCases(int[] array) {
        // create a copy of the array to sort
        int[] copyOfArray = Arrays.copyOf(array, array.length);

        // sort the array to sort with the QuickSort implementation
        underTest.sort(array);

        // sort the copy with Java "board means" and compare it to the sorted result of the QuickSort implementation
        Arrays.sort(copyOfArray);
        assertThat(array).containsExactly(copyOfArray);
    }

    private static Stream<Arguments> readTestData() throws IOException {
        int[] arrayTestCase1 = TestUtils.readIntsFromResourceFile("integer_10_random_values.txt");
        int[] arrayTestCase2 = TestUtils.readIntsFromResourceFile("integer_100_random_values.txt");
        int[] arrayChallengeProblem = TestUtils.readIntsFromResourceFile("integer_1_to_10_000_random_order.txt");
        return Stream.of(
                Arguments.of(arrayTestCase1),
                Arguments.of(arrayTestCase2),
                Arguments.of(arrayChallengeProblem)
        );
    }
}
