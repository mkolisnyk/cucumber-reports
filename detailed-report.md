---
title: Detailed Test Results Report
layout: default
---

# Where is it used?

Detailed report is some kind of enhancement for standard Cucumber HTML report. It is major source for test results analysis.

# Major sections

Detailed report consists of 3 major sections:

## Overview

![Overview Section](/cucumber-reports/images/detailed-report/overview-section.png)

Overview section contains aggregated information on run status per features/scenarios/steps. It's some kind of results summary.

## Table of Contents

![TOC Section](/cucumber-reports/images/detailed-report/toc-section.png)

Table of contents is hyper-linked list of features and scenarios in the report. Each link refers to detailed results section for specific feature/scenario.

## Detailed Results

![Detailed Section](/cucumber-reports/images/detailed-report/detailed-section.png)

Detailed results section contains steps performed with their statuses. Also, this section contains screen shots.

# Additional report generation options

## Aggregated results

Results aggregation is the part of [failed tests re-run](/cucumber-reports/failed-tests-rerun) functionality. If we use tests re-run we receive report containing all results for all re-tries. Usually it distorts actual picture. Aggregation is targeted to show the latest execution status for each test.

## Screen shots

Currently major supported way to include screen shots is embedding them into JSON report. Below is the sample code which is to be used as a part of hooks and which generates screen shot if test was ended with error:

```java
@After
public void tearDown(Scenario scenario) {
	if (scenario.getStatus().equalsIgnoreCase("failed")) {
	    try {
            File scrFile = getScreenShotFile();
			byte[] data = FileUtils.readFileToByteArray(scrFile);
			scenario.embed(data, "image/png");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
```

Here the **getScreenShotFile()** method is some method getting screen shot and storing to file. Actual implementation depends on the technology and can be varying. But entire structure is the same.

Once you have the screen shot embedded the detailed report will inject it into it's body.

# Generating report from code

```java
CucumberDetailedResults results = new CucumberDetailedResults();
results.setOutputDirectory("target/");
results.setOutputName("cucumber-results");
results.setSourceFile("./src/test/resources/cucumber.json");
results.setScreenShotLocation("../src/test/resources/");
results.execute(true, false);
results.execute(false, false);
```

# Generating report via Cucumber runner

```java
package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
        jsonReport = "target/cucumber.json",
        detailedReport = true,
        detailedAggregatedReport = true,
        toPDF = true,
        outputFolder = "target/LoginReport/ExtendedReport")
@CucumberOptions(
        features = { "src/test/java/com/github/mkolisnyk/cucumber/features/63.feature" },
        glue = "com/github/mkolisnyk/cucumber/steps", plugin = {
        "html:target/LoginReport", "json:target/cucumber.json",
        "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" })
public class SampleTest {
}
```