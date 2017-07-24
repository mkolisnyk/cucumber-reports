package com.github.mkolisnyk.cucumber.reporting.types.benchmark;

import org.apache.commons.lang.ArrayUtils;

import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class BenchmarkRowData {
    private String name;
    private OverviewStats[] results;
    public BenchmarkRowData() {
        this("", new OverviewStats[]{});
    }
    public BenchmarkRowData(String name, OverviewStats[] results) {
        super();
        this.name = name;
        this.results = results;
    }
    public String getName() {
        return name;
    }
    public OverviewStats[] getResults() {
        return results;
    }
    public void addFeatureResults(String featureId, CucumberFeatureResult[][] featureResults) {
        for (CucumberFeatureResult[] features : featureResults) {
            OverviewStats stats = new OverviewStats();
            for (CucumberFeatureResult feature : features) {
                if (feature.getId().equals(featureId)) {
                    feature.valuate();
                    stats.addFeaturesPassed(feature.getPassed());
                    stats.addFeaturesFailed(feature.getFailed());
                    stats.addFeaturesUndefined(feature.getUndefined() + feature.getSkipped());
                    stats.addFeaturesKnown(feature.getKnown());
                    
                    this.name = feature.getName();
                    break;
                }
            }
            this.results = (OverviewStats[]) ArrayUtils.add(this.results, stats);
        }
    }

    public void addScenarioResults(String scenarioId, CucumberScenarioResult[][] scenarioResults) {
        for (CucumberScenarioResult[] scenarios : scenarioResults) {
            OverviewStats stats = new OverviewStats();
            for (CucumberScenarioResult scenario : scenarios) {
                if (scenario.getId().equals(scenarioId)) {
                    scenario.valuate();
                    stats.addScenariosPassed(scenario.getPassed());
                    stats.addScenariosFailed(scenario.getFailed());
                    stats.addScenariosUndefined(scenario.getUndefined() + scenario.getSkipped());
                    stats.addScenariosKnown(scenario.getKnown());
                    
                    this.name = scenario.getName();
                    break;
                }
            }
            this.results = (OverviewStats[]) ArrayUtils.add(this.results, stats);
        }
    }
    public static CucumberScenarioResult[][] toScenarioList(CucumberFeatureResult[][] featureResults) {
        CucumberScenarioResult[][] output = new CucumberScenarioResult[][] {};
        for (CucumberFeatureResult[] features : featureResults) {
            CucumberScenarioResult[] scenarios = new CucumberScenarioResult[] {};
            for (CucumberFeatureResult feature : features) {
                scenarios = (CucumberScenarioResult[]) ArrayUtils.addAll(scenarios, feature.getElements());
            }
            output = (CucumberScenarioResult[][]) ArrayUtils.add(output, scenarios);
        }
        return output;
    }
}
