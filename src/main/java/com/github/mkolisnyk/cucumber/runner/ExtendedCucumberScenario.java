package com.github.mkolisnyk.cucumber.runner;

import java.util.Set;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.BasicStatement;
import gherkin.formatter.model.Row;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;
import cucumber.runtime.Runtime;
import cucumber.runtime.model.CucumberBackground;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.CucumberScenario;

public class ExtendedCucumberScenario extends CucumberScenario {

    private final ExtendedCucumberBackground cucumberBackground;
    private final Scenario scenario;
    private final CucumberFeature cucumberFeature;
    private final BasicStatement statement;

    public ExtendedCucumberScenario(CucumberFeature cucumberFeatureValue,
            ExtendedCucumberBackground cucumberBackgroundValue, Scenario scenarioValue) {
        super(cucumberFeatureValue, cucumberBackgroundValue, scenarioValue);
        this.cucumberFeature = cucumberFeatureValue;
        this.cucumberBackground = cucumberBackgroundValue;
        this.scenario = scenarioValue;
        this.statement = scenarioValue;
    }

    public ExtendedCucumberScenario(CucumberFeature cucumberFeatureValue,
            ExtendedCucumberBackground cucumberBackgroundValue, Scenario exampleScenario,
            Row example) {
        super(cucumberFeatureValue, cucumberBackgroundValue, exampleScenario, example);
        this.cucumberFeature = cucumberFeatureValue;
        this.cucumberBackground = cucumberBackgroundValue;
        this.scenario = exampleScenario;
        this.statement = exampleScenario;
    }

    @Override
    public void run(Formatter formatter, Reporter reporter, Runtime runtime) {
        Set<Tag> tags = tagsAndInheritedTags();
        runtime.buildBackendWorlds(reporter, tags, scenario);
        try {
            formatter.startOfScenarioLifeCycle((Scenario) getGherkinModel());
        } catch (Throwable ignore) {
            // IntelliJ has its own formatter which doesn't yet implement this.
        }
        runtime.runBeforeHooks(reporter, tags);

        runBackground(formatter, reporter, runtime);
        format(formatter);
        runSteps(reporter, runtime);

        runtime.runAfterHooks(reporter, tags);
        try {
            formatter.endOfScenarioLifeCycle((Scenario) getGherkinModel());
        } catch (Throwable ignore) {
            // IntelliJ has its own formatter which doesn't yet implement this.
        }
        runtime.disposeBackendWorlds();
    }
/*
    private String createScenarioDesignation() {
        return cucumberFeature.getPath() + ":" + Integer.toString(scenario.getLine()) + " # " +
                scenario.getKeyword() + ": " + scenario.getName();
    }*/

    private void runBackground(Formatter formatter, Reporter reporter, Runtime runtime) {
        if (cucumberBackground != null) {
            cucumberBackground.format(formatter);
            cucumberBackground.runSteps(reporter, runtime);
        }
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
