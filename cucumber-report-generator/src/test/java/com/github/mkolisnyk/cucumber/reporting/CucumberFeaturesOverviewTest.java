package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

public class CucumberFeaturesOverviewTest {
    @Test
    public void testGenerateReport() throws Exception {
        CucumberFeatureOverview results = new CucumberFeatureOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.execute(true);
    }
    @Test
    public void testGenerateReportBigger() throws Exception {
        CucumberFeatureOverview results = new CucumberFeatureOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results-2");
        results.setSourceFile("./src/test/resources/detailed-source/cucumber-2.json");
        results.execute();
    }
    @Test
    public void testGenerateReportDry() throws Exception {
        CucumberFeatureOverview results = new CucumberFeatureOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results-dry");
        results.setSourceFile("./src/test/resources/cucumber-dry.json");
        results.execute();
    }
}
