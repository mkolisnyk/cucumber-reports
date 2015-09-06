package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

//@Ignore
@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
        jsonReport = "target/cucumber-dry.json",
        retryCount = 0,
        coverageReport = true,
        excludeCoverageTags = {"@flaky" },
        includeCoverageTags = {"@passed" },
        reportPrefix = "dry-run",
        outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber-dry.json", "pretty:target/cucumber-pretty-dry.txt",
        "usage:target/cucumber-usage-dry.json", "junit:target/cucumber-results-dry.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = { },
        dryRun = true)
public class SampleDryRunCucumberTest {

    public SampleDryRunCucumberTest() {
        // TODO Auto-generated constructor stub
    }

}
