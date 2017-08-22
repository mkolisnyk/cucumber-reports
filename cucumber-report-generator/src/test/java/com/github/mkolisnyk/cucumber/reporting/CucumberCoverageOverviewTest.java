package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.testng.Assert;

import com.github.mkolisnyk.cucumber.utils.TestUtils;

public class CucumberCoverageOverviewTest {
    @Test
    public void testGenerateReport() throws Exception {
        CucumberCoverageOverview results = new CucumberCoverageOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber-dry.json");
        results.setExcludeCoverageTags(new String[]{"@flaky"});
        results.setIncludeCoverageTags(new String[]{"@passed"});
        results.execute();
        
        File output = new File("target/cucumber-results-coverage.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        TestUtils.verifyXpathValue(content, "//h1[1]/text()", "Overview Chart");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]/th[1]/text()", "Features Status");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]/th[2]/text()", "Scenario Status");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]/td/@class", "chart");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]/td/@class", "chart");

        TestUtils.verifyXpathValue(content, "//h1[2]/text()", "Features Status");
        TestUtils.verifyXpathValue(content, "count(//table[2]/tbody/tr)", "4");
        
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[1]/text()", "Feature Name");   
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[2]/text()", "Status");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[3]/text()", "Scenarios");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[4]/text()", "Tags");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[2]/th[1]/text()", "Covered"); 
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[2]/th[2]/text()", "Not Covered");
        
        TestUtils.verifyXpathValue(content, "//h1[3]/text()", "Scenario Status");
        TestUtils.verifyXpathValue(content, "count(//table[3]/tbody/tr)", "15");
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[1]/th[1]/text()", "Feature Name");   
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[1]/th[3]/text()", "Status");
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[1]/th[2]/text()", "Scenario");
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[1]/th[4]/text()", "Steps");
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[1]/th[5]/text()", "Tags");
        
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[2]/th[1]/text()", "Covered"); 
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[2]/th[2]/text()", "Not Covered");
    }
    @Test
    public void testGenerateReportWithExcludeIncludeTags() throws Exception {
        CucumberCoverageOverview results = new CucumberCoverageOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results-filtered");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.setExcludeCoverageTags(new String[]{"@android"});
        results.setIncludeCoverageTags(new String[]{"@ios"});
        results.execute();
        
        
        File output = new File("target/cucumber-results-filtered-coverage.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        TestUtils.verifyXpathValue(content, "//h1[1]/text()", "Overview Chart");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]/th[1]/text()", "Features Status");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]/th[2]/text()", "Scenario Status");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]/td/@class", "chart");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]/td/@class", "chart");

        TestUtils.verifyXpathValue(content, "//h1[2]/text()", "Features Status");
        TestUtils.verifyXpathValue(content, "count(//table[2]/tbody/tr)", "9");
       
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[1]/text()", "Feature Name");   
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[2]/text()", "Status");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[3]/text()", "Scenarios");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[4]/text()", "Tags");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[2]/th[1]/text()", "Covered"); 
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[2]/th[2]/text()", "Not Covered");
        
        TestUtils.verifyXpathValue(content, "//h1[3]/text()", "Scenario Status");
        TestUtils.verifyXpathValue(content, "count(//table[3]/tbody/tr)", "54");

        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[1]/th[1]/text()", "Feature Name");   
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[1]/th[3]/text()", "Status");
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[1]/th[2]/text()", "Scenario");
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[1]/th[4]/text()", "Steps");
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[1]/th[5]/text()", "Tags");
        
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[2]/th[1]/text()", "Covered"); 
        TestUtils.verifyXpathValue(content, "//table[3]/tbody/tr[2]/th[2]/text()", "Not Covered");
    }
}
