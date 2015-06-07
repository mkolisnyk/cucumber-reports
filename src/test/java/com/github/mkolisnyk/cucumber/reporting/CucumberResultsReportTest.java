package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

public class CucumberResultsReportTest {

    @Test
    public void testGenerateReport() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.executeFeaturesOverviewReport();
    }
    
    @Test
    public void testGenerateDetailedReport() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.setScreenShotLocation("../src/test/resources/");
        results.executeDetailedResultsReport(true);
    }
}
