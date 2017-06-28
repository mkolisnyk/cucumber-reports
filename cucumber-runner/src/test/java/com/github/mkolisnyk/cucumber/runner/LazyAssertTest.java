package com.github.mkolisnyk.cucumber.runner;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;
import org.testng.Assert;

import com.github.mkolisnyk.cucumber.assertions.LazyAssert;
import com.github.mkolisnyk.cucumber.assertions.LazyAssertionError;

import cucumber.api.CucumberOptions;
import cucumber.runtime.ExtendedRuntime;

//@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
        jsonReport = "target/cucumber.json",
        retryCount = 0,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = false,
        toPDF = false,
        outputFolder = "target/lazy")
@CucumberOptions(
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features/LazyAssert.feature" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        plugin = {
        "json:target/cucumber.json", "html: target/cucumber-html-report",
        "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json",
        "junit:target/cucumber-junit-results.xml" }, tags = {})
public class LazyAssertTest {
    @Test
    public void testRunLazyAsserts() throws Exception {
        ExtendedCucumber runner = new ExtendedCucumber(this.getClass());
        RunNotifier notifier = new RunNotifier();
        runner.run(notifier);
    }
    @Test
    public void testFailedAssertTrueWithMessage() {
        try {
            LazyAssert.assertTrue(false, "Sample message");
            Assert.fail("Previous instruction was supposed to fail!");
        } catch (LazyAssertionError e) {
            Assert.assertEquals(e.getMessage(), "Sample message");
        }
    }
    @Test
    public void testFailedAssertTrueWithoutMessage() {
        try {
            LazyAssert.assertTrue(false);
            Assert.fail("Previous instruction was supposed to fail!");
        } catch (LazyAssertionError e) {
            Assert.assertNull(e.getMessage());
        }
    }
    @Test
    public void testPasserAssertTrue() {
        LazyAssert.assertTrue(true);
    }
    @Test
    public void testExtendedRuntimeIsPending() {
        Assert.assertFalse(ExtendedRuntime.isPending(null), "Pending check for null parameter should return false");
    }
}
