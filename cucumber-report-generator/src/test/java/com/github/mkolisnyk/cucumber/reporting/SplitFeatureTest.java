package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

public class SplitFeatureTest {

    public SplitFeatureTest() {
        // TODO Auto-generated constructor stub
    }

    @Test
    public void testSingleDryRunScenario() throws Exception {
        CucumberSplitFeature report = new CucumberSplitFeature();
        report.setOutputDirectory("target/temp");
        report.setSourceFile("src/test/resources/134/cucumber.json");
        report.execute(true);
    }
    @Test
    public void testMultipleRunScenarioBig() throws Exception {
        CucumberSplitFeature report = new CucumberSplitFeature();
        report.setOutputDirectory("target/temp/big");
        report.setSourceFile("src/test/resources/cucumber.json");
        report.execute(true);
    }
}
