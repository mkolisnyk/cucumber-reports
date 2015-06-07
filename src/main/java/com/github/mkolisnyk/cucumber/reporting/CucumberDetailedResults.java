package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberStepResult;

public class CucumberDetailedResults extends CucumberResultsCommon {
    private String outputDirectory;
    private String outputName;
    private String screenShotLocation;

    /**
     * @return the outputDirectory
     */
    public final String getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * @param outputDirectoryValue the outputDirectory to set
     */
    public final void setOutputDirectory(String outputDirectoryValue) {
        this.outputDirectory = outputDirectoryValue;
    }

    /**
     * @return the outputName
     */
    public final String getOutputName() {
        return outputName;
    }

    /**
     * @param outputNameValue the outputName to set
     */
    public final void setOutputName(String outputNameValue) {
        this.outputName = outputNameValue;
    }

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

    private String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/results-report-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }

    private String generateOverview(CucumberFeatureResult[] results) {
        int featuresPassed = 0;
        int featuresFailed = 0;
        int featuresUndefined = 0;
        int scenariosPassed = 0;
        int scenariosFailed = 0;
        int scenariosUndefined = 0;
        int stepsPassed = 0;
        int stepsFailed = 0;
        int stepsUndefined = 0;
        final float highestPercent = 100.f;
        for (CucumberFeatureResult result : results) {
            if (result.getStatus().equals("passed")) {
                featuresPassed++;
            } else if (result.getStatus().equals("failed")) {
                featuresFailed++;
            } else {
                featuresUndefined++;
            }
            scenariosPassed += result.getPassed();
            scenariosFailed += result.getFailed();
            scenariosUndefined += result.getUndefined();

            for (CucumberScenarioResult scenario : result.getElements()) {
                stepsPassed += scenario.getPassed();
                stepsFailed += scenario.getFailed();
                stepsUndefined += scenario.getUndefined();
            }
        }
        return String.format("<table>"
                + "<tr><th></th><th>Passed</th><th>Failed</th><th>Undefined</th><th>%%Passed</th></tr>"
                + "<tr><th>Features</th><td class=\"passed\">%d</td><td class=\"failed\">%d</td>"
                    + "<td class=\"undefined\">%d</td><td>%.2f</td></tr>"
                + "<tr><th>Scenarios</th><td class=\"passed\">%d</td><td class=\"failed\">%d</td>"
                    + "<td class=\"undefined\">%d</td><td>%.2f</td></tr>"
                + "<tr><th>Steps</th><td class=\"passed\">%d</td><td class=\"failed\">%d</td>"
                    + "<td class=\"undefined\">%d</td><td>%.2f</td></tr></table>",
                featuresPassed,
                featuresFailed,
                featuresUndefined,
                highestPercent * (float) featuresPassed
                    / (float) (featuresPassed + featuresFailed + featuresUndefined),
                scenariosPassed,
                scenariosFailed,
                scenariosUndefined,
                highestPercent * (float) scenariosPassed
                    / (float) (scenariosPassed + scenariosFailed + scenariosUndefined),
                stepsPassed,
                stepsFailed,
                stepsUndefined,
                highestPercent * (float) stepsPassed / (float) (stepsPassed + stepsFailed + stepsUndefined));
    }
    private String generateNameFromId(String scId) {
        String result = scId.replaceAll("[; !@#$%^&*()+=]", "_");
        return result;
    }
    private String generateTableOfContents(CucumberFeatureResult[] results) {
        String reportContent = "";
        reportContent += "<a id=\"top\"></a><h1>Table of Contents</h1><ol>";
        for (CucumberFeatureResult result : results) {
            reportContent += String.format(
                    "<li> <span class=\"%s\"><a href=\"#feature-%s\">%s</a></span><ol>",
                    result.getStatus(),
                    result.getId(),
                    result.getName());
            for (CucumberScenarioResult scenario : result.getElements()) {
                reportContent += String.format(
                        "<li> <span class=\"%s\"><a href=\"#sc-%s\">%s</a></span></li>",
                        scenario.getStatus(),
                        scenario.getId(),
                        scenario.getName());
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
                    "<tr class=\"%s\"><td style=\"padding-left:20px\"><table>",
                    step.getResult().getStatus());
            for (int i = 0; i < step.getRows().length; i++) {
                reportContent += "<tr>";
                for (int j = 0; j < step.getRows()[i].length; j++) {
                    reportContent += String.format("<td>%s</td>", step.getRows()[i][j]);
                }
                reportContent += "</tr>";
            }
            reportContent += "</table></td></tr>";
        }
        return reportContent;
    }
    private String generateScreenShot(CucumberScenarioResult scenario, CucumberStepResult step) {
        String reportContent = "";
        if (step.getResult().getStatus().trim().equalsIgnoreCase("failed")) {
            reportContent += String.format(
                    "<tr class=\"%s\"><td><pre>%s%s</pre></td></tr>",
                    step.getResult().getStatus(),
                    System.lineSeparator(),
                    step.getResult().getErrorMessage()
            );
            String filePath = this.getScreenShotLocation()
                    + this.generateNameFromId(scenario.getId()) + ".png";
            File shot = new File(this.outputDirectory + filePath);
            if (shot.exists()) {
                reportContent += String.format(
                        "<tr class=\"%s\"><td><img src=\"%s\" /></td></tr>",
                        step.getResult().getStatus(),
                        filePath
                );
            }
        }
        return reportContent;
    }
    private String generateStepsReport(CucumberFeatureResult[] results) throws IOException {
        String content = this.getReportBase();
        content = content.replaceAll("__TITLE__", "Detailed Results Report");
        content = content.replaceAll("__OVERVIEW__", generateOverview(results));
        String reportContent = "";
        reportContent += generateTableOfContents(results);
        reportContent += "<h1>Detailed Results Report</h1><table>";
        for (CucumberFeatureResult result : results) {
            reportContent += String.format(
                    "<tr class=\"%s\"><td><b>Feature:</b> <a id=\"feature-%s\">%s</a></td>"
                    + "<td><b>Passed:</b> %d</td><td><b>Failed:</b> %d</td>"
                    + "<td><b>Undefined:</b> %d</td></tr><tr class=\"%s\">"
                    + "<td colspan=\"4\" style=\"padding-left:20px\"> <table width=\"100%%\">",
                    result.getStatus(),
                    result.getId(),
                    result.getName(),
                    result.getPassed(),
                    result.getFailed(),
                    result.getUndefined(),
                    result.getStatus());
            for (CucumberScenarioResult scenario : result.getElements()) {
                reportContent += String.format(
                        "<tr class=\"%s\"><td><b>Scenario:</b> <a id=\"sc-%s\">%s</a></td>"
                        + "<td><b>Passed:</b> %d</td><td><b>Failed:</b> %d</td>"
                        + "<td><b>Undefined:</b> %d</td></tr><tr class=\"%s\">"
                        + "<td colspan=\"4\" style=\"padding-left:20px\"> <table width=\"100%%\">",
                        scenario.getStatus(),
                        scenario.getId(),
                        scenario.getName(),
                        scenario.getPassed(),
                        scenario.getFailed(),
                        scenario.getUndefined(),
                        scenario.getStatus());
                for (CucumberStepResult step : scenario.getSteps()) {
                    reportContent += String.format(
                            "<tr class=\"%s\"><td><b>%s</b> %s</td></tr>",
                            step.getResult().getStatus(),
                            step.getKeyword(),
                            step.getName()
                    );
                    reportContent += this.generateStepRows(step);
                    reportContent += this.generateScreenShot(scenario, step);
                }
                reportContent += "</table></td></tr><tr><td colspan=\"5\">"
                        + "<sup><a href=\"#top\">Back to Table of Contents</a></sup></td></tr>";
            }
            reportContent += "</table></td></tr><tr><td colspan=\"5\"></td></tr>";
        }
        reportContent += "</table>";
        content = content.replaceAll("__REPORT__", reportContent);
        return content;
    }


    public void executeDetailedResultsReport(boolean toPdf) throws Exception {
        CucumberFeatureResult[] features = readFileContent();
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-test-results.html");
        FileUtils.writeStringToFile(outFile, generateStepsReport(features));
        if (toPdf) {
            String url = outFile.toURI().toURL().toString();
            String outputFile = this.getOutputDirectory() + File.separator + this.getOutputName()
                    + "-test-results.pdf";
            OutputStream os = new FileOutputStream(outputFile);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            renderer.layout();
            renderer.createPDF(os);

            os.close();
        }
    }
}
