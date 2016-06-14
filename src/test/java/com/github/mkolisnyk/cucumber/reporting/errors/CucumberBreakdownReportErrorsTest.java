package com.github.mkolisnyk.cucumber.reporting.errors;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.testng.Assert;

import com.github.mkolisnyk.cucumber.reporting.CucumberBreakdownReport;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownCellDisplayType;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;

@RunWith(Parameterized.class)
public class CucumberBreakdownReportErrorsTest {
    private CucumberBreakdownReport report;
    private CucumberReportError expectedMessage;

    public CucumberBreakdownReportErrorsTest(CucumberBreakdownReport reportValue,
            CucumberReportError expectedMessageValue) {
        super();
        this.report = reportValue;
        this.expectedMessage = expectedMessageValue;
    }
    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(
            new Object[][] {
                {
                    new CucumberBreakdownReport() {
                        {
                            setOutputDirectory("target/error-breakdown");
                            setOutputName("cucumber-results");
                            //setSourceFile("");
                        }
                    },
                    CucumberReportError.NO_SOURCE_FILE
                },
                {
                    new CucumberBreakdownReport() {
                        {
                            //setOutputDirectory("target/error-breakdown");
                            setOutputName("cucumber-results");
                            setSourceFile("./src/test/resources/breakdown-source/cucumber.json");
                        }
                    },
                    CucumberReportError.NO_OUTPUT_DIRECTORY
                },
                {
                    new CucumberBreakdownReport() {
                        {
                            setOutputDirectory("target/error-breakdown");
                            //setOutputName("cucumber-results");
                            setSourceFile("./src/test/resources/breakdown-source/cucumber.json");
                        }
                    },
                    CucumberReportError.NO_OUTPUT_NAME
                },
                {
                    new CucumberBreakdownReport() {
                        {
                            setOutputDirectory("target/error-breakdown");
                            setOutputName("cucumber-results");
                            setSourceFile("./src/test/resources/breakdown-source/cucumber-ne-file.json");
                        }
                    },
                    CucumberReportError.NON_EXISTING_SOURCE_FILE
                },
            }
        );
    }
    @Test
    public void testBreakdownReportMissing() throws Exception {
        String actualMessage = "";
        BreakdownReportModel model = new BreakdownReportModel(
                new BreakdownReportInfo[] {
                    new BreakdownReportInfo(
                            new BreakdownTable(
                                    DataDimension.allFeatures(),
                                    DataDimension.allScenarios(),
                                    BreakdownCellDisplayType.BARS_ONLY
                            ), "report1", "First Breakdown", 3, ""),
                }
            );
            try {
                report.executeReport(model);
            } catch (AssertionError e) {
                actualMessage = e.getMessage();
            }
            Assert.assertTrue(actualMessage.startsWith(CucumberReportTypes.BREAKDOWN_REPORT.toString()),
                    "Report name is unexpected");
            Assert.assertTrue(actualMessage.contains(expectedMessage.toString()),
                    "Incorrect error message is shown");
    }
}
