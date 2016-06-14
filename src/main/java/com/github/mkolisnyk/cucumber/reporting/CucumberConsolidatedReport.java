package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedItemInfo;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportBatch;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;

public class CucumberConsolidatedReport extends CucumberResultsCommon {
    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/consolidated-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }
    private String retrieveBody(String content) {
        return content.split("<body>")[1].split("</body>")[0];
    }
    private String amendHtmlHeaders(String content) {
        final int totalHeadingTypes = 6;
        for (int i = totalHeadingTypes; i > 0; i--) {
            content = content.replaceAll("<h" + i + ">", "<h" + (i + 1) + ">");
            content = content.replaceAll("</h" + i + ">", "</h" + (i + 1) + ">");
        }
        return content;
    }
    private String generateLocalLink(String title) {
        String result = title.toLowerCase();
        return result.replaceAll("[^a-z0-9]", "-");
    }
    private String generateTableOfContents(ConsolidatedReportModel model) throws Exception {
        String contents = "<ol>";
        for (ConsolidatedItemInfo item : model.getItems()) {
            contents = contents.concat(
                String.format(Locale.US, "<li><a href=\"#%s\">%s</a></li>",
                        generateLocalLink(item.getTitle()), item.getTitle()));
        }
        contents += "</ol>";
        return contents;
    }
    private String generateConsolidatedReport(ConsolidatedReportModel model) throws Exception {
        String result = getReportBase();
        result = result.replaceAll("__TITLE__", model.getTitle());
        result = result.replaceAll("__REFRESH__", "");
        String reportContent = "";
        if (model.isUseTableOfContents()) {
            reportContent = reportContent.concat(
                    String.format(Locale.US, "<h1>Table of Contents</h1>%s", generateTableOfContents(model)));
        }
        for (ConsolidatedItemInfo item : model.getItems()) {
            String content = FileUtils.readFileToString(new File(item.getPath()));
            content = this.amendHtmlHeaders(content);
            content = this.retrieveBody(content);
            reportContent = reportContent.concat(
                String.format(Locale.US, "<div class=\"content\"><a id=\"%s\"><h1>%s</h1></a>%s</div>",
                    generateLocalLink(item.getTitle()), item.getTitle(), content));
        }
        reportContent = this.replaceHtmlEntitiesWithCodes(reportContent);
        reportContent = reportContent.replaceAll("[$]", "&#36;");
        result = result.replaceAll("__REPORT__", reportContent);
        return result;
    }

    public void executeConsolidatedReport(ConsolidatedReportModel model, boolean toPDF) throws Exception {
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + model.getReportSuffix() + ".html");
        FileUtils.writeStringToFile(outFile, generateConsolidatedReport(model));
        if (toPDF) {
            this.exportToPDF(outFile, model.getReportSuffix());
        }
    }
    public void executeConsolidatedReport(ConsolidatedReportModel model) throws Exception {
        executeConsolidatedReport(model, false);
    }
    public void executeConsolidatedReport(ConsolidatedReportBatch batch, boolean toPDF) throws Exception {
        for (ConsolidatedReportModel model : batch.getModels()) {
            executeConsolidatedReport(model, toPDF);
        }
    }
    public void executeConsolidatedReport(ConsolidatedReportBatch batch) throws Exception {
        executeConsolidatedReport(batch, false);
    }
    public void executeConsolidatedReport(File config, boolean toPDF) throws Exception {
        ConsolidatedReportBatch model = (ConsolidatedReportBatch) JsonReader.jsonToJava(
                FileUtils.readFileToString(config));
        this.executeConsolidatedReport(model, toPDF);
    }
    public void executeConsolidatedReport(File config) throws Exception {
        this.executeConsolidatedReport(config, false);
    }
    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.CONSOLIDATED_REPORT;
    }
    @Override
    public void validateParameters() {
        // TODO Auto-generated method stub
        
    }
}
