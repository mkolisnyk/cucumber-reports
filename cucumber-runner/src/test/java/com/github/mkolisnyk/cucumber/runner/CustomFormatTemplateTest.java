package com.github.mkolisnyk.cucumber.runner;

import org.junit.Test;
import org.junit.runner.notification.RunNotifier;

import cucumber.api.CucumberOptions;

@ExtendedCucumberOptions(
        jsonReport = "target/cucumber.json",
        retryCount = 0,
        overviewReport = true,
        customTemplatesPath = "src/test/resources/templates/single_tmpl.json",
        toPDF = false,
        outputFolder = "target/172")
@CucumberOptions(
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features/LazyAssert.feature" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        plugin = {
        "json:target/cucumber.json", "html:target/cucumber-html-report",
        "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json",
        "junit:target/cucumber-junit-results.xml" }, tags = {})
public class CustomFormatTemplateTest {
    @Test
    public void testCustomFormatTemplate() throws Exception {
        ExtendedCucumber runner = new ExtendedCucumber(this.getClass());
        RunNotifier notifier = new RunNotifier();
        runner.run(notifier);
    }
}
