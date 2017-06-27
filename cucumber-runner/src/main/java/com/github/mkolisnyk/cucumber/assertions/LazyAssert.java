package com.github.mkolisnyk.cucumber.assertions;

public final class LazyAssert {

    private LazyAssert() {
    }

    public static void fail() {
        throw new LazyAssertionError();
    }
    public static void fail(String message) {
        throw new LazyAssertionError(message);
    }
    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            if (message == null) {
                fail();
            } else {
                fail(message);
            }
        }
    }
}
