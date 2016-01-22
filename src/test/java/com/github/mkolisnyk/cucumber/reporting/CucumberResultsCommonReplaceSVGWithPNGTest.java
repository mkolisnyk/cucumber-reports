package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class CucumberResultsCommonReplaceSVGWithPNGTest {

    @Test
    public void testReplaceSVGForOverviewReport() throws Exception {
        CucumberResultsOverview common = new CucumberResultsOverview();
        File htmlFile = new File("./src/test/resources/pdf-conversion/cucumber-feature-overview.html");
        common.setOutputDirectory("target");
        String result = common.replaceSvgWithPng(htmlFile);
        FileUtils.writeStringToFile(new File("./target/sample-output.html"), result);
    }
}
