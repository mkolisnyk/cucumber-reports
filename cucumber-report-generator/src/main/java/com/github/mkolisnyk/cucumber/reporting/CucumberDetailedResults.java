package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.github.mkolisnyk.cucumber.reporting.interfaces.KECompatibleReport;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberBeforeAfterResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberEmbedding;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberStepResult;
import com.github.mkolisnyk.cucumber.reporting.utils.helpers.StringConversionUtils;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

/**
 * @author Myk Kolisnyk
 */
public class CucumberDetailedResults extends KECompatibleReport {
    public CucumberDetailedResults() {
        super();
    }

    public CucumberDetailedResults(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
        this.setScreenShotLocation(extendedOptions.getScreenShotLocation());
        this.setScreenShotWidth(extendedOptions.getScreenShotSize());
    }

    private String screenShotLocation;
    private String screenShotWidth;

    /**
     * @return the screenShotLocation
     */
    public final String getScreenShotLocation() {
        return screenShotLocation;
    }

    /**
     * @param screenShotLocationValue the screenShotLocation to set
     */
    public final void setScreenShotLocation(String screenShotLocationValue) {
        this.screenShotLocation = screenShotLocationValue;
    }

    /**
     * @return the screenShotWidth
     */
    public final String getScreenShotWidth() {
        return screenShotWidth;
    }

    /**
     * @param screenShotWidthValue the screenShotWidth to set
     */
    public final void setScreenShotWidth(String screenShotWidthValue) {
        this.screenShotWidth = screenShotWidthValue;
    }

