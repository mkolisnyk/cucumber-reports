package com.github.mkolisnyk.cucumber.reporting;

import org.apache.maven.reporting.MavenReportException;
import org.junit.Test;

public class CucumberUsageReportingTest {

    @Test
    public void testGenerateReport() throws MavenReportException {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputDirectory("target");
        report.setJsonUsageFile("./src/test/resources/cucumber-usage.json");
        report.executeReport();
    }
}
