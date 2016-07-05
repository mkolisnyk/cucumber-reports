package com.github.mkolisnyk.cucumber.runner.runtime;

import java.lang.reflect.Field;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

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
    private String threadsCountValue;

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
    public ExtendedRuntimeOptions() {
    }
    public ExtendedRuntimeOptions(ExtendedCucumberOptions options) throws Exception {
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
            this.threadsCountValue = options.threadsCountValue();
        }
        for (Field field : this.getClass().getDeclaredFields()) {
            String propertyName = "cucumber.reports." + field.getName();
            if (System.getProperties().containsKey(propertyName)) {
                if (field.getType().equals(boolean.class)) {
                    field.setBoolean(this, System.getProperty(propertyName).equalsIgnoreCase("true"));
                } else if (field.getType().equals(int.class)) {
                    field.setInt(this, Integer.valueOf(System.getProperty(propertyName)));
                } else {
                    field.set(this, System.getProperty(propertyName));
                }
            }
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
    public final void setJsonReportPaths(String[] newPaths) {
        this.jsonReportPaths = newPaths;
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
    public final void setJsonUsageReportPaths(String[] newPaths) {
        this.jsonUsageReportPaths = newPaths;
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

    public String getThreadsCountValue() {
        return threadsCountValue;
    }

    public void setOverviewReport(boolean isOverviewReportValue) {
        this.isOverviewReport = isOverviewReportValue;
    }
    public void setUsageReport(boolean isUsageReportValue) {
        this.isUsageReport = isUsageReportValue;
    }
    public void setDetailedReport(boolean isDetailedReportValue) {
        this.isDetailedReport = isDetailedReportValue;
    }
    public void setDetailedAggregatedReport(boolean isDetailedAggregatedReportValue) {
        this.isDetailedAggregatedReport = isDetailedAggregatedReportValue;
    }
    public void setCoverageReport(boolean isCoverageReportValue) {
        this.isCoverageReport = isCoverageReportValue;
    }
    public void setOutputFolder(String outputFolderValue) {
        this.outputFolder = outputFolderValue;
    }
    public void setReportPrefix(String reportPrefixValue) {
        this.reportPrefix = reportPrefixValue;
    }
    public void setRetryCount(int retryCountValue) {
        this.retryCount = retryCountValue;
    }
    public void setScreenShotSize(String screenShotSizeValue) {
        this.screenShotSize = screenShotSizeValue;
    }
    public void setToPDF(boolean toPDFValue) {
        this.toPDF = toPDFValue;
    }
    public void setPdfPageSize(String pdfPageSizeValue) {
        this.pdfPageSize = pdfPageSizeValue;
    }
    public void setScreenShotLocation(String screenShotLocationValue) {
        this.screenShotLocation = screenShotLocationValue;
    }
    public void setBreakdownReport(boolean breakdownReportValue) {
        this.breakdownReport = breakdownReportValue;
    }
    public void setBreakdownConfig(String breakdownConfigValue) {
        this.breakdownConfig = breakdownConfigValue;
    }
    public void setFeatureMapReport(boolean featureMapReportValue) {
        this.featureMapReport = featureMapReportValue;
    }
    public void setFeatureMapConfig(String featureMapConfigValue) {
        this.featureMapConfig = featureMapConfigValue;
    }
    public void setFeatureOverviewChart(boolean featureOverviewChartValue) {
        this.featureOverviewChart = featureOverviewChartValue;
    }
    public void setKnownErrorsReport(boolean knownErrorsReportValue) {
        this.knownErrorsReport = knownErrorsReportValue;
    }
    public void setKnownErrorsConfig(String knownErrorsConfigValue) {
        this.knownErrorsConfig = knownErrorsConfigValue;
    }
    public void setConsolidatedReport(boolean consolidatedReportValue) {
        this.consolidatedReport = consolidatedReportValue;
    }
    public void setConsolidatedReportConfig(String consolidatedReportConfigValue) {
        this.consolidatedReportConfig = consolidatedReportConfigValue;
    }
    public static ExtendedRuntimeOptions[] init(Class<?> clazz) throws Exception {
        ExtendedCucumberOptions[] options = clazz.getAnnotationsByType(ExtendedCucumberOptions.class);
        return init(options);
    }
    public static ExtendedRuntimeOptions[] init(ExtendedCucumberOptions[] options) throws Exception {
        ExtendedRuntimeOptions[] result = {};
        for (ExtendedCucumberOptions option : options) {
            result = ArrayUtils.add(result, new ExtendedRuntimeOptions(option));
        }
        return result;
    }
}
