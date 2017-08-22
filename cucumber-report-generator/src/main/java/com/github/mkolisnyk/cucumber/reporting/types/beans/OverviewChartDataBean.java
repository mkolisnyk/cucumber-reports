package com.github.mkolisnyk.cucumber.reporting.types.beans;

public class OverviewChartDataBean extends CommonDataBean {
    private int[][] overviewStatuses;
    private int[][] coverageStatuses;
    private boolean isCoverageIncluded;
    public int[][] getOverviewStatuses() {
        return overviewStatuses;
    }
    public void setOverviewStatuses(int[][] overviewStatuses) {
        this.overviewStatuses = overviewStatuses;
    }
    public int[][] getCoverageStatuses() {
        return coverageStatuses;
    }
    public void setCoverageStatuses(int[][] coverageStatuses) {
        this.coverageStatuses = coverageStatuses;
    }
    public boolean isCoverageIncluded() {
        return isCoverageIncluded;
    }
    public void setCoverageIncluded(boolean isCoverageIncluded) {
        this.isCoverageIncluded = isCoverageIncluded;
    }
}
