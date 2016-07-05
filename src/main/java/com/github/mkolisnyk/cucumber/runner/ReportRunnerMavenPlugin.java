package com.github.mkolisnyk.cucumber.runner;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.VERIFY)
public class ReportRunnerMavenPlugin extends AbstractMojo {
    @Parameter
    private boolean isOverviewReport = false;
    @Parameter
    private boolean isUsageReport = false;
    @Parameter
    private boolean isDetailedReport = false;
    @Parameter
    private boolean isDetailedAggregatedReport = false;
    @Parameter
    private boolean isCoverageReport = false;
    @Parameter
    private String[] jsonReportPaths;
    @Parameter
    private String outputFolder;
    @Parameter
    private String reportPrefix;
    @Parameter
    private String screenShotSize = "";
    @Parameter
    private boolean toPDF = false;
    @Parameter
    private String pdfPageSize = "auto";
    @Parameter
    private String[] jsonUsageReportPaths;
    @Parameter
    private String screenShotLocation = "";
    @Parameter
    private String[] includeCoverageTags = {};
    @Parameter
    private String[] excludeCoverageTags = {};
    @Parameter
    private boolean breakdownReport = false;
    @Parameter
    private String breakdownConfig = "";
    @Parameter
    private boolean featureMapReport = false;
    @Parameter
    private String featureMapConfig = "";
    @Parameter
    private boolean featureOverviewChart = false;
    @Parameter
    private boolean knownErrorsReport = false;
    @Parameter
    private String knownErrorsConfig = "";
    @Parameter
    private boolean consolidatedReport = false;
    @Parameter
    private String consolidatedReportConfig = "";

    public ReportRunnerMavenPlugin() {
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        ExtendedRuntimeOptions options = new ExtendedRuntimeOptions();
        options.setBreakdownConfig(breakdownConfig);
        options.setBreakdownReport(breakdownReport);
        options.setConsolidatedReport(consolidatedReport);
        options.setConsolidatedReportConfig(consolidatedReportConfig);
        options.setCoverageReport(isCoverageReport);
        options.setDetailedAggregatedReport(isDetailedAggregatedReport);
        options.setDetailedReport(isDetailedReport);
        options.setExcludeCoverageTags(excludeCoverageTags);
        options.setIncludeCoverageTags(includeCoverageTags);
        options.setFeatureMapConfig(featureMapConfig);
        options.setFeatureMapReport(featureMapReport);
        options.setFeatureOverviewChart(featureOverviewChart);
        options.setJsonReportPaths(jsonReportPaths);
        options.setJsonUsageReportPaths(jsonUsageReportPaths);
        options.setKnownErrorsConfig(knownErrorsConfig);
        options.setKnownErrorsReport(knownErrorsReport);
        options.setOutputFolder(outputFolder);
        options.setOverviewReport(isOverviewReport);
        options.setPdfPageSize(pdfPageSize);
        options.setReportPrefix(reportPrefix);
        options.setScreenShotLocation(screenShotLocation);
        options.setScreenShotSize(screenShotSize);
        options.setToPDF(toPDF);
        options.setUsageReport(isUsageReport);
        ReportRunner.run(options);
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

    public void setJsonReportPaths(String[] jsonReportPathsValue) {
        this.jsonReportPaths = jsonReportPathsValue;
    }

    public void setOutputFolder(String outputFolderValue) {
        this.outputFolder = outputFolderValue;
    }

    public void setReportPrefix(String reportPrefixValue) {
        this.reportPrefix = reportPrefixValue;
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

    public void setJsonUsageReportPaths(String[] jsonUsageReportPathsValue) {
        this.jsonUsageReportPaths = jsonUsageReportPathsValue;
    }

    public void setScreenShotLocation(String screenShotLocationValue) {
        this.screenShotLocation = screenShotLocationValue;
    }

    public void setIncludeCoverageTags(String[] includeCoverageTagsValue) {
        this.includeCoverageTags = includeCoverageTagsValue;
    }

    public void setExcludeCoverageTags(String[] excludeCoverageTagsValue) {
        this.excludeCoverageTags = excludeCoverageTagsValue;
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

}
