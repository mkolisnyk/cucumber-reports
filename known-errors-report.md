---
title: Known Errors Report
layout: default
---

# What is it?

Known errors report is the report grouping test results errors which match some known patterns.

# Where is it used?

In vast majority of cases the same error may appear in many different places in the results report. Also, these is some common functionality. And usually, if some error occurs in the common functionality it would make multiple errors in test results with the same error message. Sometimes it is convenient to group them to see how many errors of known reasons were found.

# Major sections

The report contains only one section represented with the results table as it is shown at the below image:

![Sample Report](/cucumber-reports/images/known-errors/sample-report.png)

The table itself contains the following columns:

* Description - contains brief error summary (in bold) with more detailed description. It is used to give detailed information about error found.
* Priority - indicates the importance of the problem
* \# of Occurrences - indicates frequency of errors matching the same pattern

# Configuration options

# Generation sample

## From code

Generating from the explicitly defined model:

```java
CucumberKnownErrorsReport results = new CucumberKnownErrorsReport();
results.setOutputDirectory("target");
results.setOutputName("cucumber-results");
results.setSourceFile("./src/test/resources/cucumber.json");
KnownErrorsModel model = new KnownErrorsModel(
    new KnownErrorsInfo[] {
       new KnownErrorsInfo(
           "Unable to reach Select Ticket screen",
           "Select ticket isn't shown",
           new DataDimension(DimensionValue.FAILED_STEP, "(.*)see the \"Select Ticket\" screen"),
           KnownErrorPriority.HIGHEST),
       new KnownErrorsInfo(
           "Search Fails",
           "Some search parameters do not bring results",
           new DataDimension(DimensionValue.FAILED_STEP, "(.*)the \"Out Search Results\" screen"),
           KnownErrorPriority.HIGH),
       new KnownErrorsInfo(
           "Unexpected message",
           "Message box shows unexpected content",
           new DataDimension(DimensionValue.FAILED_STEP, "(.*)message is shown"),
           KnownErrorPriority.LOW),
    },
    KnownErrorOrderBy.FREQUENCY);
results.execute(model, false);
```

Generate from predefined configuration file:

```java
CucumberKnownErrorsReport results = new CucumberKnownErrorsReport();
results.setOutputDirectory("target");
results.setOutputName("cucumber-results-2");
results.setSourceFile("./src/test/resources/detailed-source/cucumber-2.json");
results.execute(new File("./src/test/resources/known-errors-source/sample_model.json"), false);
```

## From Extended Cucumber runner

For Extended Cucumber runner we just need to define the following options:

* jsonReport - known errors report uses JSON results file as an input
* outputFolder - output directory
* knownErrorsReport = true - this flag should be enabled in order to generate the report
* knownErrorsConfig - path to configuration file. 

So, sample known error report generation looks like this:

```java
package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
		jsonReport = "target/cucumber.json",
        toPDF = true,
        knownErrorsReport = true,
        knownErrorsConfig = "src/test/resources/known-errors-source/sample_model.json",
        outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = {"@passed"})
public class SampleCucumberTest {
}
```