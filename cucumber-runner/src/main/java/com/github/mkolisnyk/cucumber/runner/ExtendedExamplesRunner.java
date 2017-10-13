package com.github.mkolisnyk.cucumber.runner;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import cucumber.runtime.Runtime;
import cucumber.runtime.junit.JUnitReporter;
//import cucumber.runtime.model.CucumberExamples;
//import cucumber.runtime.model.CucumberScenario;

public class ExtendedExamplesRunner /*extends Suite*/ {
/*    private int retryCount;
    private Runtime runtime;

    private final CucumberExamples cucumberExamples;
    private Description description;
    private JUnitReporter jUnitReporter;
    private static List<Runner> runners;
    private static List<CucumberScenario> exampleScenarios;

    protected ExtendedExamplesRunner(
            Runtime runtimeValue,
            CucumberExamples cucumberExamplesValue,
            JUnitReporter jUnitReporterValue,
            int retryCountValue) throws InitializationError {
        super(ExtendedExamplesRunner.class,
                buildRunners(runtimeValue, cucumberExamplesValue, jUnitReporterValue));
        this.cucumberExamples = cucumberExamplesValue;
        this.jUnitReporter = jUnitReporterValue;
        this.runtime = runtimeValue;
        this.retryCount = retryCountValue;
    }

    private static List<Runner> buildRunners(
            Runtime runtime,
            CucumberExamples cucumberExamples,
            JUnitReporter jUnitReporter) {
        runners = new ArrayList<Runner>();
        exampleScenarios = cucumberExamples.createExampleScenarios();
        for (CucumberScenario scenario : exampleScenarios) {
            try {
                ExtendedExecutionUnitRunner exampleScenarioRunner
                    = new ExtendedExecutionUnitRunner(runtime, scenario, jUnitReporter);
                runners.add(exampleScenarioRunner);
            } catch (InitializationError initializationError) {
                initializationError.printStackTrace();
            }
        }
        return runners;
    }

    public final Runtime getRuntime() {
        return runtime;
    }

    @Override
    protected String getName() {
        return cucumberExamples.getExamples().getKeyword() + ": " + cucumberExamples.getExamples().getName();
    }

    @Override
    public Description getDescription() {
        if (description == null) {
            description = Description.createSuiteDescription(getName(), cucumberExamples.getExamples());
            for (Runner child : getChildren()) {
                description.addChild(describeChild(child));
            }
        }
        return description;
    }

    @Override
    public void run(final RunNotifier notifier) {
        //jUnitReporter.examples(cucumberExamples.getExamples());
        super.run(notifier);
    }

    @Override
    protected void runChild(Runner runner, RunNotifier notifier) {
        ExtendedExecutionUnitRunner featureElementRunner = (ExtendedExecutionUnitRunner) runner;
        for (int i = 0; i <= this.retryCount; i++) {
            try {
                featureElementRunner = new ExtendedExecutionUnitRunner(
                        runtime,
                        ((ExtendedExecutionUnitRunner) runner).getCucumberScenario(),
                        jUnitReporter);
                featureElementRunner.run(notifier);
                Assert.assertEquals(0, this.getRuntime().exitStatus());
                break;
            } catch (AssumptionViolatedException e) {
                System.out.println("Scenario AssumptionViolatedException...");
            } catch (Throwable e) {
                System.out.println(
                        ((ExtendedExecutionUnitRunner) runner).getCucumberScenario().getGherkinModel().getId());
                System.out.println(e.getClass().getCanonicalName() + ":" + e.getMessage());
                System.out.println("Initiating retry...");
                this.getRuntime().getErrors().clear();
                continue;
            }
        }
        System.out.println(
                ((ExtendedExecutionUnitRunner) runner).getCucumberScenario().getGherkinModel().getId()
                + "Scenario completed..." + this.getRuntime().exitStatus());
   }*/
}
