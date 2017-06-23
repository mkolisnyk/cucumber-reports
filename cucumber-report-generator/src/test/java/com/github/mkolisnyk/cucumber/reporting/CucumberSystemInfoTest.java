package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

public class CucumberSystemInfoTest {

    @Test
    public void testGenerateSystemInfo() throws Exception {
        CucumberSystemInfo report = new CucumberSystemInfo();
        report.setOutputDirectory("target");
        report.setOutputName("cucumber-results");
        report.execute();
        report.setOutputName("cucumber-results-1");
        report.execute(new String[] {"pdf"});
    }
}
