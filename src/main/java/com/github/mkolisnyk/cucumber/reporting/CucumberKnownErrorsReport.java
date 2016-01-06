package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsResult;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsResultSet;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class CucumberKnownErrorsReport extends CucumberResultsCommon {
    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/known-errors-report-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }
    @Override
    public int[][] getStatuses(CucumberFeatureResult[] results) {
        // TODO Auto-generated method stub
        return null;
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
                String.format("<tr class=\"%s\"><td><p><b>%s</b></p><p>%s</p></td><td>%s</td><td>%d</td></tr>",
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

    public void executeKnownErrorsReport(KnownErrorsModel model) throws Exception {
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-known-errors.html");
        FileUtils.writeStringToFile(outFile, generateKnownErrorsReport(features, model));
    }
    public void executeKnownErrorsReport(File config) throws Exception {
        KnownErrorsModel model = (KnownErrorsModel) JsonReader.jsonToJava(
                FileUtils.readFileToString(config));
        this.executeKnownErrorsReport(model);
    }
}
