package com.github.mkolisnyk.cucumber.reporting.types.benchmark;

public class BenchmarkReportModel {
    private BenchmarkReportInfo[] items;
    public BenchmarkReportModel(BenchmarkReportInfo[] itemsValue) {
        super();
        this.items = itemsValue;
    }
    public BenchmarkReportInfo[] getItems() {
        return items;
    }
}
