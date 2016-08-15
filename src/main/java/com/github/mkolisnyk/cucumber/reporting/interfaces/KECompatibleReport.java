package com.github.mkolisnyk.cucumber.reporting.interfaces;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;

import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public abstract class KECompatibleReport extends AggregatedReport {

    public KECompatibleReport() {
    }

    public KECompatibleReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }

    public abstract void execute(KnownErrorsModel batch, boolean aggregate, boolean toPDF) throws Exception;
    public void execute(KnownErrorsModel batch, boolean aggregate) throws Exception {
        execute(batch, aggregate, false);
    }
    public void execute(KnownErrorsModel batch) throws Exception {
        execute(batch, false, false);
    }
    public void execute(File config, boolean aggregate, boolean toPDF) throws Exception {
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_CONFIG_FILE, ""),
                config);
        Assert.assertTrue(
                this.constructErrorMessage(CucumberReportError.NON_EXISTING_CONFIG_FILE, ""),
                config.exists());
        String content = FileUtils.readFileToString(config);
        KnownErrorsModel model = null;
        try {
            model = (KnownErrorsModel) JsonReader.jsonToJava(content);
        } catch (Throwable e) {
            Assert.fail(this.constructErrorMessage(CucumberReportError.INVALID_CONFIG_FILE, ""));
        }
        this.execute(model, aggregate, toPDF);
    }
    public void execute(File config, boolean aggregate) throws Exception {
        execute(config, aggregate, false);
    }
    public void execute(File config) throws Exception {
        execute(config, false, false);
    }
}
