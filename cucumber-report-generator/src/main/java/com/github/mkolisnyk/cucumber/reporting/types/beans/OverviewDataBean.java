package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;

public class OverviewDataBean extends CommonDataBean {

    public class FeatureStatusRow {
        private String featureName;
        private OverviewStats stats;
        private String duration;
        private String status;
        public String getFeatureName() {
            return featureName;
        }
        public void setFeatureName(String featureNameValue) {
            this.featureName = featureNameValue;
        }
        public OverviewStats getStats() {
            return stats;
        }
        public void setStats(OverviewStats statsValue) {
            this.stats = statsValue;
        }
       public String getDuration() {
            return duration;
        }
        public void setDuration(String durationValue) {
            this.duration = durationValue;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String statusValue) {
            this.status = statusValue;
        }
    }
    public class ScenarioStatusRow extends FeatureStatusRow {
        private String scenarioName;
        private int retries;

        public String getScenarioName() {
            return scenarioName;
        }

        public void setScenarioName(String scenarioNameValue) {
            this.scenarioName = scenarioNameValue;
        }
        public int getRetries() {
            return retries;
        }
        public void setRetries(int retriesValue) {
            this.retries = retriesValue;
        }
    }
    private FeatureStatusRow[] features;
    private ScenarioStatusRow[] scenarios;
    private OverviewStats overallStats;
    public FeatureStatusRow[] getFeatures() {
        return features;
    }
    public void setFeatures(FeatureStatusRow[] featuresValue) {
        this.features = featuresValue;
    }
    public ScenarioStatusRow[] getScenarios() {
        return scenarios;
    }
    public void setScenarios(ScenarioStatusRow[] scenariosValue) {
        this.scenarios = scenariosValue;
    }
    public OverviewStats getOverallStats() {
        return overallStats;
    }
    public void setOverallStats(OverviewStats overallStatsValue) {
        this.overallStats = overallStatsValue;
    }
}
