package com.github.mkolisnyk.cucumber.runner;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import cucumber.runtime.Runtime;
import cucumber.runtime.junit.ExecutionUnitRunner;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberExamples;
import cucumber.runtime.model.CucumberScenario;

public class ExtendedExamplesRunner extends Suite {
    private static final int DEFAULT_RETRY_COUNT = 3;
    private int retryCount = DEFAULT_RETRY_COUNT;
    private Runtime runtime;

    private final CucumberExamples cucumberExamples;
    private Description description;
    private JUnitReporter jUnitReporter;
    private int exampleCount = 0;
    private static List<Runner> runners;
    private static List<CucumberScenario> exampleScenarios;

    protected ExtendedExamplesRunner(
            Runtime runtimeValue,
            CucumberExamples cucumberExamplesValue,
            JUnitReporter jUnitReporterValue) throws InitializationError {
        super(ExtendedExamplesRunner.class, buildRunners(runtimeValue, cucumberExamplesValue, jUnitReporterValue));
        this.cucumberExamples = cucumberExamplesValue;
        this.jUnitReporter = jUnitReporterValue;
        this.runtime = runtimeValue;
    }

    private static List<Runner> buildRunners(
            Runtime runtime,
            CucumberExamples cucumberExamples,
            JUnitReporter jUnitReporter) {
        runners = new ArrayList<Runner>();
        exampleScenarios = cucumberExamples.createExampleScenarios();
        for (CucumberScenario scenario : exampleScenarios) {
            try {
                ExecutionUnitRunner exampleScenarioRunner = new ExecutionUnitRunner(runtime, scenario, jUnitReporter);
                runners.add(exampleScenarioRunner);
            } catch (InitializationError initializationError) {
                initializationError.printStackTrace();
            }
        }
        return runners;
    }

    /**
     * @return the runtime
     */
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
        jUnitReporter.examples(cucumberExamples.getExamples());
        super.run(notifier);
    }

    /* (non-Javadoc)
     * @see org.junit.runners.Suite#runChild(org.junit.runner.Runner, org.junit.runner.notification.RunNotifier)
     */
    @Override
    protected void runChild(Runner runner, RunNotifier notifier) {
        ParentRunner featureElementRunner = null;
        featureElementRunner = (ExecutionUnitRunner) runner;

        try {
                featureElementRunner.run(notifier);
                Assert.assertEquals(0, this.getRuntime().exitStatus());
        } catch (AssumptionViolatedException e) {
            System.out.println("Scenario AssumptionViolatedException...");
            notifier.fireTestAssumptionFailed(new Failure(runner.getDescription(), e));
        } catch (Throwable e) {
            System.out.println("Initiating retry...");
            retry(notifier, featureElementRunner, e);
        } finally {
            System.out.println("Scenario completed..." + this.getRuntime().exitStatus());
            notifier.fireTestFinished(runner.getDescription());
        }
        exampleCount++;
   }

    public void retry(RunNotifier notifier, ParentRunner child, Throwable currentThrowable) {
        Throwable caughtThrowable = currentThrowable;
        CucumberScenario scenario = exampleScenarios.get(exampleCount);
        ParentRunner featureElementRunner = null;
        boolean failed = true;

        System.out.println("Retrying...");

        int failedAttempts = 0;
        while (retryCount > failedAttempts) {
            try {
                featureElementRunner = new ExecutionUnitRunner(runtime, scenario, jUnitReporter);
                //super.getChildren().add(exampleCount, featureElementRunner);
                //super.getChildren().get(exampleCount).run(notifier);
                featureElementRunner.run(notifier);
                Assert.assertEquals(0, this.getRuntime().exitStatus());
                failed = false;
                break;
            } catch (Throwable t) {
                failedAttempts++;
                caughtThrowable = t;
                this.getRuntime().getErrors().clear();
            }
        }
        if (failed) {
            notifier.fireTestFailure(new Failure(featureElementRunner.getDescription(), caughtThrowable));
        }
    }
}
