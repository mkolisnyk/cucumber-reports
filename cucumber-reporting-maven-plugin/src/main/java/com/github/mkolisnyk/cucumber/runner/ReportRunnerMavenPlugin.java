package com.github.mkolisnyk.cucumber.runner;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.VERIFY)
public class ReportRunnerMavenPlugin extends AbstractMojo {
    /**
     * Defines path to breakdown report configuration file.
     * Parameter is needed only when {@link #breakdownReport} parameter is true.
     */
    @Parameter
    private String breakdownConfig = "";
    /**
     * Flag identifying that breakdown report is to be generated.
     * If set, the {@link #breakdownConfig} parameter should be defined as well.
     */
    @Parameter
    private boolean breakdownReport = false;
    /**
     * Flag identifying that consolidated report is to be generated.
     * If set, the {@link #consolidatedReportConfig} parameter should be defined as well.
     */
    @Parameter
    private boolean consolidatedReport = false;
    /**
     * Defines path to consolidated report configuration file.
     * Parameter is needed only when {@link #consolidatedReport} parameter is true.
     */
    @Parameter
    private String consolidatedReportConfig = "";
    @Parameter
    private List<String> excludeCoverageTags = new ArrayList<>();
    @Parameter
    private String featureMapConfig = "";
    @Parameter
    /**
     * Flag identifying that feature map report is to be generated.
     * If set, the {@link #featureMapConfig} parameter should be defined as well.
     */
    private boolean featureMapReport = false;
    /**
     * Flag identifying that feature overview chart is to be generated.
     */
    @Parameter
    private boolean featureOverviewChart = false;
    @Parameter
    private List<String> includeCoverageTags = new ArrayList<>();
    /**
     * Flag identifying if coverage report is to be generated.
     */
    @Parameter
    private boolean isCoverageReport = false;
    /**
     * Flag identifying if detailed aggregated report is to be generated.
     */
    @Parameter
    private boolean isDetailedAggregatedReport = false;
    /**
     * Flag identifying if detailed report is to be generated.
     */
    @Parameter
    private boolean isDetailedReport = false;
    /**
     * Flag identifying if overview report is to be generated.
     */
    @Parameter
    private boolean isOverviewReport = false;
    /**
     * Flag identifying if usage report is to be generated.
     */
    @Parameter
    private boolean isUsageReport = false;
    @Parameter
    private List<String> jsonReportPaths;
    @Parameter
    private List<String> jsonUsageReportPaths;
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
        String[] tags = new String[excludeCoverageTags.size()];
        options.setExcludeCoverageTags(excludeCoverageTags.toArray(tags));
        tags = new String[includeCoverageTags.size()];
        options.setIncludeCoverageTags(includeCoverageTags.toArray(tags));
        options.setFeatureMapConfig(featureMapConfig);
        options.setFeatureMapReport(featureMapReport);
        options.setFeatureOverviewChart(featureOverviewChart);
        String[] paths = new String[jsonReportPaths.size()];
        options.setJsonReportPaths(jsonReportPaths.toArray(paths));
        paths = new String[jsonUsageReportPaths.size()];
        options.setJsonUsageReportPaths(jsonUsageReportPaths.toArray(paths));
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
        options.processParameters();
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

    public void setExcludeCoverageTags(List<String> excludeCoverageTagsValue) {
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

    public void setIncludeCoverageTags(List<String> includeCoverageTagsValue) {
        this.includeCoverageTags = includeCoverageTagsValue;
    }

    public void setJsonReportPaths(List<String> jsonReportPathsValue) {
        this.jsonReportPaths = jsonReportPathsValue;
    }

    public void setJsonUsageReportPaths(List<String> jsonUsageReportPathsValue) {
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
