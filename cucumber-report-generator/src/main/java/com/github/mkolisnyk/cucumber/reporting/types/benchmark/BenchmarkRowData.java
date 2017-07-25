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
    public BenchmarkRowData(String nameValue, OverviewStats[] resultsValue) {
        super();
        this.name = nameValue;
        this.results = resultsValue;
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
                    stats.addScenariosPassed(feature.getPassed());
                    stats.addScenariosFailed(feature.getFailed());
                    stats.addScenariosUndefined(feature.getUndefined() + feature.getSkipped());
                    stats.addScenariosKnown(feature.getKnown());

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
                    stats.addStepsPassed(scenario.getPassed());
                    stats.addStepsFailed(scenario.getFailed());
                    stats.addStepsUndefined(scenario.getUndefined() + scenario.getSkipped());
                    stats.addStepsKnown(scenario.getKnown());

                    this.name = scenario.getFeature().getName() + "/" + scenario.getName();
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
