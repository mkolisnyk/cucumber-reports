---
title: Extended Cucumber Runner
layout: default
---

# Introduction

Extended Cucumber Runner is the extension of standard **Cucumber** JUnit runner which additionally supports:

* [Before- and After- suite methods](/cucumber-reports/before-after-methods)
* Failed Tests Re-run
* Advanced Reporting after tests completion

This extension is done in a form of JUnit runner. Since there are some additional options there is dedicated **@ExtendedCucumberOptions** annotation for that.

# Usage

The use of Extended Cucumber runner is similar to standard runner. Actually it is wrapper above the standard Cucumber runner object.

## JUnit

{% highlight java linenos=table %}
package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        retryCount = 3,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        //coverageReport = true,
        jsonUsageReport = "target/cucumber-usage.json",
        usageReport = true,
        toPDF = true,
        excludeCoverageTags = {"@flaky" },
        includeCoverageTags = {"@passed" },
        outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = {"@consistent"})
public class SampleCucumberTest {
}
{% endhighlight %}

## TestNG 

{% highlight java linenos=table %}
package com.github.mkolisnyk.cucumber.reporting;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import com.github.mkolisnyk.cucumber.runner.ExtendedTestNGRunner;

import cucumber.api.CucumberOptions;

@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        retryCount = 3,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        //coverageReport = true,
        jsonUsageReport = "target/cucumber-usage.json",
        usageReport = true,
        toPDF = true,
        excludeCoverageTags = {"@flaky" },
        includeCoverageTags = {"@passed" },
        outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = {"@consistent"})
public class SampleCucumberTest extends ExtendedTestNGRunner {
}
{% endhighlight %}

# Major options to set

| Option | Type | Mandatory | Description | Default Value | Applied to |
| ------ | ---- | --------- | ----------- | ------------- | ---------- |
| **jsonReport** | **String** | | | | |
| **jsonUsageReport** | **String** | | | | "cucumber-usage.json" |
| **outputFolder** | **String** | | | | "." |
| **reportPrefix** | **String** | | | | "cucumber-results" |
| **usageReport** | **boolean** | | | | false |
| **overviewReport** | **boolean** | | | | false |
| **coverageReport** | **boolean** | | | | false |
| **detailedReport** | **boolean** | | | | false |
| **detailedAggregatedReport** | **boolean** | | | | false |
| **toPDF** | **boolean** | | | | false |
| **screenShotSize** | **String** | | | | "" |
| **screenShotLocation** | **String** | | | | "" |
| **includeCoverageTags** | **String[]** | | | | { } |
| **excludeCoverageTags** | **String[]** | | | | { } |
| **retryCount** | **int** | | | | 0 |

