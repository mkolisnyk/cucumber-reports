package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.Map;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberStepSource;

public class CustomReportDataBean extends CommonDataBean {

    private Map<String, CucumberStepSource[]> usageResults;
    private Map<String, CucumberFeatureResult[]> runResults;
    public Map<String, CucumberStepSource[]> getUsageResults() {
        return usageResults;
    }
    public void setUsageResults(Map<String, CucumberStepSource[]> usageResults) {
        this.usageResults = usageResults;
    }
    public Map<String, CucumberFeatureResult[]> getRunResults() {
        return runResults;
    }
    public void setRunResults(Map<String, CucumberFeatureResult[]> runResults) {
        this.runResults = runResults;
    }
}
