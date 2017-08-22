package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.testng.Assert;

import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;
import com.github.mkolisnyk.cucumber.reporting.types.beans.BenchmarkDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkRowData;
import com.github.mkolisnyk.cucumber.utils.TestUtils;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class CucumberBenchmarkReportTest {

    @Test
    public void testSimilarSetOfTests() throws Exception {
        CucumberBenchmarkReport report = new CucumberBenchmarkReport();
        report.setOutputDirectory("target/benchmark/1");
        report.setOutputName("similar-tests");
        BenchmarkReportModel batch = new BenchmarkReportModel(new BenchmarkReportInfo[] {
                new BenchmarkReportInfo("First", "src/test/resources/benchmark-source/1/cucumber-1.json"),
                new BenchmarkReportInfo("Second", "src/test/resources/benchmark-source/1/cucumber-2.json")
        });
        report.execute(batch, new String[] {});
        File output = new File("target/benchmark/1/similar-tests-benchmark.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        TestUtils.verifyXpathValue(content, "//h1[1]/text()", "Features Status");
        
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[1]/th[1]", "Feature Name");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[1]/th[2]", "First");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[1]/th[3]", "Second");
        
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[3]/td[2]", "7 / 1 / 0");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[3]/td[2]/@class", "failed");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[5]/td[3]", "3 / 0 / 0");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[5]/td[3]/@class", "passed");

        TestUtils.verifyXpathValue(content, "//h1[2]/text()", "Scenarios Status");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[1]", "Feature/Scenario Name");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[2]", "First");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[3]", "Second");
        
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[3]/td[2]", "20 / 1 / 2");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[3]/td[2]/@class", "failed");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[5]/td[3]", "25 / 0 / 0");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[5]/td[3]/@class", "passed");
    }
    @Test
    public void testOrphanedFeatures() throws Exception {
        CucumberBenchmarkReport report = new CucumberBenchmarkReport();
        report.setOutputDirectory("target/benchmark/2");
        report.setOutputName("orphaned-features");
        BenchmarkReportModel batch = new BenchmarkReportModel(new BenchmarkReportInfo[] {
                new BenchmarkReportInfo("First", "src/test/resources/benchmark-source/2/cucumber-1.json"),
                new BenchmarkReportInfo("Second", "src/test/resources/benchmark-source/2/cucumber-2.json")
        });
        report.execute(batch, new String[] {});
        File output = new File("target/benchmark/2/orphaned-features-benchmark.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        TestUtils.verifyXpathValue(content, "//h1[1]/text()", "Features Status");
        
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[1]/th[1]", "Feature Name");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[1]/th[2]", "First");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[1]/th[3]", "Second");

        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[2]/td[3]", "");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[2]/td[3]/@class", "skipped");

        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[3]/td[2]", "7 / 1 / 0");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[3]/td[2]/@class", "failed");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[5]/td[3]", "3 / 0 / 0");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[5]/td[3]/@class", "passed");

        TestUtils.verifyXpathValue(content, "//h1[2]/text()", "Scenarios Status");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[1]", "Feature/Scenario Name");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[2]", "First");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[3]", "Second");
        
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[3]/td[2]", "20 / 1 / 2");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[3]/td[2]/@class", "failed");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[5]/td[3]", "25 / 0 / 0");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[5]/td[3]/@class", "passed");
    }
    @Test
    public void testReportFromConfig() throws Exception {
        CucumberBenchmarkReport report = new CucumberBenchmarkReport();
        report.setOutputDirectory("target/benchmark/3");
        report.setOutputName("multi-report");
        report.execute(new File("src/test/resources/benchmark-source/config.json"), new String[] {});
        File output = new File("target/benchmark/3/multi-report-benchmark.html");
        Assert.assertTrue(output.exists());
        String content = FileUtils.readFileToString(output);
        TestUtils.verifyXpathValue(content, "//h1[1]/text()", "Features Status");
        
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[1]/th[1]", "Feature Name");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[1]/th[2]", "First");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[1]/th[3]", "Second");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[1]/th[4]", "Third");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[1]/th[5]", "Fourth");

        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[2]/td[3]", "");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[2]/td[3]/@class", "skipped");

        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[3]/td[2]", "7 / 1 / 0");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[3]/td[2]/@class", "failed");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[5]/td[3]", "3 / 0 / 0");
        TestUtils.verifyXpathValue(content, "//table[1]/tbody/tr[5]/td[3]/@class", "passed");

        TestUtils.verifyXpathValue(content, "//h1[2]/text()", "Scenarios Status");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[1]", "Feature/Scenario Name");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[2]", "First");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[3]", "Second");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[4]", "Third");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[1]/th[5]", "Fourth");
        
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[3]/td[2]", "20 / 1 / 2");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[3]/td[2]/@class", "failed");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[5]/td[3]", "25 / 0 / 0");
        TestUtils.verifyXpathValue(content, "//table[2]/tbody/tr[5]/td[3]/@class", "passed");
    }
    @Ignore
    @Test
    public void testGenerateTemplate() throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        
        InputStream is = this.getClass().getResourceAsStream("/templates/default/benchmark.ftlh");
        String templateString = IOUtils.toString(is);
        
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("benchmark", templateString);
        
        cfg.setTemplateLoader(stringLoader);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);

        BenchmarkDataBean data = new BenchmarkDataBean();
        data.setTitle("Sample Title");
        data.setPdfPageSize("auto");
        data.setHeaders(new String[] {"First", "Second"});
        
        data.setFeatureRows(new BenchmarkRowData[] {
                new BenchmarkRowData("Sample Feature 1", new OverviewStats[] {
                        new OverviewStats() {
                            {
                                addScenariosFailed(1);
                                addScenariosPassed(5);
                                addScenariosUndefined(2);
                            }
                        },
                        new OverviewStats() {
                            {
                                addScenariosFailed(0);
                                addScenariosPassed(6);
                                addScenariosUndefined(0);
                            }
                        },
                }),
                new BenchmarkRowData("Sample Feature 2", new OverviewStats[] {
                        new OverviewStats() {
                            {
                                addScenariosFailed(0);
                                addScenariosPassed(8);
                                addScenariosUndefined(1);
                            }
                        },
                        new OverviewStats() {
                            {
                                addScenariosFailed(2);
                                addScenariosPassed(6);
                                addScenariosUndefined(1);
                            }
                        },
                }),
        });
        data.setScenarioRows(new BenchmarkRowData[] {
                new BenchmarkRowData("Sample Feature 1/Scenario 1", new OverviewStats[] {
                        new OverviewStats() {
                            {
                                addStepsFailed(0);
                                addStepsPassed(5);
                                addStepsUndefined(0);
                            }
                        },
                        new OverviewStats() {
                            {
                                addStepsFailed(3);
                                addStepsPassed(2);
                                addStepsUndefined(0);
                            }
                        },
                }),
                new BenchmarkRowData("Sample Feature 2/Scenario 2", new OverviewStats[] {
                        new OverviewStats() {
                            {
                                addStepsFailed(0);
                                addStepsPassed(8);
                                addStepsUndefined(1);
                            }
                        },
                        new OverviewStats() {
                            {
                                addStepsFailed(1);
                                addStepsPassed(2);
                                addStepsUndefined(3);
                            }
                        },
                }),
        });
        /* Get the template (uses cache internally) */
        Template temp = cfg.getTemplate("benchmark");

        /* Merge data-model with template */
        Writer out = new OutputStreamWriter(System.out);
        temp.process(data, out);
    }
}
