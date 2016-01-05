package com.github.mkolisnyk.cucumber.runner;

import java.io.File;

import com.github.mkolisnyk.cucumber.reporting.CucumberBreakdownReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberCoverageOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;

public final class ReportRunner {

    private ReportRunner() { }

    public static void runUsageReport(ExtendedRuntimeOptions extendedOptions) {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputDirectory(extendedOptions.getOutputFolder());
        report.setJsonUsageFile(extendedOptions.getJsonUsageReportPath());
        try {
            report.executeReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runOverviewReport(ExtendedRuntimeOptions extendedOptions) {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        try {
            results.executeFeaturesOverviewReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runFeatureOverviewChartReport(ExtendedRuntimeOptions extendedOptions) {
        CucumberFeatureOverview results = new CucumberFeatureOverview();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        try {
            results.executeFeatureOverviewChartReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public static void runDetailedReport(ExtendedRuntimeOptions extendedOptions) {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        results.setScreenShotLocation(extendedOptions.getScreenShotLocation());
        results.setScreenShotWidth(extendedOptions.getScreenShotSize());
        try {
            results.executeDetailedResultsReport(
                    extendedOptions.isToPDF(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void runDetailedAggregatedReport(ExtendedRuntimeOptions extendedOptions) {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        results.setScreenShotLocation(extendedOptions.getScreenShotLocation());
        results.setScreenShotWidth(extendedOptions.getScreenShotSize());
        try {
            results.executeDetailedResultsReport(
                    extendedOptions.isToPDF(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void runCoverageReport(ExtendedRuntimeOptions extendedOptions) {
        CucumberCoverageOverview results = new CucumberCoverageOverview();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        results.setExcludeCoverageTags(extendedOptions.getExcludeCoverageTags());
        results.setIncludeCoverageTags(extendedOptions.getIncludeCoverageTags());
        try {
            results.executeCoverageReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void runBreakdownReport(ExtendedRuntimeOptions extendedOptions) {
        CucumberBreakdownReport report = new CucumberBreakdownReport();
        report.setOutputDirectory(extendedOptions.getOutputFolder());
        report.setOutputName(extendedOptions.getReportPrefix());
        report.setSourceFile(extendedOptions.getJsonReportPath());
        try {
            report.executeReport(new File(extendedOptions.getBreakdownConfig()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
