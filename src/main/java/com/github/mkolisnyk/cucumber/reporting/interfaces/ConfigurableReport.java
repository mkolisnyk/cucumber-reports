package com.github.mkolisnyk.cucumber.reporting.interfaces;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;

import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
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
    @SuppressWarnings("unchecked")
    public void execute(File config, boolean toPDF) throws Exception {
        Assert.assertTrue(this.constructErrorMessage(CucumberReportError.NON_EXISTING_CONFIG_FILE, ""),
                config.exists());
        String content = FileUtils.readFileToString(config);
        Model model = null;
        try {
            model = (Model) JsonReader.jsonToJava(content);
            Assert.assertNotEquals(Object[].class, model.getClass().getCanonicalName());
        } catch (Throwable e) {
            Assert.fail(this.constructErrorMessage(CucumberReportError.INVALID_CONFIG_FILE, ""));
        }
        this.execute(model, toPDF);
    }
}
