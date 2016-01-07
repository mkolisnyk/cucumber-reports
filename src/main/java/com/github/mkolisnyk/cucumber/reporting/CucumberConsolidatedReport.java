package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedItemInfo;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportBatch;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;

public class CucumberConsolidatedReport extends CucumberResultsCommon {
    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/consolidated-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }
    @Override
    public int[][] getStatuses(CucumberFeatureResult[] results) {
        // TODO Auto-generated method stub
        return null;
    }

    private String amendHtmlHeaders(String content) {
        for (int i = 6; i > 0; i--) {
            content = content.replaceAll("<h" + i + ">", "<h" + (i + 1) + ">");
            content = content.replaceAll("</h" + i + ">", "</h" + (i + 1) + ">");
        }
        return content;
    }

    private String generateConsolidatedReport(ConsolidatedReportModel model) throws Exception {
        String result = getReportBase();
        result = result.replaceAll("__TITLE__", model.getTitle());
        String reportContent = "";
        for (ConsolidatedItemInfo item : model.getItems()) {
            String content = FileUtils.readFileToString(new File(item.getPath()));
            content = this.amendHtmlHeaders(content);
            reportContent = reportContent.concat(String.format("<h1>%s</h1>%s", item.getTitle(), content));
        }
        result = result.replaceAll("__REPORT__", reportContent);
        return result;
    }

    public void executeConsolidatedReport(ConsolidatedReportModel model) throws Exception {
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + model.getReportSuffix() + ".html");
        FileUtils.writeStringToFile(outFile, generateConsolidatedReport(model));
    }
    public void executeConsolidatedReport(ConsolidatedReportBatch batch) throws Exception {
        for (ConsolidatedReportModel model : batch.getModels()) {
            executeConsolidatedReport(model);
        }
    }
    public void executeConsolidatedReport(File config) throws Exception {
        ConsolidatedReportBatch model = (ConsolidatedReportBatch) JsonReader.jsonToJava(
                FileUtils.readFileToString(config));
        this.executeConsolidatedReport(model);
    }
}
