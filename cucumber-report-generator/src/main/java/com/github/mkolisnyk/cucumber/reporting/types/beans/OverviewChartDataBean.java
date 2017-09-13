package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;

/**
 * Data structure which is used for <a href="http://mkolisnyk.github.io/cucumber-reports/chart-report">
 * Charts Report</a> generation.
 * @author Mykola Kolisnyk
 */
public class OverviewChartDataBean extends CommonDataBean {
    /**
     * General test results overview data.
     */
    private OverviewStats overviewData;
    /**
     * Flag indicating if test coverage charts should be included.
     */
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
