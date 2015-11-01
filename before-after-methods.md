---
title: BeforeSuite and AfterSuite methods
layout: default
---

# Where is it used?

Standard Cucumber runner has functionality of hooks which is represented with **@Before** and **@After** annotations and which are running before and after each scenario respectively.
But there are some cases when we need to perform some global setup/cleanup. Thus we need some additional hooks which provide such capabilities.
As the part of [Extended Cucumber Runner](/cucumber-reports/extended-cucumber-runner) there are 2 additional annotations introduced. They are:

* **@BeforeSuite** - annotates method which is supposed to run before the entire test suite starts
* **@AfterSuite** -  annotates method which is supposed to run after the entire test suite ends

# How to use it?

The above annotations should be applied to the main test class where **ExtendedCucumber** runner is applied to. Here is some sample code showing the use of global setup/tear down methods:

{% highlight java linenos=table %}
package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.AfterSuite;
import com.github.mkolisnyk.cucumber.runner.BeforeSuite;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        retryCount = 3,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        toPDF = true,
        outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" })
public class SampleCucumberTest {
	@BeforeSuite
	public static void setUp() {
		// TODO: Add setup code
	}
	@AfterSuite
	public static void tearDown() {
		// TODO: Add tear down code
	}
}
{% endhighlight %}

Note that methods which have **@BeforeSuite** and **@AfterSuite** annotations asigned must be static as at the state we run those methods there is no information about running class available.

# Related Links

* [Extended Cucumber Runner](/cucumber-reports/extended-cucumber-runner)
* [Ploblem Solved: Cucumber-JVM running actions before and after tests execution with JUnit](http://mkolisnyk.blogspot.co.uk/2015/05/ploblem-solved-cucumber-jvm-running.html)