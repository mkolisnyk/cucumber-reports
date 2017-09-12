package com.github.mkolisnyk.cucumber.reporting.types.benchmark;

import org.apache.commons.lang.ArrayUtils;

import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

/**
 * A part of input data for the <a href="http://mkolisnyk.github.io/cucumber-reports/benchmark">Benchmark Report</a>.
 * It contains benchmarked data for single feature/scenario.
 * @author mykolak
 */
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
    /**
     * Gets the feature or scenario name in the benchmark report table.
     * @return the name of the feature/scenario
     */
    public String getName() {
        return name;
    }
    /**
     * Gets the array of {@link OverviewStats} objects containing statistical information
     * about each run in benchmark. The size of list should correspond to the number of
     * runs in a benchmark.
     * @return the array with run statistics for every run from benchmark.
     */
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
