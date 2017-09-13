package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;

/**
 * Represents data structure which is used for the
 * <a href="http://mkolisnyk.github.io/cucumber-reports/detailed-report">Detailed Report</a> generation.
 * @author mykolak
 *
 */
public class DetailedReportingDataBean extends CommonDataBean {
    /**
     * General consolidated statistics of the run. For standard report it is used
     * for summary table generation.
     */
    private OverviewStats stats;
    /**
     * List of feature run results. It is the data which was processed directly from
     * the results JSON file.
     */
    private CucumberFeatureResult[] results;
    /**
     * String containing the screenshot width. It should be inserted as the <b>width</b> attribute
     * for the <b>img</b> tag in case screenshots are available for specific step.
     */
    private String screenShotWidth = "";
    public OverviewStats getStats() {
        return stats;
    }
    public void setStats(OverviewStats statsValue) {
        this.stats = statsValue;
    }
    public CucumberFeatureResult[] getResults() {
        return results;
    }
    public void setResults(CucumberFeatureResult[] resultsValue) {
        this.results = resultsValue;
    }
    public String getScreenShotWidth() {
        return screenShotWidth;
    }
    public void setScreenShotWidth(String screenShotWidthValue) {
        this.screenShotWidth = screenShotWidthValue;
    }
}
