package com.github.mkolisnyk.cucumber.reporting.interfaces;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;

import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public abstract class ConfigurableReport<Model> extends AggregatedReport {
    public ConfigurableReport() {
        super();
    }
    public ConfigurableReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }
    public abstract void execute(Model batch, boolean aggregate, String[] formats) throws Exception;
    public abstract void execute(File config, boolean aggregate, String[] formats) throws Exception;

    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
    }
    @Override
    public void execute(String[] formats) throws Exception {
    }
    public void execute(Model batch, String[] formats) throws Exception {
        execute(batch, false, formats);
    }
    @SuppressWarnings("unchecked")
    public void execute(File config, String[] formats) throws Exception {
        Assert.assertTrue(this.constructErrorMessage(CucumberReportError.NON_EXISTING_CONFIG_FILE, ""),
                config.exists());
        String content = FileUtils.readFileToString(config);
        Model model = null;
        try {
            model = (Model) JsonReader.jsonToJava(content);
            Assert.assertNotEquals("java.lang.Object[]", model.getClass().getCanonicalName());
        } catch (Throwable e) {
            Assert.fail(this.constructErrorMessage(CucumberReportError.INVALID_CONFIG_FILE, ""));
        }
        this.execute(model, formats);
    }
}
