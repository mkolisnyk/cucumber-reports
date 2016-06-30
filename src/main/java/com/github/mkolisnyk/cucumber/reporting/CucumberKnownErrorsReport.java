package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.Assert;

import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.interfaces.ConfigurableReport;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsResult;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsResultSet;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class CucumberKnownErrorsReport extends ConfigurableReport<KnownErrorsModel> {
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
    @Deprecated
    public void executeKnownErrorsReport(KnownErrorsModel model) throws Exception {
        executeKnownErrorsReport(model, false);
    }
    @Deprecated
    public void executeKnownErrorsReport(KnownErrorsModel model, boolean toPDF) throws Exception {
        execute(model, toPDF);
    }
    @Deprecated
    public void executeKnownErrorsReport(File config) throws Exception {
        executeKnownErrorsReport(config, false);
    }
    @Deprecated
    public void executeKnownErrorsReport(File config, boolean toPDF) throws Exception {
        execute(config, toPDF);
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
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-known-errors.html");
        FileUtils.writeStringToFile(outFile, generateKnownErrorsReport(features, batch));
        if (toPDF) {
            this.exportToPDF(outFile, "known-errors");
        }
    }

    @Override
    public void execute(File config, boolean toPDF) throws Exception {
        Assert.assertTrue(config.exists(),
                this.constructErrorMessage(CucumberReportError.NON_EXISTING_CONFIG_FILE, ""));
        String content = FileUtils.readFileToString(config);
        KnownErrorsModel model = null;
        try {
            model = (KnownErrorsModel) JsonReader.jsonToJava(content);
        } catch (Throwable e) {
            Assert.fail(this.constructErrorMessage(CucumberReportError.INVALID_CONFIG_FILE, ""), e);
        }
        this.execute(model, toPDF);
    }

    @Deprecated
    @Override
    public void execute(boolean aggregate, boolean toPDF) throws Exception {
    }

    @Override
    public void validateParameters() {
    }

    @Override
    public void execute(KnownErrorsModel batch, boolean aggregate, boolean toPDF)
            throws Exception {
        // TODO Auto-generated method stub
        execute(batch, toPDF);
    }

    @Override
    public void execute(File config, boolean aggregate, boolean toPDF)
            throws Exception {
        execute(config, toPDF);
    }
}