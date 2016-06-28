package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.CucumberResultsCommon;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.utils.drawers.PieChartDrawer;

public class CucumberResultsOverview extends CucumberResultsCommon {

    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/feature-overview-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }

    @Override
    public int[][] getStatuses(CucumberFeatureResult[] results) {
        int[][] statuses = {{0, 0, 0}, {0, 0, 0}};
        for (CucumberFeatureResult result : results) {
            if (result.getStatus().trim().equalsIgnoreCase("passed")) {
                statuses[0][0]++;
            } else if (result.getStatus().trim().equalsIgnoreCase("failed")) {
                statuses[0][1]++;
            } else {
                statuses[0][2]++;
            }
            for (CucumberScenarioResult element : result.getElements()) {
                if (element.getStatus().trim().equalsIgnoreCase("passed")) {
                    statuses[1][0]++;
                } else if (element.getStatus().trim().equalsIgnoreCase("failed")) {
                    statuses[1][1]++;
                } else {
                    statuses[1][2]++;
                }
            }
        }
        return statuses;
    }
    protected String generateFeatureOverview(CucumberFeatureResult[] results) throws IOException {
        String content = this.getReportBase();
        content = content.replaceAll("__TITLE__", "Features Overview");
        String reportContent = "";

        reportContent += "<h1>Features Status</h1><table><tr><th>Feature Name</th><th>Status</th>"
                + "<th>Passed</th><th>Failed</th><th>Undefined</th><th>Duration</th></tr>";

        for (CucumberFeatureResult result : results) {
            reportContent += String.format(Locale.US,
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

        int[][] statuses = this.getStatuses(results);
        int[] featureStatuses = statuses[0];
        int[] scenarioStatuses = statuses[1];
        for (CucumberFeatureResult result : results) {
            for (CucumberScenarioResult element : result.getElements()) {
                reportContent += String.format(Locale.US,
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
        PieChartDrawer pieChart = new PieChartDrawer();
        content = content.replaceAll("__FEATURE_DATA__", pieChart.generatePieChart(
                CHART_WIDTH, CHART_HEIGHT,
                featureStatuses,
                new String[]{"Passed", "Failed", "Undefined"},
                new String[]{"green", "red", "silver"},
                new String[]{"darkgreen", "darkred", "darkgray"},
                CHART_THICKNESS,
                2));
        content = content.replaceAll("__SCENARIO_DATA__", pieChart.generatePieChart(
                CHART_WIDTH, CHART_HEIGHT,
                scenarioStatuses,
                new String[]{"Passed", "Failed", "Undefined"},
                new String[]{"green", "red", "silver"},
                new String[]{"darkgreen", "darkred", "darkgray"},
                CHART_THICKNESS,
                2));
        return content;
    }

    public void executeOverviewReport(String reportSuffix) throws Exception {
        executeOverviewReport(reportSuffix, false);
    }
    public void executeOverviewReport(String reportSuffix, boolean toPdf) throws Exception {
        this.validateParameters();
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + reportSuffix + ".html");
        FileUtils.writeStringToFile(outFile, generateFeatureOverview(features));
        if (toPdf) {
            this.exportToPDF(outFile, reportSuffix);
        }
        try {
            outFile = new File(
                    this.getOutputDirectory() + File.separator + this.getOutputName()
                    + "-" + reportSuffix + "-dump.xml");
            this.dumpOverviewStats(outFile, features);
        } catch (Throwable e) {
            e.printStackTrace();
            return;
        }
    }
    public void executeFeaturesOverviewReport() throws Exception {
        executeFeaturesOverviewReport(false);
    }
    public void executeFeaturesOverviewReport(boolean toPDF) throws Exception {
        executeOverviewReport("feature-overview", toPDF);
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.RESULTS_OVERVIEW;
    }

    @Override
    public void validateParameters() {
        Assert.assertNotNull(this.getSourceFiles(),
                this.constructErrorMessage(CucumberReportError.NO_SOURCE_FILE, ""));
        Assert.assertNotNull(this.getOutputDirectory(),
                this.constructErrorMessage(CucumberReportError.NO_OUTPUT_DIRECTORY, ""));
        Assert.assertNotNull(this.getOutputName(),
                this.constructErrorMessage(CucumberReportError.NO_OUTPUT_NAME, ""));
        for (String sourceFile : this.getSourceFiles()) {
            Assert.assertNotNull(sourceFile,
                    this.constructErrorMessage(CucumberReportError.NO_SOURCE_FILE, ""));
            File path = new File(sourceFile);
            Assert.assertTrue(path.exists(),
                    this.constructErrorMessage(CucumberReportError.NON_EXISTING_SOURCE_FILE, "")
                    + ". Was looking for path: \"" + path.getAbsolutePath() + "\"");
        }
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.RESULTS_OVERVIEW_URL;
    }
}
