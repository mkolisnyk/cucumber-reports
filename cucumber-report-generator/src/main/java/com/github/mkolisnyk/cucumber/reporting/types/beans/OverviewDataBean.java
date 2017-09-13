package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;

/**
 * Data structure for the <a href="http://mkolisnyk.github.io/cucumber-reports/overview-report">
 * Results Overview Report</a> generation.
 * @author Mykola Kolisnyk
 */
public class OverviewDataBean extends CommonDataBean {

    /**
     * Represents data structure for Feature Status table row of the
     * <a href="http://mkolisnyk.github.io/cucumber-reports/overview-report">
     * Results Overview Report</a>.
     * @author Mykola Kolisnyk
     */
    public class FeatureStatusRow {
        /**
         * The name of the feature in the row.
         */
        private String featureName;
        /**
         * Overall resutls statistics for current feature/scenario
         */
        private OverviewStats stats;
        /**
         * The string containing formatted duration for scenario/feature.
         */
        private String duration;
        /**
         * String containing feature/scenario status, e.g. "passed", "failed", "undefined", "known".
         */
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
    /**
     * Represents data structure for Scenario Status table row of the
     * <a href="http://mkolisnyk.github.io/cucumber-reports/overview-report">
     * Results Overview Report</a>.
     * @author Mykola Kolisnyk
     */
    public class ScenarioStatusRow extends FeatureStatusRow {
        /**
         * The name of the scenario.
         */
        private String scenarioName;
        /**
         * The number of failed test run retries.
         */
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
    /**
     * The list of run results for each specific feature.
     */
    private FeatureStatusRow[] features;
    /**
     * The list of run results for each specific scenario.
     */
    private ScenarioStatusRow[] scenarios;
    /**
     * The overall run statistics. Mainly used for the summary table.
     */
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
