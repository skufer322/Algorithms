package de.sk.basics.ch6.selection;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Full test coverage.
 */
class ArgumentValidatingSelectorTest {

    /**
     * Instantiable class to test the methods implemented in abstract class {@link ArgumentValidatingSelector}.
     */
    private static class ArgumentValidatingSelectorForTest extends ArgumentValidatingSelector {

        @Override
        public int select(int @NotNull [] array, int ithOrderStatistics) {
            throw new UnsupportedOperationException("Method not implemented by abstract class ArgumentValidatingSelector.");
        }
    }

    private static final int[] ARRAY = new int[]{1, 2, 3, 4, 5};

    private ArgumentValidatingSelector underTest;

    @BeforeEach
    void setUp() {
        underTest = new ArgumentValidatingSelectorForTest();
    }

    @ParameterizedTest(name = "[{index}] ithOrderStatistics={0}")
    @MethodSource("getRangeFrom1toNofArraySize")
    void shouldNotThrowException_ifIthOrderStatistics_isBetweenNAndArrayLength(int ithOrderStatistics) {
        assertThatCode(() -> underTest.validateSelectMethodArguments(ARRAY, ithOrderStatistics))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "[{index}] ithOrderStatistics={0}")
    @ValueSource(ints = {Integer.MIN_VALUE, -1_000, -1, 0})
    void shouldThrowException_ifIthOrderStatistics_isSmallerThan1(int ithOrderStatistics) {
        String expectedExceptionMsg = String.format(ArgumentValidatingSelector.INVALID_ARGUMENTS_SELECT_METHOD_TEXT_STRING,
                ithOrderStatistics, ARRAY.length, Arrays.toString(ARRAY));
        assertThatThrownBy(() -> underTest.validateSelectMethodArguments(ARRAY, ithOrderStatistics))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedExceptionMsg);
    }

    @ParameterizedTest(name = "[{index}] ithOrderStatistics={0}")
    @MethodSource("getRangeFromArrayLength1ToArbitraryGreaterNumbers")
    void shouldThrowException_ifIthOrderStatistics_isGreaterThanArrayLength(int ithOrderStatistics) {
        String expectedExceptionMsg = String.format(ArgumentValidatingSelector.INVALID_ARGUMENTS_SELECT_METHOD_TEXT_STRING,
                ithOrderStatistics, ARRAY.length, Arrays.toString(ARRAY));
        assertThatThrownBy(() -> underTest.validateSelectMethodArguments(ARRAY, ithOrderStatistics))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedExceptionMsg);
    }

    private static Stream<Integer> getRangeFrom1toNofArraySize() {
        return IntStream.range(1, ARRAY.length + 1).boxed();
    }

    private static Stream<Integer> getRangeFromArrayLength1ToArbitraryGreaterNumbers() {
        int anyStepSize = 10_000;
        int startInt = ARRAY.length + 1;
        return Stream.iterate(startInt, i -> i + anyStepSize).limit(3);
    }
}
