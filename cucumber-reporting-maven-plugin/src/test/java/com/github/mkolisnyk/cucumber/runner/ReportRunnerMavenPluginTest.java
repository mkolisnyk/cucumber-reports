package com.github.mkolisnyk.cucumber.runner;

import java.io.File;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class ReportRunnerMavenPluginTest {
    private ReportRunnerMavenPlugin plugin;

    public ReportRunnerMavenPluginTest() {
    }

    @Test
    public void testSamplePluginRun() throws Exception {
        String[] expectedFiles = new String[] {
            //"cucumber-usage-report.html",
            "sample-maven-agg-test-results.html",
            "sample-maven-agg-test-results.html.bak.html",
            "sample-maven-batch-config1.html",
            "sample-maven-batch-config1.html.bak.html",
            "sample-maven-batch-config1.pdf",
            "sample-maven-batch-config2.html",
            "sample-maven-batch-config2.html.bak.html",
            "sample-maven-batch-config2.pdf",
            //"sample-maven-coverage-dump.xml",
            "sample-maven-coverage.html",
            "sample-maven-coverage.html.bak.html",
            "sample-maven-coverage.pdf",
            "sample-maven-feature-overview-chart.html",
            "sample-maven-feature-overview-chart.html.bak.html",
            "sample-maven-feature-overview-chart.pdf",
            "sample-maven-feature-overview-dump.xml",
            "sample-maven-feature-overview.html",
            "sample-maven-feature-overview.html.bak.html",
            "sample-maven-feature-overview.pdf",
            //"sample-maven-frame.html",
            "sample-maven-known-errors.html",
            "sample-maven-known-errors.html.bak.html",
            "sample-maven-known-errors.pdf",
            "sample-maven-report1.html",
            "sample-maven-report1.html.bak.html",
            "sample-maven-report1.pdf",
            "sample-maven-report2.html",
            "sample-maven-report2.html.bak.html",
            "sample-maven-report2.pdf",
            "sample-maven-report3.html",
            "sample-maven-report3.html.bak.html",
            "sample-maven-report3.pdf",
            "sample-maven-report4.html",
            "sample-maven-report4.html.bak.html",
            "sample-maven-report4.pdf",
            "sample-maven-test-results.html",
            "sample-maven-test-results.html.bak.html",
            "sample-maven-test-results.pdf",
        };
        plugin = new ReportRunnerMavenPlugin();
        plugin.setJsonReportPaths(new ArrayList<String>() {
            {
                add("src/test/resources/cucumber.json");
            }
        });
        plugin.setFeatureOverviewChart(true);
        plugin.setDetailedReport(true);
        plugin.setDetailedAggregatedReport(true);
        plugin.setOverviewReport(true);
        plugin.setCoverageReport(true);
        plugin.setUsageReport(true);
        plugin.setJsonUsageReportPaths(new ArrayList<String>() {
            {
                add("src/test/resources/cucumber-usage.json");
            }
        });
        plugin.setToPDF(true);
        plugin.setBreakdownReport(true);
        plugin.setBreakdownConfig("src/test/resources/breakdown-source/simple.json");
        plugin.setKnownErrorsReport(true);
        plugin.setKnownErrorsConfig("src/test/resources/known-errors-source/sample_model.json");
        plugin.setConsolidatedReport(true);
        plugin.setConsolidatedReportConfig("./src/test/resources/consolidated-source/sample_batch.json");
        plugin.setOutputFolder("target/maven-plugin");
        plugin.setReportPrefix("sample-maven");
        plugin.setIncludeCoverageTags(new ArrayList<String>());
        plugin.setExcludeCoverageTags(new ArrayList<String>());
        plugin.setFeatureMapReport(true);
        plugin.setFeatureMapConfig("src/test/resources/breakdown-source/simple.json");
        plugin.setPdfPageSize("A4 Portrait");
        plugin.setScreenShotLocation("/screenshots");
        plugin.setScreenShotSize("300px");
        plugin.execute();
        for (String report : expectedFiles) {
            File file = new File("target/maven-plugin/" + report);
            Assert.assertTrue("Unable to find output file: " + file.getAbsolutePath(), file.exists());
        }
    }
}
