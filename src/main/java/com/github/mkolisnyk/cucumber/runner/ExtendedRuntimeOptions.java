package com.github.mkolisnyk.cucumber.runner;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ExtendedRuntimeOptions {

    private boolean isOverviewReport = false;
    private boolean isUsageReport = false;
    private boolean isDetailedReport = false;
    private boolean isDetailedAggregatedReport = false;
    private boolean isCoverageReport = false;
    private String[] jsonReportPaths;
    private String outputFolder;
    private String reportPrefix;
    private int retryCount = 0;
    private String screenShotSize = "";
    private boolean toPDF = false;
    private String pdfPageSize = "auto";
    private String[] jsonUsageReportPaths;
    private String screenShotLocation = "";
    private String[] includeCoverageTags = {};
    private String[] excludeCoverageTags = {};
    private boolean breakdownReport = false;
    private String breakdownConfig = "";
    private boolean featureMapReport = false;
    private String featureMapConfig = "";
    private boolean featureOverviewChart = false;
    private boolean knownErrorsReport = false;
    private String knownErrorsConfig = "";
    private boolean consolidatedReport = false;
    private String consolidatedReportConfig = "";
    private int threadsCount;

    private String[] joinPaths(String singlePath, String[] arrayPath) {
        String[] result = {};
        if (StringUtils.isNotBlank(singlePath)) {
            result = ArrayUtils.add(result, singlePath);
        }
        if (arrayPath != null && arrayPath.length > 0) {
            result = ArrayUtils.addAll(result, arrayPath);
        }
        return result;
    }
    public ExtendedRuntimeOptions(ExtendedCucumberOptions options) {
        if (options != null) {
            this.isOverviewReport = options.overviewReport();
            this.isUsageReport = options.usageReport();
            this.isDetailedReport = options.detailedReport();
            this.isDetailedAggregatedReport = options
                    .detailedAggregatedReport();
            this.isCoverageReport = options.coverageReport();
            this.jsonReportPaths = joinPaths(options.jsonReport(), options.jsonReports());
            this.outputFolder = options.outputFolder();
            this.reportPrefix = options.reportPrefix();
            this.retryCount = options.retryCount();
            this.screenShotSize = options.screenShotSize();
            this.toPDF = options.toPDF();
            this.pdfPageSize = options.pdfPageSize();
            this.jsonUsageReportPaths = joinPaths(options.jsonUsageReport(), options.jsonUsageReports());
            this.screenShotLocation = options.screenShotLocation();
            this.includeCoverageTags = options.includeCoverageTags();
            this.excludeCoverageTags = options.excludeCoverageTags();
            this.breakdownReport = options.breakdownReport();
            this.breakdownConfig = options.breakdownConfig();
            this.featureMapReport = options.featureMapReport();
            this.featureMapConfig = options.featureMapConfig();
            this.featureOverviewChart = options.featureOverviewChart();
            this.knownErrorsReport = options.knownErrorsReport();
            this.knownErrorsConfig = options.knownErrorsConfig();
            this.consolidatedReport = options.consolidatedReport();
            this.consolidatedReportConfig = options.consolidatedReportConfig();
            this.threadsCount = options.threadsCount();
        }
    }

    public final boolean isOverviewReport() {
        return isOverviewReport;
    }

    public final boolean isUsageReport() {
        return isUsageReport;
    }

    public final boolean isDetailedReport() {
        return isDetailedReport;
    }

    public final boolean isDetailedAggregatedReport() {
        return isDetailedAggregatedReport;
    }

    public final boolean isCoverageReport() {
        return isCoverageReport;
    }

    public final String[] getJsonReportPaths() {
        return jsonReportPaths;
    }

    public final String getOutputFolder() {
        return outputFolder;
    }

    public final String getReportPrefix() {
        return reportPrefix;
    }

    public final int getRetryCount() {
        return retryCount;
    }

    public final String getScreenShotSize() {
        return screenShotSize;
    }

    public final boolean isToPDF() {
        return toPDF;
    }

    public String getPdfPageSize() {
        return pdfPageSize;
    }

    public final String[] getJsonUsageReportPaths() {
        return jsonUsageReportPaths;
    }

    public final String getScreenShotLocation() {
        return screenShotLocation;
    }

    public final String[] getIncludeCoverageTags() {
        return includeCoverageTags;
    }

    public final void setIncludeCoverageTags(String[] includeCoverageTagsValue) {
        this.includeCoverageTags = includeCoverageTagsValue;
    }

    public final String[] getExcludeCoverageTags() {
        return excludeCoverageTags;
    }

    public final void setExcludeCoverageTags(String[] excludeCoverageTagsValue) {
        this.excludeCoverageTags = excludeCoverageTagsValue;
    }

    public boolean isBreakdownReport() {
        return breakdownReport;
    }

    public String getBreakdownConfig() {
        return breakdownConfig;
    }

    public boolean isFeatureMapReport() {
        return featureMapReport;
    }

    public String getFeatureMapConfig() {
        return featureMapConfig;
    }

    public boolean isFeatureOverviewChart() {
        return featureOverviewChart;
    }

    public boolean isKnownErrorsReport() {
        return knownErrorsReport;
    }

    public String getKnownErrorsConfig() {
        return knownErrorsConfig;
    }

    public boolean isConsolidatedReport() {
        return consolidatedReport;
    }

    public String getConsolidatedReportConfig() {
        return consolidatedReportConfig;
    }
    public int getThreadsCount() {
        return threadsCount;
    }

    public static ExtendedRuntimeOptions[] init(Class<?> clazz) {
        ExtendedCucumberOptions[] options = clazz.getAnnotationsByType(ExtendedCucumberOptions.class);
        ExtendedRuntimeOptions[] result = {};
        for (ExtendedCucumberOptions option : options) {
            result = ArrayUtils.add(result, new ExtendedRuntimeOptions(option));
        }
        return result;
    }
}
