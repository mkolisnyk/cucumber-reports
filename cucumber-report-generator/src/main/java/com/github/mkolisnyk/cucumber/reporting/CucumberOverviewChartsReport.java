package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.KECompatibleReport;
import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;
import com.github.mkolisnyk.cucumber.reporting.types.beans.OverviewChartDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberOverviewChartsReport extends KECompatibleReport {
    private ExtendedRuntimeOptions options;

    public CucumberOverviewChartsReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
        this.options = extendedOptions;
    }

    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
        this.execute((KnownErrorsModel) null, aggregate, formats);
    }

    @Override
    public void execute(KnownErrorsModel batch, boolean aggregate, String[] formats)
            throws Exception {
        this.validateParameters();
        CucumberFeatureResult[] features = readFileContent(aggregate);
        execute(batch, features, aggregate, formats);
    }
    public void execute(KnownErrorsModel batch, CucumberFeatureResult[] features, boolean aggregate, String[] formats)
            throws Exception {
        this.validateParameters();
        if (batch != null) {
            for (CucumberFeatureResult feature : features) {
                feature.valuateKnownErrors(batch);
            }
        }
        File outFile = getOutputHtmlFile();
        OverviewChartDataBean data = new OverviewChartDataBean();
        OverviewStats stats = new OverviewStats();
        data.setCoverageIncluded(options.isCoverageReport());
        data.setOverviewData(stats.valuate(features));
        generateReportFromTemplate(outFile, this.templateName(), data);
        this.export(outFile, this.reportSuffix(), formats, this.isImageExportable());
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

    @Override
    public void execute(boolean aggregate, CucumberFeatureResult[] features,
            String[] formats) throws Exception {
        execute((KnownErrorsModel) null, features, aggregate, formats);
    }

}
