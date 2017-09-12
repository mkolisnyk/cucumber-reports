package com.github.mkolisnyk.cucumber.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber-127.json",
    retryCount = 1,
    detailedReport = true,
    detailedAggregatedReport = true,
    overviewReport = false, toPDF = false, outputFolder = "target/127")
@CucumberOptions(
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features/issue127" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        plugin = {
        "json:target/cucumber-127.json", "html:target/cucumber-html-report",
        "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json",
        "junit:target/cucumber-junit-results.xml" }, tags = {})
public class BackgroundRunTest {

}
