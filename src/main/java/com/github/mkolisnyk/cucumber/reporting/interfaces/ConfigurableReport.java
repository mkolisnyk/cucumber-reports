package com.github.mkolisnyk.cucumber.reporting.interfaces;

import java.io.File;

public abstract class ConfigurableReport<Model> extends AggragatedReport {
    public abstract void execute(Model batch, boolean toPDF);
    public abstract void execute(File config, boolean toPDF);

    public void executeConsolidatedReport(Model batch) throws Exception {
        execute(batch, false);
    }
    public void executeConsolidatedReport(File config) throws Exception {
        this.execute(config, false);
    }
}
