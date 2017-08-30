package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.testng.Assert;

import com.github.mkolisnyk.cucumber.utils.TestUtils;

public class CucumberSystemInfoTest {

    @Test
    public void testGenerateSystemInfo() throws Exception {
        CucumberSystemInfo report = new CucumberSystemInfo();
        report.setOutputDirectory("target/system-info");
        report.setOutputName("cucumber-results-1");
        report.execute(new String[] {"pdf"});
        
        File output = new File("target/system-info/cucumber-results-1-system-info.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        
        TestUtils.verifyXpathValue(content, "//h1[1]/text()", "System Properties");
        TestUtils.verifyXpathValue(content, "//table[1]//th[1]/text()", "Property");
        TestUtils.verifyXpathValue(content, "//table[1]//th[2]/text()", "Value");
        TestUtils.verifyXpathValue(content, "count(//table[1]//tr)", "" + (System.getProperties().size() + 1));

        TestUtils.verifyXpathValue(content, "(//h1)[2]/text()", "Environment Variables");
        TestUtils.verifyXpathValue(content, "//table[2]//th[1]/text()", "Variable");
        TestUtils.verifyXpathValue(content, "//table[2]//th[2]/text()", "Value");
        TestUtils.verifyXpathValue(content, "count(//table[2]//tr)", "" + (System.getenv().size() + 1));
    }
}
