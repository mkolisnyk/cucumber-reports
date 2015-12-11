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
        report.setSourceFile("./src/test/resources/breakdown-source/cucumber-2.json");
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
                    new DataDimension("Kiosk", DimensionValue.FEATURE, "(.*)[Kk]iosk(.*)",
                        new DataDimension[] {
                            new DataDimension("Registered", DimensionValue.FEATURE, "(.*)",
                                new DataDimension[] {
                                    new DataDimension("Payment Types", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("By Card", DimensionValue.FEATURE, "(.*)",
                                                new DataDimension[] {
                                                    new DataDimension("Visa Credit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Mastercard", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Maestro", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Visa Debit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Amex", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Diners", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Mastercard Debit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                }
                                            ),
                                            new DataDimension("By PayPal", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                }),
                                    new DataDimension("Journey Types", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("Within an hour", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Within 3 hours", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Far into the future", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Each way fare", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                        }
                                    ),
                                    new DataDimension("Actions", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("View Details", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Share trip details", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                        }
                                    )
                                }
                            ),
                            new DataDimension("Guest", DimensionValue.FEATURE, "(.*)", new DataDimension[] {
                                new DataDimension("Payment Types", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("By Card", DimensionValue.FEATURE, "(.*)",
                                                new DataDimension[] {
                                                    new DataDimension("Visa Credit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Mastercard", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Maestro", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Visa Debit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Amex", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Diners", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    new DataDimension("Mastercard Debit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                }
                                            ),
                                            new DataDimension("By PayPal", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                }),
                                    new DataDimension("Journey Types", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("Within an hour", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Within 3 hours", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Far into the future", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Each way fare", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                        }
                                    ),
                                    new DataDimension("Actions", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("View Details", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Share trip details", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                        }
                                    )
                                }
                            ),
                        }),
                    new DataDimension("Mobile", DimensionValue.FEATURE, "(.*)[Mm]obile(.*)",
                        new DataDimension[] {
                            new DataDimension("Registered", DimensionValue.FEATURE, "(.*)",
                                    new DataDimension[] {
                                        new DataDimension("Payment Types", DimensionValue.CONTAINER, "(.*)",
                                            new DataDimension[] {
                                                new DataDimension("By Card", DimensionValue.FEATURE, "(.*)",
                                                    new DataDimension[] {
                                                        new DataDimension("Visa Credit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Mastercard", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Maestro", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Visa Debit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Amex", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Diners", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Mastercard Debit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    }
                                                ),
                                                new DataDimension("By PayPal", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                    }),
                                        new DataDimension("Journey Types", DimensionValue.CONTAINER, "(.*)",
                                            new DataDimension[] {
                                                new DataDimension("Within an hour", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Within 3 hours", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Far into the future", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Each way fare", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            }
                                        ),
                                        new DataDimension("Actions", DimensionValue.CONTAINER, "(.*)",
                                            new DataDimension[] {
                                                new DataDimension("View Details", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Share trip details", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            }
                                        )
                                    }
                                ),
                                new DataDimension("Guest", DimensionValue.FEATURE, "(.*)", new DataDimension[] {
                                    new DataDimension("Payment Types", DimensionValue.CONTAINER, "(.*)",
                                            new DataDimension[] {
                                                new DataDimension("By Card", DimensionValue.FEATURE, "(.*)",
                                                    new DataDimension[] {
                                                        new DataDimension("Visa Credit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Mastercard", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Maestro", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Visa Debit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Amex", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Diners", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                        new DataDimension("Mastercard Debit", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                    }
                                                ),
                                                new DataDimension("By PayPal", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                    }),
                                        new DataDimension("Journey Types", DimensionValue.CONTAINER, "(.*)",
                                            new DataDimension[] {
                                                new DataDimension("Within an hour", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Within 3 hours", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Far into the future", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Each way fare", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            }
                                        ),
                                        new DataDimension("Actions", DimensionValue.CONTAINER, "(.*)",
                                            new DataDimension[] {
                                                new DataDimension("View Details", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Share trip details", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Download", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Activate", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Refund", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                                new DataDimension("Move", DimensionValue.FEATURE, "(.*)", new DataDimension[] {}),
                                            }
                                        )
                                    }
                                ),
                            }),
                    new DataDimension("Web", DimensionValue.STEP, "(.*)make a web booking(.*)",
                        new DataDimension[] {
                            new DataDimension("Leisure", DimensionValue.CONTAINER, "(.*)",
                                new DataDimension[] {
                                    new DataDimension("Ticket Type", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("Anytime Day Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Cheapest Standard Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Anytime Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Advance Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Cheapest First Class Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("First Advance Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Off-Peak Day Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("First Anytime Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                        }
                                    ),
                                    new DataDimension("Delivery Option", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("Kiosk", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("First class post", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("PYO", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Royal Mail", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("International post", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Mobile", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                        }
                                    ),
                                }
                            ),
                            new DataDimension("Business", DimensionValue.CONTAINER, "(.*)",
                                new DataDimension[] {
                                    new DataDimension("Ticket Type", DimensionValue.CONTAINER, "(.*)", 
                                        new DataDimension[] {
                                            new DataDimension("Anytime Day Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Cheapest Standard Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Anytime Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Advance Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Cheapest First Class Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("First Advance Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Off-Peak Day Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("First Anytime Single", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                        }
                                    ),
                                    new DataDimension("Delivery Option", DimensionValue.CONTAINER, "(.*)",
                                        new DataDimension[] {
                                            new DataDimension("Kiosk", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("First class post", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Next day delivery", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("Office delivery", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                            new DataDimension("PYO", DimensionValue.STEP, "(.*)", new DataDimension[] {}),
                                        }
                                    ),
                                }
                            ),
                        }
                    ),
                    new DataDimension("Walkup", DimensionValue.FEATURE, "(.*)Walk-up(.*)", new DataDimension[] {}),
            }));

        //table.setCols(new DataDimension("All Features", DimensionValue.SCENARIO, "(.*)"));
        //table.setRows(new DataDimension("All Features", DimensionValue.SCENARIO, "(.*)"));
        table.setCell(BreakdownCellValue.STEPS);
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
