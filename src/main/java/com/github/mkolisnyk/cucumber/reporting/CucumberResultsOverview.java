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

    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/feature-overview-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }

    protected String getFeatureData(CucumberFeatureResult[] results) {
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
            if (result.getStatus().trim().equalsIgnoreCase("undefined")
                    || result.getStatus().trim().equalsIgnoreCase("skipped")) {
                undefined++;
            }
        }
        return String.format("['Passed', %d], ['Failed', %d], ['Undefined', %d]", passed, failed, undefined);
    }

    protected String getScenarioData(CucumberFeatureResult[] results) {
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
                if (element.getStatus().trim().equalsIgnoreCase("undefined")
                        || element.getStatus().trim().equalsIgnoreCase("skipped")) {
                    undefined++;
                }
            }
        }

        return String.format("['Passed', %d], ['Failed', %d], ['Undefined', %d]", passed, failed, undefined);
    }

    protected String generateFeatureOverview(CucumberFeatureResult[] results) throws IOException {
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
                    result.getUndefined() + result.getSkipped(),
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

        int[] featureStatuses = {0, 0, 0};
        int[] scenarioStatuses = {0, 0, 0};
        for (CucumberFeatureResult result : results) {
            if (result.getStatus().trim().equalsIgnoreCase("passed")) {
                featureStatuses[0]++;
            }
            if (result.getStatus().trim().equalsIgnoreCase("failed")) {
                featureStatuses[1]++;
            }
            if (result.getStatus().trim().equalsIgnoreCase("undefined")
                    || result.getStatus().trim().equalsIgnoreCase("skipped")) {
                featureStatuses[2]++;
            }
            for (CucumberScenarioResult element : result.getElements()) {
                if (element.getStatus().trim().equalsIgnoreCase("passed")) {
                    scenarioStatuses[0]++;
                }
                if (element.getStatus().trim().equalsIgnoreCase("failed")) {
                    scenarioStatuses[1]++;
                }
                if (element.getStatus().trim().equalsIgnoreCase("undefined")
                        || element.getStatus().trim().equalsIgnoreCase("skipped")) {
                    scenarioStatuses[2]++;
                }
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
                        element.getUndefined() + element.getSkipped(),
                        element.getRerunAttempts(),
                        element.getDuration());
            }
        }
        reportContent += "</table>";
        content = content.replaceAll("__REPORT__", reportContent);
        content = content.replaceAll("__FEATURE_DATA__", this.generatePieChart(
                350, 240,
                featureStatuses,
                new String[]{"Passed", "Failed", "Undefined"},
                new String[]{"green", "red", "silver"},
                new String[]{"darkgreen", "darkred", "darkgray"},
                20,
                2));
        content = content.replaceAll("__SCENARIO_DATA__", this.generatePieChart(
                350, 240,
                scenarioStatuses,
                new String[]{"Passed", "Failed", "Undefined"},
                new String[]{"green", "red", "silver"},
                new String[]{"darkgreen", "darkred", "darkgray"},
                20,
                2));
        return content;
    }

    public void executeOverviewReport(String reportSuffix) throws Exception {
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + reportSuffix + ".html");
        FileUtils.writeStringToFile(outFile, generateFeatureOverview(features));
        /*final WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_11);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        final HtmlPage page = webClient.getPage(new URL("file://" + outFile.getAbsolutePath()).toExternalForm());
        String content = page.asXml();
        FileUtils.writeStringToFile(outFile, content);
        webClient.close();*/
    }

    public void executeFeaturesOverviewReport() throws Exception {
        executeOverviewReport("feature-overview");
    }
}
