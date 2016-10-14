package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.KECompatibleReport;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsResult;
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

    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/known-errors-report-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }

    public String generateKnownErrorsReport(CucumberFeatureResult[] features, KnownErrorsModel model) throws Exception {
        String content = this.getReportBase();
        content = content.replaceAll("__TITLE__", "Known Errors");
        String reportContent = "";
        CucumberScenarioResult[] scenarios = {};
        for (CucumberFeatureResult feature : features) {
            scenarios = ArrayUtils.addAll(scenarios, feature.getElements());
        }
        KnownErrorsResultSet results = new KnownErrorsResultSet();
        results.valuate(scenarios, model);
        for (KnownErrorsResult result : results.getResults()) {
            reportContent = reportContent.concat(
                String.format(
                        Locale.US,
                        "<tr class=\"%s\"><td><p><b>%s</b></p><p>%s</p></td><td>%s</td><td>%d</td></tr>",
                    result.getInfo().getPriority().toString().toLowerCase(),
                    result.getInfo().getTitle(),
                    result.getInfo().getDescription(),
                    result.getInfo().getPriority(),
                    result.getFrequency()
                )
            );
        }
        content = content.replaceAll("__REPORT__", reportContent);
        return content;
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
    public void execute(KnownErrorsModel batch, boolean toPDF) throws Exception {
        validateParameters();
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-known-errors.html");
        FileUtils.writeStringToFile(outFile, generateKnownErrorsReport(features, batch));
        if (toPDF) {
            this.exportToPDF(outFile, "known-errors");
        }
    }

    @Deprecated
    @Override
    public void execute(boolean aggregate, boolean toPDF) throws Exception {
    }

    @Override
    public void validateParameters() {
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_DIRECTORY, ""),
                this.getOutputDirectory());
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_NAME, ""),
                this.getOutputName());
    }

    @Override
    public void execute(KnownErrorsModel batch, boolean aggregate, boolean toPDF)
            throws Exception {
        execute(batch, toPDF);
    }
}
