package com.github.mkolisnyk.cucumber.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
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

public class ExtendedFeatureRunner extends FeatureRunner {
    private final List<ParentRunner> children = new ArrayList<ParentRunner>();

    private int retryCount;
    private Method[] retryMethods;
    private int failedAttempts = 0;
    private int scenarioCount = 0;
    private Runtime runtime;
    private CucumberFeature cucumberFeature;
    private JUnitReporter jUnitReporter;

    public ExtendedFeatureRunner(
            CucumberFeature cucumberFeatureValue,
            Runtime runtimeValue,
            JUnitReporter jUnitReporterValue,
            int retryCountValue,
            Method[] retryMethodsValue)
            throws InitializationError {
        super(cucumberFeatureValue, runtimeValue, jUnitReporterValue);
        this.cucumberFeature = cucumberFeatureValue;
        this.runtime = runtimeValue;
        this.jUnitReporter = jUnitReporterValue;
        this.retryCount = retryCountValue;
        this.retryMethods = retryMethodsValue;
        buildFeatureElementRunners();
    }

    private void buildFeatureElementRunners() {
        /*for (CucumberTagStatement cucumberTagStatement : cucumberFeature.getFeatureElements()) {
            try {
                ParentRunner featureElementRunner;
                if (cucumberTagStatement instanceof CucumberScenario) {
                    featureElementRunner = new ExtendedExecutionUnitRunner(
                            runtime, (CucumberScenario) cucumberTagStatement, jUnitReporter);
                } else {
                    featureElementRunner = new ExtendedScenarioOutlineRunner(
                            runtime, (CucumberScenarioOutline) cucumberTagStatement, jUnitReporter, retryCount);
                }
                children.add(featureElementRunner);
            } catch (InitializationError e) {
                throw new CucumberException("Failed to create scenario runner", e);
            }
        }*/
    }
    private boolean isRetryApplicable(Throwable e) {
        if (this.retryMethods == null || this.retryMethods.length == 0) {
            return true;
        }
        for (Method method : this.retryMethods) {
            Class<?>[] types = method.getParameterTypes();
            if (types.length != 1 || !ArrayUtils.contains(types, Throwable.class)) {
                continue;
            }
            try {
                if (!(Boolean) method.invoke(null, e)) {
                    return false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }
    /**
     * @return the runtime
     */
    public final Runtime getRuntime() {
        return runtime;
    }

    //@Override
/*    protected void runChild(ParentRunner child, RunNotifier notifier) {
        System.out.println("Running Feature child (scenario)...");
        try {
            System.out.println("Begin scenario run...");
            child.run(notifier);
            Assert.assertEquals(0, this.getRuntime().exitStatus());
        } catch (AssumptionViolatedException e) {
            System.out.println("Scenario AssumptionViolatedException...");
        } catch (Throwable e) {
            if (this.isRetryApplicable(e)) {
                System.out.println("Initiating retry...");
                retry(notifier, child, e);
            }
        } finally {
            System.out.println("Scenario completed..." + this.getRuntime().exitStatus());
        }

        this.setScenarioCount(this.getScenarioCount() + 1);
        this.setFailedAttempts(0);
    }

    private CucumberScenario getCurrentScenario() {
        CucumberTagStatement cucumberTagStatement
        = this.cucumberFeature.getFeatureElements().get(this.getScenarioCount());

        if (cucumberTagStatement instanceof CucumberScenarioOutline) {
            return null;
        }
        return (CucumberScenario) cucumberTagStatement;
    }

    public void retry(RunNotifier notifier, ParentRunner child, Throwable currentThrowable) {
        ParentRunner featureElementRunner = null;
        Class<? extends ParentRunner> clazz = child.getClass();
        System.out.println("Current class is: " + clazz.getCanonicalName());

        CucumberScenario scenario = getCurrentScenario();
        if (scenario == null) {
            return;
        }
        while (this.getRetryCount() > this.getFailedAttempts()) {
            try {
                featureElementRunner = new ExtendedExecutionUnitRunner(
                        runtime,
                        scenario,
                        jUnitReporter);
                featureElementRunner.run(notifier);
                Assert.assertEquals(0, this.getRuntime().exitStatus());
                break;
            } catch (Throwable t) {
                this.setFailedAttempts(this.getFailedAttempts() + 1);
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
    }*/
}
