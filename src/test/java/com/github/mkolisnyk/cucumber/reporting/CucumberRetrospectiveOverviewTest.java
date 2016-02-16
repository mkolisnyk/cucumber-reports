package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.cedarsoftware.util.io.JsonWriter;
import com.github.mkolisnyk.cucumber.reporting.types.retrospective.RetrospectiveBatch;
import com.github.mkolisnyk.cucumber.reporting.types.retrospective.RetrospectiveModel;

public class CucumberRetrospectiveOverviewTest {

    @Test
    public void testGenerateReportFromModel() throws Exception {
        CucumberRetrospectiveOverviewReport report = new CucumberRetrospectiveOverviewReport();
        report.setOutputDirectory("./target");
        report.setOutputName("cucumber-results");
        RetrospectiveModel model
            = new RetrospectiveModel(
                    "retro1",
                    "Nightly Runs Retrospective",
                    "(.*)retrospective-source.1(.*)json", 600, 200);
        report.executeReport(model, true, true);
    }
    @Test
    public void testGenerateReportFromBatch() throws Exception {
        CucumberRetrospectiveOverviewReport report = new CucumberRetrospectiveOverviewReport();
        report.setOutputDirectory("./target");
        report.setOutputName("cucumber-results");
        RetrospectiveBatch batch = new RetrospectiveBatch(
            new RetrospectiveModel[] {
                new RetrospectiveModel(
                        "retro2",
                        "Nightly Runs Retrospective",
                        "(.*)retrospective-source.1(.*)json", 600, 200),
                new RetrospectiveModel(
                        "retro3",
                        "Restricted Runs Retrospective",
                        "(.*)retrospective-source.1.131(.*)json", 300, 400)
            }
        );
        report.executeReport(batch, true, false);
        String json = JsonWriter.objectToJson(batch);
        FileUtils.writeStringToFile(new File("./src/test/resources/retrospective-source/sample_batch.json"), json);
    }
    @Test
    public void testGenerateReportFromConfigFile() throws Exception {
        CucumberRetrospectiveOverviewReport report = new CucumberRetrospectiveOverviewReport();
        report.setOutputDirectory("./target");
        report.setOutputName("cucumber-results-batch");
        report.executeReport(new File("./src/test/resources/retrospective-source/sample_batch.json"), true, true);
    }
}
