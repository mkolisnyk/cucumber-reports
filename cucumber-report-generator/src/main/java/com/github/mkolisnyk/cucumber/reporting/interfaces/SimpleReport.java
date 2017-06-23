package com.github.mkolisnyk.cucumber.reporting.interfaces;

import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public abstract class SimpleReport extends CucumberResultsCommon {
    public SimpleReport() {
        super();
    }
    public SimpleReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }
    public abstract void execute() throws Exception;
    public abstract void execute(String[] formats) throws Exception;
}
