package com.github.mkolisnyk.cucumber.runner;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import cucumber.runtime.CucumberException;
import cucumber.runtime.Runtime;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.CucumberScenario;
import cucumber.runtime.model.CucumberScenarioOutline;
import cucumber.runtime.model.CucumberTagStatement;

public class ExtendedFeatureRunner extends FeatureRunner {
    private final List<ParentRunner> children = new ArrayList<ParentRunner>();

    private int retryCount;
    private int failedAttempts = 0;
    private int scenarioCount = 0;
    private Runtime runtime;
    private CucumberFeature cucumberFeature;
    private JUnitReporter jUnitReporter;

    public ExtendedFeatureRunner(
            CucumberFeature cucumberFeatureValue,
            Runtime runtimeValue,
            JUnitReporter jUnitReporterValue,
            int retryCountValue)
            throws InitializationError {
        super(cucumberFeatureValue, runtimeValue, jUnitReporterValue);
        this.cucumberFeature = cucumberFeatureValue;
        this.runtime = runtimeValue;
        this.jUnitReporter = jUnitReporterValue;
        this.retryCount = retryCountValue;
        buildFeatureElementRunners();
    }

    private void buildFeatureElementRunners() {
        for (CucumberTagStatement cucumberTagStatement : cucumberFeature.getFeatureElements()) {
            try {
                ParentRunner featureElementRunner;
                if (cucumberTagStatement instanceof CucumberScenario) {
                    featureElementRunner = new ExtendedExecutionUnitRunner(
                            runtime, (CucumberScenario) cucumberTagStatement, jUnitReporter/*, retryCount*/);
                } else {
                    featureElementRunner = new ExtendedScenarioOutlineRunner(
                            runtime, (CucumberScenarioOutline) cucumberTagStatement, jUnitReporter, retryCount);
                }
                children.add(featureElementRunner);
            } catch (InitializationError e) {
                throw new CucumberException("Failed to create scenario runner", e);
            }
        }
    }
    /**
     * @return the runtime
     */
    public final Runtime getRuntime() {
        return runtime;
    }

    @Override
    protected void runChild(ParentRunner child, RunNotifier notifier) {
        System.out.println("Running Feature child (scenario)...");
        //notifier.fireTestStarted(child.getDescription());
        try {
            System.out.println("Begin scenario run...");
            child.run(notifier);
            Assert.assertEquals(0, this.getRuntime().exitStatus());
        } catch (AssumptionViolatedException e) {
            System.out.println("Scenario AssumptionViolatedException...");
            //notifier.fireTestAssumptionFailed(new Failure(child.getDescription(), e));
        } catch (Throwable e) {
            System.out.println("Initiating retry...");
            retry(notifier, child, e);
        } finally {
            System.out.println("Scenario completed..." + this.getRuntime().exitStatus());
            //notifier.fireTestFinished(child.getDescription());
        }
        scenarioCount++;
        failedAttempts = 0;
    }

    public void retry(RunNotifier notifier, ParentRunner child, Throwable currentThrowable) {
        //Throwable caughtThrowable = currentThrowable;
        ParentRunner featureElementRunner = null;
        //boolean failed = true;
        Class<? extends ParentRunner> clazz = child.getClass();
        System.out.println("Current class is: " + clazz.getCanonicalName());
        CucumberTagStatement cucumberTagStatement = this.cucumberFeature.getFeatureElements().get(scenarioCount);

        if (cucumberTagStatement instanceof CucumberScenarioOutline) {
            return;
        }
        while (retryCount > failedAttempts) {
            try {
                featureElementRunner = new ExtendedExecutionUnitRunner(
                        runtime,
                        (CucumberScenario) cucumberTagStatement,
                        jUnitReporter/*,
                        retryCount*/);
                featureElementRunner.run(notifier);
                Assert.assertEquals(0, this.getRuntime().exitStatus());
                //failed = false;
                break;
            } catch (Throwable t) {
                failedAttempts++;
                //caughtThrowable = t;
                this.getRuntime().getErrors().clear();
            }
        }
    }

    @Override
    protected List<ParentRunner> getChildren() {
        return children;
    }

    @Override
    protected Description describeChild(ParentRunner child) {
        return child.getDescription();
    }
}
