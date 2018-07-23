package com.github.mkolisnyk.cucumber.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import cucumber.runtime.Runtime;
import cucumber.runtime.junit.JUnitReporter;
//import cucumber.runtime.model.CucumberExamples;
//import cucumber.runtime.model.CucumberScenario;

public class ExtendedExamplesRunner /*extends Suite*/ {
    private int retryCount;
    private Runtime runtime;
    private Method[] retryMethods;
    private int failedAttempts = 0;
    private int scenarioCount = 0;

//    private final CucumberExamples cucumberExamples;
    private Description description;
    private JUnitReporter jUnitReporter;
    private static List<Runner> runners;
//    private static List<CucumberScenario> exampleScenarios;
/*
    protected ExtendedExamplesRunner(
            Runtime runtimeValue,
//            CucumberExamples cucumberExamplesValue,
            JUnitReporter jUnitReporterValue,
            int retryCountValue,
            Method[] retryMethodsValue) throws InitializationError {
//        super(ExtendedExamplesRunner.class,
//                buildRunners(runtimeValue, cucumberExamplesValue, jUnitReporterValue));
//        this.cucumberExamples = cucumberExamplesValue;
        this.jUnitReporter = jUnitReporterValue;
        this.runtime = runtimeValue;
        this.retryCount = retryCountValue;
        this.retryMethods = retryMethodsValue;
    }

    private static List<Runner> buildRunners(
            Runtime runtime,
//            CucumberExamples cucumberExamples,
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
*/
    /**
     * @return the runtime
     */
    public final Runtime getRuntime() {
        return runtime;
    }
/*
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
*/
    /* (non-Javadoc)
     * @see org.junit.runners.Suite#runChild(org.junit.runner.Runner, org.junit.runner.notification.RunNotifier)
     */
/*    @Override
    protected void runChild(Runner runner, RunNotifier notifier) {
        ExtendedExecutionUnitRunner featureElementRunner = (ExtendedExecutionUnitRunner) runner;
        try {
            featureElementRunner = new ExtendedExecutionUnitRunner(
                    runtime,
                    ((ExtendedExecutionUnitRunner) runner).getCucumberScenario(),
                    jUnitReporter);
            featureElementRunner.run(notifier);
            Assert.assertEquals(0, this.getRuntime().exitStatus());
        } catch (AssumptionViolatedException e) {
            System.out.println("Scenario AssumptionViolatedException...");
        } catch (Throwable e) {
            System.out.println(
                    ((ExtendedExecutionUnitRunner) runner).getCucumberScenario().getGherkinModel().getId());
            System.out.println(e.getClass().getCanonicalName() + ":" + e.getMessage());
            List<Throwable> errors = this.getRuntime().getErrors();
            Throwable error = errors.get(errors.size() - 1);
            if (ExtendedCucumber.isRetryApplicable(error, this.retryMethods)) {
                System.out.println("===Initiating retry...");
                retry(notifier, featureElementRunner, e);
            }
        } finally {
            System.out.println(((ExtendedExecutionUnitRunner) runner).getCucumberScenario().getGherkinModel().getId()
            + "Scenario completed..." + this.getRuntime().exitStatus());
        }

        this.setScenarioCount(this.getScenarioCount() + 1);
        this.setFailedAttempts(0);
   }

   private CucumberScenario getCurrentScenario(Runner runner) {
        return ((ExtendedExecutionUnitRunner) runner).getCucumberScenario();
    }

    public void retry(RunNotifier notifier, ParentRunner child, Throwable currentThrowable) {
        ParentRunner featureElementRunner = null;
        Class<? extends ParentRunner> clazz = child.getClass();
        System.out.println("Current class is: " + clazz.getCanonicalName());

        CucumberScenario scenario = getCurrentScenario(child);
        if (scenario == null) {
            return;
        }
        runtime.getErrors().clear();
        while (this.getRetryCount() > this.getFailedAttempts()) {
            try {
                featureElementRunner = new ExtendedExecutionUnitRunner(
                        runtime,
                        scenario,
                        jUnitReporter);
                featureElementRunner.run(notifier);
                Assert.assertEquals(0, runtime.exitStatus());
                runtime.getErrors().clear();
                return;
            } catch (Throwable t) {
                this.setFailedAttempts(this.getFailedAttempts() + 1);
                runtime.getErrors().clear();
            }
        }
    }
*/
    public int getRetryCount() {
        return retryCount;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttemptsValue) {
        this.failedAttempts = failedAttemptsValue;
    }

    public int getScenarioCount() {
        return scenarioCount;
    }

    public void setScenarioCount(int scenarioCountValue) {
        this.scenarioCount = scenarioCountValue;
    }
}
