package de.sk.basics.ch1.multiply.multiplier;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.basics.injection.BasicsInjectionModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Only test cases from: <a href="https://www.algorithmsilluminated.org/">https://www.algorithmsilluminated.org</a>
 */
class KaratsubaMultiplierTest {

    private KaratsubaMultiplier underTest;

    @BeforeEach
    void setUp() {
        Injector injector = Guice.createInjector(new BasicsInjectionModule());
        underTest = injector.getInstance(KaratsubaMultiplier.class);
    }

    @Test
    void shouldSolveBasicTestCase() {
        BigInteger x = BigInteger.valueOf(99_999L);
        BigInteger y = BigInteger.valueOf(9_999L);

        BigInteger karatsubaResult = underTest.multiply(x, y);

        BigInteger expectedResult = x.multiply(y);
        assertThat(karatsubaResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldSolveChallengeProblemTest() {
        BigInteger x = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592");
        BigInteger y = new BigInteger("2718281828459045235360287471352662497757247093699959574966967627");

        BigInteger karatsubaResult = underTest.multiply(x, y);

        BigInteger expectedResult = x.multiply(y);
        assertThat(karatsubaResult).isEqualTo(expectedResult);
    }
}
