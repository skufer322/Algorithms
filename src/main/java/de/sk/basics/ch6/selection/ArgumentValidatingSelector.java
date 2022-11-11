package de.sk.basics.ch6.selection;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Abstract class defining common default methods/properties for Selector-type subclasses.
 */
public abstract class ArgumentValidatingSelector implements Selector {

    static final String INVALID_ARGUMENTS_SELECT_METHOD_TEXT_STRING = "i-th order statistics (%d) must be between [1;%d = array.length] for array %s.";

    protected void validateSelectMethodArguments(int @NotNull [] array, int ithOrderStatistics) {
        if (ithOrderStatistics <= 0 || ithOrderStatistics > array.length) {
            throw new IllegalArgumentException(String.format(INVALID_ARGUMENTS_SELECT_METHOD_TEXT_STRING, ithOrderStatistics, array.length, Arrays.toString(array)));
        }
    }
}
