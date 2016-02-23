package com.github.mkolisnyk.cucumber.runner;

import java.io.File;

import com.github.mkolisnyk.cucumber.reporting.CucumberBreakdownReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberConsolidatedReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberCoverageOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureMapReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberKnownErrorsReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;

public final class ReportRunner {

    private ReportRunner() { }

    public void runUsageReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isUsageReport()) {
            return;
        }
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputDirectory(extendedOptions.getOutputFolder());
        report.setJsonUsageFile(extendedOptions.getJsonUsageReportPath());
        try {
            report.executeReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runOverviewReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isOverviewReport()) {
            return;
        }
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        results.setPdfPageSize(extendedOptions.getPdfPageSize());
        try {
            results.executeFeaturesOverviewReport(extendedOptions.isToPDF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runFeatureOverviewChartReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isFeatureOverviewChart()) {
            return;
        }
        CucumberFeatureOverview results = new CucumberFeatureOverview();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        results.setPdfPageSize(extendedOptions.getPdfPageSize());
        try {
            results.executeFeatureOverviewChartReport(extendedOptions.isToPDF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runDetailedReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isDetailedReport()) {
            return;
        }
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        results.setScreenShotLocation(extendedOptions.getScreenShotLocation());
        results.setScreenShotWidth(extendedOptions.getScreenShotSize());
        results.setPdfPageSize(extendedOptions.getPdfPageSize());
        try {
            results.executeDetailedResultsReport(
                    extendedOptions.isToPDF(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void runDetailedAggregatedReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isDetailedAggregatedReport()) {
            return;
        }
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        results.setScreenShotLocation(extendedOptions.getScreenShotLocation());
        results.setScreenShotWidth(extendedOptions.getScreenShotSize());
        results.setPdfPageSize(extendedOptions.getPdfPageSize());
        try {
            results.executeDetailedResultsReport(
                    extendedOptions.isToPDF(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void runCoverageReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isCoverageReport()) {
            return;
        }
        CucumberCoverageOverview results = new CucumberCoverageOverview();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        results.setExcludeCoverageTags(extendedOptions.getExcludeCoverageTags());
        results.setIncludeCoverageTags(extendedOptions.getIncludeCoverageTags());
        results.setPdfPageSize(extendedOptions.getPdfPageSize());
        try {
            results.executeCoverageReport(extendedOptions.isToPDF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void runBreakdownReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isBreakdownReport()) {
            return;
        }
        CucumberBreakdownReport report = new CucumberBreakdownReport();
        report.setOutputDirectory(extendedOptions.getOutputFolder());
        report.setOutputName(extendedOptions.getReportPrefix());
        report.setSourceFile(extendedOptions.getJsonReportPath());
        report.setPdfPageSize(extendedOptions.getPdfPageSize());
        try {
            report.executeReport(new File(extendedOptions.getBreakdownConfig()), extendedOptions.isToPDF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void runFeatureMapReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isFeatureMapReport()) {
            return;
        }
        CucumberFeatureMapReport report = new CucumberFeatureMapReport();
        report.setOutputDirectory(extendedOptions.getOutputFolder());
        report.setOutputName(extendedOptions.getReportPrefix());
        report.setSourceFile(extendedOptions.getJsonReportPath());
        report.setPdfPageSize(extendedOptions.getPdfPageSize());
        try {
            report.executeReport(new File(extendedOptions.getFeatureMapConfig()), extendedOptions.isToPDF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void runKnownErrorsReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isKnownErrorsReport()) {
            return;
        }
        CucumberKnownErrorsReport report = new CucumberKnownErrorsReport();
        report.setOutputDirectory(extendedOptions.getOutputFolder());
        report.setOutputName(extendedOptions.getReportPrefix());
        report.setSourceFile(extendedOptions.getJsonReportPath());
        report.setPdfPageSize(extendedOptions.getPdfPageSize());
        try {
            report.executeKnownErrorsReport(new File(extendedOptions.getKnownErrorsConfig()),
                    extendedOptions.isToPDF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void runConsolidatedReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isConsolidatedReport()) {
            return;
        }
        CucumberConsolidatedReport report = new CucumberConsolidatedReport();
        report.setOutputDirectory(extendedOptions.getOutputFolder());
        report.setOutputName(extendedOptions.getReportPrefix());
        report.setSourceFile(extendedOptions.getJsonReportPath());
        report.setPdfPageSize(extendedOptions.getPdfPageSize());
        try {
            report.executeConsolidatedReport(new File(extendedOptions.getConsolidatedReportConfig()),
                    extendedOptions.isToPDF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void run(ExtendedRuntimeOptions extendedOption) {
        ReportRunner runner = new ReportRunner();
        runner.runUsageReport(extendedOption);
        runner.runOverviewReport(extendedOption);
        runner.runFeatureOverviewChartReport(extendedOption);
        runner.runDetailedReport(extendedOption);
        runner.runDetailedAggregatedReport(extendedOption);
        runner.runCoverageReport(extendedOption);
        runner.runBreakdownReport(extendedOption);
        runner.runFeatureMapReport(extendedOption);
        runner.runKnownErrorsReport(extendedOption);
        runner.runConsolidatedReport(extendedOption);
    }
}
