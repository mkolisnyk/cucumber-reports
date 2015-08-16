package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

public class CucumberCoverageOverviewTest {
    @Test
    public void testGenerateReport() throws Exception {
        CucumberCoverageOverview results = new CucumberCoverageOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber-dry.json");
        results.setExcludeCoverageTags(new String[]{"@flaky"});
        results.setIncludeCoverageTags(new String[]{"@passed"});
        results.executeOverviewReport("coverage");
    }
    @Test
    public void testGenerateReportWithExcludeIncludeTags() throws Exception {
        CucumberCoverageOverview results = new CucumberCoverageOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.setExcludeCoverageTags(new String[]{"@android"});
        results.setIncludeCoverageTags(new String[]{"@ios"});
        results.executeOverviewReport("coverage-filtered");
    }
}
