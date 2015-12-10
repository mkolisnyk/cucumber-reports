package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownCellValue;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DimensionValue;

public class CucumberBreakdownReportTest {
    @Test
    public void testGenerateSmallReport() throws Exception {
        CucumberBreakdownReport report = new CucumberBreakdownReport();
        report.setOutputDirectory("target");
        report.setOutputName("cucumber-results");
        report.setSourceFile("./src/test/resources/breakdown-source/cucumber.json");
        BreakdownTable table = new BreakdownTable();
        table.setCols(new DataDimension("Screens", DimensionValue.CONTAINER, "(.*)",
                new DataDimension[] {
                    new DataDimension("Me", DimensionValue.CONTAINER, "(.*)",
                        new DataDimension[] {
                            new DataDimension("Recents and favourites", DimensionValue.STEP, "(.*)"),
                            new DataDimension("Login/Register", DimensionValue.CONTAINER, "(.*)",
                                new DataDimension[] {
                                    new DataDimension("Login", DimensionValue.STEP, "(.*)"),
                                    new DataDimension("Business Login", DimensionValue.STEP, "(.*)"),
                                    new DataDimension("Register", DimensionValue.STEP, "(.*)"),
                            }),
                        }),
                    new DataDimension("Journey Search", DimensionValue.CONTAINER, "(.*)",
                        new DataDimension[] {
                            new DataDimension("Live Times", DimensionValue.CONTAINER, "(.*)",
                                new DataDimension[] {
                                    new DataDimension("Search", DimensionValue.STEP, "(.*)"),
                                    new DataDimension("Search Results", DimensionValue.STEP, "(.*)"),
                                    new DataDimension("Live Progress", DimensionValue.STEP, "(.*)"),
                            }),
                            new DataDimension("Search", DimensionValue.CONTAINER, "(.*)", new DataDimension[] {
                                    new DataDimension("Plan and Buy", DimensionValue.STEP, "(.*)"),
                                    new DataDimension("Outbound Results", DimensionValue.STEP, "(.*)"),
                                    new DataDimension("Return Results", DimensionValue.STEP, "(.*)"),
                                    new DataDimension("Journey Info", DimensionValue.STEP, "(.*)"),
                                    new DataDimension("Select Ticket", DimensionValue.STEP, "(.*)"),
                            })
                    }),
                    new DataDimension("Payments", DimensionValue.CONTAINER, "(.*)",
                        new DataDimension[] {
                            new DataDimension("Payment Screen", DimensionValue.STEP, "(.*)"),
                            new DataDimension("Payment Methods", DimensionValue.STEP, "(.*)"),
                            new DataDimension("Payments Types", DimensionValue.CONTAINER, "(.*)",
                                new DataDimension[] {
                                    new DataDimension("Card", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("Add New Card", DimensionValue.STEP, "(.*)"),
                                            new DataDimension("Billing Address", DimensionValue.STEP, "(.*)")
                                    }),
                                    new DataDimension("Paypal", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("Login", DimensionValue.STEP, "(.*)"),
                                            new DataDimension("Terms and Conditions", DimensionValue.STEP, "(.*)"),
                                    })
                            })
                    }),
                    new DataDimension("My Tickets", DimensionValue.CONTAINER, "(.*)",
                        new DataDimension[] {
                            new DataDimension("My Tickets Screen", DimensionValue.STEP, "(.*)"),
                            new DataDimension("My Tickets", DimensionValue.CONTAINER, "(.*)",
                                new DataDimension[] {
                                    new DataDimension("Kiosk", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("Booking Info", DimensionValue.STEP, "(.*)")
                                        }),
                                    new DataDimension("Mobile", DimensionValue.CONTAINER, "(.*)",
                                            new DataDimension[] {
                                                new DataDimension("Booking Info", DimensionValue.STEP, "(.*)"),
                                                new DataDimension("Ticket Details", DimensionValue.STEP, "(.*)"),
                                                new DataDimension("Ticket Barcode", DimensionValue.STEP, "(.*)")
                                        })
                            })
                    }),
                })
        );
        table.setRows(
                new DataDimension(DimensionValue.FEATURE, "(.*)",
                              new DataDimension[] {
                                  DataDimension.allFeatures(),
                                  DataDimension.allScenarios(),
                                  DataDimension.allSteps(),
                                  DataDimension.allTags()
                          }));
        table.setRows(new DataDimension("Booking", DimensionValue.CONTAINER, "(.*)",
                new DataDimension[] {
                    new DataDimension("Kiosk", DimensionValue.FEATURE, "(.*)[Kk]iosk(.*)", new DataDimension[] {}),
                    //new DataDimension("Mobile", DimensionValue.FEATURE, "(.*)[Mm]obile(.*)", new DataDimension[] {}),
                    new DataDimension("Web", DimensionValue.STEP, "(.*)make a web booking(.*)", new DataDimension[] {}),
                    new DataDimension("Walkup", DimensionValue.FEATURE, "(.*)Walk-up(.*)", new DataDimension[] {}),
            }));
        //table.setCols(new DataDimension("All Features", DimensionValue.SCENARIO, "(.*)"));
        //table.setRows(new DataDimension("All Features", DimensionValue.SCENARIO, "(.*)"));
        table.setCell(BreakdownCellValue.SCENARIOS);
        report.executeReport(table);
    }
    @Test
    public void testGenerateMultipleReports() throws Exception {
        BreakdownReportModel model = new BreakdownReportModel(
            new BreakdownReportInfo[] {
                new BreakdownReportInfo(new BreakdownTable(), "report1", "First Breakdown", 10, ""),
                new BreakdownReportInfo(new BreakdownTable(
                        new DataDimension("Screens", DimensionValue.CONTAINER, "(.*)",
                                new DataDimension[] {
                                    new DataDimension("Me", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("Recents and favourites", DimensionValue.STEP, "(.*)"),
                                            new DataDimension("Login/Register", DimensionValue.CONTAINER, "(.*)",
                                                new DataDimension[] {
                                                    new DataDimension("Login", DimensionValue.STEP, "(.*)"),
                                                    new DataDimension("Business Login", DimensionValue.STEP, "(.*)"),
                                                    new DataDimension("Register", DimensionValue.STEP, "(.*)"),
                                            }),
                                        }),
                                    new DataDimension("Journey Search", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("Live Times", DimensionValue.CONTAINER, "(.*)",
                                                new DataDimension[] {
                                                    new DataDimension("Search", DimensionValue.STEP, "(.*)"),
                                                    new DataDimension("Search Results", DimensionValue.STEP, "(.*)"),
                                                    new DataDimension("Live Progress", DimensionValue.STEP, "(.*)"),
                                            }),
                                            new DataDimension("Search", DimensionValue.CONTAINER, "(.*)", new DataDimension[] {
                                                    new DataDimension("Plan and Buy", DimensionValue.STEP, "(.*)"),
                                                    new DataDimension("Outbound Results", DimensionValue.STEP, "(.*)"),
                                                    new DataDimension("Return Results", DimensionValue.STEP, "(.*)"),
                                                    new DataDimension("Journey Info", DimensionValue.STEP, "(.*)"),
                                                    new DataDimension("Select Ticket", DimensionValue.STEP, "(.*)"),
                                            })
                                    }),
                        }), new DataDimension("Booking", DimensionValue.CONTAINER, "(.*)",
                                new DataDimension[] {
                                new DataDimension("Kiosk", DimensionValue.FEATURE, "(.*)[Kk]iosk(.*)", new DataDimension[] {}),
                                new DataDimension("Mobile", DimensionValue.FEATURE, "(.*)[Mm]obile(.*)", new DataDimension[] {}),
                                new DataDimension("Web", DimensionValue.STEP, "(.*)make a web booking(.*)", new DataDimension[] {}),
                                new DataDimension("Walkup", DimensionValue.FEATURE, "(.*)Walk-up(.*)", new DataDimension[] {}),
                        })
                        ), "report2", "Second Breakdown", 10, ""),
                new BreakdownReportInfo(new BreakdownTable(
                        new DataDimension("Payments", DimensionValue.CONTAINER, "(.*)",
                                new DataDimension[] {
                                    new DataDimension("Payment Screen", DimensionValue.STEP, "(.*)"),
                                    new DataDimension("Payment Methods", DimensionValue.STEP, "(.*)"),
                                    new DataDimension("Payments Types", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("Card", DimensionValue.CONTAINER, "(.*)",
                                                new DataDimension[] {
                                                    new DataDimension("Add New Card", DimensionValue.STEP, "(.*)"),
                                                    new DataDimension("Billing Address", DimensionValue.STEP, "(.*)")
                                            }),
                                            new DataDimension("Paypal", DimensionValue.CONTAINER, "(.*)",
                                                new DataDimension[] {
                                                    new DataDimension("Login", DimensionValue.STEP, "(.*)"),
                                                    new DataDimension("Terms and Conditions", DimensionValue.STEP, "(.*)"),
                                            })
                                    })
                            })
            
                        , new DataDimension("Booking", DimensionValue.CONTAINER, "(.*)",
                                new DataDimension[] {
                                new DataDimension("Kiosk", DimensionValue.FEATURE, "(.*)[Kk]iosk(.*)", new DataDimension[] {}),
                                new DataDimension("Mobile", DimensionValue.FEATURE, "(.*)[Mm]obile(.*)", new DataDimension[] {}),
                                new DataDimension("Web", DimensionValue.STEP, "(.*)make a web booking(.*)", new DataDimension[] {}),
                                new DataDimension("Walkup", DimensionValue.FEATURE, "(.*)Walk-up(.*)", new DataDimension[] {}),
                        })
                        ), "report3", "Third Breakdown", 10, ""),
            }
        );
        CucumberBreakdownReport report = new CucumberBreakdownReport();
        report.setOutputDirectory("target/multi-breakdown");
        report.setOutputName("cucumber-results");
        report.setSourceFile("./src/test/resources/breakdown-source/cucumber.json");
        report.executeReport(model);
    }
    @Test
    public void testGenerateMultipleReportsSkipFirstRedirect() throws Exception {
        BreakdownReportModel model = new BreakdownReportModel(
            new BreakdownReportInfo[] {
                new BreakdownReportInfo(new BreakdownTable(), "report1", "First Breakdown", 0, ""),
                new BreakdownReportInfo(new BreakdownTable(), "report2", "Second Breakdown", 10, ""),
                new BreakdownReportInfo(new BreakdownTable(), "report3", "Third Breakdown", 10, ""),
                new BreakdownReportInfo(new BreakdownTable(), "report4", "Fourth Breakdown", 10, ""),
            }
        );
        CucumberBreakdownReport report = new CucumberBreakdownReport();
        report.setOutputDirectory("target/multi-breakdown/1");
        report.setOutputName("cucumber-results");
        report.setSourceFile("./src/test/resources/breakdown-source/cucumber.json");
        report.executeReport(model);
    }
    @Test
    public void testGenerateMultipleReportsSkipLastRedirect() throws Exception {
        BreakdownReportModel model = new BreakdownReportModel(
            new BreakdownReportInfo[] {
                new BreakdownReportInfo(new BreakdownTable(), "report1", "First Breakdown", 10, ""),
                new BreakdownReportInfo(new BreakdownTable(), "report2", "Second Breakdown", 10, ""),
                new BreakdownReportInfo(new BreakdownTable(), "report3", "Third Breakdown", 10, ""),
                new BreakdownReportInfo(new BreakdownTable(), "report4", "Fourth Breakdown", 0, ""),
            }
        );
        CucumberBreakdownReport report = new CucumberBreakdownReport();
        report.setOutputDirectory("target/multi-breakdown/2");
        report.setOutputName("cucumber-results");
        report.setSourceFile("./src/test/resources/breakdown-source/cucumber.json");
        report.executeReport(model);
    }
    @Test
    public void testGenerateMultipleReportsSkipMidRedirect() throws Exception {
        BreakdownReportModel model = new BreakdownReportModel(
            new BreakdownReportInfo[] {
                new BreakdownReportInfo(new BreakdownTable(), "report1", "First Breakdown", 5, ""),
                new BreakdownReportInfo(new BreakdownTable(), "report2", "Second Breakdown", 5, ""),
                new BreakdownReportInfo(new BreakdownTable(), "report3", "Third Breakdown", 0, ""),
                new BreakdownReportInfo(new BreakdownTable(), "report4", "Fourth Breakdown", 5, ""),
            }
        );
        CucumberBreakdownReport report = new CucumberBreakdownReport();
        report.setOutputDirectory("target/multi-breakdown/3");
        report.setOutputName("cucumber-results");
        report.setSourceFile("./src/test/resources/breakdown-source/cucumber.json");
        report.executeReport(model);
    }
    @Test
    public void testGenerateMultipleReportsLoadFromJSON() throws Exception {
        CucumberBreakdownReport report = new CucumberBreakdownReport();
        report.setOutputDirectory("target/multi-breakdown/5");
        report.setOutputName("cucumber-results");
        report.setSourceFile("./src/test/resources/breakdown-source/cucumber.json");
        report.executeReport(new File("src/test/resources/breakdown-source/simple.json"));
    }
}
