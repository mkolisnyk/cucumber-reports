package com.github.mkolisnyk.cucumber.reporting.interfaces;

public abstract class SimpleReport extends CucumberResultsCommon {
    public abstract void execute(boolean toPDF) throws Exception;
    public void execute() throws Exception {
        execute(false);
    }
}
