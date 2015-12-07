package com.github.mkolisnyk.cucumber.runner.assertion;

public class LazyAssertionError extends AssertionError {
    private static final long serialVersionUID = 1L;

    public LazyAssertionError() {
    }

    public LazyAssertionError(Object detailMessage) {
        super(detailMessage);
    }

    public LazyAssertionError(boolean detailMessage) {
        super(detailMessage);
    }

    public LazyAssertionError(char detailMessage) {
        super(detailMessage);
    }

    public LazyAssertionError(int detailMessage) {
        super(detailMessage);
    }

    public LazyAssertionError(long detailMessage) {
        super(detailMessage);
    }

    public LazyAssertionError(float detailMessage) {
        super(detailMessage);
    }

    public LazyAssertionError(double detailMessage) {
        super(detailMessage);
        // TODO Auto-generated constructor stub
    }

    public LazyAssertionError(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
