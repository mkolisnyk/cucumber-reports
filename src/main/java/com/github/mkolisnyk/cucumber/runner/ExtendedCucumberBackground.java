package com.github.mkolisnyk.cucumber.runner;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.BasicStatement;
import gherkin.formatter.model.Step;
import cucumber.runtime.Runtime;
import cucumber.runtime.model.CucumberBackground;
import cucumber.runtime.model.CucumberFeature;

public class ExtendedCucumberBackground extends CucumberBackground {
    private final CucumberFeature cucumberFeature;
    private final BasicStatement statement;
    public ExtendedCucumberBackground(CucumberFeature cucumberFeatureValue,
            Background backgroundValue) {
        super(cucumberFeatureValue, backgroundValue);
        this.cucumberFeature = cucumberFeatureValue;
        this.statement = backgroundValue;
    }
    public void format(Formatter formatter) {
        statement.replay(formatter);
        for (Step step : getSteps()) {
            formatter.step(step);
        }
    }

    public void runSteps(Reporter reporter, Runtime runtime) {
        for (Step step : getSteps()) {
            runStep(step, reporter, runtime);
        }
    }

    public void runStep(Step step, Reporter reporter, Runtime runtime) {
        runtime.runStep(cucumberFeature.getPath(), step, reporter, cucumberFeature.getI18n());
    }
}
