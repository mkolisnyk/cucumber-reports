package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.KECompatibleReport;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.utils.drawers.PieChartDrawer;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberResultsOverview extends KECompatibleReport {

    public CucumberResultsOverview() {
        super();
    }

    public CucumberResultsOverview(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }

    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/feature-overview-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }

    @Override
    public int[][] getStatuses(CucumberFeatureResult[] results) {
        final int kePosition = 3;
        int[][] statuses = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        for (CucumberFeatureResult result : results) {
            if (result.getStatus().trim().equalsIgnoreCase("passed")) {
                statuses[0][0]++;
            } else if (result.getStatus().trim().equalsIgnoreCase("failed")) {
                statuses[0][1]++;
            } else if (result.getStatus().trim().equalsIgnoreCase("known")) {
                statuses[0][kePosition]++;
            } else {
                statuses[0][2]++;
            }
            for (CucumberScenarioResult element : result.getElements()) {
                if (element.getStatus().trim().equalsIgnoreCase("passed")) {
                    statuses[1][0]++;
                } else if (element.getStatus().trim().equalsIgnoreCase("failed")) {
                    statuses[1][1]++;
                } else if (element.getStatus().trim().equalsIgnoreCase("known")) {
                    statuses[1][kePosition]++;
                } else {
                    statuses[1][2]++;
                }
                statuses[2][0] += element.getPassed();
                statuses[2][1] += element.getFailed();
                statuses[2][2] += element.getSkipped() + element.getUndefined();
                statuses[2][kePosition] += element.getKnown();
            }
        }
        return statuses;
    }
    @Override
    public boolean isImageExportable() {
        return true;
    }
    protected String generateFeatureOverview(CucumberFeatureResult[] results) throws IOException {
        String content = this.getReportBase();
        content = content.replaceAll("__TITLE__", "Features Overview");
        String reportContent = "";
        reportContent += "<h1>Summary</h1>" + this.generateRunStatsTable(results);
        reportContent += "<h1>Features Status</h1><table><tr><th>Feature Name</th><th>Status</th>"
                + "<th>Passed</th><th>Failed</th><th>Known</th><th>Undefined</th><th>Total</th>"
                + "<th>Duration</th></tr>";

        for (CucumberFeatureResult result : results) {
            reportContent += String.format(Locale.US,
                    "<tr class=\"%s\"><td>%s</td><td>%s</td>"
                    + "<td>%d</td><td>%d</td><td>%d</td><td>%d</td><td>%d</td><td>%.2fs</td></tr>",
                    result.getStatus(),
                    result.getName(),
                    result.getStatus(),
                    result.getPassed(),
                    result.getFailed(),
                    result.getKnown(),
                    result.getUndefined() + result.getSkipped(),
                    result.getPassed() + result.getFailed() + result.getKnown()
                    + result.getUndefined() + result.getSkipped(),
                    result.getDuration());
        }
        reportContent += "</table>";
        reportContent += "<h1>Scenario Status</h1><table>"
                + "<tr><th>Feature Name</th>"
                + "<th>Scenario</th>"
                + "<th>Status</th>"
                + "<th>Passed</th>"
                + "<th>Failed</th>"
                + "<th>Known</th>"
                + "<th>Undefined</th>"
                + "<th>Total</th>"
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
                        + "<td>%d</td><td>%d</td><td>%d</td><td>%d</td><td>%d</td><td>%d</td>"
                        + "<td>%.2fs</td></tr>",
                        element.getStatus(),
                        result.getName(),
                        element.getName(),
                        element.getStatus(),
                        element.getPassed(),
                        element.getFailed(),
                        element.getKnown(),
                        element.getUndefined() + element.getSkipped(),
                        element.getPassed() + element.getFailed()
                        + element.getKnown() + element.getUndefined() + element.getSkipped(),
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
                new String[]{"Passed", "Failed", "Undefined", "Known"},
                new String[]{"green", "red", "silver", "gold"},
                new String[]{"darkgreen", "darkred", "darkgray", "goldenrod"},
                CHART_THICKNESS,
                2));
        content = content.replaceAll("__SCENARIO_DATA__", pieChart.generatePieChart(
                CHART_WIDTH, CHART_HEIGHT,
                scenarioStatuses,
                new String[]{"Passed", "Failed", "Undefined", "Known"},
                new String[]{"green", "red", "silver", "gold"},
                new String[]{"darkgreen", "darkred", "darkgray", "goldenrod"},
                CHART_THICKNESS,
                2));
        return content;
    }

    protected void executeOverviewReport(String reportSuffix) throws Exception {
        executeOverviewReport(reportSuffix, false);
    }
    protected void executeOverviewReport(String reportSuffix, boolean toPdf) throws Exception {
        executeOverviewReport(null, reportSuffix, toPdf);
    }
    protected void executeOverviewReport(KnownErrorsModel batch, String reportSuffix, boolean toPDF) throws Exception {
        this.validateParameters();
        CucumberFeatureResult[] features = readFileContent(true);

        if (batch != null) {
            for (CucumberFeatureResult feature : features) {
                feature.valuateKnownErrors(batch);
            }
        }
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + reportSuffix + ".html");
        FileUtils.writeStringToFile(outFile, generateFeatureOverview(features));
        this.export(outFile, reportSuffix, "", toPDF, this.isImageExportable());
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
    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.RESULTS_OVERVIEW;
    }

    @Override
    public void validateParameters() {
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_SOURCE_FILE, ""),
                this.getSourceFiles());
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_DIRECTORY, ""),
                this.getOutputDirectory());
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_NAME, ""),
                this.getOutputName());
        for (String sourceFile : this.getSourceFiles()) {
            Assert.assertNotNull(
                    this.constructErrorMessage(CucumberReportError.NO_SOURCE_FILE, ""), sourceFile);
            File path = new File(sourceFile);
            Assert.assertTrue(this.constructErrorMessage(CucumberReportError.NON_EXISTING_SOURCE_FILE, "")
                    + ". Was looking for path: \"" + path.getAbsolutePath() + "\"", path.exists());
        }
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.RESULTS_OVERVIEW_URL;
    }

    @Override
    public void execute(boolean toPDF) throws Exception {
        executeOverviewReport("feature-overview", toPDF);
    }

    @Override
    public void execute(boolean aggregate, boolean toPDF) throws Exception {
        executeOverviewReport("feature-overview", toPDF);
    }

    @Override
    public void execute(KnownErrorsModel batch, boolean aggregate, boolean toPDF)
            throws Exception {
        executeOverviewReport(batch, "feature-overview", toPDF);
    }
}
