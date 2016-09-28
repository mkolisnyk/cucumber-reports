package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.AggregatedReport;
import com.github.mkolisnyk.cucumber.reporting.interfaces.KECompatibleReport;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.utils.drawers.PieChartDrawer;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberOverviewChartsReport extends KECompatibleReport {
    private ExtendedRuntimeOptions options;

    public CucumberOverviewChartsReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
        this.options = extendedOptions;
    }

    private String generateCharts(CucumberFeatureResult[] features) throws Exception {
        String result = this.getReportBase();
        String content = "<table>";
        CucumberResultsOverview report = new CucumberResultsOverview(options);
        int[][] statuses = report.getStatuses(features);
        PieChartDrawer pieChart = new PieChartDrawer();
        content = content.concat(
            String.format(Locale.US,
                "<tr><th colspan=\"3\">Run Results Status</th></tr>"
                    + "<tr><th>Features</th><th>Scenarios</th><th>Steps</th></tr>"
                    + "<tr><td>%s</td><td>%s</td><td>%s</td></tr>",
                pieChart.generatePieChart(
                    CHART_WIDTH, CHART_HEIGHT,
                    statuses[0],
                    new String[]{"Passed", "Failed", "Undefined", "Known"},
                    new String[]{"green", "red", "silver", "goldenrod"},
                    new String[]{"darkgreen", "darkred", "darkgray", "darkred"},
                    CHART_THICKNESS,
                    2
                ),
                pieChart.generatePieChart(
                    CHART_WIDTH, CHART_HEIGHT,
                    statuses[1],
                    new String[]{"Passed", "Failed", "Undefined", "Known"},
                    new String[]{"green", "red", "silver", "goldenrod"},
                    new String[]{"darkgreen", "darkred", "darkgray", "darkred"},
                    CHART_THICKNESS,
                    2
                ),
                pieChart.generatePieChart(
                    CHART_WIDTH, CHART_HEIGHT,
                    statuses[2],
                    new String[]{"Passed", "Failed", "Undefined", "Known"},
                    new String[]{"green", "red", "silver", "goldenrod"},
                    new String[]{"darkgreen", "darkred", "darkgray", "darkred"},
                    CHART_THICKNESS,
                    2
                )
            )
        );
        if (options.isCoverageReport()) {
            CucumberCoverageOverview coverage = new CucumberCoverageOverview(options);
            statuses = coverage.getStatuses(features);
            content = content.concat(
                String.format(Locale.US,
                    "<tr><td colspan=\"3\"></td></tr>"
                        + "<tr><th colspan=\"3\">Coverage Status</th></tr>"
                        + "<tr><th>Features</th><th>Scenarios</th><th>Steps</th></tr>"
                        + "<tr><td>%s</td><td>%s</td><td>%s</td></tr>",
                    pieChart.generatePieChart(
                        CHART_WIDTH, CHART_HEIGHT,
                        statuses[0],
                        new String[]{"Covered", "Not Covered"},
                        new String[]{"green", "gold"},
                        new String[]{"darkgreen", "GoldenRod"},
                        CHART_THICKNESS,
                        2),
                    pieChart.generatePieChart(
                        CHART_WIDTH, CHART_HEIGHT,
                        statuses[1],
                        new String[]{"Covered", "Not Covered"},
                        new String[]{"green", "gold"},
                        new String[]{"darkgreen", "GoldenRod"},
                        CHART_THICKNESS,
                        2),
                    pieChart.generatePieChart(
                        CHART_WIDTH, CHART_HEIGHT,
                        statuses[1],
                        new String[]{"Covered", "Not Covered"},
                        new String[]{"green", "gold"},
                        new String[]{"darkgreen", "GoldenRod"},
                        CHART_THICKNESS,
                        2)
                )
            );
        }
        content = content.concat("</table>");
        result = result.replaceAll("__REPORT__", content);
        return result;
    }

    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/charts-overview-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }

    @Override
    public void execute(boolean aggregate, boolean toPDF) throws Exception {
        this.execute((KnownErrorsModel) null, aggregate, toPDF);
    }

    @Override
    public void execute(KnownErrorsModel batch, boolean aggregate, boolean toPDF)
            throws Exception {
        this.validateParameters();
        CucumberFeatureResult[] features = readFileContent(aggregate);
        if (batch != null) {
            for (CucumberFeatureResult feature : features) {
                feature.valuateKnownErrors(batch);
            }
        }
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-charts-report.html");
        FileUtils.writeStringToFile(outFile, generateCharts(features));
        if (toPDF) {
            this.exportToPDF(outFile, "charts-report");
        }
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.CHARTS_REPORT;
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.CHART_URL;
    }

    @Override
    public void validateParameters() {
        Assert.assertNotNull(
                this.constructErrorMessage(CucumberReportError.NO_OUTPUT_DIRECTORY, ""),
                this.getOutputDirectory());
        Assert.assertNotNull(
                this.constructErrorMessage(CucumberReportError.NO_OUTPUT_NAME, ""),
                this.getOutputName());
        Assert.assertNotNull(
                this.constructErrorMessage(CucumberReportError.NO_SOURCE_FILE, ""),
                this.getSourceFiles());
        for (String sourceFile : this.getSourceFiles()) {
            File path = new File(sourceFile);
            Assert.assertTrue(
                    this.constructErrorMessage(CucumberReportError.NON_EXISTING_SOURCE_FILE, "")
                    + ". Was looking for path: \"" + path.getAbsolutePath() + "\"",
                    path.exists());
        }
    }

}
