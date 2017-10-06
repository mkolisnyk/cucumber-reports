package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.Map;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberStepSource;

/**
 * Represents data structure which is used for the
 * <a href="http://mkolisnyk.github.io/cucumber-reports/custom-report">Custom Report</a> generation.
 * @author Mykola Kolisnyk
 */
public class CustomReportDataBean extends SystemInfoDataBean {

    /**
     * Map containing steps usage data. Each key contains the path to the file.
     * Values contain actual {@link CucumberStepSource} data.
     */
    private Map<String, CucumberStepSource[]> usageResults;
    /**
     * Map containing test results data. Each key contains the path to the file.
     * Values contain actual {@link CucumberFeatureResult} data.
     */
    private Map<String, CucumberFeatureResult[]> runResults;
    public Map<String, CucumberStepSource[]> getUsageResults() {
        return usageResults;
    }
    public void setUsageResults(Map<String, CucumberStepSource[]> usageResultsValue) {
        this.usageResults = usageResultsValue;
    }
    public Map<String, CucumberFeatureResult[]> getRunResults() {
        return runResults;
    }
    public void setRunResults(Map<String, CucumberFeatureResult[]> runResultsValue) {
        this.runResults = runResultsValue;
    }
}
