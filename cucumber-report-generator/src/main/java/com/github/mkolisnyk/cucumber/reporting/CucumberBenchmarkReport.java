package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.ConfigurableReport;
import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;
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

    private String[] titles;
    private CucumberFeatureResult[][] results;
    private String[] uniqueFeatureIds;
    private String[] uniqueScenarioIds;
    public CucumberBenchmarkReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }
    public CucumberBenchmarkReport() {
    }
    private String generateFeatureOverview() {
        String reportContent = "<h1>Features Status</h1>"
                + "<table><tr><th>Feature Name</th><th>"
                + StringUtils.join(titles, "</th><th>")
                + "</th></tr>";
        for (String id : uniqueFeatureIds) {
            BenchmarkRowData row = new BenchmarkRowData();
            row.addFeatureResults(id, results);
            reportContent = reportContent.concat(String.format("<tr><td>%s</td>", row.getName()));
            for (OverviewStats stats : row.getResults()) {
                if (stats.isEmpty()) {
                    reportContent = reportContent.concat("<td class=\"skipped\"></td>");
                } else {
                    reportContent = reportContent.concat(String.format(
                        "<td class=\"%s\">%d / %d / %d</td>",
                        stats.getFeatureStatus(),
                        stats.getScenariosPassed(),
                        stats.getScenariosFailed(),
                        stats.getScenariosUndefined()));
                }
            }
            reportContent = reportContent.concat("</tr>");
        }
        reportContent += "</table>";
        return reportContent;
    }
    private String generateScenarioOverview() {
        String reportContent = "<h1>Scenarios Status</h1>"
                + "<table><tr><th>Feature/Scenario Name</th><th>"
                + StringUtils.join(titles, "</th><th>")
                + "</th></tr>";
        CucumberScenarioResult[][] scenarioData = BenchmarkRowData.toScenarioList(results);
        for (String id : uniqueScenarioIds) {
            BenchmarkRowData row = new BenchmarkRowData();
            row.addScenarioResults(id, scenarioData);
            reportContent = reportContent.concat(String.format("<tr><td>%s</td>", row.getName()));
            for (OverviewStats stats : row.getResults()) {
                if (stats.isEmpty()) {
                    reportContent = reportContent.concat("<td class=\"skipped\"></td>");
                } else {
                    reportContent = reportContent.concat(String.format(
                            "<td class=\"%s\">%d / %d / %d</td>",
                            stats.getScenarioStatus(),
                            stats.getStepsPassed(),
                            stats.getStepsFailed(),
                            stats.getStepsUndefined()));
                }
            }
            reportContent = reportContent.concat("</tr>");
        }
        reportContent += "</table>";
        return reportContent;
    }
    private String generate() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/consolidated-tmpl.html");
        String result = IOUtils.toString(is);
        String reportContent = "";
        reportContent += this.generateFeatureOverview();
        reportContent += this.generateScenarioOverview();
        result = result.replaceAll("__REFRESH__", "");
        result = result.replaceAll("__TITLE__", "Benchmark");
        result = result.replaceAll("__REPORT__", reportContent);
        return result;
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
        String result = generate();
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-benchmark.html");
        FileUtils.writeStringToFile(outFile, result);
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
