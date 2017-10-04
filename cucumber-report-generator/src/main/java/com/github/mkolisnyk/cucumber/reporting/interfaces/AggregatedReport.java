package com.github.mkolisnyk.cucumber.reporting.interfaces;

import org.apache.commons.lang.ArrayUtils;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public abstract class AggregatedReport extends SimpleReport {
    public AggregatedReport() {
        super();
    }
    public AggregatedReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }
    public abstract void execute(boolean aggregate, String[] formats) throws Exception;
    public void execute(boolean aggregate) throws Exception {
        execute(aggregate, new String[] {});
    }
    @Override
    public void execute() throws Exception {
        execute(false, new String[] {});
    }
    public abstract void execute(boolean aggregate, CucumberFeatureResult[] results, String[] formats) throws Exception;
    public void execute(CucumberFeatureResult[] results) throws Exception {
        execute(false, results, new String[] {});
    }
    public void execute(boolean aggregate, CucumberFeatureResult[] results) throws Exception {
        execute(aggregate, results, new String[] {});
    }

    public CucumberFeatureResult[] aggregateResults(CucumberFeatureResult[] input, boolean collapse) {
        for (int i = 0; i < input.length; i++) {
            input[i].setId("" + input[i].getLine() + "" + input[i].getId());
            input[i].aggregateScenarioResults(collapse);
        }
        return input;
    }
    public CucumberFeatureResult[] readFileContent(String sourceFileValue, boolean aggregate) throws Exception {
        CucumberFeatureResult[] sources = readFileContent(sourceFileValue);
        sources = aggregateResults(sources, aggregate);
        return sources;
    }

    public CucumberFeatureResult[] readFileContent(boolean aggregate) throws Exception {
        return readFileContent(this.getSourceFiles(), aggregate);
    }

    private CucumberFeatureResult[] readFileContent(String[] sourceFilesValue, boolean aggregate) throws Exception {
        CucumberFeatureResult[] output = {};
        for (String sourceFile : sourceFilesValue) {
            output = (CucumberFeatureResult[]) ArrayUtils.addAll(output, readFileContent(sourceFile, aggregate));
        }
        return output;
    }
}
