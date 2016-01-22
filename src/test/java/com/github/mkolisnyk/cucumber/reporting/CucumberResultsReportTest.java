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
    public void testGenerateOverview100Report() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-100-results");
        results.setSourceFile("./src/test/resources/overview-sources/all-passed.json");
        results.executeOverviewReport("feature-overview-2", true);
    }
    @Test
    public void testGenerateOverviewReportIssue41() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-issue41-results");
        results.setSourceFile("./src/test/resources/detailed-source/issue41-2.json");
        results.executeFeaturesOverviewReport();
    }
    @Test
    public void testGenerateOverviewLocalizedReportIssue41() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-issue41-localized");
        results.setSourceFile("./src/test/resources/detailed-source/localized-1.json");
        results.executeFeaturesOverviewReport();
    }
    @Test
    public void testGenerateOverview100FailedReport() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-100-f-results");
        results.setSourceFile("./src/test/resources/overview-sources/all-failed.json");
        results.executeFeaturesOverviewReport();
    }

    @Test
    public void testGenerateOverviewNoDataReport() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-0-results");
        results.setSourceFile("./src/test/resources/overview-sources/nothing.json");
        results.executeFeaturesOverviewReport();
    }

    @Test
    public void testGenerateDetailedReport() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.setScreenShotLocation("../src/test/resources/");
        results.executeDetailedResultsReport(true, false);
    }

    @Test
    public void testGenerateDetailedAggregatedReport() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.setScreenShotLocation("../src/test/resources/");
        results.executeDetailedResultsReport(true, true);
    }

    @Test
    public void testGenerateDetailedReportScreenShotWidth() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("cucumber-results-width");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.setScreenShotLocation("../src/test/resources/");
        results.setScreenShotWidth("200px");
        results.executeDetailedResultsReport(true, false);
    }

    @Test
    public void testDetailedReportIssue27() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("cucumber-results-27");
        results.setSourceFile("./src/test/resources/cucumber1.json");
        results.executeDetailedResultsReport(true, false);
    }
    @Test
    public void testLocalizedDetailedReportIssue41() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("localized-results");
        results.setSourceFile("./src/test/resources/detailed-source/localized-1.json");
        results.executeDetailedResultsReport(true, false);
    }
    @Test
    public void testLocalizedDetailedReportIssue44() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("issue44-results");
        results.setSourceFile("./src/test/resources/detailed-source/cucumber-2.json");
        results.executeDetailedResultsReport(true, false);
    }
}
