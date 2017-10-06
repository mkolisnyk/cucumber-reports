package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedItemInfo;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportBatch;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportModel;
import com.github.mkolisnyk.cucumber.utils.TestUtils;

public class CucumberConsolidatedReportTest {
    @Test
    public void testGenerateReportFromModel() throws Exception {
        CucumberConsolidatedReport results = new CucumberConsolidatedReport();
        results.setOutputDirectory("target/consolidated/model");
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
                true,
                -1);
        results.executeConsolidatedReport(batch);
        File output = new File("target/consolidated/model/cucumber-results-consolidated.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        TestUtils.verifyXpathValue(content, "//h1[1]/text()", "Table of Contents");
        TestUtils.verifyXpathValue(content, "//a[@id='overview-chart']/h1/text()", "Overview Chart");
        TestUtils.verifyXpathValue(content, "//a[@id='known-errors']/h1/text()", "Known Errors");
        TestUtils.verifyXpathValue(content, "//a[@id='feature-overview']/h1/text()", "Feature Overview");

        TestUtils.verifyXpathValue(content, "//ol[1]/li[1]/a/text()", "Overview Chart");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[2]/a/text()", "Known Errors");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[3]/a/text()", "Feature Overview");

        TestUtils.verifyXpathValue(content, "//ol[1]/li[1]/a/@href", "#overview-chart");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[2]/a/@href", "#known-errors");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[3]/a/@href", "#feature-overview");

        TestUtils.verifyXpathValue(content, "(//a[@id='feature-overview']/following-sibling::h2)[1]", "Overview Chart");
        TestUtils.verifyXpathValue(content, "(//a[@id='feature-overview']/following-sibling::h2)[2]", "Features Status");
        TestUtils.verifyXpathValue(content, "(//a[@id='feature-overview']/following-sibling::h2)[3]", "Scenario Status");
    }
    @Test
    public void testGenerateReportFromBatch() throws Exception {
        CucumberConsolidatedReport results = new CucumberConsolidatedReport();
        results.setOutputDirectory("target/consolidated/batch");
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
        results.execute(batch, new String[] {"pdf"});
        File output = new File("target/consolidated/batch/cucumber-results-batch01.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        TestUtils.verifyXpathValue(content, "//h1[1]/text()", "Table of Contents");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[1]/a/@href", "#overview-chart");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[2]/a/@href", "#known-errors");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[3]/a/@href", "#feature-overview");
        TestUtils.verifyXpathValue(content, "//a[@id='overview-chart']/h1/text()", "Overview Chart");
        TestUtils.verifyXpathValue(content, "//a[@id='known-errors']/h1/text()", "Known Errors");
        TestUtils.verifyXpathValue(content, "//a[@id='feature-overview']/h1/text()", "Feature Overview");

        TestUtils.verifyXpathValue(content, "//ol[1]/li[1]/a/text()", "Overview Chart");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[2]/a/text()", "Known Errors");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[3]/a/text()", "Feature Overview");

        TestUtils.verifyXpathValue(content, "(//a[@id='feature-overview']/following-sibling::h2)[1]", "Overview Chart");
        TestUtils.verifyXpathValue(content, "(//a[@id='feature-overview']/following-sibling::h2)[2]", "Features Status");
        TestUtils.verifyXpathValue(content, "(//a[@id='feature-overview']/following-sibling::h2)[3]", "Scenario Status");

        output = new File("target/consolidated/batch/cucumber-results-batch02.html");
        Assert.assertTrue(output.exists());
        content = FileUtils.readFileToString(output);
        TestUtils.verifyXpathValue(content, "//a[@id='overview-chart']/h1/text()", "Overview Chart");
        TestUtils.verifyXpathValue(content, "//a[@id='test-coverage']/h1/text()", "Test Coverage");

        TestUtils.verifyXpathValue(content, "(//a[@id='test-coverage']/following-sibling::h2)[1]", "Overview Chart");
        TestUtils.verifyXpathValue(content, "(//a[@id='test-coverage']/following-sibling::h2)[2]", "Features Status");
        TestUtils.verifyXpathValue(content, "(//a[@id='test-coverage']/following-sibling::h2)[3]", "Scenario Status");

        //String json = JsonWriter.objectToJson(batch);
        //FileUtils.writeStringToFile(new File("./src/test/resources/consolidated-source/sample_batch.json"), json);
    }
    @Test
    public void testGenerateReportFromConfigFile() throws Exception {
        CucumberConsolidatedReport results = new CucumberConsolidatedReport();
        results.setOutputDirectory("target/consolidated/config");
        results.setOutputName("cucumber-results");
        results.setPdfPageSize("A4 landscape");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.execute(new File("./src/test/resources/consolidated-source/sample_batch.json"), new String[] {"pdf"});
        
        File output = new File("target/consolidated/config/cucumber-results-batch-config1.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        TestUtils.verifyXpathValue(content, "//h1[1]/text()", "Table of Contents");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]/td[1]/div/a[@id='overview-chart']/h1/text()", "Overview Chart");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]/td[2]/div/a[@id='known-errors']/h1/text()", "Known Errors");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]/td[1]/div/a[@id='feature-overview']/h1/text()", "Feature Overview");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]/td[2]/div/a[@id='detailed-results']/h1/text()", "Detailed Results");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[3]/td[1]/div/a[@id='usage-report']/h1/text()", "Usage Report");

        TestUtils.verifyXpathValue(content, "//ol[1]/li[1]/a/text()", "Overview Chart");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[2]/a/text()", "Known Errors");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[3]/a/text()", "Feature Overview");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[4]/a/text()", "Detailed Results");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[5]/a/text()", "Usage Report");

        TestUtils.verifyXpathValue(content, "//ol[1]/li[1]/a/@href", "#overview-chart");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[2]/a/@href", "#known-errors");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[3]/a/@href", "#feature-overview");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[4]/a/@href", "#detailed-results");
        TestUtils.verifyXpathValue(content, "//ol[1]/li[5]/a/@href", "#usage-report");

        TestUtils.verifyXpathValue(content, "(//a[@id='feature-overview']/following-sibling::h2)[1]", "Overview Chart");
        TestUtils.verifyXpathValue(content, "(//a[@id='feature-overview']/following-sibling::h2)[2]", "Features Status");
        TestUtils.verifyXpathValue(content, "(//a[@id='feature-overview']/following-sibling::h2)[3]", "Scenario Status");

        output = new File("target/consolidated/config/cucumber-results-batch-config2.html");
        Assert.assertTrue(output.exists());
        content = FileUtils.readFileToString(output);
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]/td[1]/div/a[@id='overview-chart']/h1/text()", "Overview Chart");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]/td[2]/div/a[@id='test-coverage']/h1/text()", "Test Coverage");

        TestUtils.verifyXpathValue(content, "(//a[@id='test-coverage']/following-sibling::h2)[1]", "Overview Chart");
        TestUtils.verifyXpathValue(content, "(//a[@id='test-coverage']/following-sibling::h2)[2]", "Features Status");
        TestUtils.verifyXpathValue(content, "(//a[@id='test-coverage']/following-sibling::h2)[3]", "Scenario Status");

    }
}
