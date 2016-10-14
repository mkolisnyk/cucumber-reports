package com.github.mkolisnyk.cucumber.runner.parallel;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.notification.RunNotifier;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import com.github.mkolisnyk.cucumber.runner.runtime.BaseRuntimeOptionsFactory;

import cucumber.api.CucumberOptions;
import cucumber.runtime.RuntimeOptions;

@ExtendedCucumberOptions(
        jsonReport = "target/cucumber-dry.json",
        retryCount = 0,
        coverageReport = true,
        featureMapReport = true,
        featureMapConfig = "src/test/resources/breakdown-source/simple.json",
        excludeCoverageTags = {"@flaky" },
        includeCoverageTags = {"@passed" },
        reportPrefix = "dry-run-thread",
        outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber-dry.json", "pretty:target/cucumber-pretty-dry.txt",
        "usage:target/cucumber-usage-dry.json", "junit:target/cucumber-results-dry.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = { },
        dryRun = true,
        name = {"Sample", "Name"},
        monochrome = true,
        strict = true)
public class CucumberRunnerThreadTest {

    @Test
    public void testRunThread() throws Exception {
        CucumberRunnerThread thread = new CucumberRunnerThread(
                new ExtendedCucumber(
                        this.getClass()),
                new RunNotifier());
        Thread run = new Thread(thread);
        run.start();
        int timeout = 30;
        long startTime = (new Date()).getTime();
        while (run.isAlive()) {
            long endTime = (new Date()).getTime();
            Assert.assertTrue("Thread timeout", (endTime - startTime) / 1000 < timeout);
        }
    }
    @Test
    public void testGenerateOptions() {
        BaseRuntimeOptionsFactory factory = new BaseRuntimeOptionsFactory(this.getClass());
        RuntimeOptions runtime = factory.create(this.getClass().getAnnotation(CucumberOptions.class));
        Assert.assertNotNull(runtime);
    }
    @Test
    public void testGenerateOptionsForNullParameter() {
        BaseRuntimeOptionsFactory factory = new BaseRuntimeOptionsFactory(this.getClass());
        RuntimeOptions runtime = factory.create(null);
        Assert.assertNotNull(runtime);
    }
}
