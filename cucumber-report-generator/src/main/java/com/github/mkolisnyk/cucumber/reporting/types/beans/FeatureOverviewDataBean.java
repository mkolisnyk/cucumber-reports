package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents data structure for the <a href="http://mkolisnyk.github.io/cucumber-reports/overview-chart-report">
 * Overview Charts Report</a> generation.
 * @author Mykola Kolisnyk
 */
public class FeatureOverviewDataBean extends CommonDataBean {
    /**
     * Contains the letter reflecting overall rate. In standard report it's the letter
     * inside the black arrow on the right hand side of the chart table.
     */
    private String overallRate;
    /**
     * The number reflecting the overall %pass.
     */
    private int passRate;
    /**
     * The map associating feature name with it's run status letter (any from A B C D E F
     * depending on the actual pass rate).
     */
    private Map<String, String> featureRate = new LinkedHashMap<>();
    public String getOverallRate() {
        return overallRate;
    }
    public void setOverallRate(String overallRateValue) {
        this.overallRate = overallRateValue;
    }
    public Map<String, String> getFeatureRate() {
        return featureRate;
    }
    public void setFeatureRate(Map<String, String> featureRateValue) {
        this.featureRate = featureRateValue;
    }
    public int getPassRate() {
        return passRate;
    }
    public void setPassRate(int passRateValue) {
        this.passRate = passRateValue;
    }
}
