package com.github.mkolisnyk.cucumber.runner;

import org.junit.Test;

import cucumber.api.CucumberOptions;

@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = {"@passed"})
public class ExtendedParallelCucumberTest {
    @Test
    public void testGetAnnotations() throws Exception {
        CucumberOptions[] option = this.getClass().getAnnotationsByType(CucumberOptions.class);
        ExtendedParallelCucumber cucumber = new ExtendedParallelCucumber(this.getClass());
        CucumberOptions[] results = cucumber.splitCucumberOption(option[0]);
        for (CucumberOptions result : results) {
            System.out.println("" + result.features()[0]);
        }
    }
}
