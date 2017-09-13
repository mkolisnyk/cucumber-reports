package com.github.mkolisnyk.cucumber.reporting.types.beans;

/**
 * Represents data structure for <a href="http://mkolisnyk.github.io/cucumber-reports/coverage-report">
 * Coverage Report</a> generation.
 * @author Mykola Kolisnyk
 */
public class CoverageDataBean extends CommonDataBean {

    /**
     * Represents data structure for Feature Status table row of the
     * <a href="http://mkolisnyk.github.io/cucumber-reports/coverage-report">
     * Coverage Report</a>.
     * @author Mykola Kolisnyk
     */
    public class FeatureStatusRow {
        /**
         * Represents the name of the feature
         */
        private String featureName;
        /**
         * Represents the status string
         */
        private String status;
        /**
         * Contains the number of scenarios/steps marked as covered.
         */
        private int covered;
        /**
         * Contains the number of scenarios/steps marked as not covered.
         */
        private int notCovered;
        /**
         * Contains the list of tags applicable for the feature/scenario.
         */
        private String[] tags;
        public String getFeatureName() {
            return featureName;
        }
        public void setFeatureName(String featureNameValue) {
            this.featureName = featureNameValue;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String statusValue) {
            this.status = statusValue;
        }
        public int getCovered() {
            return covered;
        }
        public void setCovered(int coveredValue) {
            this.covered = coveredValue;
        }
        public int getNotCovered() {
            return notCovered;
        }
        public void setNotCovered(int notCoveredValue) {
            this.notCovered = notCoveredValue;
        }
        public String[] getTags() {
            return tags;
        }
        public void setTags(String[] tagsValue) {
            this.tags = tagsValue;
        }
    }
    /**
     * Represents data structure for Scenario Status table row of the
     * <a href="http://mkolisnyk.github.io/cucumber-reports/coverage-report">
     * Coverage Report</a>. Major difference from {@link FeatureStatusRow} is
     * the scenario name.
     * @author mykolak
     */
    public class ScenarioStatusRow extends FeatureStatusRow {
        /**
         * Represents the name of the scenario.
         */
        private String scenarioName;

        public String getScenarioName() {
            return scenarioName;
        }

        public void setScenarioName(String scenarioNameValue) {
            this.scenarioName = scenarioNameValue;
        }
    }
    /**
     * The list of row data for Feature Status table.
     */
    private FeatureStatusRow[] features;
    /**
     * The list of row data for Scenario Status table.
     */
    private ScenarioStatusRow[] scenarios;
    /**
     * Array of 2 elements containing the number of covered and not covered features.
     */
    private int[] featureStatuses;
    /**
     * Array of 2 elements containing the number of covered and not covered scenarios.
     */
    private int[] scenarioStatuses;
    public FeatureStatusRow[] getFeatures() {
        return features;
    }
    public void setFeatures(FeatureStatusRow[] featuresValue) {
        this.features = featuresValue;
        featureStatuses = new int[] {0, 0};
        for (FeatureStatusRow feature : this.features) {
            if (feature.getNotCovered() > 0) {
                featureStatuses[1] = featureStatuses[1] + 1;
            } else {
                featureStatuses[0] = featureStatuses[0] + 1;
            }
        }
    }
    public ScenarioStatusRow[] getScenarios() {
        return scenarios;
    }
    public void setScenarios(ScenarioStatusRow[] scenariosValue) {
        this.scenarios = scenariosValue;
        scenarioStatuses = new int[] {0, 0};
        for (ScenarioStatusRow scenario : this.scenarios) {
            if (scenario.getNotCovered() > 0 || scenario.getCovered() == 0) {
                scenarioStatuses[1] = scenarioStatuses[1] + 1;
            } else {
                scenarioStatuses[0] = scenarioStatuses[0] + 1;
            }
        }
    }
    public int[] getFeatureStatuses() {
        return featureStatuses;
    }
    public int[] getScenarioStatuses() {
        return scenarioStatuses;
    }
}
