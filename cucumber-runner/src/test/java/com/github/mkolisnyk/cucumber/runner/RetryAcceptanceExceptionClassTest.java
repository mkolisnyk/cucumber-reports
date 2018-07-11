package com.github.mkolisnyk.cucumber.runner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.notification.RunNotifier;

import cucumber.api.CucumberOptions;
import cucumber.api.java.Before;

public class RetryAcceptanceExceptionClassTest {

    @ExtendedCucumberOptions(
            jsonReport = "target/172/cucumber.json",
            jsonUsageReport = "target/172/cucumber-usage.json",
            usageReport = true,
            detailedReport = true,
            detailedAggregatedReport = true,
            overviewReport = true,
            overviewChartsReport = true,
            pdfPageSize = "A4 Landscape",
            toPDF = true,
            outputFolder = "target/172",
            retryCount = 3)
    @CucumberOptions(
            features = { "src/test/java/com/github/mkolisnyk/cucumber/issue172/RetryCheck.feature" },
            glue = {"com/github/mkolisnyk/cucumber/steps",
                    "com/github/mkolisnyk/cucumber/runner"}, plugin = {
            "html:target/172", "json:target/172/cucumber.json",
            "pretty:target/172/cucumber-pretty.txt",
            "usage:target/172/cucumber-usage.json", "junit:target/172/cucumber-results.xml" })
    public static class SampleTestException1 {
        public static int retries = 0;
        public static int testRuns = 0;
        public SampleTestException1() {
        }
        
        @RetryAcceptance
        public static boolean retryCheck(Throwable e) {
            retries++;
            return e instanceof Exception;
        }
    }
    @Before
    public void beforeTest() {
        SampleTestException1.testRuns++;
    }
    @Test
    public void testRetryException1() throws Exception {
        ExtendedCucumber runner = new ExtendedCucumber(SampleTestException1.class);
        RunNotifier notifier = new RunNotifier();
        runner.run(notifier);
        Assert.assertEquals(6, SampleTestException1.retries);
        Assert.assertEquals(18, SampleTestException1.testRuns);
    }
}
