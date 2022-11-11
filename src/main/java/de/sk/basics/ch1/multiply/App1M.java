package de.sk.basics.ch1.multiply;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.sk.basics.ch1.multiply.multiplier.Multiplier;
import de.sk.basics.injection.BasicsInjectionModule;

import java.math.BigInteger;

public class App1M {

    public static void main(String[] args) {
        // setup guice dependency injection
        Injector injector = Guice.createInjector(new BasicsInjectionModule());

        // create container-controlled instances and do some work
        Multiplier karatsuba = injector.getInstance(Multiplier.class);
        BigInteger x = new BigInteger("5678");
        BigInteger y = new BigInteger("1234");
        BigInteger product = karatsuba.multiply(x, y);
        System.out.println(product);
    }
}
