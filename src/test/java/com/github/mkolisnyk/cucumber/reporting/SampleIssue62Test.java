package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;
//import cucumber.api.junit.Cucumber;

@Ignore
@RunWith(ExtendedCucumber.class)
//@RunWith(Cucumber.class)
@ExtendedCucumberOptions(
        jsonReport = "target/cucumber.json",
        jsonUsageReport = "target/cucumber-usage.json",
        usageReport = true,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        toPDF = true,
        outputFolder = "target/LoginReport/ExtendedReport")
@CucumberOptions(
        features = { "src/test/java/com/github/mkolisnyk/cucumber/features/63.feature" },
        //tags = { "~@ignore" },
        glue = "com/github/mkolisnyk/cucumber/steps", plugin = {
        "html:target/LoginReport", "json:target/cucumber.json",
        "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" })
public class SampleIssue62Test {

}
