package com.github.mkolisnyk.cucumber.runner;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.notification.RunNotifier;

import cucumber.api.CucumberOptions;

public class ExtendedParallelScenarioCucumberTest {

    public ExtendedParallelScenarioCucumberTest() {
        // TODO Auto-generated constructor stub
    }

    @ExtendedCucumberOptions(jsonReport = "target/cucumber-eps.json",
    		retryCount = 3, detailedReport = true,
    		detailedAggregatedReport = true, overviewReport = false,
    		toPDF = false, outputFolder = "target/eps")
    @CucumberOptions(
            features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
            glue = { "com/github/mkolisnyk/cucumber/steps" },
            plugin = {
            "json:target/cucumber-eps.json", "html:target/cucumber-html-report-eps",
            "pretty:target/cucumber-pretty-eps.txt",
            "usage:target/cucumber-usage-eps.json",
            "junit:target/cucumber-junit-results-eps.xml" })
    public class SampleRunnerClass {
    }

    @Test
    public void testRunScenarioParallelRunner() {
    		ExtendedParallelScenarioCucumber runner
    			= new ExtendedParallelScenarioCucumber(SampleRunnerClass.class);
    		RunNotifier notifier = new RunNotifier();
		runner.run(notifier);
		File outFile = new File("target/eps/cucumber-results-test-results.html");
		Assert.assertTrue(outFile.exists());
    }
}
