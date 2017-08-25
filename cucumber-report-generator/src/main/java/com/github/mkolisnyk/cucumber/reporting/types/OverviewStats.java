package com.github.mkolisnyk.cucumber.reporting.types;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class OverviewStats {
    private int featuresPassed;
    private int featuresFailed;
    private int featuresKnown;
    private int featuresUndefined;
    private int scenariosPassed;
    private int scenariosFailed;
    private int scenariosKnown;
    private int scenariosUndefined;
    private int stepsPassed;
    private int stepsFailed;
    private int stepsKnown;
    private int stepsUndefined;
    private float overallDuration;

    public OverviewStats() {
        reset();
    }

    private void reset() {
        featuresPassed = 0;
        featuresFailed = 0;
        featuresKnown = 0;
        featuresUndefined = 0;
        scenariosPassed = 0;
        scenariosFailed = 0;
        scenariosKnown = 0;
        scenariosUndefined = 0;
        stepsPassed = 0;
        stepsFailed = 0;
        stepsKnown = 0;
        stepsUndefined = 0;
        overallDuration = 0.f;
    }

    public final int getFeaturesPassed() {
        return featuresPassed;
    }

    public final int getFeaturesFailed() {
        return featuresFailed;
    }

    public final int getFeaturesUndefined() {
        return featuresUndefined;
    }

    public final int getFeaturesTotal() {
        return this.getFeaturesPassed() + this.getFeaturesKnown()
        + this.getFeaturesFailed() + this.getFeaturesUndefined();
    }

    public final int getScenariosPassed() {
        return scenariosPassed;
    }

    public final int getScenariosFailed() {
        return scenariosFailed;
    }

    public final int getScenariosUndefined() {
        return scenariosUndefined;
    }
    public final int getScenariosTotal() {
        return this.getScenariosPassed() + this.getScenariosKnown()
        + this.getScenariosFailed() + this.getScenariosUndefined();
    }
    public final int getStepsPassed() {
        return stepsPassed;
    }

    public final int getStepsFailed() {
        return stepsFailed;
    }

    public final int getStepsUndefined() {
        return stepsUndefined;
    }
    public final int getStepsTotal() {
        return this.getStepsPassed() + this.getStepsKnown()
        + this.getStepsFailed() + this.getStepsUndefined();
    }
    public final float getOverallDuration() {
        return overallDuration;
    }

    public int getFeaturesKnown() {
        return featuresKnown;
    }

    public int getScenariosKnown() {
        return scenariosKnown;
    }

    public int getStepsKnown() {
        return stepsKnown;
    }

    public final void addFeaturesPassed(int featuresPassedValue) {
        this.featuresPassed += featuresPassedValue;
    }

    public final void addFeaturesFailed(int featuresFailedValue) {
        this.featuresFailed += featuresFailedValue;
    }

    public final void addFeaturesUndefined(int featuresUndefinedValue) {
        this.featuresUndefined += featuresUndefinedValue;
    }

    public final void addScenariosPassed(int scenariosPassedValue) {
        this.scenariosPassed += scenariosPassedValue;
    }

    public final void addScenariosFailed(int scenariosFailedValue) {
        this.scenariosFailed += scenariosFailedValue;
    }

    public final void addScenariosUndefined(int scenariosUndefinedValue) {
        this.scenariosUndefined += scenariosUndefinedValue;
    }

    public final void addStepsPassed(int stepsPassedValue) {
        this.stepsPassed += stepsPassedValue;
    }

    public final void addStepsFailed(int stepsFailedValue) {
        this.stepsFailed += stepsFailedValue;
    }

    public final void addStepsUndefined(int stepsUndefinedValue) {
        this.stepsUndefined += stepsUndefinedValue;
    }

    public final void addOverallDuration(float overallDurationValue) {
        this.overallDuration += overallDurationValue;
    }

    public void addFeaturesKnown(int featuresKnownValue) {
        this.featuresKnown += featuresKnownValue;
    }

    public void addScenariosKnown(int scenariosKnownValue) {
        this.scenariosKnown += scenariosKnownValue;
    }

    public void addStepsKnown(int stepsKnownValue) {
        this.stepsKnown += stepsKnownValue;
    }
    public OverviewStats add(OverviewStats other) {
        this.addFeaturesFailed(other.getFeaturesFailed());
        this.addFeaturesKnown(other.getFeaturesKnown());
        this.addFeaturesPassed(other.getFeaturesPassed());
        this.addFeaturesUndefined(other.getFeaturesUndefined());
        this.addScenariosFailed(other.getScenariosFailed());
        this.addScenariosKnown(other.getScenariosKnown());
        this.addScenariosPassed(other.getScenariosPassed());
        this.addScenariosUndefined(other.getScenariosUndefined());
        this.addStepsFailed(other.getStepsFailed());
        this.addStepsKnown(other.getStepsKnown());
        this.addStepsPassed(other.getStepsPassed());
        this.addStepsUndefined(other.getStepsUndefined());
        return this;
    }
    public OverviewStats valuate(CucumberScenarioResult scenario) {
        this.reset();
        scenario.valuate();
        this.addOverallDuration((float) scenario.getDuration());
        this.addStepsPassed(scenario.getPassed());
        this.addStepsFailed(scenario.getFailed());
        this.addStepsKnown(scenario.getKnown());
        this.addStepsUndefined(scenario.getUndefined() + scenario.getSkipped());
        return this;
    }
    public OverviewStats valuate(CucumberFeatureResult result) {
        this.reset();
        result.valuate();
        this.addOverallDuration(result.getDuration());
        if (result.getStatus().equals("passed")) {
            this.addFeaturesPassed(1);
        } else if (result.getStatus().equals("failed")) {
            this.addFeaturesFailed(1);
        }  else if (result.getStatus().equals("known")) {
            this.addFeaturesKnown(1);
        } else {
            this.addFeaturesUndefined(1);
        }
        this.addScenariosPassed(result.getPassed());
        this.addScenariosFailed(result.getFailed());
        this.addScenariosKnown(result.getKnown());
        this.addScenariosUndefined(result.getUndefined() + result.getSkipped());

        for (CucumberScenarioResult scenario : result.getElements()) {
            this.addStepsPassed(scenario.getPassed());
            this.addStepsFailed(scenario.getFailed());
            this.addStepsKnown(scenario.getKnown());
            this.addStepsUndefined(scenario.getUndefined() + scenario.getSkipped());
        }
        return this;
    }
    public OverviewStats valuate(CucumberFeatureResult[] results) {
        this.reset();
        for (CucumberFeatureResult result : results) {
            result.valuate();
            this.addOverallDuration(result.getDuration());
            /*if (result.getStatus().equals("passed")) {
                this.addFeaturesPassed(1);
            } else if (result.getStatus().equals("failed")) {
                this.addFeaturesFailed(1);
            }  else if (result.getStatus().equals("known")) {
                this.addFeaturesKnown(1);
            } else {
                this.addFeaturesUndefined(1);
            }
            this.addScenariosPassed(result.getPassed());
            this.addScenariosFailed(result.getFailed());
            this.addScenariosKnown(result.getKnown());
            this.addScenariosUndefined(result.getUndefined() + result.getSkipped());*/
            OverviewStats stats = new OverviewStats();
            stats.valuate(result);
            this.add(stats);
            for (CucumberScenarioResult scenario : result.getElements()) {
                /*this.addStepsPassed(scenario.getPassed());
                this.addStepsFailed(scenario.getFailed());
                this.addStepsKnown(scenario.getKnown());
                this.addStepsUndefined(scenario.getUndefined() + scenario.getSkipped());*/
                stats.valuate(scenario);
                this.add(stats);
            }
        }
        return this;
    }
    public String getFeatureStatus() {
        if (this.getScenariosFailed() > 0) {
            return "failed";
        } else if (this.getScenariosKnown() > 0) {
            return "known";
        } else if (this.getScenariosUndefined() > 0) {
            return "undefined";
        } else {
            return "passed";
        }
    }
    public String getScenarioStatus() {
        if (this.getStepsFailed() > 0) {
            return "failed";
        } else if (this.getStepsKnown() > 0) {
            return "known";
        } else if (this.getStepsUndefined() > 0) {
            return "undefined";
        } else {
            return "passed";
        }
    }
    public boolean isEmpty() {
        return this.featuresFailed + this.featuresKnown + this.featuresPassed + this.featuresUndefined
                + this.scenariosFailed + this.scenariosKnown + this.scenariosPassed + this.scenariosUndefined
                + this.stepsFailed + this.stepsKnown + this.stepsPassed + this.stepsUndefined <= 0;
    }
}
