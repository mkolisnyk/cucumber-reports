package com.github.mkolisnyk.cucumber.assertions;

public class LazyAssertionError extends Error {

    private static final long serialVersionUID = 8668292838814104465L;

    public LazyAssertionError() {
    }

    public LazyAssertionError(String message) {
        super(message);
    }

    public LazyAssertionError(Throwable cause) {
        super(cause);
    }

    public LazyAssertionError(String message, Throwable cause) {
        super(message, cause);
    }

    public LazyAssertionError(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
