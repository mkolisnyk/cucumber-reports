package com.github.mkolisnyk.cucumber.runner;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;

import com.github.mkolisnyk.cucumber.steps.TestSteps;

import cucumber.api.CucumberOptions;

public class ExtendedParallelCucumberRunnerTest {

    public ExtendedParallelCucumberRunnerTest() {
        // TODO Auto-generated constructor stub
    }

    @RunWith(ExtendedParallelCucumber.class)
    @ExtendedCucumberOptions(jsonReport = "target/cucumber-141.json",
            detailedReport = true,
    		overviewReport = true,
    		toPDF = false, outputFolder = "target/141")
    @CucumberOptions(
            features = { "./src/test/java/com/github/mkolisnyk/cucumber/issue141" },
            glue = { "com/github/mkolisnyk/cucumber/steps" },
            plugin = {
            "json:target/cucumber-141.json", "html:target/cucumber-html-report-141",
            "pretty:target/cucumber-pretty-141.txt",
            "usage:target/cucumber-usage-141.json",
            "junit:target/cucumber-junit-results-141.xml" })
    public class SampleRunnerClass {
    }

    @Test
    public void testRunParallelRunner() throws Exception {
    		ExtendedParallelCucumber runner
    			= new ExtendedParallelCucumber(SampleRunnerClass.class);
    		RunNotifier notifier = new RunNotifier();
		runner.run(notifier);
		File outFile = new File("target/141/cucumber-results-test-results.html");
		Assert.assertTrue(outFile.exists());
		Assert.assertEquals(1, TestSteps.counter);
    }
}
