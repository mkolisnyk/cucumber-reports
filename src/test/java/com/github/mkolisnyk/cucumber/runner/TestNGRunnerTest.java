package com.github.mkolisnyk.cucumber.runner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import cucumber.api.CucumberOptions;

public class TestNGRunnerTest {

    /*@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
            retryCount = 0,
            featureOverviewChart = true,
            detailedReport = true,
            detailedAggregatedReport = true,
            overviewReport = true,
            coverageReport = true,
            jsonUsageReport = "target/cucumber-usage.json",
            usageReport = true,
            toPDF = true,
            breakdownReport = true,
            breakdownConfig = "src/test/resources/breakdown-source/simple.json",
            knownErrorsReport = true,
            knownErrorsConfig = "src/test/resources/known-errors-source/sample_model.json",
            consolidatedReport = true,
            reportPrefix = "testng-result",
            consolidatedReportConfig = "src/test/resources/consolidated-source/sample_batch.json",
            outputFolder = "target/testng")*/
    @ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
            retryCount = 0,
            featureOverviewChart = true,
            detailedReport = true,
            detailedAggregatedReport = true,
            overviewReport = true,
            coverageReport = true,
            jsonUsageReport = "target/cucumber-usage.json",
            usageReport = true,
            toPDF = true,
            breakdownReport = true,
            breakdownConfig = "src/test/resources/breakdown-source/simple.json",
            knownErrorsReport = true,
            knownErrorsConfig = "src/test/resources/known-errors-source/sample_model.json",
            consolidatedReport = true,
            reportPrefix = "testng-result",
            consolidatedReportConfig = "src/test/resources/consolidated-source/sample_batch.json",
            outputFolder = "target/testng")
    @CucumberOptions(
            plugin = {"html:target/cucumber-html-report",
                      "json:target/cucumber.json",
                      "pretty:target/cucumber-pretty.txt",
                      "usage:target/cucumber-usage.json"
                     },
            features = {"src/test/java/com/github/mkolisnyk/cucumber/features/" },
            //glue = {"com/github/mkolisnyk/cucumber/steps" },
            tags = {"@passed"}
    )
    public class TestSubClass extends ExtendedTestNGRunner {
        @BeforeSuite
        public void beforeRun() {
            System.out.println("Setup");
        }
        @AfterSuite
        public void tearDown() {
            System.out.println("Teardown");
        }
    }
    @ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
            retryCount = 0,
            featureOverviewChart = true,
            detailedReport = true,
            detailedAggregatedReport = true,
            overviewReport = true,
            coverageReport = true,
            jsonUsageReport = "target/cucumber-usage.json",
            usageReport = true,
            toPDF = true,
            reportPrefix = "testng-ex-result",
            outputFolder = "target/testng-ex")
    @CucumberOptions(
            plugin = {"html:target/cucumber-html-report",
                      "json:target/cucumber.json",
                      "pretty:target/cucumber-pretty.txt",
                      "usage:target/cucumber-usage.json"
                     },
            features = {"src/test/java/com/github/mkolisnyk/cucumber/features/" },
            //glue = {"com/github/mkolisnyk/cucumber/steps" },
            tags = {"@passed"}
    )
    public class TestSubClassWithException extends ExtendedTestNGRunner {
        @BeforeSuite
        public void beforeRun() throws Exception {
            throw new Exception("Before exception");
        }
        @AfterSuite
        public void tearDown() throws Exception {
            throw new Exception("After exception");
        }
    }
    @After
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testRunSampleTestNGClass() throws Exception {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] {TestSubClass.class});
        testng.addListener(tla);
        testng.run();
    }
    @Test
    public void testRunSampleTestNGClassWithBeforeAfterExceptions() throws Exception {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] {TestSubClassWithException.class});
        testng.addListener(tla);
        testng.run();
    }
}
