package com.github.mkolisnyk.cucumber.assertions;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public final class MatcherLazyAssert {
    private MatcherLazyAssert() {
    }
//    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
//        assertThat("", actual, matcher);
//    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            description.appendText(reason)
                       .appendText("\nExpected: ")
                       .appendDescriptionOf(matcher)
                       .appendText("\n     but: ");
            matcher.describeMismatch(actual, description);

            throw new LazyAssertionError(description.toString());
        }
    }

    public static void assertThat(String reason, boolean assertion) {
        if (!assertion) {
            throw new LazyAssertionError(reason);
        }
    }
}
