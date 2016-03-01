package com.github.mkolisnyk.cucumber.runner.parallel;

import java.util.Date;

import org.junit.Assert;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;

import com.github.mkolisnyk.cucumber.runner.ExtendedFeatureRunner;

public class FeatureRunnerThread implements Runnable {
    private ExtendedFeatureRunner runner;
    private ParentRunner child;
    private RunNotifier notifier;
    private long runId = 0;

    public FeatureRunnerThread(ExtendedFeatureRunner runnerValue,
            ParentRunner childValue, RunNotifier notifierValue) {
        super();
        this.runner = runnerValue;
        this.child = childValue;
        this.notifier = notifierValue;
        this.runId = (new Date()).getTime();
    }

    @Override
    public void run() {
        String prefix = "[thread" + this.runId + "] ";
        System.out.println(prefix + "Running Feature child (scenario)...");
        try {
            System.out.println(prefix + "Begin scenario run...");
            child.run(notifier);
            Assert.assertEquals(0, runner.getRuntime().exitStatus());
        } catch (AssumptionViolatedException e) {
            System.out.println(prefix + "Scenario AssumptionViolatedException...");
        } catch (Throwable e) {
            System.out.println(prefix + "Initiating retry...");
            e.printStackTrace();
            runner.retry(notifier, child, e);
        } finally {
            System.out.println(prefix + "Scenario completed..." + runner.getRuntime().exitStatus());
        }
    }

}
