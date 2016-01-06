package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DimensionValue;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorOrderBy;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorPriority;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsInfo;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;

public class CucumberKnownErrorsTest {
    @Test
    public void testGenerateReport() throws Exception {
        CucumberKnownErrorsReport results = new CucumberKnownErrorsReport();
        results.setOutputDirectory("target");
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
        results.executeKnownErrorsReport(model);
    }
/*    @Test
    public void testGenerateReportBigger() throws Exception {
        CucumberFeatureOverview results = new CucumberFeatureOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results-2");
        results.setSourceFile("./src/test/resources/detailed-source/cucumber-2.json");
        results.executeFeatureOverviewChartReport();
    }
    @Test
    public void testGenerateReportDry() throws Exception {
        CucumberFeatureOverview results = new CucumberFeatureOverview();
        results.setOutputDirectory("target");
        results.setOutputName("cucumber-results-dry");
        results.setSourceFile("./src/test/resources/cucumber-dry.json");
        results.executeFeatureOverviewChartReport();
    }*/
}
