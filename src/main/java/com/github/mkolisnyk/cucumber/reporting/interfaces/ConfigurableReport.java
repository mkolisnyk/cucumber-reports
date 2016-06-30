package com.github.mkolisnyk.cucumber.reporting.interfaces;

import java.io.File;

public abstract class ConfigurableReport<Model> extends AggragatedReport {
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
