package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.LinkedHashMap;
import java.util.Map;

public class FeatureOverviewDataBean extends CommonDataBean {
    private String overallRate;
    private int passRate;
    private Map<String, String> featureRate = new LinkedHashMap<String, String>();
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
