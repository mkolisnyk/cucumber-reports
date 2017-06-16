package com.github.mkolisnyk.cucumber.runner;

import java.io.File;

import com.github.mkolisnyk.cucumber.reporting.CucumberBreakdownReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberConsolidatedReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberCoverageOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureMapReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberKnownErrorsReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberOverviewChartsReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberSystemInfo;
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public final class ReportRunner {

    private ReportRunner() { }

    public void runUsageReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isUsageReport()) {
            return;
        }
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputDirectory(extendedOptions.getOutputFolder());
        report.setJsonUsageFiles(extendedOptions.getJsonUsageReportPaths());
        try {
            report.executeReport();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void runOverviewReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isOverviewReport()) {
            return;
        }
        CucumberResultsOverview results = new CucumberResultsOverview(extendedOptions);
        try {
            if (extendedOptions.isKnownErrorsReport()) {
                results.execute(new File(extendedOptions.getKnownErrorsConfig()),
                        extendedOptions.isDetailedAggregatedReport(),
                        extendedOptions.isToPDF());
            } else {
                results.execute(extendedOptions.isToPDF());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void runFeatureOverviewChartReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isFeatureOverviewChart()) {
            return;
        }
        CucumberFeatureOverview results = new CucumberFeatureOverview(extendedOptions);
        try {
            results.execute(extendedOptions.isToPDF());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void runDetailedReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isDetailedReport()) {
            return;
        }
        CucumberDetailedResults results = new CucumberDetailedResults(extendedOptions);
        try {
            if (extendedOptions.isKnownErrorsReport()) {
                results.execute(new File(extendedOptions.getKnownErrorsConfig()),
                        false, extendedOptions.isToPDF());
            } else {
                results.execute(false, extendedOptions.isToPDF());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runDetailedAggregatedReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isDetailedAggregatedReport()) {
            return;
        }
        CucumberDetailedResults results = new CucumberDetailedResults(extendedOptions);
        try {
            if (extendedOptions.isKnownErrorsReport()) {
                results.execute(new File(extendedOptions.getKnownErrorsConfig()),
                        true, extendedOptions.isToPDF());
            } else {
                results.execute(true, extendedOptions.isToPDF());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runCoverageReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isCoverageReport()) {
            return;
        }
        CucumberCoverageOverview results = new CucumberCoverageOverview(extendedOptions);
        try {
            results.execute(extendedOptions.isToPDF());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runBreakdownReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isBreakdownReport()) {
            return;
        }
        CucumberBreakdownReport report = new CucumberBreakdownReport(extendedOptions);
        try {
            report.execute(new File(extendedOptions.getBreakdownConfig()), extendedOptions.isToPDF());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runFeatureMapReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isFeatureMapReport()) {
            return;
        }
        CucumberFeatureMapReport report = new CucumberFeatureMapReport(extendedOptions);
        try {
            report.execute(new File(extendedOptions.getFeatureMapConfig()), extendedOptions.isToPDF());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runKnownErrorsReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isKnownErrorsReport()) {
            return;
        }
        CucumberKnownErrorsReport report = new CucumberKnownErrorsReport(extendedOptions);
        try {
            report.execute(new File(extendedOptions.getKnownErrorsConfig()),
                    true,
                    extendedOptions.isToPDF());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runOverviewChartsReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isOverviewChartsReport()) {
            return;
        }
        CucumberOverviewChartsReport report = new CucumberOverviewChartsReport(extendedOptions);
        try {
            if (extendedOptions.isKnownErrorsReport()) {
                report.execute(new File(extendedOptions.getKnownErrorsConfig()),
                        extendedOptions.isDetailedAggregatedReport(), extendedOptions.isToPDF());
            } else {
                report.execute(extendedOptions.isDetailedAggregatedReport(), extendedOptions.isToPDF());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runConsolidatedReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isConsolidatedReport()) {
            return;
        }
        CucumberConsolidatedReport report = new CucumberConsolidatedReport(extendedOptions);
        try {
            report.execute(new File(extendedOptions.getConsolidatedReportConfig()),
                    extendedOptions.isToPDF());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runSystemInfoReport(ExtendedRuntimeOptions extendedOptions) {
        if (!extendedOptions.isSystemInfoReport()) {
            return;
        }
        CucumberSystemInfo report = new CucumberSystemInfo(extendedOptions);
        try {
            report.execute(extendedOptions.isToPDF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void run(ExtendedRuntimeOptions extendedOption) {
        ReportRunner runner = new ReportRunner();
        runner.runUsageReport(extendedOption);
        runner.runOverviewReport(extendedOption);
        runner.runOverviewChartsReport(extendedOption);
        runner.runFeatureOverviewChartReport(extendedOption);
        runner.runDetailedReport(extendedOption);
        runner.runDetailedAggregatedReport(extendedOption);
        runner.runCoverageReport(extendedOption);
        runner.runBreakdownReport(extendedOption);
        runner.runFeatureMapReport(extendedOption);
        runner.runKnownErrorsReport(extendedOption);
        runner.runSystemInfoReport(extendedOption);
        runner.runConsolidatedReport(extendedOption);
    }
}
