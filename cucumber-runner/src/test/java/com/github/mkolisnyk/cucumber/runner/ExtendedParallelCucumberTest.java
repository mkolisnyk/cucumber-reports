package com.github.mkolisnyk.cucumber.runner;

import java.io.File;

import org.junit.Test;
import org.testng.Assert;

import cucumber.api.CucumberOptions;

@ExtendedCucumberOptions(jsonReport = "target/cucumber-u.json",
    jsonReports = {
        "target/cucumber-01.json",
        "target/cucumber-02.json",
        "target/cucumber-03.json"
    },
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
    outputFolder = "target")
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
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
    retryCount = 3,
    coverageReport = true,
    jsonUsageReport = "target/cucumber-usage.json",
    excludeCoverageTags = {"@flaky" },
    includeCoverageTags = {"@passed" },
    outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml",
        "com.github.mkolisnyk.cucumber.runner.CustomReporter",
        "com.github.mkolisnyk.cucumber.runner.CustomFormatter:test.txt" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = {"@passed"})
public class ExtendedParallelCucumberTest {
    @Test
    public void testGetAnnotations() throws Exception {
        CucumberOptions[] option = this.getClass().getAnnotationsByType(CucumberOptions.class);
        ExtendedParallelCucumber cucumber = new ExtendedParallelCucumber(this.getClass());
        CucumberOptions[] results = cucumber.splitCucumberOption(option[0]);
        for (CucumberOptions result : results) {
            //System.out.println("" + result.features()[0]);
            Assert.assertEquals(result.features().length, 1);
        }
    }
    @Test
    public void testGetExtendedAnnotations() throws Exception {
        ExtendedCucumberOptions[] extendedOptions
            = this.getClass().getAnnotationsByType(ExtendedCucumberOptions.class);
        ExtendedParallelCucumber cucumber = new ExtendedParallelCucumber(this.getClass());
        CucumberOptions[] option = this.getClass().getAnnotationsByType(CucumberOptions.class);
        CucumberOptions[] cucumberOptions = cucumber.splitCucumberOption(option[0]);

        Assert.assertEquals(extendedOptions.length, 3);
        ExtendedCucumberOptions[][] results = cucumber.splitExtendedCucumberOptions(
                extendedOptions, cucumberOptions.length);
        Assert.assertEquals(results.length, cucumberOptions.length);
        for (int i = 0; i < cucumberOptions.length; i++) {
            for (int j = 0; j < results[i].length; j++) {
                String expectedLocation = new File(
                    cucumberOptions[i].plugin()[1].replaceFirst("json:", "")).getParent();
                String actualLocation = new File(results[i][j].jsonReport()).getParent();
                Assert.assertEquals(actualLocation, expectedLocation);
            }
        }
    }
    @Test
    public void testGenerateClasses() throws Exception {
        ExtendedCucumberOptions[] extendedOptions
            = this.getClass().getAnnotationsByType(ExtendedCucumberOptions.class);
        ExtendedParallelCucumber cucumber = new ExtendedParallelCucumber(this.getClass());
        CucumberOptions[] option = this.getClass().getAnnotationsByType(CucumberOptions.class);
        CucumberOptions[] cucumberOptions = cucumber.splitCucumberOption(option[0]);
        ExtendedCucumberOptions[][] results = cucumber.splitExtendedCucumberOptions(
                extendedOptions, cucumberOptions.length);
        ExtendedCucumber[] classes = cucumber.generateTestClasses(cucumberOptions, results);
        Assert.assertEquals(classes.length, cucumberOptions.length);
        String[] jsonReports = cucumber.getOutputJsonPaths(false);
        String[] jsonUsageReports = cucumber.getOutputJsonPaths(true);
        /*for (int i = 0; i < cucumberOptions.length; i++) {
            Assert.assertEquals(jsonReports[i], cucumberOptions[i].plugin()[1].split(":")[1]);
            Assert.assertEquals(jsonUsageReports[i], cucumberOptions[i].plugin()[3].split(":")[1]);
        }*/
    }
    @Test
    public void testConvertPaths() throws Exception {
        String[] input = {
            "html:target/cucumber-html-report",
            "json:target/cucumber-dry.json",
            "pretty:target/cucumber-pretty-dry.txt",
            "usage:target/cucumber-usage-dry.json",
            "junit:target/cucumber-results-dry.xml"
        };
        ExtendedParallelCucumber cucumber = new ExtendedParallelCucumber(this.getClass());
        String[] output = cucumber.convertPluginPaths(input, 1, true);
        Assert.assertEquals(output[0], "html:target/1/cucumber-html-report");
        Assert.assertEquals(output[1], "json:target/1/cucumber-dry.json");
        Assert.assertEquals(output[2], "pretty:target/1/cucumber-pretty-dry.txt");
        Assert.assertEquals(output[3], "usage:target/1/cucumber-usage-dry.json");
        Assert.assertEquals(output[4], "junit:target/1/cucumber-results-dry.xml");
        
        output = cucumber.convertPluginPaths(input, 3, false);
        Assert.assertEquals(output[0], "html:target/3/cucumber-html-report");
        Assert.assertEquals(output[1], "json:target/3/cucumber-dry.json");
        Assert.assertEquals(output[2], "pretty:target/3/cucumber-pretty-dry.txt");
        Assert.assertEquals(output[3], "usage:target/3/cucumber-usage-dry.json");
        Assert.assertEquals(output[4], "junit:target/3/cucumber-results-dry.xml");
    }
}
