package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
/*@ExtendedCucumberOptions(jsonReport = "target/cucumber-u.json",
        retryCount = 0,
        featureOverviewChart = true,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        coverageReport = true,
        jsonUsageReport = "target/cucumber-usage-u.json",
        usageReport = true,
        toPDF = true,
        breakdownReport = true,
        breakdownConfig = "src/test/resources/breakdown-source/simple.json",
        knownErrorsReport = true,
        knownErrorsConfig = "src/test/resources/known-errors-source/sample_model.json",
        consolidatedReport = true,
        consolidatedReportConfig = "src/test/resources/consolidated-source/s_ample_batch.json",
        outputFolder = "target")*/
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        retryCount = 3,
        threadsCount = 3,
        featureOverviewChart = true,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        //coverageReport = true,
        jsonUsageReport = "target/cucumber-usage.json",
        usageReport = true,
        toPDF = true,
        breakdownReport = true,
        breakdownConfig = "src/test/resources/breakdown-source/simple.json",
        knownErrorsReport = true,
        knownErrorsConfig = "src/test/resources/known-errors-source/sample_model.json",
        consolidatedReport = true,
        consolidatedReportConfig = "src/test/resources/consolidated-source/sample_batch.json",
        excludeCoverageTags = {"@flaky" },
        includeCoverageTags = {"@passed" },
        outputFolder = "target")
/*@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        retryCount = 3,
        coverageReport = true,
        jsonUsageReport = "target/cucumber-usage.json",
        excludeCoverageTags = {"@flaky" },
        includeCoverageTags = {"@passed" },
        outputFolder = "target")*/
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = {"@passed"})
public class SampleCucumberTest {
}
