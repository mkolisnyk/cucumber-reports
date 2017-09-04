package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.interfaces.ConfigurableReport;
import com.github.mkolisnyk.cucumber.reporting.types.beans.RetrospectiveDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.retrospective.RetrospectiveBatch;
import com.github.mkolisnyk.cucumber.reporting.types.retrospective.RetrospectiveModel;
import com.github.mkolisnyk.cucumber.reporting.utils.helpers.FolderUtils;
import com.github.mkolisnyk.cucumber.reporting.utils.helpers.StringConversionUtils;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberRetrospectiveOverviewReport extends ConfigurableReport<RetrospectiveBatch> {

    public CucumberRetrospectiveOverviewReport() {
        super();
    }
    public CucumberRetrospectiveOverviewReport(
            ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }
    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/consolidated-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }
    private BreakdownStats[] calculateStats(String[] files) throws Exception {
        BreakdownStats[] result = {};
        for (String file : files) {
            BreakdownStats stat = new BreakdownStats();
            CucumberFeatureResult[] features = this.readFileContent(file, true);
            for (CucumberFeatureResult feature : features) {
                feature.valuate();
                stat.addPassed(feature.getPassed());
                stat.addFailed(feature.getFailed());
                stat.addSkipped(feature.getSkipped() + feature.getUndefined());
            }
            result = (BreakdownStats[]) ArrayUtils.add(result, stat);
        }
        return result;
    }

    public void executeReport(RetrospectiveModel model, boolean aggregate, String[] formats) throws Exception {
        String[] files = FolderUtils.getFilesByMask(".", model.getMask());
        BreakdownStats[] stats = calculateStats(files);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + model.getReportSuffix() + ".html");
        RetrospectiveDataBean data = new RetrospectiveDataBean();
        data.setTitle(model.getTitle());
        if (model.getRefreshTimeout() > 0 && StringUtils.isNotBlank(model.getRedirectTo())) {
            data.setRefreshData(
                String.format(
                    Locale.US,
                    "<meta http-equiv=\"Refresh\" content=\"%d; url=%s\" />",
                    model.getRefreshTimeout(), model.getRedirectTo()
                )
            );
        }
        data.setModel(model);
        data.setStats(stats);
        generateReportFromTemplate(outFile, "retrospective", data);
        this.export(outFile, model.getReportSuffix(), formats, this.isImageExportable());
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.RETROSPECTIVE_OVERVIEW;
    }
    @Override
    public void validateParameters() {
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_DIRECTORY, ""),
            this.getOutputDirectory());
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_NAME, ""),
            this.getOutputName());
    }
    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.RETROSPECTIVE_OVERVIEW_URL;
    }

    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
        // TODO Auto-generated method stub
    }
    @Override
    public void execute(RetrospectiveBatch batch, boolean aggregate,
            String[] formats) throws Exception {
        for (RetrospectiveModel model : batch.getModels()) {
            this.executeReport(model, aggregate, formats);
        }
    }
    @Override
    public void execute(File config, boolean aggregate, String[] formats)
            throws Exception {
        Assert.assertTrue(this.constructErrorMessage(CucumberReportError.NON_EXISTING_CONFIG_FILE, ""),
            config.exists());
        validateParameters();
        String content = FileUtils.readFileToString(config);
        RetrospectiveBatch batch = null;
        try {
            batch = (RetrospectiveBatch) JsonReader.jsonToJava(content);
        } catch (Throwable e) {
            Assert.fail(this.constructErrorMessage(CucumberReportError.INVALID_CONFIG_FILE, ""));
        }
        this.execute(batch, aggregate, formats);
    }
}
