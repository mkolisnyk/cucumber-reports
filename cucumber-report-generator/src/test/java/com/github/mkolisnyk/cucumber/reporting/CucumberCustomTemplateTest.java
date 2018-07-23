package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

public class CucumberCustomTemplateTest {

    @Test
    public void testOverviewWithCustomTemplate() throws Exception {
        CucumberResultsOverview report = new CucumberResultsOverview();
        report.setOutputDirectory("target/172");
        report.setOutputName("issue172-direct");
        report.setSourceFile("src/test/resources/cucumber.json");
        report.setTemplatesLocation("src/test/resources/templates/single_tmpl.json");
        report.execute(true);
    }
    
}
