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
    public void setOverallRate(String overallRate) {
        this.overallRate = overallRate;
    }
    public Map<String, String> getFeatureRate() {
        return featureRate;
    }
    public void setFeatureRate(Map<String, String> featureRate) {
        this.featureRate = featureRate;
    }
    public int getPassRate() {
        return passRate;
    }
    public void setPassRate(int passRate) {
        this.passRate = passRate;
    }
}
