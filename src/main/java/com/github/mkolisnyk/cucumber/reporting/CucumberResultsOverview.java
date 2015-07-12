package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class CucumberResultsOverview extends CucumberResultsCommon {
    private String outputDirectory;
    private String outputName;

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

    private String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/feature-overview-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }

    private String getFeatureData(CucumberFeatureResult[] results) {
        int passed = 0;
        int failed = 0;
        int undefined = 0;

        for (CucumberFeatureResult result : results) {
            if (result.getStatus().trim().equalsIgnoreCase("passed")) {
                passed++;
            }
            if (result.getStatus().trim().equalsIgnoreCase("failed")) {
                failed++;
            }
            if (result.getStatus().trim().equalsIgnoreCase("undefined")) {
                undefined++;
            }
        }
        return String.format("['Passed', %d], ['Failed', %d], ['Undefined', %d]", passed, failed, undefined);
    }

    private String getScenarioData(CucumberFeatureResult[] results) {
        int passed = 0;
        int failed = 0;
        int undefined = 0;

        for (CucumberFeatureResult result : results) {
            for (CucumberScenarioResult element : result.getElements()) {
                if (element.getStatus().trim().equalsIgnoreCase("passed")) {
                    passed++;
                }
                if (element.getStatus().trim().equalsIgnoreCase("failed")) {
                    failed++;
                }
                if (element.getStatus().trim().equalsIgnoreCase("undefined")) {
                    undefined++;
                }
            }
        }

        return String.format("['Passed', %d], ['Failed', %d], ['Undefined', %d]", passed, failed, undefined);
    }

    private String generateFeatureOverview(CucumberFeatureResult[] results) throws IOException {
        String content = this.getReportBase();
        content = content.replaceAll("__TITLE__", "Features Overview");
        String reportContent = "";

        reportContent += "<h1>Features Status</h1><table><tr><th>Feature Name</th><th>Status</th>"
                + "<th>Passed</th><th>Failed</th><th>Undefined</th><th>Duration</th></tr>";

        for (CucumberFeatureResult result : results) {
            reportContent += String.format(
                    "<tr class=\"%s\"><td>%s</td><td>%s</td><td>%d</td><td>%d</td><td>%d</td><td>%.2fs</td></tr>",
                    result.getStatus(),
                    result.getName(),
                    result.getStatus(),
                    result.getPassed(),
                    result.getFailed(),
                    result.getUndefined(),
                    result.getDuration());
        }
        reportContent += "</table>";
        reportContent += "<h1>Scenario Status</h1><table>"
                + "<tr><th>Feature Name</th>"
                + "<th>Scenario</th>"
                + "<th>Status</th>"
                + "<th>Passed</th>"
                + "<th>Failed</th>"
                + "<th>Undefined</th>"
                + "<th>Retries</th>"
                + "<th>Duration</th></tr>";

        for (CucumberFeatureResult result : results) {
            for (CucumberScenarioResult element : result.getElements()) {
                reportContent += String.format(
                        "<tr class=\"%s\">"
                        + "<td>%s</td><td>%s</td><td>%s</td>"
                        + "<td>%d</td><td>%d</td><td>%d</td><td>%d</td>"
                        + "<td>%.2fs</td></tr>",
                        element.getStatus(),
                        result.getName(),
                        element.getName(),
                        element.getStatus(),
                        element.getPassed(),
                        element.getFailed(),
                        element.getUndefined(),
                        element.getRerunAttempts(),
                        element.getDuration());
            }
        }
        reportContent += "</table>";
        content = content.replaceAll("__REPORT__", reportContent);
        content = content.replaceAll("__FEATURE_DATA__", getFeatureData(results));
        content = content.replaceAll("__SCENARIO_DATA__", getScenarioData(results));
        return content;
    }


    public void executeFeaturesOverviewReport() throws Exception {
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-feature-overview.html");
        FileUtils.writeStringToFile(outFile, generateFeatureOverview(features));
        final WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_11);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        final HtmlPage page = webClient.getPage(new URL("file://" + outFile.getAbsolutePath()).toExternalForm());
        String content = page.asXml();
        FileUtils.writeStringToFile(outFile, content);
        webClient.close();
    }
}
