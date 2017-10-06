package com.github.mkolisnyk.cucumber.runner.runtime;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

public class ExtendedRuntimeOptions {

    private static final String USAGE_RESOURCE = "cli_params.txt";
    private static String usageText;

    private boolean isOverviewReport = false;
    private boolean isOverviewChartsReport = false;
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
    private boolean systemInfoReport = false;
    private boolean benchmarkReport = false;
    private String benchmarkReportConfig = "";
    private boolean customReport = false;
    private String[] customReportTemplateNames = {};
    private String customTemplatesPath = "";
    private String[] formats = {};

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
    public void processParameters() {
        if (!this.isToPDF()) {
            this.formats = ArrayUtils.removeElement(this.formats, "pdf");
        } else if (!ArrayUtils.contains(this.formats, "pdf")) {
            this.formats = ArrayUtils.add(this.formats, "pdf");
        }
    }
    public ExtendedRuntimeOptions() {
    }
    public ExtendedRuntimeOptions(List<String> args) throws Exception {

        Map<String, String> singleValueMap = new HashMap<String, String>() {
            {
                put("-o", "outputFolder");
                put("--out-folder", "outputFolder");
                put("-rp", "reportPrefix");
                put("--report-prefix", "reportPrefix");
                put("-ss", "screenShotSize");
                put("--screenshot-size", "screenShotSize");
                put("-ps", "pdfPageSize");
                put("-pdf-page-size", "pdfPageSize");
                put("-sl", "screenShotLocation");
                put("--screenshot-location", "screenShotLocation");
                put("-bc", "breakdownConfig");
                put("--breakdown-config", "breakdownConfig");
                put("-fmc", "featureMapConfig");
                put("--feature-map-config", "featureMapConfig");
                put("-kec", "knownErrorsConfig");
                put("--known-errors-config", "knownErrorsConfig");
                put("-crc", "consolidatedReportConfig");
                put("--consolidated-report-config", "consolidatedReportConfig");
                put("-ctp", "customTemplatesPath");
                put("--custom-templates-path", "customTemplatesPath");
            }
        };
        Map<String, String> multiValueMap = new HashMap<String, String>() {
            {
                put("-jrp", "jsonReportPaths");
                put("--json-report-path", "jsonReportPaths");
                put("-urp", "jsonUsageReportPaths");
                put("--usage-report-path", "jsonUsageReportPaths");
                put("-ict", "includeCoverageTags");
                put("--include-coverage-tags", "includeCoverageTags");
                put("-ect", "excludeCoverageTags");
                put("--exclude-coverage-tags", "excludeCoverageTags");
                put("-f", "formats");
                put("-formats", "formats");
            }
        };

        int size = args.size();
        for (int i = 0; i < size; i++) {
            String arg = "";
            String value = "";
            String field = "";
            if (args.get(i).equals("-r")) {
                arg = args.remove(i).trim();
                value = args.remove(i).trim();
                this.isOverviewReport = value.contains("o");
                this.isOverviewChartsReport = value.contains("O");
                this.isUsageReport = value.contains("u");
                this.isDetailedReport = value.contains("d");
                this.isDetailedAggregatedReport = value.contains("a");
                this.isCoverageReport = value.contains("c");
                this.breakdownReport = value.contains("B");
                this.featureMapReport = value.contains("F");
                this.featureOverviewChart = value.contains("f");
                this.knownErrorsReport = value.contains("K");
                this.systemInfoReport = value.contains("s");
                this.consolidatedReport = value.contains("C");
            } else if (args.get(i).equals("-pdf")) {
                this.toPDF = true;
                args.remove(i);
            } else if (singleValueMap.containsKey(args.get(i))) {
                arg = args.remove(i).trim();
                value = args.remove(i).trim();
                field = singleValueMap.get(arg);
                this.getClass().getField(field).set(this, value);
            } else if (multiValueMap.containsKey(args.get(i))) {
                arg = args.remove(i).trim();
                value = args.remove(i).trim();
                field = singleValueMap.get(arg);
                String[] currentValues = (String[]) this.getClass().getField(field).get(this);
                currentValues = (String[]) ArrayUtils.add(currentValues, value.split(","));
                this.getClass().getField(field).set(this, currentValues);
            } else if (args.get(i).equals("-h") || args.get(i).equals("--help")) {
                loadUsageTextIfNeeded();
                System.out.println(usageText);
            }
            size = args.size();
        }
        processParameters();
    }
    public ExtendedRuntimeOptions(ExtendedCucumberOptions options) throws Exception {
        if (options != null) {
            this.isOverviewReport = options.overviewReport();
            this.isOverviewChartsReport = options.overviewChartsReport();
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
            this.systemInfoReport = options.systemInfoReport();
            this.benchmarkReport = options.benchmarkReport();
            this.benchmarkReportConfig = options.benchmarkReportConfig();
            this.customReport = options.customReport();
            this.customReportTemplateNames = options.customReportTemplateNames();
            this.customTemplatesPath = options.customTemplatesPath();
            this.formats = options.formats();
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
        processParameters();
    }

    private static void loadUsageTextIfNeeded() {
        if (usageText == null) {
            try {
                Reader reader = new InputStreamReader(
                        ExtendedRuntimeOptions.class.getResourceAsStream(USAGE_RESOURCE), "UTF-8");
                //usageText = FixJava.readReader(reader);
            } catch (Exception e) {
                usageText = "Could not load usage text: " + e.toString();
                e.printStackTrace();
            }
        }
    }

    public final boolean isOverviewReport() {
        return isOverviewReport;
    }

    public boolean isOverviewChartsReport() {
        return isOverviewChartsReport;
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
    public void setOverviewChartsReport(boolean isOverviewChartsReportValue) {
        this.isOverviewChartsReport = isOverviewChartsReportValue;
    }
    public boolean isSystemInfoReport() {
        return systemInfoReport;
    }
    public void setSystemInfoReport(boolean systemInfoReportValue) {
        this.systemInfoReport = systemInfoReportValue;
    }
    public boolean isBenchmarkReport() {
        return benchmarkReport;
    }
    public void setBenchmarkReport(boolean benchmarkReportValue) {
        this.benchmarkReport = benchmarkReportValue;
    }
    public String getBenchmarkReportConfig() {
        return benchmarkReportConfig;
    }
    public void setBenchmarkReportConfig(String benchmarkReportConfigValue) {
        this.benchmarkReportConfig = benchmarkReportConfigValue;
    }
    public String getCustomTemplatesPath() {
        return customTemplatesPath;
    }
    public void setCustomTemplatesPath(String customTemplatesPathValue) {
        this.customTemplatesPath = customTemplatesPathValue;
    }
    public boolean isCustomReport() {
        return customReport;
    }
    public void setCustomReport(boolean customReportValue) {
        this.customReport = customReportValue;
    }
    public String[] getCustomReportTemplateNames() {
        return customReportTemplateNames;
    }
    public void setCustomReportTemplateNames(String[] customReportTemplateNamesValue) {
        this.customReportTemplateNames = customReportTemplateNamesValue;
    }
    public String[] getFormats() {
        return formats;
    }
    public void setFormats(String[] formatsValue) {
        this.formats = formatsValue;
    }
    public static ExtendedRuntimeOptions[] init(Class<?> clazz) throws Exception {
        ExtendedCucumberOptions[] options = clazz.getAnnotationsByType(ExtendedCucumberOptions.class);
        return init(options);
    }
    public static ExtendedRuntimeOptions[] init(ExtendedCucumberOptions[] options) throws Exception {
        ExtendedRuntimeOptions[] result = {};
        for (ExtendedCucumberOptions option : options) {
            ExtendedRuntimeOptions runtimeOption = new ExtendedRuntimeOptions(option);
            result = ArrayUtils.add(result, runtimeOption);
        }
        return result;
    }
    @Override
    public String toString() {
        return "ExtendedRuntimeOptions [isOverviewReport=" + isOverviewReport
                + ", isOverviewChartsReport=" + isOverviewChartsReport
                + ", isUsageReport=" + isUsageReport + ", isDetailedReport="
                + isDetailedReport + ", isDetailedAggregatedReport="
                + isDetailedAggregatedReport + ", isCoverageReport="
                + isCoverageReport + ", jsonReportPaths="
                + Arrays.toString(jsonReportPaths) + ", outputFolder="
                + outputFolder + ", reportPrefix=" + reportPrefix
                + ", retryCount=" + retryCount + ", screenShotSize="
                + screenShotSize + ", toPDF=" + toPDF + ", pdfPageSize="
                + pdfPageSize + ", jsonUsageReportPaths="
                + Arrays.toString(jsonUsageReportPaths)
                + ", screenShotLocation=" + screenShotLocation
                + ", includeCoverageTags="
                + Arrays.toString(includeCoverageTags)
                + ", excludeCoverageTags="
                + Arrays.toString(excludeCoverageTags) + ", breakdownReport="
                + breakdownReport + ", breakdownConfig=" + breakdownConfig
                + ", featureMapReport=" + featureMapReport
                + ", featureMapConfig=" + featureMapConfig
                + ", featureOverviewChart=" + featureOverviewChart
                + ", knownErrorsReport=" + knownErrorsReport
                + ", knownErrorsConfig=" + knownErrorsConfig
                + ", consolidatedReport=" + consolidatedReport
                + ", consolidatedReportConfig=" + consolidatedReportConfig
                + ", threadsCount=" + threadsCount + ", threadsCountValue="
                + threadsCountValue + "]";
    }
}
