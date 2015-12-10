package com.github.mkolisnyk.cucumber.runner;

public class ExtendedRuntimeOptions {

    private boolean isOverviewReport = false;
    private boolean isUsageReport = false;
    private boolean isDetailedReport = false;
    private boolean isDetailedAggregatedReport = false;
    private boolean isCoverageReport = false;
    private String jsonReportPath;
    private String outputFolder;
    private String reportPrefix;
    private int retryCount = 0;
    private String screenShotSize = "";
    private boolean toPDF = false;
    private String jsonUsageReportPath;
    private String screenShotLocation = "";
    private String[] includeCoverageTags = {};
    private String[] excludeCoverageTags = {};
    private boolean breakdownReport = false;
    private String breakdownConfig = "";

    public ExtendedRuntimeOptions(Class<?> clazz) {
        ExtendedCucumberOptions options = clazz
                .getAnnotation(ExtendedCucumberOptions.class);
        if (options != null) {
            this.isOverviewReport = options.overviewReport();
            this.isUsageReport = options.usageReport();
            this.isDetailedReport = options.detailedReport();
            this.isDetailedAggregatedReport = options
                    .detailedAggregatedReport();
            this.isCoverageReport = options.coverageReport();
            this.jsonReportPath = options.jsonReport();
            this.outputFolder = options.outputFolder();
            this.reportPrefix = options.reportPrefix();
            this.retryCount = options.retryCount();
            this.screenShotSize = options.screenShotSize();
            this.toPDF = options.toPDF();
            this.jsonUsageReportPath = options.jsonUsageReport();
            this.screenShotLocation = options.screenShotLocation();
            this.includeCoverageTags = options.includeCoverageTags();
            this.excludeCoverageTags = options.excludeCoverageTags();
            this.breakdownReport = options.breakdownReport();
            this.breakdownConfig = options.breakdownConfig();
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

    public final String getJsonReportPath() {
        return jsonReportPath;
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

    public final String getJsonUsageReportPath() {
        return jsonUsageReportPath;
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
}
