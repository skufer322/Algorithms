package de.sk.basics.ch6.selection;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.TestUtils;
import de.sk.basics.injection.BasicsInjectionModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Only test cases from: <a href="https://www.algorithmsilluminated.org/">https://www.algorithmsilluminated.org</a>
 */
class DSelectTest {

    private DSelect underTest;

    @BeforeEach
    void setUp() {
        Injector injector = Guice.createInjector(new BasicsInjectionModule());
        underTest = injector.getInstance(DSelect.class);
    }

    @ParameterizedTest
    @MethodSource("readTestData")
    void shouldSolveTestCases(int[] array, int ithOrderStatisticsToDetermine, int expectedIthOrderStatisticsValue) {
        int determinedIthOrderStatisticsValue = underTest.select(array, ithOrderStatisticsToDetermine);
        assertThat(determinedIthOrderStatisticsValue).isEqualTo(expectedIthOrderStatisticsValue);
    }

    private static Stream<Arguments> readTestData() throws IOException {
        int[] arrayTestCase1 = TestUtils.readIntsFromResourceFile("integer_10_random_values.txt");
        int[] arrayTestCase2 = TestUtils.readIntsFromResourceFile("integer_100_random_values.txt");
        return Stream.of(
                Arguments.of(arrayTestCase1, 5, 5469),
                Arguments.of(arrayTestCase2, 50, 4715)
        );
    }
}
