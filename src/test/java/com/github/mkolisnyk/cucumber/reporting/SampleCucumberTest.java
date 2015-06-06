package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;

import cucumber.api.CucumberOptions;

@Ignore
@RunWith(ExtendedCucumber.class)
@CucumberOptions(
        plugin = {"html:target/cucumber-html-report",
                  "json:target/cucumber.json",
                  "pretty:target/cucumber-pretty.txt",
                  "usage:target/cucumber-usage.json",
                  "junit:target/cucumber-results.xml"
                 },
        features = {"./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = {"com/github/mkolisnyk/cucumber/steps" },
        tags = { }
)
public class SampleCucumberTest {

}
