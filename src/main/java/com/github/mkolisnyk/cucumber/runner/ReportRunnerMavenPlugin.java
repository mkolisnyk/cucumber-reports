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
    private String breakdownConfig = "";
    @Parameter
    private boolean breakdownReport = false;
    @Parameter
    private boolean consolidatedReport = false;
    @Parameter
    private String consolidatedReportConfig = "";
    @Parameter
    private String[] excludeCoverageTags = {};
    @Parameter
    private String featureMapConfig = "";
    @Parameter
    private boolean featureMapReport = false;
    @Parameter
    private boolean featureOverviewChart = false;
    @Parameter
    private String[] includeCoverageTags = {};
    @Parameter
    private boolean isCoverageReport = false;
    @Parameter
    private boolean isDetailedAggregatedReport = false;
    @Parameter
    private boolean isDetailedReport = false;
    @Parameter
    private boolean isOverviewReport = false;
    @Parameter
    private boolean isUsageReport = false;
    @Parameter
    private String[] jsonReportPaths;
    @Parameter
    private String[] jsonUsageReportPaths;
    @Parameter
    private String knownErrorsConfig = "";
    @Parameter
    private boolean knownErrorsReport = false;
    @Parameter
    private String outputFolder;
    @Parameter
    private String pdfPageSize = "auto";
    @Parameter
    private String reportPrefix = "cucumber-reports";
    @Parameter
    private String screenShotLocation = "";
    @Parameter
    private String screenShotSize = "";
    @Parameter
    private boolean toPDF = false;

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

    public void setBreakdownConfig(String breakdownConfigValue) {
        this.breakdownConfig = breakdownConfigValue;
    }

    public void setBreakdownReport(boolean breakdownReportValue) {
        this.breakdownReport = breakdownReportValue;
    }

    public void setConsolidatedReport(boolean consolidatedReportValue) {
        this.consolidatedReport = consolidatedReportValue;
    }

    public void setConsolidatedReportConfig(String consolidatedReportConfigValue) {
        this.consolidatedReportConfig = consolidatedReportConfigValue;
    }

    public void setCoverageReport(boolean isCoverageReportValue) {
        this.isCoverageReport = isCoverageReportValue;
    }

    public void setDetailedAggregatedReport(boolean isDetailedAggregatedReportValue) {
        this.isDetailedAggregatedReport = isDetailedAggregatedReportValue;
    }

    public void setDetailedReport(boolean isDetailedReportValue) {
        this.isDetailedReport = isDetailedReportValue;
    }

    public void setExcludeCoverageTags(String[] excludeCoverageTagsValue) {
        this.excludeCoverageTags = excludeCoverageTagsValue;
    }

    public void setFeatureMapConfig(String featureMapConfigValue) {
        this.featureMapConfig = featureMapConfigValue;
    }

    public void setFeatureMapReport(boolean featureMapReportValue) {
        this.featureMapReport = featureMapReportValue;
    }

    public void setFeatureOverviewChart(boolean featureOverviewChartValue) {
        this.featureOverviewChart = featureOverviewChartValue;
    }

    public void setIncludeCoverageTags(String[] includeCoverageTagsValue) {
        this.includeCoverageTags = includeCoverageTagsValue;
    }

    public void setJsonReportPaths(String[] jsonReportPathsValue) {
        this.jsonReportPaths = jsonReportPathsValue;
    }

    public void setJsonUsageReportPaths(String[] jsonUsageReportPathsValue) {
        this.jsonUsageReportPaths = jsonUsageReportPathsValue;
    }

    public void setKnownErrorsConfig(String knownErrorsConfigValue) {
        this.knownErrorsConfig = knownErrorsConfigValue;
    }

    public void setKnownErrorsReport(boolean knownErrorsReportValue) {
        this.knownErrorsReport = knownErrorsReportValue;
    }
    public void setOutputFolder(String outputFolderValue) {
        this.outputFolder = outputFolderValue;
    }

    public void setOverviewReport(boolean isOverviewReportValue) {
        this.isOverviewReport = isOverviewReportValue;
    }

    public void setPdfPageSize(String pdfPageSizeValue) {
        this.pdfPageSize = pdfPageSizeValue;
    }
    public void setReportPrefix(String reportPrefixValue) {
        this.reportPrefix = reportPrefixValue;
    }

    public void setScreenShotLocation(String screenShotLocationValue) {
        this.screenShotLocation = screenShotLocationValue;
    }

    public void setScreenShotSize(String screenShotSizeValue) {
        this.screenShotSize = screenShotSizeValue;
    }

    public void setToPDF(boolean toPDFValue) {
        this.toPDF = toPDFValue;
    }

    public void setUsageReport(boolean isUsageReportValue) {
        this.isUsageReport = isUsageReportValue;
    }

}
