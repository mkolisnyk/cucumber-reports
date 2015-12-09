package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Test;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownCellValue;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DimensionValue;

public class CucumberBreakdownReportTest {
    @Test
    public void testGenerateSmallReport() throws Exception {
        CucumberBreakdownReport report = new CucumberBreakdownReport();
        report.setOutputDirectory("target");
        report.setOutputName("cucumber-results");
        report.setSourceFile("./src/test/resources/cucumber.json");
        BreakdownTable table = new BreakdownTable();
        table.setCols(new DataDimension("All Features", DimensionValue.FEATURE, "(.*)",
                new DataDimension[] {
                new DataDimension("System Tag", DimensionValue.TAG, "@android",
                    new DataDimension[] {}),
                DataDimension.allFeatures(),
                DataDimension.allScenarios(),
                DataDimension.allSteps(),
                DataDimension.allTags(),
                new DataDimension("Any Search", DimensionValue.FEATURE, "(.*)Search(.*)",
                    new DataDimension[] {
                        new DataDimension("Special Tags", DimensionValue.TAG, "(.*)",
                            new DataDimension[] {
                                new DataDimension("System", DimensionValue.TAG, "@android"),
                                new DataDimension("iOS", DimensionValue.TAG, "@ios")
                            })
                })
            }));
        table.setRows(
                new DataDimension(DimensionValue.FEATURE, "(.*)",
                              new DataDimension[] {
                                  DataDimension.allFeatures(),
                                  DataDimension.allScenarios(),
                                  DataDimension.allSteps(),
                                  DataDimension.allTags()
                          }));
        table.setRows(new DataDimension("Journey Search", DimensionValue.FEATURE, "(.*)Search(.*)",
                new DataDimension[] {
                new DataDimension("Any tag", DimensionValue.TAG, "(.*)",
                    new DataDimension[] {}),
                new DataDimension("Negative Search", DimensionValue.SCENARIO, "(.*)[Nn]egative(.*)",
                        new DataDimension[] {}),
                new DataDimension("Return Journey", DimensionValue.SCENARIO, "(.*)return(.*)",
                    new DataDimension[] {
                        new DataDimension("Platforms", DimensionValue.TAG, "(.*)",
                            new DataDimension[] {
                                new DataDimension("Android", DimensionValue.TAG, "@android"),
                                new DataDimension("iOS", DimensionValue.TAG, "@ios")
                            })
                })
            }));
        //table.setCols(new DataDimension("All Features", DimensionValue.SCENARIO, "(.*)"));
        //table.setRows(new DataDimension("All Features", DimensionValue.SCENARIO, "(.*)"));
        table.setCell(BreakdownCellValue.SCENARIOS);
        report.executeReport("breakdown", table);
    }
}
