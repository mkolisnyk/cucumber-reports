package com.github.mkolisnyk.cucumber.reporting.types.benchmark;

public class BenchmarkReportInfo {
    private String title;
    private String path;
    public BenchmarkReportInfo() {
        this("", "");
    }
    public BenchmarkReportInfo(String titleValue, String pathValue) {
        super();
        this.title = titleValue;
        this.path = pathValue;
    }
    public String getTitle() {
        return title;
    }
    public String getPath() {
        return path;
    }
}
