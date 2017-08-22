package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkRowData;

public class BenchmarkDataBean extends CommonDataBean {
    private String[] headers;
    private BenchmarkRowData[] featureRows;
    private BenchmarkRowData[] scenarioRows;
    public String[] getHeaders() {
        return headers;
    }
    public void setHeaders(String[] headersValue) {
        this.headers = headersValue;
    }
    public BenchmarkRowData[] getFeatureRows() {
        return featureRows;
    }
    public void setFeatureRows(BenchmarkRowData[] featureRowsValue) {
        this.featureRows = featureRowsValue;
    }
    public BenchmarkRowData[] getScenarioRows() {
        return scenarioRows;
    }
    public void setScenarioRows(BenchmarkRowData[] scenarioRowsValue) {
        this.scenarioRows = scenarioRowsValue;
    }
}
