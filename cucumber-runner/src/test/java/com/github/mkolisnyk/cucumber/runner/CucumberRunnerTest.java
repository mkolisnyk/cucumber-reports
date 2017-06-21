package com.github.mkolisnyk.cucumber.runner;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

@Ignore
@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json", retryCount = 3, detailedReport = true, detailedAggregatedReport = true, overviewReport = false, toPDF = false, outputFolder = "target")
@CucumberOptions(
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        plugin = {
        "json:target/cucumber.json", "html: target/cucumber-html-report",
        "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json",
        "junit:target/cucumber-junit-results.xml" }, tags = {})
public class CucumberRunnerTest {
}
