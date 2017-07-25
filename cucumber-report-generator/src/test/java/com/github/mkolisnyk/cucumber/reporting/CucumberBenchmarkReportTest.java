package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.junit.Test;

import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkReportModel;

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
    }
    @Test
    public void testReportFromConfig() throws Exception {
        CucumberBenchmarkReport report = new CucumberBenchmarkReport();
        report.setOutputDirectory("target/benchmark/3");
        report.setOutputName("multi-report");
        report.execute(new File("src/test/resources/benchmark-source/config.json"), new String[] {});
    }
}
