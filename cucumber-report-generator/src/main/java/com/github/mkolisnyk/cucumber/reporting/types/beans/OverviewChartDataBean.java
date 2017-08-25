package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;

public class OverviewChartDataBean extends CommonDataBean {
    private OverviewStats overviewData;
    private boolean coverageIncluded;
    public OverviewStats getOverviewData() {
        return overviewData;
    }
    public void setOverviewData(OverviewStats overviewStatuses) {
        this.overviewData = overviewStatuses;
    }
    public boolean isCoverageIncluded() {
        return coverageIncluded;
    }
    public void setCoverageIncluded(boolean isCoverageIncluded) {
        this.coverageIncluded = isCoverageIncluded;
    }
}
