package com.github.mkolisnyk.cucumber.runner.parallel;

import org.junit.Assert;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;

import com.github.mkolisnyk.cucumber.runner.ExtendedFeatureRunner;

public class FeatureRunnerThread implements Runnable {
    private ExtendedFeatureRunner runner;
    private ParentRunner child;
    private RunNotifier notifier;

    public FeatureRunnerThread(ExtendedFeatureRunner runnerValue,
            ParentRunner childValue, RunNotifier notifierValue) {
        super();
        this.runner = runnerValue;
        this.child = childValue;
        this.notifier = notifierValue;
    }

    @Override
    public void run() {
        System.out.println("Running Feature child (scenario)...");
        try {
            System.out.println("Begin scenario run...");
            child.run(notifier);
            Assert.assertEquals(0, runner.getRuntime().exitStatus());
        } catch (AssumptionViolatedException e) {
            System.out.println("Scenario AssumptionViolatedException...");
        } catch (Throwable e) {
            System.out.println("Initiating retry...");
            runner.retry(notifier, child, e);
        } finally {
            System.out.println("Scenario completed..." + runner.getRuntime().exitStatus());
        }
    }

}
