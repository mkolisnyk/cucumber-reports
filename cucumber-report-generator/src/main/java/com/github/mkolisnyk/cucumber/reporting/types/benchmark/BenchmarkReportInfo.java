package com.github.mkolisnyk.cucumber.reporting.types.benchmark;

public class BenchmarkReportInfo {
    private String title;
    private String path;
    public BenchmarkReportInfo() {
        this("", "");
    }
    public BenchmarkReportInfo(String title, String path) {
        super();
        this.title = title;
        this.path = path;
    }
    public String getTitle() {
        return title;
    }
    public String getPath() {
        return path;
    }
}
