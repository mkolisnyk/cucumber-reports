package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.Assert;

@RunWith(Parameterized.class)
public class BreakdownReportModelInitRedirectTest {
    public BreakdownReportModel model;
    public String[] expectedFiles;

    public BreakdownReportModelInitRedirectTest(BreakdownReportModel modelValue,
            String[] expectedFilesValue) {
        super();
        this.model = modelValue;
        this.expectedFiles = expectedFilesValue;
    }

    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            {
                new BreakdownReportModel(
                    new BreakdownReportInfo[] {
                        new BreakdownReportInfo(new BreakdownTable(), "report1", "Breakdown 1", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report2", "Breakdown 2", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report3", "Breakdown 3", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report4", "Breakdown 4", 3, ""),
                    }
                ),
                new String[] {
                    "test-report2.html",
                    "test-report3.html",
                    "test-report4.html",
                    "test-report1.html"
                }
            },
            {
                new BreakdownReportModel(
                    new BreakdownReportInfo[] {
                        new BreakdownReportInfo(new BreakdownTable(), "report1", "Breakdown 1", 0, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report2", "Breakdown 2", 0, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report3", "Breakdown 3", 0, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report4", "Breakdown 4", 0, ""),
                    }
                ),
                new String[] {
                    "",
                    "",
                    "",
                    ""
                }
            },
            {
                new BreakdownReportModel(
                    new BreakdownReportInfo[] {
                        new BreakdownReportInfo(new BreakdownTable(), "report1", "Breakdown 1", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report2", "Breakdown 2", 0, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report3", "Breakdown 3", 0, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report4", "Breakdown 4", 0, ""),
                    }
                ),
                new String[] {
                    "test-report1.html",
                    "",
                    "",
                    ""
                }
            },
            {
                new BreakdownReportModel(
                    new BreakdownReportInfo[] {
                        new BreakdownReportInfo(new BreakdownTable(), "report1", "Breakdown 1", 0, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report2", "Breakdown 2", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report3", "Breakdown 3", 0, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report4", "Breakdown 4", 0, ""),
                    }
                ),
                new String[] {
                    "",
                    "test-report2.html",
                    "",
                    ""
                }
            },
            {
                new BreakdownReportModel(
                    new BreakdownReportInfo[] {
                        new BreakdownReportInfo(new BreakdownTable(), "report1", "Breakdown 1", 0, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report2", "Breakdown 2", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report3", "Breakdown 3", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report4", "Breakdown 4", 3, ""),
                    }
                ),
                new String[] {
                    "",
                    "test-report3.html",
                    "test-report4.html",
                    "test-report2.html"
                }
            },
            {
                new BreakdownReportModel(
                    new BreakdownReportInfo[] {
                        new BreakdownReportInfo(new BreakdownTable(), "report1", "Breakdown 1", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report2", "Breakdown 2", 0, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report3", "Breakdown 3", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report4", "Breakdown 4", 3, ""),
                    }
                ),
                new String[] {
                    "test-report3.html",
                    "",
                    "test-report4.html",
                    "test-report1.html"
                }
            },
            {
                new BreakdownReportModel(
                    new BreakdownReportInfo[] {
                        new BreakdownReportInfo(new BreakdownTable(), "report1", "Breakdown 1", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report2", "Breakdown 2", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report3", "Breakdown 3", 0, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report4", "Breakdown 4", 3, ""),
                    }
                ),
                new String[] {
                    "test-report2.html",
                    "test-report4.html",
                    "",
                    "test-report1.html"
                }
            },
            {
                new BreakdownReportModel(
                    new BreakdownReportInfo[] {
                        new BreakdownReportInfo(new BreakdownTable(), "report1", "Breakdown 1", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report2", "Breakdown 2", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report3", "Breakdown 3", 3, ""),
                        new BreakdownReportInfo(new BreakdownTable(), "report4", "Breakdown 4", 0, ""),
                    }
                ),
                new String[] {
                    "test-report2.html",
                    "test-report3.html",
                    "test-report1.html",
                    ""
                }
            },
        });
    }
    @Test
    public void testVerifyFiles() {
        model.initRedirectSequence("test-");
        for (int i = 0; i < model.getReportsInfo().length; i++) {
            Assert.assertEquals(model.getReportsInfo()[i].getNextFile(), expectedFiles[i]);
        }
    }
}
