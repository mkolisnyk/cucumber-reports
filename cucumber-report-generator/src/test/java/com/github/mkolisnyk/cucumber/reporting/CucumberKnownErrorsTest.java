package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DimensionValue;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorOrderBy;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorPriority;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsInfo;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.utils.TestUtils;

public class CucumberKnownErrorsTest {
    @Test
    public void testGenerateReport() throws Exception {
        CucumberKnownErrorsReport results = new CucumberKnownErrorsReport();
        results.setOutputDirectory("target/known-errors");
        results.setOutputName("cucumber-results");
        results.setSourceFile("./src/test/resources/cucumber.json");
        KnownErrorsModel model = new KnownErrorsModel(
            new KnownErrorsInfo[] {
               new KnownErrorsInfo(
                   "Unable to reach Select Ticket screen",
                   "Select ticket isn't shown",
                   new DataDimension(DimensionValue.FAILED_STEP, "(.*)see the \"Select Ticket\" screen"),
                   KnownErrorPriority.HIGHEST),
               new KnownErrorsInfo(
                   "Search Fails",
                   "Some search parameters do not bring results",
                   new DataDimension(DimensionValue.FAILED_STEP, "(.*)the \"Out Search Results\" screen"),
                   KnownErrorPriority.HIGH),
               new KnownErrorsInfo(
                   "Unexpected message",
                   "Message box shows unexpected content",
                   new DataDimension(DimensionValue.FAILED_STEP, "(.*)message is shown"),
                   KnownErrorPriority.LOW),
            },
            KnownErrorOrderBy.FREQUENCY);
        results.execute(model, new String[] {"pdf", "png", "jpg"});
        //String json = JsonWriter.objectToJson(model);
        //FileUtils.writeStringToFile(new File("./src/test/resources/known-errors-source/sample_model.json"), json);
        File output = new File("target/known-errors/cucumber-results-known-errors.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        
        TestUtils.verifyXpathValue(content, "count(//table//tr)", "4");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]//td[2]/text()", "HIGH");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]/td[1]//p[2]/text()", "Some search parameters do not bring results");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]/td[1]/p[1]/b/text()", "Search Fails");
        TestUtils.verifyXpathValue(content, "//table//tr[2]/@class", "high");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]//th[3]/text()", "# of Occurrences");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]//th[2]/text()", "Priority");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]//th[1]/text()", "Description");

    }
    @Test
    public void testGenerateReportBigger() throws Exception {
        CucumberKnownErrorsReport results = new CucumberKnownErrorsReport();
        results.setOutputDirectory("target/known-errors");
        results.setOutputName("cucumber-results-2");
        results.setSourceFile("./src/test/resources/detailed-source/cucumber-2.json");
        results.execute(new File("./src/test/resources/known-errors-source/sample_model.json"), false);
        
        File output = new File("target/known-errors/cucumber-results-2-known-errors.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        
        TestUtils.verifyXpathValue(content, "count(//table//tr)", "1");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]//th[3]/text()", "# of Occurrences");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]//th[2]/text()", "Priority");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]//th[1]/text()", "Description");
    }
    @Test
    public void testGenerateReportIssue67() throws Exception {
        CucumberKnownErrorsReport results = new CucumberKnownErrorsReport();
        results.setOutputDirectory("target/known-errors");
        results.setOutputName("cucumber-results-67");
        results.setSourceFile("./src/test/resources/67/cucumber.json");
        results.execute(new File("./src/test/resources/67/known_errors.json"), false);

        File output = new File("target/known-errors/cucumber-results-67-known-errors.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        
        TestUtils.verifyXpathValue(content, "count(//table//tr)", "3");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]//td[2]/text()", "HIGH");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]/td[1]//p[2]/text()", "Some search parameters do not bring results");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[2]/td[1]/p[1]/b/text()", "Search Fails");
        TestUtils.verifyXpathValue(content, "//table//tr[2]/@class", "high");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]//th[3]/text()", "# of Occurrences");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]//th[2]/text()", "Priority");
        TestUtils.verifyXpathValue(content, "//table/tbody/tr[1]//th[1]/text()", "Description");
    }
}
