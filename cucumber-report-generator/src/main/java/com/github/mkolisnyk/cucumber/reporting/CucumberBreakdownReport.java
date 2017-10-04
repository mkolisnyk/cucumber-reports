package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.ConfigurableReport;
import com.github.mkolisnyk.cucumber.reporting.types.beans.BreakdownDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberBreakdownReport extends ConfigurableReport<BreakdownReportModel> {
    public CucumberBreakdownReport() {
        super();
    }
    public CucumberBreakdownReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }

    @Override
    public int[][] getStatuses(CucumberFeatureResult[] results) {
        return null;
    }
    public void executeReport(BreakdownReportInfo info, BreakdownTable table, String[] formats) throws Exception {
        CucumberFeatureResult[] features = readFileContent(true);
        this.executeReport(info, features, table, formats);
    }
    public void executeReport(BreakdownReportInfo info, CucumberFeatureResult[] features,
            BreakdownTable table, String[] formats) throws Exception {
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + info.getReportSuffix() + ".html");
        BreakdownDataBean data = new BreakdownDataBean();
        data.setTitle(info.getTitle());
        if (info.getRefreshTimeout() > 0 && StringUtils.isNotBlank(info.getNextFile())) {
            data.setRefreshData(String.format(Locale.US,
                    "<meta http-equiv=\"Refresh\" content=\"%d; url=%s\" />",
                        info.getRefreshTimeout(), info.getNextFile()));
        }
        data.setTable(table);
        CucumberScenarioResult[] scenarios = new CucumberScenarioResult[] {};
        for (CucumberFeatureResult feature : features) {
            scenarios = ArrayUtils.addAll(scenarios, feature.getElements());
        }
        BreakdownStats[][] results = table.valuate(scenarios);
        data.setStats(results);
        generateReportFromTemplate(outFile, this.templateName(), data);
        this.export(outFile, info.getReportSuffix(), formats, this.isImageExportable());
    }
    public void executeReport(BreakdownTable table, String[] formats) throws Exception {
        executeReport(new BreakdownReportInfo(table), table, formats);
    }
    @Override
    public void execute(BreakdownReportModel batch, String[] formats) throws Exception {
        validateParameters();
        batch.initRedirectSequence("./" + this.getOutputName() + "-");
        for (BreakdownReportInfo info : batch.getReportsInfo()) {
            this.executeReport(info, info.getTable(), formats);
        }
    }

    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
    }
    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.BREAKDOWN_REPORT;
    }
    @Override
    public void validateParameters() {
        Assert.assertNotNull(
            this.constructErrorMessage(CucumberReportError.NO_SOURCE_FILE, ""),
            this.getSourceFiles());
        Assert.assertNotNull(
                this.constructErrorMessage(CucumberReportError.NO_OUTPUT_DIRECTORY, ""),
                this.getOutputDirectory());
        Assert.assertNotNull(
                this.constructErrorMessage(CucumberReportError.NO_OUTPUT_NAME, ""),
                this.getOutputName());
        for (String sourceFile : this.getSourceFiles()) {
            File path = new File(sourceFile);
            Assert.assertTrue(
                    this.constructErrorMessage(CucumberReportError.NON_EXISTING_SOURCE_FILE, "")
                    + ". Was looking for path: \"" + path.getAbsolutePath() + "\"",
                    path.exists());
        }
    }
    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.BREAKDOWN_URL;
    }
    @Override
    public void execute(BreakdownReportModel batch, boolean aggregate,
            String[] formats) throws Exception {
        execute(batch, formats);
    }
    @Override
    public void execute(File config, boolean aggregate, String[] formats)
            throws Exception {
        execute(config, formats);
    }
    @Override
    public void execute(String[] formats) throws Exception {
    }
    @Override
    public void execute(boolean aggregate, CucumberFeatureResult[] results,
            String[] formats) throws Exception {
    }
    @Override
    public void execute(BreakdownReportModel model,
            CucumberFeatureResult[] results, boolean aggregate, String[] formats)
            throws Exception {
        validateParameters();
        model.initRedirectSequence("./" + this.getOutputName() + "-");
        for (BreakdownReportInfo info : model.getReportsInfo()) {
            this.executeReport(info, results, info.getTable(), formats);
        }
    }
}
