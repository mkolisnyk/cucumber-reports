---
title: Tests Results Overview Report
layout: default
---

# Where is it used?

Overview report is summarized test results representation and it is mainly targeted to be used as the message body for notification e-mail.

# Major sections

Report consists of 3 major sections:

* Overview Chart
* Summary
* Features Status
* Scenario Status

## Overview Chart

Overview chart section contains pie charts showing the ratio of passed/failed features and scenarios.
Scenario is considered as failed when it has failed steps. Otherwise if scenario has undefined steps (without any failed steps) the scenario status is undefined.
In all other cases scenario is treated as passed. Features status uses similar logic but in this case the elementary part is scenario.
In other words if feature contains at least one failed scenario it is treated as failed. If no fails occurred but there are some undefined scenarios the
 feature is undefined. Otherwise it is treated as passed. Eventually, the overview chart looks like this: 

![Overview Chart](/cucumber-reports/images/overview-report/overview-chart.png)

## Summary

Summary section shows aggregated statistics about features/scenarios/steps statuses. This section is the same as [Overview section in detailed report](/cucumber-reports/detailed-report#overview).

Typical representation is:

![Overview Chart](/cucumber-reports/images/overview-report/summary.png)

## Features Status

Features Status section is represented with the table containing the list of features by their names and scenario run statistics.
It shows the number of passed, failed and undefined scenarios for each specific features. 
Here is the sample of feature overview table:

![Feature Status](/cucumber-reports/images/overview-report/feature-status.png)


## Scenario Status

Scenario Status section contains more detailed breakdown where features are also split into scenarios.
The table contain the number of passed, failed and undefined steps for each specific scenarios.
Sample table looks like (sample fragment):

![Scenario Status](/cucumber-reports/images/overview-report/scenario-status.png)

Since this format is adapted to be an e-mailable report there is no need to add steps breakdown as thus the report becomes too big as well as the actual steps breakdown can be taken from standard Cucumber JVM HTML output.

# Generating report from code

Overview report can be generated using built-in Cucumber Reports API. This report uses standard Cucumber results JSON file as an input.
Here is the sample code generating coverage report:

{% highlight java linenos=table %}
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;

...

CucumberResultsOverview results = new CucumberResultsOverview();
results.setOutputDirectory("target");
results.setOutputName("cucumber-results");
results.setSourceFile("./src/test/resources/cucumber.json");
results.execute();
{% endhighlight %}

If similar code snippet is executed somewhere after the Cucumber JSON report is generated and completed the coverage report will be generated as well.
Such snippet is good as some form of post-processing.

# Generating report via Cucumber runner

In order to produce consistent output there is an ability to generate overview report as the part of [Extended Cucumber Runner](/cucumber-reports/extended-cucumber-runner) functionality. Here is the sample annotated test class:

{% highlight java linenos=table %}
package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        overviewReport = true,
        outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" })
public class SampleCucumberTest {
}
{% endhighlight %}


# Related Links

* [Cucumber JVM: Advanced Reporting](http://mkolisnyk.blogspot.com/2015/05/cucumber-jvm-advanced-reporting.html)