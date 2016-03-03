package com.github.mkolisnyk.cucumber.runner.parallel;

import java.util.Date;

import org.junit.runner.notification.RunNotifier;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;

public class CucumberRunnerThread implements Runnable {
    private ExtendedCucumber runner;
    private RunNotifier notifier;
    private long runId = 0;

    public CucumberRunnerThread(ExtendedCucumber runnerValue, RunNotifier notifierValue) {
        super();
        this.runner = runnerValue;
        this.notifier = notifierValue;
        this.runId = (new Date()).getTime();
    }

    @Override
    public void run() {
        //String prefix = "[thread" + this.runId + "] ";
        runner.run(notifier);
    }

}
