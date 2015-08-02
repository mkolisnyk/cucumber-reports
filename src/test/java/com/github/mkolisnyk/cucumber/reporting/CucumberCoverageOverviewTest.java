package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

public class CucumberCoverageOverviewTest {
    @Test
    public void testGenerateReport() throws Exception {
        CucumberCoverageOverview results = new CucumberCoverageOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber-dry.json");
        results.executeOverviewReport("coverage");
    }
}
