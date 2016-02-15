package com.github.mkolisnyk.cucumber.runner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import cucumber.api.CucumberOptions;

public class TestNGRunnerTest {

    @CucumberOptions(
            format = {"html:target/cucumber-html-report",
                      "json:target/cucumber.json",
                      "pretty:target/cucumber-pretty.txt",
                      "usage:target/cucumber-usage.json"
                     },
            features = {"output/" },
            glue = {"com/github/mkolisnyk/aerial" },
            tags = { }
    )
    public static class TestSubClass extends ExtendedTestNGRunner {
        @BeforeSuite
        public static void setUp() {
        }
        @AfterSuite
        public static void tearDown() {
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
}
