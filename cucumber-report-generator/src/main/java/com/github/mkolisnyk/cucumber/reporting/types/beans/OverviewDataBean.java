package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;

public class OverviewDataBean extends CommonDataBean {

    public class FeatureStatusRow {
        private String featureName;
        private OverviewStats stats;
        private int retries;
        private String duration;
        public String getFeatureName() {
            return featureName;
        }
        public void setFeatureName(String featureName) {
            this.featureName = featureName;
        }
        public OverviewStats getStats() {
            return stats;
        }
        public void setStats(OverviewStats stats) {
            this.stats = stats;
        }
        public int getRetries() {
            return retries;
        }
        public void setRetries(int retries) {
            this.retries = retries;
        }
        public String getDuration() {
            return duration;
        }
        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
    public class ScenarioStatusRow extends FeatureStatusRow {
        private String scenarioName;

        public String getScenarioName() {
            return scenarioName;
        }

        public void setScenarioName(String scenarioName) {
            this.scenarioName = scenarioName;
        }
    }
    private FeatureStatusRow[] features;
    private ScenarioStatusRow[] scenarios;
    public FeatureStatusRow[] getFeatures() {
        return features;
    }
    public void setFeatures(FeatureStatusRow[] features) {
        this.features = features;
    }
    public ScenarioStatusRow[] getScenarios() {
        return scenarios;
    }
    public void setScenarios(ScenarioStatusRow[] scenarios) {
        this.scenarios = scenarios;
    }
}
