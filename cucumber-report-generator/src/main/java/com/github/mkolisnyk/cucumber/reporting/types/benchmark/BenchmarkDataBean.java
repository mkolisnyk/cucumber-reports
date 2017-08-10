package com.github.mkolisnyk.cucumber.reporting.types.benchmark;

import com.github.mkolisnyk.cucumber.reporting.types.CommonDataBean;

public class BenchmarkDataBean extends CommonDataBean {
    private String[] headers;
    private BenchmarkRowData[] featureRows;
    private BenchmarkRowData[] scenarioRows;
    public String[] getHeaders() {
        return headers;
    }
    public void setHeaders(String[] headers) {
        this.headers = headers;
    }
    public BenchmarkRowData[] getFeatureRows() {
        return featureRows;
    }
    public void setFeatureRows(BenchmarkRowData[] featureRows) {
        this.featureRows = featureRows;
    }
    public BenchmarkRowData[] getScenarioRows() {
        return scenarioRows;
    }
    public void setScenarioRows(BenchmarkRowData[] scenarioRows) {
        this.scenarioRows = scenarioRows;
    }
}
