package com.github.mkolisnyk.cucumber.runner;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.notification.RunNotifier;

import cucumber.api.CucumberOptions;

public class RetryAcceptanceTest {

    @ExtendedCucumberOptions(
            jsonReport = "target/81/cucumber.json",
            jsonUsageReport = "target/81/cucumber-usage.json",
            usageReport = true,
            detailedReport = true,
            detailedAggregatedReport = true,
            overviewReport = true,
            overviewChartsReport = true,
            pdfPageSize = "A4 Landscape",
            toPDF = true,
            outputFolder = "target/81",
            retryCount = 3)
    @CucumberOptions(
            features = { "src/test/java/com/github/mkolisnyk/cucumber/features/Test.feature" },
            tags = { "@failed" },
            glue = "com/github/mkolisnyk/cucumber/steps", plugin = {
            "html:target/81", "json:target/81/cucumber.json",
            "pretty:target/81/cucumber-pretty.txt",
            "usage:target/81/cucumber-usage.json", "junit:target/81/cucumber-results.xml" })
    public static class SampleTestRetryIsOff {
        public static int retries = 0;
        public SampleTestRetryIsOff() {
        }
        @RetryAcceptance
        public static boolean retryCheck() {
            retries++;
            return false;
        }
    }

    @ExtendedCucumberOptions(
            jsonReport = "target/81/cucumber.json",
            jsonUsageReport = "target/81/cucumber-usage.json",
            usageReport = true,
            detailedReport = true,
            detailedAggregatedReport = true,
            overviewReport = true,
            overviewChartsReport = true,
            pdfPageSize = "A4 Landscape",
            toPDF = true,
            outputFolder = "target/81",
            retryCount = 3)
    @CucumberOptions(
            features = { "src/test/java/com/github/mkolisnyk/cucumber/features/Test.feature" },
            tags = { "@failed" },
            glue = "com/github/mkolisnyk/cucumber/steps", plugin = {
            "html:target/81", "json:target/81/cucumber.json",
            "pretty:target/81/cucumber-pretty.txt",
            "usage:target/81/cucumber-usage.json", "junit:target/81/cucumber-results.xml" })
    public static class SampleTestRetryIsOn {
        public static int retries = 0;
        public SampleTestRetryIsOn() {
        }
        @RetryAcceptance
        public static boolean retryCheck() {
            retries++;
            return true;
        }
    }

    @Test
    public void testRetryGetsFalse() throws Exception {
        ExtendedCucumber runner = new ExtendedCucumber(SampleTestRetryIsOff.class);
        RunNotifier notifier = new RunNotifier();
        runner.run(notifier);
        Assert.assertEquals(1, SampleTestRetryIsOff.retries);
    }
    @Test
    public void testRetryGetsTrue() throws Exception {
        ExtendedCucumber runner = new ExtendedCucumber(SampleTestRetryIsOn.class);
        RunNotifier notifier = new RunNotifier();
        runner.run(notifier);
        Assert.assertEquals(1, SampleTestRetryIsOn.retries);
    }
}
