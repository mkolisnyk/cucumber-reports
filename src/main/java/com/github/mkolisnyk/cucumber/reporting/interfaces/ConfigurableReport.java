package com.github.mkolisnyk.cucumber.reporting.interfaces;

import java.io.File;

import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public abstract class ConfigurableReport<Model> extends AggregatedReport {
    public ConfigurableReport() {
        super();
    }
    public ConfigurableReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }
    public abstract void execute(Model batch, boolean aggregate, boolean toPDF) throws Exception;
    public abstract void execute(File config, boolean aggregate, boolean toPDF) throws Exception;
    public void execute(Model batch, boolean toPDF) throws Exception {
        execute(batch, false, toPDF);
    }
    public void execute(File config, boolean toPDF) throws Exception {
        execute(config, false, toPDF);
    }
    public void executeConsolidatedReport(Model batch) throws Exception {
        execute(batch, false);
    }
    public void executeConsolidatedReport(File config) throws Exception {
        this.execute(config, false);
    }
}