    private String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/results-report-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }

    private String escapeHtml(String input) {
        return StringEscapeUtils.escapeHtml(input);
    }
    public String generateNameFromId(String scId) {
        if (scId == null) {
            scId = "null";
        }
        String result = scId.replaceAll("[^A-Za-z0-9]", "_");
        return result;
    }
    private String generateTableOfContents(CucumberFeatureResult[] results) {
        String reportContent = "";
        reportContent += "<a id=\"top\"></a><h1>Table of Contents</h1><ol>";
        for (CucumberFeatureResult result : results) {
            reportContent += String.format(
                    Locale.US,
                    "<li> <span class=\"%s\"><a href=\"#feature-%s\">%s</a></span><ol>",
                    result.getStatus(),
                    escapeHtml(result.getId()),
                    escapeHtml(result.getName()));
            for (CucumberScenarioResult scenario : result.getElements()) {
                if (scenario.getKeyword().contains("Scenario")) {
                    reportContent += String.format(
                            Locale.US,
                            "<li> <span class=\"%s\"><a href=\"#sc-%s\">%s</a></span></li>",
                            scenario.getStatus(),
                            escapeHtml(scenario.getId()),
                            escapeHtml(scenario.getName()));
                }
            }
            reportContent += "</ol></li>";
        }
        reportContent += "</ol>";
        return reportContent;
    }
    private String generateStepRows(CucumberStepResult step) {
        String reportContent = "";
        if (step.getRows() != null) {
            reportContent += String.format(
                    Locale.US,
                    "<tr class=\"%s\"><td style=\"padding-left:20px\" colspan=\"2\"><table>",
                    step.getResult().getStatus());
            for (int i = 0; i < step.getRows().length; i++) {
                reportContent += "<tr>";
                for (int j = 0; j < step.getRows()[i].length; j++) {
                    reportContent += String.format(Locale.US,
                            "<td>%s</td>", escapeHtml(step.getRows()[i][j]));
                }
                reportContent += "</tr>";
            }
            reportContent += "</table></td></tr>";
        }
        return reportContent;
    }
    private String generateDocString(CucumberStepResult step) {
        String reportContent = "";
        if (StringUtils.isNotBlank(step.getDocString())) {
            reportContent += String.format(
                    Locale.US,
                    "<tr class=\"%s_description\"><td style=\"padding-left:20px\" colspan=\"2\">",
                    step.getResult().getStatus());
            reportContent += String.format(
                    Locale.US,
                    "<br>%s</br>",
                    escapeHtml(step.getDocString()).replaceAll("\n", "</br><br>"));
            reportContent += "</td></tr>";
        }
        return reportContent;
    }
    private String getExtensionFromMime(String mime) {
        if (mime.contains("png")) {
            return "png";
        }
        if (mime.contains("jpg") || mime.contains("jpeg")) {
            return "jpg";
        }
        return "txt";
    }
    private String embeddingScreenShots(CucumberScenarioResult scenario, CucumberStepResult step) throws IOException {
        String reportContent = "";
        String scenarioId = scenario.getId();
        if (StringUtils.isBlank(scenarioId)) {
            scenarioId = "background";
            // Add more precise background generation
        }
        if (step.getEmbeddings() != null) {
            int index = 0;
            long base = new Date().getTime();
            for (CucumberEmbedding embedding : step.getEmbeddings()) {
                String embedPath = this.getScreenShotLocation()
                        + this.generateNameFromId(scenarioId) + (base + index) + "."
                        + getExtensionFromMime(embedding.getMimeType());
                File embedShot = new File(this.getOutputDirectory() + embedPath);
                FileUtils.writeByteArrayToFile(embedShot, embedding.getData());
                String widthString = "";
                if (StringUtils.isNotBlank(this.getScreenShotWidth())) {
                    widthString = String.format(Locale.US, "width=\"%s\"", this.getScreenShotWidth());
                }
                reportContent += String.format(Locale.US,
                        "<tr class=\"%s\"><td colspan=\"2\"><img src=\"%s\" %s /></td></tr>",
                        step.getResult().getStatus(),
                        embedPath,
                        widthString
                );
                index++;
            }
        }
        return reportContent;
    }
    private String embeddingOutput(CucumberStepResult step) {
        String reportContent = "";
        if (step.getOutput() != null) {
            reportContent += String.format(Locale.US,
                    "<tr class=\"%s\"><td colspan=\"2\" class=\"comment\">"
                    + "<span class=\"tip\">Output</span>"
                    + "<pre class=\"comment\">",
                    step.getResult().getStatus());
            for (String line : step.getOutput()) {
                reportContent += this.escapeHtml(line) + System.lineSeparator();
            }
            reportContent += String.format(Locale.US,
                    "</pre></td></tr>");
        }
        return reportContent;
    }
    private String generateScreenShot(CucumberScenarioResult scenario, CucumberStepResult step) throws IOException {
        String reportContent = "";
        if (step.getResult().getStatus().trim().equalsIgnoreCase("failed")) {
            reportContent += String.format(
                    Locale.US,
                    "<tr class=\"%s\"><td colspan=\"2\"><div>%s%s</br></div></td></tr>",
                    step.getResult().getStatus(),
                    "<br>",
                    escapeHtml(step.getResult().getErrorMessage()).replaceAll(System.lineSeparator(),
                            "</br><br>" + System.lineSeparator())
            );
            String scenarioId = scenario.getId();
            if (StringUtils.isBlank(scenarioId)) {
                scenarioId = "background";
                // Add more precise background generation
            }
            String filePath = this.getScreenShotLocation()
                    + this.generateNameFromId(scenarioId) + ".png";
            File shot = new File(this.getOutputDirectory() + filePath);
            if (shot.exists()) {
                String widthString = "";
                if (StringUtils.isNotBlank(this.getScreenShotWidth())) {
                    widthString = String.format(Locale.US, "width=\"%s\"", this.getScreenShotWidth());
                }
                reportContent += String.format(Locale.US,
                        "<tr class=\"%s\"><td colspan=\"2\"><img src=\"%s\" %s /></td></tr>",
                        step.getResult().getStatus(),
                        filePath,
                        widthString
                );
            }
        }
        return reportContent;
    }
    private String generateBeforeAfterRow(CucumberBeforeAfterResult results, String name) {
        if (results != null) {
            String error = escapeHtml(results.getResult().getErrorMessage());
            if (StringUtils.isBlank(error)) {
                error = "";
            } else {
                error = "<br>" + error.replaceAll(System.lineSeparator(), "</br><br>") + "</br>";
            }
            return String.format(
                    Locale.US,
                    "<tr class=\"%s\"><td>%s</td><td colspan=\"2\"></td><td width=\"100\">%s</td></tr>"
                    + "<tr class=\"%s\"><td colspan=\"4\">%s</td></tr>",
                    results.getResult().getStatus(),
                    name,
                    results.getResult().getDurationTimeString("HH:mm:ss:S"),
                    results.getResult().getStatus(),
                    error
            );
        }
        return "";
    }
    private String generateStepsReport(CucumberFeatureResult[] results) throws IOException {
        String content = this.getReportBase();
        content = content.replaceAll("__TITLE__", "Detailed Results Report");
        content = content.replaceAll("__OVERVIEW__", generateRunStatsTable(results));
        String reportContent = "";
        reportContent += generateTableOfContents(results);
        reportContent += "<h1>Detailed Results Report</h1><table width=\"700px\">";
        for (CucumberFeatureResult result : results) {
            String featureDescriptionHeading = "";
            if (StringUtils.isNotBlank(result.getDescription())) {
                featureDescriptionHeading = String.format(Locale.US, "<tr class=\"%s_description\">"
                        + "<td colspan=\"4\"><br>%s</br></td></tr>", result.getStatus(),
                    escapeHtml(result.getDescription()).replaceAll(System.lineSeparator(),
                            "</br><br>" + System.lineSeparator()));
            }
            reportContent += String.format(
                    Locale.US,
                    "<tr class=\"%s\"><td colspan=\"4\"><b>Feature:</b> <a id=\"feature-%s\">%s</a></td></tr>"
                    + "%s"
                    + "<tr class=\"%s\"><td><small><b>Passed:</b> %d</small></td>"
                        + "<td><small><b>Failed:</b> %d</small></td>"
                    + "<td><small><b>Undefined:</b> %d</small></td><td><small>Duration: %.2fs</small></td></tr>"
                    + "<tr class=\"%s\">"
                    + "<td colspan=\"4\" style=\"padding-left:20px\"> <table width=\"100%%\">",
                    result.getStatus(),
                    escapeHtml(result.getId()),
                    escapeHtml(result.getName()),
                    featureDescriptionHeading,
                    result.getStatus(),
                    result.getPassed(),
                    result.getFailed(),
                    result.getUndefined() + result.getSkipped(),
                    result.getDuration(),
                    result.getStatus());
            for (CucumberScenarioResult scenario : result.getElements()) {
                String descriptionHeading = "";
                if (StringUtils.isNotBlank(scenario.getDescription())) {
                    descriptionHeading = String.format(Locale.US, "<tr class=\"%s_description\">"
                        + "<td colspan=\"4\"><br>%s</br></td></tr>", scenario.getStatus(),
                        escapeHtml(scenario.getDescription()).replaceAll(System.lineSeparator(),
                                "</br><br>" + System.lineSeparator()));
                }
                reportContent += String.format(
                        Locale.US,
                        "<tr class=\"%s\"><td colspan=\"4\"><b>%s:</b> <a id=\"sc-%s\">%s</a></td></tr>"
                        + "%s"
                           + "<tr class=\"%s\">"
                        + "<td><small><b>Passed:</b> %d</small></td><td><small><b>Failed:</b> %d</small></td>"
                        + "<td><small><b>Undefined:</b> %d</small></td><td><small>Duration: %.2fs</small></td></tr>"
                           + "%s"
                        + "<tr class=\"%s\">"
                        + "<td colspan=\"4\" style=\"padding-left:20px\"> <table width=\"100%%\">",
                        scenario.getStatus(),
                        scenario.getKeyword(),
                        escapeHtml(scenario.getId()),
                        escapeHtml(scenario.getName()),
                        descriptionHeading,
                        scenario.getStatus(),
                        scenario.getPassed(),
                        scenario.getFailed(),
                        scenario.getUndefined() + scenario.getSkipped(),
                        scenario.getDuration(),
                        this.generateBeforeAfterRow(scenario.getBefore(), "Before"),
                        scenario.getStatus());
                for (CucumberStepResult step : scenario.getSteps()) {
                    reportContent += String.format(
                            Locale.US,
                            "<tr class=\"%s\"><td><b>%s</b> %s</td><td width=\"100\">%s</td></tr>",
                            step.getResult().getStatus(),
                            step.getKeyword(),
                            escapeHtml(step.getName()),
                            step.getResult().getDurationTimeString("HH:mm:ss:S")
                    );
                    reportContent += this.generateStepRows(step);
                    reportContent += this.generateDocString(step);
                    reportContent += this.generateScreenShot(scenario, step);
                    reportContent += this.embeddingScreenShots(scenario, step);
                    reportContent += this.embeddingOutput(step);
                }
                reportContent += "</table></td></tr>"
                        + this.generateBeforeAfterRow(scenario.getAfter(), "After")
                        + "<tr><td colspan=\"5\">"
                        + "<sup><a href=\"#top\">Back to Table of Contents</a></sup></td></tr>";
            }
            reportContent += "</table></td></tr><tr><td colspan=\"5\"></td></tr>";
        }
        reportContent += "</table>";
        reportContent = StringConversionUtils.replaceHtmlEntitiesWithCodes(reportContent);
        reportContent = reportContent.replaceAll("[$]", "&#36;");
        content = content.replaceAll("__REPORT__", reportContent);
        return content;
    }

    @Override
    public int[][] getStatuses(CucumberFeatureResult[] results) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.DETAILED_REPORT;
    }

    @Override
    public void validateParameters() {
        // TODO Auto-generated method stub
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.DETAILED_URL;
    }

    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
        execute((KnownErrorsModel) null, aggregate, formats);
    }

    @Override
    public void execute(KnownErrorsModel batch, boolean aggregate, String[] formats)
            throws Exception {
        CucumberFeatureResult[] features = readFileContent(aggregate);
        String formatName = "";
        if (batch != null) {
            for (CucumberFeatureResult feature : features) {
                feature.valuateKnownErrors(batch);
            }
        }
        if (aggregate) {
            formatName = "%s%s%s-agg-test-results.html";
        } else {
            formatName = "%s%s%s-test-results.html";
        }
        File outFile = new File(
                String.format(Locale.US,
                        formatName,
                        this.getOutputDirectory(), File.separator, this.getOutputName()));
        String content = generateStepsReport(features);
        FileUtils.writeStringToFile(outFile, content, "UTF-8");
        this.export(outFile, "test-results", formats, this.isImageExportable());
    }
}
