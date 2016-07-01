package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.junit.Test;

import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedItemInfo;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportBatch;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportModel;

public class CucumberConsolidatedReportTest {
    @Test
    public void testGenerateReportFromModel() throws Exception {
        CucumberConsolidatedReport results = new CucumberConsolidatedReport();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");

        ConsolidatedReportModel batch = new ConsolidatedReportModel(
                new ConsolidatedItemInfo[] {
                    new ConsolidatedItemInfo(
                        "Overview Chart",
                        "src/test/resources/consolidated-source/cucumber-results-feature-overview-chart.html"),
                    new ConsolidatedItemInfo(
                        "Known Errors",
                        "src/test/resources/consolidated-source/cucumber-results-known-errors.html"),
                    new ConsolidatedItemInfo(
                        "Feature Overview",
                        "src/test/resources/consolidated-source/cucumber-results-feature-overview.html"),
                },
                "consolidated",
                "Overall Results",
                true);
        results.executeConsolidatedReport(batch);
        //String json = JsonWriter.objectToJson(model);
        //FileUtils.writeStringToFile(new File("./src/test/resources/known-errors-source/sample_model.json"), json);
    }
    @Test
    public void testGenerateReportFromBatch() throws Exception {
        CucumberConsolidatedReport results = new CucumberConsolidatedReport();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");

        ConsolidatedReportBatch batch = new ConsolidatedReportBatch(
            new ConsolidatedReportModel[] {
                new ConsolidatedReportModel(
                    new ConsolidatedItemInfo[] {
                        new ConsolidatedItemInfo(
                            "Overview Chart",
                            "src/test/resources/consolidated-source/cucumber-results-feature-overview-chart.html"),
                        new ConsolidatedItemInfo(
                            "Known Errors",
                            "src/test/resources/consolidated-source/cucumber-results-known-errors.html"),
                        new ConsolidatedItemInfo(
                            "Feature Overview",
                            "src/test/resources/consolidated-source/cucumber-results-feature-overview.html"),
                    },
                "batch01",
                "Overall Results Batch 1",
                true),
                new ConsolidatedReportModel(
                    new ConsolidatedItemInfo[] {
                        new ConsolidatedItemInfo(
                            "Overview Chart",
                            "src/test/resources/consolidated-source/cucumber-results-2-feature-overview-chart.html"),
                        new ConsolidatedItemInfo(
                            "Test Coverage",
                            "src/test/resources/consolidated-source/cucumber-results-coverage-filtered.html"),
                    },
                "batch02",
                "Overall Results Batch 2",
                false)
            }
        );
        results.execute(batch, true);
        //String json = JsonWriter.objectToJson(batch);
        //FileUtils.writeStringToFile(new File("./src/test/resources/consolidated-source/sample_batch.json"), json);
    }
    @Test
    public void testGenerateReportFromConfigFile() throws Exception {
        CucumberConsolidatedReport results = new CucumberConsolidatedReport();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results");
        results.setPdfPageSize("A4 landscape");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.execute(new File("./src/test/resources/consolidated-source/sample_batch.json"), true);
    }
}
