package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.util.Locale;

import javax.xml.bind.JAXB;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class CucumberResultsReportTest {

    @Test
    public void testGenerateReport() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.execute();
        File outFile = new File(
                results.getOutputDirectory() + File.separator + results.getOutputName()
                + "-" + "feature-overview" + ".html");

        results.export(outFile, "feature-overview", new String[] {"pdf", "png", "jpg"}, true);
        /*File dump = new File("./target/cucumber-results-feature-overview-dump.xml");
        int[][] result = JAXB.unmarshal(dump, int[][].class);
        for (int[] row : result) {
            String text = "";
            for (int item : row) {
                text = text.concat("" + item + ";");
            }
            System.out.println(text);
        }*/
    }
    @Test
    public void testGenerateReportLocalized() throws Exception {
        Locale.setDefault(Locale.FRENCH);
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results-fr");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.execute();
        File outFile = new File(
                results.getOutputDirectory() + File.separator + results.getOutputName()
                + "-" + "feature-overview" + ".html");

        results.export(outFile, "feature-overview", new String[] {"pdf", "png", "jpg"}, true);
        /*File dump = new File("./target/cucumber-results-feature-overview-dump.xml");
        int[][] result = JAXB.unmarshal(dump, int[][].class);
        for (int[] row : result) {
            String text = "";
            for (int item : row) {
                text = text.concat("" + item + ";");
            }
            System.out.println(text);
        }*/
    }

    @Test
    public void testGenerateOverview100Report() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-100-results");
        results.setSourceFile("./src/test/resources/overview-sources/all-passed.json");
        results.executeOverviewReport("feature-overview-2", new String[] {"pdf"});
    }
    @Test
    public void testGenerateOverviewRubyReport() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target/ruby");
        results.setOutputName("cucumber-ruby-results");
        results.setSourceFile("./src/test/resources/ruby/cucumber.json");
        results.executeOverviewReport("feature-overview", new String[] {"pdf"});
    }
    @Test
    public void testGenerateOverviewReportIssue41() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-issue41-results");
        results.setSourceFile("./src/test/resources/detailed-source/issue41-2.json");
        results.execute();
    }
    @Test
    public void testGenerateOverviewLocalizedReportIssue41() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-issue41-localized");
        results.setSourceFile("./src/test/resources/detailed-source/localized-1.json");
        results.execute(new String[] {"pdf"});
        //results.execute();
    }

    @Test
    public void testGenerateOverviewReportIssue63() throws Exception {
        Locale.setDefault(Locale.FRANCE);
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-issue63-results");
        results.setSourceFile("./src/test/resources/63/gralTest.json");
        results.execute(true);
    }
    @Test
    public void testGenerateOverview100FailedReport() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-100-f-results");
        results.setSourceFile("./src/test/resources/overview-sources/all-failed.json");
        results.execute();
    }

    @Test
    public void testGenerateOverviewNoDataReport() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-0-results");
        results.setSourceFile("./src/test/resources/overview-sources/nothing.json");
        results.execute();
    }
    @Test
    public void testGenerateOverviewReportIssue83() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-83-results");
        results.setSourceFile("./src/test/resources/83/cucumber.json");
        results.execute();
    }
    @Test
    public void testGenerateOverviewEmptyElementsReport() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-empty-results");
        results.setSourceFile("./src/test/resources/overview-sources/empty-elements.json");
        results.execute();
    }

    @Test
    public void testGenerateDetailedReport() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.setScreenShotLocation("../src/test/resources/");
        results.execute(true, new String[] {});
        results.execute(false, new String[] {});
    }

    @Test
    public void testGenerateDetailedAggregatedReport() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.setScreenShotLocation("../src/test/resources/");
        results.execute(true, new String[] {"pdf"});
    }

    @Test
    public void testGenerateDetailedReportScreenShotWidth() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("cucumber-results-width");
        results.setSourceFile("./src/test/resources/cucumber.json");
        results.setScreenShotLocation("../src/test/resources/");
        results.setScreenShotWidth("200px");
        results.execute(true, new String[] {});
        results.execute(false, new String[] {});
    }

    @Test
    public void testDetailedReportIssue27() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("cucumber-results-27");
        results.setSourceFile("./src/test/resources/cucumber1.json");
        results.execute(true, new String[] {});
        results.execute(false, new String[] {});
    }
    @Test
    public void testLocalizedDetailedReportIssue41() throws Exception {
        // TODO: No features shown in feature overview
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("localized-results");
        results.setSourceFile("./src/test/resources/detailed-source/localized-1.json");
        results.execute(true, new String[] {});
        results.execute(false, new String[] {});
    }
    @Test
    public void testDetailedReportDocstring() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("docstring-results");
        results.setSourceFile("./src/test/resources/detailed-source/cucumber-docstring.json");
        results.execute(true, new String[] {"pdf"});
        //results.execute(false, new String[] {});
    }
    @Test
    public void testLocalizedDetailedReportIssue44() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/");
        results.setOutputName("issue44-results");
        results.setSourceFile("./src/test/resources/detailed-source/cucumber-2.json");
        results.execute(true, new String[] {});
        results.execute(false, new String[] {});
    }
    @Test
    public void testRubyDetailedReport() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/ruby");
        results.setOutputName("cucumber-ruby-results");
        results.setSourceFile("./src/test/resources/ruby/cucumber.json");
        results.execute(true, new String[] {});
        results.execute(false, new String[] {});
    }

    @Test
    public void testEmbedScreenshotDetailedReportIssue56() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setScreenShotLocation("screenshots/");
        results.setOutputDirectory("target/");
        results.setOutputName("issue56-results");
        results.setSourceFile("./src/test/resources/56/embed-screenshot.json");
        results.execute(true, new String[] {});
    }
    @Test
    public void testEmbedScreenshotDetailedReportWith2ImagesIssue56() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setScreenShotLocation("screenshots/");
        results.setScreenShotWidth("300");
        results.setOutputDirectory("target/");
        results.setOutputName("issue56-2-results");
        results.setSourceFile("./src/test/resources/56/embed-screenshot-2images.json");
        results.execute(true, new String[] {});
    }
    @Test
    public void testEmbedOutputTestIssue85() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setScreenShotLocation("screenshots/");
        results.setScreenShotWidth("300");
        results.setOutputDirectory("target/");
        results.setOutputName("issue85-results");
        results.setSourceFile("./src/test/resources/85/cucumber.json");
        results.execute(true, new String[] {});
    }
    @Test
    public void testEmbedOutputTestIssue122() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setScreenShotLocation("screenshots/");
        results.setScreenShotWidth("600");
        results.setOutputDirectory("target/");
        results.setOutputName("issue122-results");
        results.setSourceFile("./src/test/resources/122/cucumber.json");
        results.execute(true, new String[] {});
    }
    @Test
    public void testGenerateOverviewLocalizedReportFrench() throws Exception {
        CucumberResultsOverview overview = new CucumberResultsOverview();
        overview.setOutputDirectory("target/fr");
        overview.setOutputName("cucumber-localized");
        overview.setSourceFile("./src/test/resources/fr_locale/cucumber-2.json");
        overview.execute( new String[] {"pdf"});

        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory("target/fr");
        results.setOutputName("cucumber-localized");
        results.setSourceFile("./src/test/resources/fr_locale/cucumber-2.json");
        results.execute(true, new String[] {"pdf"});
        //results.execute();
    }
    @Test
    public void testMultipleTextStrings() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setScreenShotLocation("screenshots/");
        results.setScreenShotWidth("600");
        results.setOutputDirectory("target/");
        results.setOutputName("issue165-results");
        results.setSourceFile("./src/test/resources/165/cucumber.json");
        results.execute(true, new String[] {"pdf"});
    }
    @Test
    public void testIssue168SpecialCharacters() throws Exception {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setScreenShotLocation("screenshots");
        results.setScreenShotWidth("600");
        results.setOutputDirectory("target/");
        results.setOutputName("issue168-results");
        results.setSourceFile("./src/test/resources/168/cucumber.json");
        results.execute(true, new String[] {"pdf"});
    }
}
