package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.KECompatibleReport;
import com.github.mkolisnyk.cucumber.reporting.types.beans.KnownErrorsDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsResultSet;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberKnownErrorsReport extends KECompatibleReport {
    public CucumberKnownErrorsReport() {
        super();
    }

    public CucumberKnownErrorsReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.KNOWN_ERRORS;
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.KNOWN_ERRORS_URL;
    }

    @Override
    public void execute(KnownErrorsModel batch, String[] formats) throws Exception {
        validateParameters();
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-known-errors.html");
        KnownErrorsDataBean data = new KnownErrorsDataBean();
        CucumberScenarioResult[] scenarios = {};
        for (CucumberFeatureResult feature : features) {
            scenarios = ArrayUtils.addAll(scenarios, feature.getElements());
        }
        KnownErrorsResultSet results = new KnownErrorsResultSet();
        results.valuate(scenarios, batch);
        data.setResults(results.getResults());
        generateReportFromTemplate(outFile, "known_errors", data);
        this.export(outFile, "known-errors", formats, this.isImageExportable());
    }

    @Deprecated
    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
    }

    @Override
    public void validateParameters() {
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_DIRECTORY, ""),
                this.getOutputDirectory());
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_NAME, ""),
                this.getOutputName());
    }

    @Override
    public void execute(KnownErrorsModel batch, boolean aggregate, String[] formats)
            throws Exception {
        execute(batch, formats);
    }
}
