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
        public void setFeatureName(String featureName) {
            this.featureName = featureName;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public int getCovered() {
            return covered;
        }
        public void setCovered(int covered) {
            this.covered = covered;
        }
        public int getNotCovered() {
            return notCovered;
        }
        public void setNotCovered(int notCovered) {
            this.notCovered = notCovered;
        }
        public String[] getTags() {
            return tags;
        }
        public void setTags(String[] tags) {
            this.tags = tags;
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
