package com.github.mkolisnyk.cucumber.runner;

import java.io.File;

import com.github.mkolisnyk.cucumber.reporting.CucumberBenchmarkReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberBreakdownReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberConsolidatedReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberCoverageOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberCustomReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureMapReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberKnownErrorsReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberOverviewChartsReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberSystemInfo;
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.utils.helpers.FreemarkerConfiguration;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public final class ReportRunner {

    private ReportRunner() { }

    public void runUsageReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isUsageReport()) {
            return;
        }
        CucumberUsageReporting report = new CucumberUsageReporting(extendedOptions);
        try {
            report.execute(extendedOptions.getFormats());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void runOverviewReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isOverviewReport()) {
            return;
        }
        CucumberResultsOverview results = new CucumberResultsOverview(extendedOptions);
        try {
            if (extendedOptions.isKnownErrorsReport()) {
                if (features != null) {
                    results.execute(
                            new File(extendedOptions.getKnownErrorsConfig()),
                            features, extendedOptions.isDetailedAggregatedReport(),
                            extendedOptions.getFormats());
                } else {
                    results.execute(new File(extendedOptions.getKnownErrorsConfig()),
                        extendedOptions.isDetailedAggregatedReport(),
                        extendedOptions.getFormats());
                }
            } else {
                if (features != null) {
                    results.execute(
                            extendedOptions.isDetailedAggregatedReport(),
                            features,
                            extendedOptions.getFormats());
                } else {
                    results.execute(extendedOptions.getFormats());
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runFeatureOverviewChartReport(
            ExtendedRuntimeOptions extendedOptions,
            CucumberFeatureResult[] features) {
        if (!extendedOptions.isFeatureOverviewChart()) {
            return;
        }
        CucumberFeatureOverview results = new CucumberFeatureOverview(extendedOptions);
        try {
            if (features != null) {
                results.execute(
                        extendedOptions.isDetailedAggregatedReport(),
                        features,
                        extendedOptions.getFormats());
            } else {
                results.execute(extendedOptions.getFormats());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void runDetailedReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isDetailedReport()) {
            return;
        }
        CucumberDetailedResults results = new CucumberDetailedResults(extendedOptions);
        try {
            if (extendedOptions.isKnownErrorsReport()) {
                if (features != null) {
                    results.execute(new File(extendedOptions.getKnownErrorsConfig()),
                            features, false, extendedOptions.getFormats());
                } else {
                    results.execute(new File(extendedOptions.getKnownErrorsConfig()),
                            false, extendedOptions.getFormats());
                }
            } else {
                if (features != null) {
                    results.execute(false, extendedOptions.getFormats());
                } else {
                    results.execute(false, features, extendedOptions.getFormats());
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runDetailedAggregatedReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isDetailedAggregatedReport()) {
            return;
        }
        CucumberDetailedResults results = new CucumberDetailedResults(extendedOptions);
        try {
            if (extendedOptions.isKnownErrorsReport()) {
                if (features != null) {
                    results.execute(new File(extendedOptions.getKnownErrorsConfig()),
                            features, true, extendedOptions.getFormats());
                } else {
                    results.execute(new File(extendedOptions.getKnownErrorsConfig()),
                            true, extendedOptions.getFormats());
                }
            } else {
                if (features != null) {
                    results.execute(true, extendedOptions.getFormats());
                } else {
                    results.execute(true, features, extendedOptions.getFormats());
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runCoverageReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isCoverageReport()) {
            return;
        }
        CucumberCoverageOverview results = new CucumberCoverageOverview(extendedOptions);
        try {
            if (features != null) {
                results.execute(false, features, extendedOptions.getFormats());
            } else {
                results.execute(extendedOptions.getFormats());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runBreakdownReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isBreakdownReport()) {
            return;
        }
        CucumberBreakdownReport report = new CucumberBreakdownReport(extendedOptions);
        try {
            if (features != null) {
                report.execute(
                        new File(extendedOptions.getBreakdownConfig()),
                        features,
                        false,
                        extendedOptions.getFormats());
            } else {
                report.execute(new File(extendedOptions.getBreakdownConfig()), extendedOptions.getFormats());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runFeatureMapReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isFeatureMapReport()) {
            return;
        }
        CucumberFeatureMapReport report = new CucumberFeatureMapReport(extendedOptions);
        try {
            if (features != null) {
                report.execute(
                        new File(extendedOptions.getBreakdownConfig()),
                        features,
                        false,
                        extendedOptions.getFormats());
            } else {
                report.execute(new File(extendedOptions.getFeatureMapConfig()), extendedOptions.getFormats());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runKnownErrorsReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isKnownErrorsReport()) {
            return;
        }
        CucumberKnownErrorsReport report = new CucumberKnownErrorsReport(extendedOptions);
        try {
            if (features != null) {
                report.execute(
                    new File(extendedOptions.getKnownErrorsConfig()),
                    features,
                    true,
                    extendedOptions.getFormats());
            } else {
                report.execute(new File(extendedOptions.getKnownErrorsConfig()),
                    true,
                    extendedOptions.getFormats());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runOverviewChartsReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isOverviewChartsReport()) {
            return;
        }
        CucumberOverviewChartsReport report = new CucumberOverviewChartsReport(extendedOptions);
        try {
            if (extendedOptions.isKnownErrorsReport()) {
                if (features != null) {
                    report.execute(
                            new File(extendedOptions.getKnownErrorsConfig()),
                            features,
                            extendedOptions.isDetailedAggregatedReport(),
                            extendedOptions.getFormats());
                } else {
                    report.execute(new File(extendedOptions.getKnownErrorsConfig()),
                            extendedOptions.isDetailedAggregatedReport(), extendedOptions.getFormats());
                }
            } else {
                report.execute(extendedOptions.isDetailedAggregatedReport(), extendedOptions.getFormats());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runConsolidatedReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isConsolidatedReport()) {
            return;
        }
        CucumberConsolidatedReport report = new CucumberConsolidatedReport(extendedOptions);
        try {
            if (features != null) {
                report.execute(
                        new File(extendedOptions.getConsolidatedReportConfig()),
                        features,
                        false,
                        extendedOptions.getFormats());
            } else {
                report.execute(new File(extendedOptions.getConsolidatedReportConfig()),
                        extendedOptions.getFormats());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runSystemInfoReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isSystemInfoReport()) {
            return;
        }
        CucumberSystemInfo report = new CucumberSystemInfo(extendedOptions);
        try {
            report.execute(extendedOptions.getFormats());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runBenchmarkReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isBenchmarkReport()) {
            return;
        }
        CucumberBenchmarkReport report = new CucumberBenchmarkReport(extendedOptions);
        try {
            report.execute(new File(extendedOptions.getBenchmarkReportConfig()),
                    extendedOptions.getFormats());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void runCustomReport(ExtendedRuntimeOptions extendedOptions, CucumberFeatureResult[] features) {
        if (!extendedOptions.isCustomReport()) {
            return;
        }
        CucumberCustomReport report = new CucumberCustomReport(extendedOptions);
        try {
            report.execute(extendedOptions.getFormats());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public static void run(ExtendedRuntimeOptions extendedOption) {
        FreemarkerConfiguration.flush();
        ReportRunner runner = new ReportRunner();
        runner.runUsageReport(extendedOption, null);
        runner.runOverviewReport(extendedOption, null);
        runner.runOverviewChartsReport(extendedOption, null);
        runner.runFeatureOverviewChartReport(extendedOption, null);
        runner.runDetailedReport(extendedOption, null);
        runner.runDetailedAggregatedReport(extendedOption, null);
        runner.runCoverageReport(extendedOption, null);
        runner.runBreakdownReport(extendedOption, null);
        runner.runFeatureMapReport(extendedOption, null);
        runner.runKnownErrorsReport(extendedOption, null);
        runner.runSystemInfoReport(extendedOption, null);
        runner.runBenchmarkReport(extendedOption, null);
        runner.runCustomReport(extendedOption, null);
        runner.runConsolidatedReport(extendedOption, null);
    }
    public static void run(ExtendedRuntimeOptions extendedOption, CucumberFeatureResult[] features) {
        FreemarkerConfiguration.flush();
        ReportRunner runner = new ReportRunner();
        runner.runUsageReport(extendedOption, features);
        runner.runOverviewReport(extendedOption, features);
        runner.runOverviewChartsReport(extendedOption, features);
        runner.runFeatureOverviewChartReport(extendedOption, features);
        runner.runDetailedReport(extendedOption, features);
        runner.runDetailedAggregatedReport(extendedOption, features);
        runner.runCoverageReport(extendedOption, features);
        runner.runBreakdownReport(extendedOption, features);
        runner.runFeatureMapReport(extendedOption, features);
        runner.runKnownErrorsReport(extendedOption, features);
        runner.runSystemInfoReport(extendedOption, features);
        runner.runBenchmarkReport(extendedOption, features);
        runner.runCustomReport(extendedOption, features);
        runner.runConsolidatedReport(extendedOption, features);
    }
}
