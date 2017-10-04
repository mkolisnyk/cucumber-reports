package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class SplitFeatureDataBean extends CommonDataBean {

    private CucumberScenarioResult scenario;
    public SplitFeatureDataBean() {
    }
    public CucumberScenarioResult getScenario() {
        return scenario;
    }
    public void setScenario(CucumberScenarioResult scenarioValue) {
        this.scenario = scenarioValue;
    }
}
