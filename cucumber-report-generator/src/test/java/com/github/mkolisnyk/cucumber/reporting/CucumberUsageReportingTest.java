package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

public class CucumberUsageReportingTest {

    @Test
    public void testGenerateReport() throws Exception {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputName("cucumber-results");
        report.setOutputDirectory("target");
        report.setJsonUsageFile("./src/test/resources/cucumber-usage.json");
        report.execute();
    }
    @Test
    public void testGenerateReportLarge() throws Exception {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputName("cucumber-results");
        report.setOutputDirectory("target/large");
        report.setJsonUsageFile("./src/test/resources/cucumber-usage-large.json");
        report.execute();
    }
    @Test
    public void testGenerateReportWithBackRef() throws Exception {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputName("cucumber-results");
        report.setOutputDirectory("target/large");
        report.setJsonUsageFile("./src/test/resources/usage-source/sample5.json");
        report.execute();
    }
    @Test
    public void testGenerateReportForNullFile() throws Exception {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputName("cucumber-results");
        report.setOutputDirectory("target/none");
        report.setJsonUsageFile("./src/test/resources/usage-source/cucumber-empty-usage.json");
        report.execute();
    }
    @Test
    public void testGenerateReportFrLocalized() throws Exception {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputName("cucumber-results");
        report.setOutputDirectory("target/fr");
        report.setJsonUsageFile("./src/test/resources/fr_locale/cucumber-usage.json");
        report.execute(new String[] {"pdf"});
    }
    @Test
    public void testGenerateReportSpecialCharacters() throws Exception {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputName("cucumber-results");
        report.setOutputDirectory("target/170");
        report.setJsonUsageFile("./src/test/resources/170/cucumber-usage.json");
        report.execute(new String[] {"pdf"});
    }
    @Test
    public void testGenerateReportMultiSource() throws Exception {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputName("cucumber-results");
        report.setOutputDirectory("target/174");
        report.setJsonUsageFiles(
            new String[] {
//                "./src/test/resources/usage-source/sample1.json",
//                "./src/test/resources/usage-source/sample2.json",
                "./src/test/resources/cucumber-usage.json",
                "./src/test/resources/cucumber-usage-large.json",
                "./src/test/resources/usage-source/sample5.json",
            }
        );
        report.execute(new String[] {"pdf"});
    }
}
