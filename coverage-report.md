---
title: Test Coverage Report
layout: default
---

# Where is it used?

Test Coverage report mainly shows which scenarios from the entire test suite are runnable at all or included into some specific group.
Scenario is treated as runnable if 2 conditions are met:

* Scenario contains at least one step
* All scenario steps contain their implementations

Additional tags filtering allows mark test as covered if it belongs to some specific group (specified by tag).

# Major sections

Generally the report looks like this:

[![Test Coverage Report](/cucumber-reports/test-coverage-report/coverage-sample.png)]]

This report contains 3 major sections:

* Overview Charts
* Feature Status
* Scenario Status

## Overview Charts section

Overview charts section contains graphical summary of coverage statistics and has the following view:

[![Overview Section](/cucumber-reports/test-coverage-report/overview-section.png)]]

Here we see pie charts for features and scenario coverage with summary information.

## Feature Status section

Feature status section is represented with the table with detailed information on feature level. Typical section looks like this:

[![Feature Status Section](/cucumber-reports/test-coverage-report/feature-status-section.png)]]

Each feature row shows the feature coverage status, the number of scenarios covered/non-covered and the list of tags available within the feature.

## Scenario Status section

Scenario status section is represented with the table with detailed information on scenario level. Typical section looks like this:

[![Scenario Status Section](/cucumber-reports/test-coverage-report/scenario-status-section.png)]]

This time it shows the list of scenarios with their coverage status and the number of steps covered/non-covered.

# Generating report from code

Coverage report can be generated using built-in Cucumber Reports API. This report uses standard Cucumber results JSON file as an input.
Here is the sample code generating coverage report:

{% highlight java linenos=table %}
import com.github.mkolisnyk.cucumber.reporting.CucumberCoverageOverview;

...

CucumberCoverageOverview results = new CucumberCoverageOverview();
results.setOutputDirectory("target");
results.setOutputName("cucumber-results");
results.setSourceFile("./src/test/resources/cucumber.json");
results.setExcludeCoverageTags(new String[]{"@android"});
results.setIncludeCoverageTags(new String[]{"@ios"});
results.executeOverviewReport("coverage-filtered");
{% endhighlight %}

If similar code snippet is executed somewhere after the Cucumber JSON report is generated and completed the coverage report will be generated as well.
Such snippet is good as some form of post-processing.

# Generating report via Cucumber runner

But if we need some consistent way of reports generation when we create reports for tests we've just run we should use [Extended Cucumber Runner](/cucumber-reports/extended-cucumber-runner).
Since this report is about estimate not just runnable tests but also tests which can be empty or which do not belong to major runnable tags it is recommended
to enable coverage report as the part of **dry run**.

Here some sample test definition which includes coverage run:

{% highlight java linenos=table %}
package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
        jsonReport = "target/cucumber-dry.json",
        retryCount = 0,
        coverageReport = true,
        excludeCoverageTags = {"@flaky" },
        includeCoverageTags = {"@passed" },
        reportPrefix = "dry-run",
        outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber-dry.json", "pretty:target/cucumber-pretty-dry.txt",
        "usage:target/cucumber-usage-dry.json", "junit:target/cucumber-results-dry.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = { },
        dryRun = true)
public class SampleDryRunCucumberTest {

    public SampleDryRunCucumberTest() {
        // TODO Auto-generated constructor stub
    }
}
{% endhighlight %}