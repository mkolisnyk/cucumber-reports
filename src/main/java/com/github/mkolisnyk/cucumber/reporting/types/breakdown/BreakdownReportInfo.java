package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

public class BreakdownReportInfo {
    private String reportSuffix;
    private String title;
    private int refreshTimeout;
    private String nextFile;
    private BreakdownTable table;
    public BreakdownReportInfo(BreakdownTable tableValue) {
        this(tableValue, "breakdown", "Breakdown Report", 0, "");
    }
    public BreakdownReportInfo(BreakdownTable tableValue, String reportSuffixValue, String titleValue,
            int refreshTimeoutValue, String nextFileValue) {
        super();
        this.reportSuffix = reportSuffixValue;
        this.title = titleValue;
        this.refreshTimeout = refreshTimeoutValue;
        this.nextFile = nextFileValue;
        this.table = tableValue;
    }
    public String getReportSuffix() {
        return reportSuffix;
    }
    public String getTitle() {
        return title;
    }
    public int getRefreshTimeout() {
        return refreshTimeout;
    }
    public String getNextFile() {
        return nextFile;
    }
    public void setNextFile(String nextFileValue) {
        this.nextFile = nextFileValue;
    }
    public BreakdownTable getTable() {
        return table;
    }
}
