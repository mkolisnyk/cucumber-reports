package com.github.mkolisnyk.cucumber.reporting.types.benchmark;

public class BenchmarkReportModel {
    private BenchmarkReportInfo[] items;
    public BenchmarkReportModel(BenchmarkReportInfo[] items) {
        super();
        this.items = items;
    }
    public BenchmarkReportInfo[] getItems() {
        return items;
    }
}
