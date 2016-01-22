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
                    new ConsolidatedItemInfo("Overview Chart", "target/cucumber-results-feature-overview-chart.html"),
                    new ConsolidatedItemInfo("Known Errors", "target/cucumber-results-known-errors.html"),
                    new ConsolidatedItemInfo("Feature Overview", "target/cucumber-results-feature-overview.html"),
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
                        new ConsolidatedItemInfo("Overview Chart", "target/cucumber-results-feature-overview-chart.html"),
                        new ConsolidatedItemInfo("Known Errors", "target/cucumber-results-known-errors.html"),
                        new ConsolidatedItemInfo("Feature Overview", "target/cucumber-results-feature-overview.html"),
                    },
                "batch1",
                "Overall Results Batch 1",
                true),
                new ConsolidatedReportModel(
                    new ConsolidatedItemInfo[] {
                        new ConsolidatedItemInfo("Overview Chart", "target/cucumber-results-2-feature-overview-chart.html"),
                        new ConsolidatedItemInfo("Test Coverage", "target/cucumber-results-coverage-filtered.html"),
                    },
                "batch2",
                "Overall Results Batch 2",
                false)
            }
        );
        results.executeConsolidatedReport(batch);
        //String json = JsonWriter.objectToJson(batch);
        //FileUtils.writeStringToFile(new File("./src/test/resources/consolidated-source/sample_batch.json"), json);
    }
    @Test
    public void testGenerateReportFromConfigFile() throws Exception {
        CucumberConsolidatedReport results = new CucumberConsolidatedReport();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.executeConsolidatedReport(new File("./src/test/resources/consolidated-source/sample_batch.json"), true);
    }
}
