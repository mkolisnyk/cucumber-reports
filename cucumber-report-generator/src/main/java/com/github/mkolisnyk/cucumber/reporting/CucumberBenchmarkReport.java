package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.ConfigurableReport;
import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class CucumberBenchmarkReport extends ConfigurableReport<BenchmarkReportModel> {

    private String[] titles;
    private CucumberFeatureResult[][] results;
    private String[] uniqueFeatureIds;
    private String[] uniqueScenarioIds;
    private void generate() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/consolidated-tmpl.html");
        String result = IOUtils.toString(is);
    }
    @Override
    public void execute(BenchmarkReportModel batch, boolean aggregate,
            String[] formats) throws Exception {
        titles = new String[] {};
        results = new CucumberFeatureResult[][] {};
        uniqueFeatureIds = new String[] {};
        uniqueScenarioIds = new String[] {};
        for (BenchmarkReportInfo item : batch.getItems()) {
            CucumberFeatureResult[] result = this.readFileContent(item.getPath(), aggregate);
            titles = (String[]) ArrayUtils.add(titles, item.getTitle());
            results = (CucumberFeatureResult[][]) ArrayUtils.add(results, result);
            for (CucumberFeatureResult feature : result) {
                String id = feature.getId();
                if (ArrayUtils.contains(uniqueFeatureIds, id)) {
                    uniqueFeatureIds = (String[]) ArrayUtils.add(uniqueFeatureIds, id);
                }
                for (CucumberScenarioResult scenario : feature.getElements()) {
                    id = scenario.getId();
                    if (ArrayUtils.contains(uniqueScenarioIds, id)) {
                        uniqueScenarioIds = (String[]) ArrayUtils.add(uniqueScenarioIds, id);
                    }
                }
            }
        }
        generate();
    }

    @Override
    public void execute(File config, boolean aggregate, String[] formats)
            throws Exception {
        execute(config, true, formats);
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.BENCHMARK_REPORT;
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.BENCHMARK_URL;
    }

    @Override
    public void validateParameters() {
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_DIRECTORY, ""),
                this.getOutputDirectory());
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_NAME, ""),
                this.getOutputName());
    }
}
