package com.github.mkolisnyk.cucumber.reporting.errors;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.github.mkolisnyk.cucumber.reporting.CucumberCoverageOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberOverviewChartsReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberRetrospectiveOverviewReport;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

@RunWith(Parameterized.class)
public class CucumberCoverageAndOverviewReportTest {
    private String outputDirectory;
    private String outputName;
    private String sourceFile;
    private CucumberReportError expectedMessage;

    public CucumberCoverageAndOverviewReportTest(String outputDirectoryValue,
            String outputNameValue, String sourceFileValue,
            CucumberReportError expectedMessageValue) {
        super();
        this.outputDirectory = outputDirectoryValue;
        this.outputName = outputNameValue;
        this.sourceFile = sourceFileValue;
        this.expectedMessage = expectedMessageValue;
    }

    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {"target", "cucumber-results", null,
                        CucumberReportError.NO_SOURCE_FILE },
                {"target", "cucumber-results",
                        "./src/test/resources/cucumber-non-existing.json",
                        CucumberReportError.NON_EXISTING_SOURCE_FILE },
                {null, "cucumber-results",
                        "./src/test/resources/cucumber.json",
                        CucumberReportError.NO_OUTPUT_DIRECTORY },
                {"target", null, "./src/test/resources/cucumber.json",
                        CucumberReportError.NO_OUTPUT_NAME }, });
    }

    @Test
    public void testVerifyErrorMessagesCoverageReport() throws Exception {
        String actualMessage = "";
        CucumberCoverageOverview results = new CucumberCoverageOverview();
        results.setOutputDirectory(outputDirectory);
        results.setOutputName(outputName);
        results.setSourceFile(sourceFile);

        try {
            results.execute();
        } catch (AssertionError e) {
            actualMessage = e.getMessage();
        }
        Assert.assertTrue("Report name is unexpected", actualMessage
                .startsWith(CucumberReportTypes.COVERAGE_OVERVIEW.toString()));
        Assert.assertTrue("Incorrect error message is shown",
                actualMessage.contains(expectedMessage.toString()));
        Assert.assertTrue("Report URL wasn't found", actualMessage
                .contains(CucumberReportLink.COVERAGE_OVERVIEW_URL.toString()));
    }

    @Test
    public void testVerifyErrorMessagesOverviewReport() throws Exception {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory(outputDirectory);
        results.setOutputName(outputName);
        results.setSourceFile(sourceFile);
        String actualMessage = "";
        try {
            results.execute();
        } catch (AssertionError e) {
            actualMessage = e.getMessage();
        }
        Assert.assertTrue("Report name is unexpected", actualMessage
                .startsWith(CucumberReportTypes.RESULTS_OVERVIEW.toString()));
        Assert.assertTrue("Incorrect error message is shown",
                actualMessage.contains(expectedMessage.toString()));
        Assert.assertTrue("Report URL wasn't found", actualMessage
                .contains(CucumberReportLink.RESULTS_OVERVIEW_URL.toString()));
    }

    @Test
    public void testVerifyErrorMessagesFeatureOverviewReport() throws Exception {
        CucumberFeatureOverview results = new CucumberFeatureOverview();
        results.setOutputDirectory(outputDirectory);
        results.setOutputName(outputName);
        results.setSourceFile(sourceFile);
        String actualMessage = "";
        try {
            results.execute();
        } catch (AssertionError e) {
            actualMessage = e.getMessage();
        }
        Assert.assertTrue("Report name is unexpected", actualMessage
                .startsWith(CucumberReportTypes.FEATURE_OVERVIEW.toString()));
        Assert.assertTrue("Incorrect error message is shown",
                actualMessage.contains(expectedMessage.toString()));
        Assert.assertTrue("Report URL wasn't found", actualMessage
                .contains(CucumberReportLink.FEATURE_OVERVIEW_URL.toString()));
    }
    @Test
    public void testVerifyErrorMessagesOverviewChartsReport() throws Exception {
        //CucumberFeatureOverview results = new CucumberFeatureOverview();
        ExtendedRuntimeOptions options = new ExtendedRuntimeOptions();
        CucumberOverviewChartsReport results = new CucumberOverviewChartsReport(options);
        results.setOutputDirectory(outputDirectory);
        results.setOutputName(outputName);
        results.setSourceFile(sourceFile);
        String actualMessage = "";
        try {
            results.execute();
        } catch (AssertionError e) {
            actualMessage = e.getMessage();
        }
        Assert.assertTrue("Report name is unexpected", actualMessage
                .startsWith(CucumberReportTypes.CHARTS_REPORT.toString()));
        Assert.assertTrue("Incorrect error message is shown",
                actualMessage.contains(expectedMessage.toString()));
        Assert.assertTrue("Report URL wasn't found", actualMessage
                .contains(CucumberReportLink.CHART_URL.toString()));
    }
    @Test
    public void testVerifyErrorMessagesRetrospectiveReport() throws Exception {
        CucumberRetrospectiveOverviewReport results = new CucumberRetrospectiveOverviewReport();
        results.setOutputDirectory(outputDirectory);
        results.setOutputName(outputName);
        results.setSourceFile(sourceFile);
        String actualMessage = "";
        if (expectedMessage.equals(CucumberReportError.NO_SOURCE_FILE)
            || expectedMessage.equals(CucumberReportError.NON_EXISTING_SOURCE_FILE)) {
            return;
        }
        try {
            results.execute(new File("./src/test/resources/retrospective-source/sample_batch.json"), true, true);
        } catch (AssertionError e) {
            actualMessage = e.getMessage();
        }
        Assert.assertTrue("Report name is unexpected. Actual message is: " + actualMessage,
            actualMessage.startsWith(CucumberReportTypes.RETROSPECTIVE_OVERVIEW.toString()));
        Assert.assertTrue("Incorrect error message is shown",
            actualMessage.contains(expectedMessage.toString()));
        Assert.assertTrue("Report URL wasn't found",
            actualMessage.contains(CucumberReportLink.RETROSPECTIVE_OVERVIEW_URL.toString()));
    }
}
