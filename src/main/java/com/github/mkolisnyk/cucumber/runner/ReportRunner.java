package com.github.mkolisnyk.cucumber.runner;

import java.io.File;

import com.github.mkolisnyk.cucumber.reporting.CucumberBreakdownReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberConsolidatedReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberCoverageOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberKnownErrorsReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;

public final class ReportRunner {

    private ReportRunner() { }

    public static void runUsageReport(ExtendedRuntimeOptions extendedOptions) {
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

    public static void runOverviewReport(ExtendedRuntimeOptions extendedOptions) {
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

    public static void runFeatureOverviewChartReport(ExtendedRuntimeOptions extendedOptions) {
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

    public static void runDetailedReport(ExtendedRuntimeOptions extendedOptions) {
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
    public static void runDetailedAggregatedReport(ExtendedRuntimeOptions extendedOptions) {
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
    public static void runCoverageReport(ExtendedRuntimeOptions extendedOptions) {
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
    public static void runBreakdownReport(ExtendedRuntimeOptions extendedOptions) {
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
    public static void runKnownErrorsReport(ExtendedRuntimeOptions extendedOptions) {
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
    public static void runConsolidatedReport(ExtendedRuntimeOptions extendedOptions) {
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
        ReportRunner.runUsageReport(extendedOption);
        ReportRunner.runOverviewReport(extendedOption);
        ReportRunner.runFeatureOverviewChartReport(extendedOption);
        ReportRunner.runDetailedReport(extendedOption);
        ReportRunner.runDetailedAggregatedReport(extendedOption);
        ReportRunner.runCoverageReport(extendedOption);
        ReportRunner.runBreakdownReport(extendedOption);
        ReportRunner.runKnownErrorsReport(extendedOption);
        ReportRunner.runConsolidatedReport(extendedOption);
    }
}
