package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

public class CucumberUsageReportingTest {

    @Test
    public void testGenerateReport() throws Exception {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputName("cucumber-results");
        report.setOutputDirectory("target");
        report.setJsonUsageFile("./src/test/resources/cucumber-usage.json");
        report.execute();
    }
    @Test
    public void testGenerateReportLarge() throws Exception {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputName("cucumber-results");
        report.setOutputDirectory("target/large");
        report.setJsonUsageFile("./src/test/resources/cucumber-usage-large.json");
        report.execute();
    }
    @Test
    public void testGenerateReportWithBackRef() throws Exception {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputName("cucumber-results");
        report.setOutputDirectory("target/large");
        report.setJsonUsageFile("./src/test/resources/usage-source/sample5.json");
        report.execute();
    }
    @Test
    public void testGenerateReportForNullFile() throws Exception {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputName("cucumber-results");
        report.setOutputDirectory("target/none");
        report.setJsonUsageFile("./src/test/resources/usage-source/cucumber-empty-usage.json");
        report.execute();
    }
}
