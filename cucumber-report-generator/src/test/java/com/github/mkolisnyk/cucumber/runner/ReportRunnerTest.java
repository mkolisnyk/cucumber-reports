package com.github.mkolisnyk.cucumber.runner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

@RunWith(Parameterized.class)
public class ReportRunnerTest {
    private static int index = 0;
    private ExtendedRuntimeOptions options;
    
    public ReportRunnerTest(ExtendedRuntimeOptions optionsValue) {
        options = optionsValue;
        options.setReportPrefix("cucumber-results");
        options.setOutputFolder("target/runner/" + index);
        options.setJsonReportPaths(new String[] {"src/test/resources/feature-overview/cucumber.json"});
        options.setJsonUsageReportPaths(new String[] {"src/test/resources/cucumber-usage-large.json"});
        index++;
    }
    @Parameters
    public static Collection<Object[]> getParameters() throws IOException {
        return Arrays.asList(new Object[][] {
            {new ExtendedRuntimeOptions() {
                {
                    setBenchmarkReport(true);
                    setBenchmarkReportConfig("src/test/resources/benchmark-source/config.json");
                }
            }
            },
            {new ExtendedRuntimeOptions() {
                {
                    setBenchmarkReport(true);
                    setBenchmarkReportConfig("src/test/resources/benchmark-source/ne-config.json");
                }
            }
            },
            {new ExtendedRuntimeOptions() {
                {
                    setBreakdownReport(true);
                    setBreakdownConfig("src/test/resources/breakdown-source/simple.json");
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setBreakdownReport(true);
                    setBreakdownConfig("src/test/resources/breakdown-source/ne-simple.json");
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setConsolidatedReport(true);
                    setConsolidatedReportConfig("src/test/resources/consolidated-source/sample_batch.json");
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setConsolidatedReport(true);
                    setConsolidatedReportConfig("src/test/resources/consolidated-source/ne-sample_batch.json");
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setCoverageReport(true);
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setDetailedAggregatedReport(true);
                    setDetailedReport(true);
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setDetailedAggregatedReport(false);
                    setDetailedReport(true);
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setDetailedAggregatedReport(true);
                    setDetailedReport(false);
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setKnownErrorsReport(true);
                    setKnownErrorsConfig("src/test/resources/known-errors-source/sample_model.json");
                    setDetailedAggregatedReport(true);
                    setDetailedReport(true);
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setKnownErrorsReport(true);
                    setKnownErrorsConfig("src/test/resources/known-errors-source/ne-sample_model.json");
                    setDetailedAggregatedReport(true);
                    setDetailedReport(true);
                }
            }},

            {new ExtendedRuntimeOptions() {
                {
                    setFeatureMapReport(true);
                    setFeatureMapConfig("src/test/resources/breakdown-source/simple.json");
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setFeatureMapReport(true);
                    setFeatureMapConfig("src/test/resources/breakdown-source/ne-simple.json");
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setFeatureOverviewChart(true);
                    setOverviewChartsReport(true);
                    setOverviewReport(true);
                    setSystemInfoReport(true);
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setKnownErrorsReport(true);
                    setKnownErrorsConfig("src/test/resources/known-errors-source/sample_model.json");
                    setFeatureOverviewChart(true);
                    setOverviewChartsReport(true);
                    setOverviewReport(true);
                    setSystemInfoReport(true);
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setKnownErrorsReport(true);
                    setKnownErrorsConfig("src/test/resources/known-errors-source/ne-sample_model.json");
                    setFeatureOverviewChart(true);
                    setOverviewChartsReport(true);
                    setOverviewReport(true);
                    setSystemInfoReport(true);
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setKnownErrorsReport(true);
                    setKnownErrorsConfig("src/test/resources/known-errors-source/sample_model.json");
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setKnownErrorsReport(true);
                    setKnownErrorsConfig("src/test/resources/known-errors-source/ne-sample_model.json");
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setUsageReport(true);
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setOverviewReport(true);
                    setCustomTemplatesPath("src/test/resources/templates/single_tmpl.json");
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setOverviewReport(true);
                    setCustomTemplatesPath("src/test/resources/templates/test/");
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setOverviewReport(true);
                    setCustomTemplatesPath("src/test/resources/templates/ne-test/");
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                    setOverviewReport(true);
                    setCustomTemplatesPath("src/test/resources/templates/single_ne_tmpl.json");
                }
            }},
            /*{new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }}, 
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},
            {new ExtendedRuntimeOptions() {
                {
                }
            }},*/
        });
    }

    @Test
    public void testReportRunner() {
        ReportRunner.run(options);
    }
}
