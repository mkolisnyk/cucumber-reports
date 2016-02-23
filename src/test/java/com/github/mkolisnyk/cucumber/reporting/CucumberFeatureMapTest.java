package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.junit.Test;

public class CucumberFeatureMapTest {
    @Test
    public void testGenerateMultipleReportsLoadFromJSON() throws Exception {
        CucumberFeatureMapReport report = new CucumberFeatureMapReport();
        report.setOutputDirectory("target/feature-map/1");
        report.setOutputName("cucumber-results-feature-map");
        report.setSourceFile("./src/test/resources/breakdown-source/cucumber.json");
        report.executeReport(new File("src/test/resources/breakdown-source/simple.json"), true);
    }
}
