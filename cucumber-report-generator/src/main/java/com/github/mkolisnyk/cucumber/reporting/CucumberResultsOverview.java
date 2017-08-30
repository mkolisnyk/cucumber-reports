package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.KECompatibleReport;
import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;
import com.github.mkolisnyk.cucumber.reporting.types.beans.OverviewDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.beans.OverviewDataBean.FeatureStatusRow;
import com.github.mkolisnyk.cucumber.reporting.types.beans.OverviewDataBean.ScenarioStatusRow;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberResultsOverview extends KECompatibleReport {

    public CucumberResultsOverview() {
        super();
    }

    public CucumberResultsOverview(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
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

    protected void executeOverviewReport(String reportSuffix) throws Exception {
        executeOverviewReport(reportSuffix, new String[] {});
    }
    protected void executeOverviewReport(String reportSuffix,  String[] formats) throws Exception {
        executeOverviewReport(null, reportSuffix, formats);
    }
    protected void executeOverviewReport(
            KnownErrorsModel batch, String reportSuffix,  String[] formats) throws Exception {
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
        OverviewDataBean data = new OverviewDataBean();
        FeatureStatusRow[] featureRows = new FeatureStatusRow[] {};
        ScenarioStatusRow[] scenarioRows = new ScenarioStatusRow[] {};
        for (CucumberFeatureResult feature : features) {
            feature.valuate();
            if (batch != null) {
                feature.valuateKnownErrors(batch);
            }
            FeatureStatusRow featureRow = data.new FeatureStatusRow();
            featureRow.setFeatureName(feature.getName());
            OverviewStats stats = new OverviewStats();
            stats.valuate(feature);
            featureRow.setStats(stats);
            featureRow.setDuration(String.format("%.2f", feature.getDuration()));
            featureRow.setStatus(feature.getStatus());
            featureRows = (FeatureStatusRow[]) ArrayUtils.add(featureRows, featureRow);
            for (CucumberScenarioResult scenario : feature.getElements()) {
                ScenarioStatusRow scenarioRow = data.new ScenarioStatusRow();
                scenario.valuate();
                OverviewStats scenarioStats = new OverviewStats();
                scenarioStats.valuate(scenario);
                scenarioRow.setDuration(String.format("%.2f", scenario.getDuration()));
                scenarioRow.setFeatureName(feature.getName());
                scenarioRow.setRetries(scenario.getRerunAttempts());
                scenarioRow.setScenarioName(scenario.getName());
                scenarioRow.setStats(scenarioStats);
                scenarioRow.setStatus(scenario.getStatus());
                scenarioRows = (ScenarioStatusRow[]) ArrayUtils.add(scenarioRows, scenarioRow);
            }
        }
        OverviewStats stats = new OverviewStats();
        stats.valuate(features);
        data.setOverallStats(stats);
        data.setFeatures(featureRows);
        data.setScenarios(scenarioRows);
        generateReportFromTemplate(outFile, "overview", data);
        this.export(outFile, reportSuffix, formats, this.isImageExportable());
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
    public void execute(String[] formats) throws Exception {
        executeOverviewReport("feature-overview", formats);
    }

    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
        executeOverviewReport("feature-overview", formats);
    }

    @Override
    public void execute(KnownErrorsModel batch, boolean aggregate, String[] formats)
            throws Exception {
        executeOverviewReport(batch, "feature-overview", formats);
    }
}
