package com.github.mkolisnyk.cucumber.runner;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

@Ignore
@RunWith(ExtendedParallelCucumber.class)
@ExtendedCucumberOptions(
    reportPrefix = "cucumber-parallel",
    jsonReport = "target/cucumber.json",
    retryCount = 3,
    threadsCount = 3,
    featureOverviewChart = true,
    detailedReport = true,
    detailedAggregatedReport = true,
    overviewReport = true,
    // coverageReport = true,
    jsonUsageReport = "target/cucumber-usage.json",
    usageReport = true,
    toPDF = true,
    breakdownReport = true,
    breakdownConfig = "src/test/resources/breakdown-source/simple.json",
    knownErrorsReport = true,
    knownErrorsConfig = "src/test/resources/known-errors-source/sample_model.json",
    consolidatedReport = true,
    consolidatedReportConfig = "src/test/resources/consolidated-source/sample_batch.json",
    excludeCoverageTags = { "@flaky" },
    includeCoverageTags = { "@passed" },
    outputFolder = "target/parallel")
@CucumberOptions(
        plugin = {
            "html:target/cucumber-html-report",
            "json:target/cucumber.json",
            "pretty:target/cucumber-pretty.txt",
            "usage:target/cucumber-usage.json",
            "junit:target/cucumber-results.xml"
        },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = { "@passed" })
public class SampleParallelTest {

}
