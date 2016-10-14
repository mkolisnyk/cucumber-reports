package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberOverviewChartsReportTest {

    @Test
    public void testGenerateBasicOverviewChartReport() throws Exception {
        ExtendedRuntimeOptions options = new ExtendedRuntimeOptions();
        options.setOutputFolder("target/charts");
        options.setReportPrefix("cucumber-reports");
        options.setJsonReportPaths(new String[] {"src/test/resources/cucumber.json"});
        options.setOverviewReport(true);
        options.setCoverageReport(true);
        CucumberOverviewChartsReport report = new CucumberOverviewChartsReport(options);
        report.execute(true);
    }
    @Test
    public void testGenerateBasicOverviewChartReportNoCoverage() throws Exception {
        ExtendedRuntimeOptions options = new ExtendedRuntimeOptions();
        options.setOutputFolder("target/charts");
        options.setReportPrefix("cucumber-reports-no-cov");
        options.setJsonReportPaths(new String[] {"src/test/resources/cucumber.json"});
        options.setOverviewReport(true);
        CucumberOverviewChartsReport report = new CucumberOverviewChartsReport(options);
        report.execute(true);
    }
}
