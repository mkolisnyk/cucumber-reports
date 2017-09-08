package com.github.mkolisnyk.cucumber.reporting.types.beans;

public class CoverageDataBean extends CommonDataBean {

    public class FeatureStatusRow {
        private String featureName;
        private String status;
        private int covered;
        private int notCovered;
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
    public class ScenarioStatusRow extends FeatureStatusRow {
        private String scenarioName;

        public String getScenarioName() {
            return scenarioName;
        }

        public void setScenarioName(String scenarioNameValue) {
            this.scenarioName = scenarioNameValue;
        }
    }
    private FeatureStatusRow[] features;
    private ScenarioStatusRow[] scenarios;
    private int[] featureStatuses;
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
