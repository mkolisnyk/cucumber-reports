package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.ConfigurableReport;
import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkRowData;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberBenchmarkReport extends ConfigurableReport<BenchmarkReportModel> {

    private BenchmarkDataBean data;
    public CucumberBenchmarkReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }
    public CucumberBenchmarkReport() {
    }
    @Override
    public void execute(BenchmarkReportModel batch, boolean aggregate,
            String[] formats) throws Exception {
        String[] titles;
        CucumberFeatureResult[][] results;
        String[] uniqueFeatureIds;
        String[] uniqueScenarioIds;
        titles = new String[] {};

        results = new CucumberFeatureResult[][] {};
        uniqueFeatureIds = new String[] {};
        uniqueScenarioIds = new String[] {};
        data = new BenchmarkDataBean();
        for (BenchmarkReportInfo item : batch.getItems()) {
            CucumberFeatureResult[] result = this.readFileContent(item.getPath(), aggregate);
            titles = (String[]) ArrayUtils.add(titles, item.getTitle());
            results = (CucumberFeatureResult[][]) ArrayUtils.add(results, result);
            for (CucumberFeatureResult feature : result) {
                String id = feature.getId();
                if (!ArrayUtils.contains(uniqueFeatureIds, id)) {
                    uniqueFeatureIds = (String[]) ArrayUtils.add(uniqueFeatureIds, id);
                }
                for (CucumberScenarioResult scenario : feature.getElements()) {
                    id = scenario.getId();
                    if (!ArrayUtils.contains(uniqueScenarioIds, id)) {
                        uniqueScenarioIds = (String[]) ArrayUtils.add(uniqueScenarioIds, id);
                    }
                }
            }
        }
        data.setHeaders(titles);
        for (String id : uniqueFeatureIds) {
            BenchmarkRowData row = new BenchmarkRowData();
            row.addFeatureResults(id, results);
            data.setFeatureRows((BenchmarkRowData[]) ArrayUtils.add(data.getFeatureRows(), row));
        }
        CucumberScenarioResult[][] scenarioData = BenchmarkRowData.toScenarioList(results);
        for (String id : uniqueScenarioIds) {
            BenchmarkRowData row = new BenchmarkRowData();
            row.addScenarioResults(id, scenarioData);
            data.setScenarioRows((BenchmarkRowData[]) ArrayUtils.add(data.getScenarioRows(), row));
        }
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-benchmark.html");
        generateReportFromTemplate(outFile, "benchmark", data);
        this.export(outFile, "benchmark", formats, this.isImageExportable());
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
