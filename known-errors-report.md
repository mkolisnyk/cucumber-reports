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

## Top-Level Parameters

At the highest level we should define the following options:

* Source file - the path to initial JSON report to generate breakdown from
* Output directory - the directory to send output to
* Enable breakdown - flag which enables breakdown report generation
* Known errors configuration file - the path to known errors report configuration file. This is one of the ways to specify configuration from code and the only way to do it via extended Cucumber runner

## Configuration File Format

### Sample configuration JSON

The configuration file is defined in JSON format. Here is one of the examples:

```json
{  
   "@type":"com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel",
   "errorDescriptions":[  
      {  
         "title":"Unexpected message",
         "description":"Message box shows unexpected content",
         "filter":{  
            "alias":"(.*)message is shown",
            "dimensionValue":"FAILED_STEP",
            "expression":"(.*)message is shown",
            "subElements":[  

            ],
            "isFinal":false
         },
         "priority":{  
            "name":"LOW",
            "ordinal":1
         }
      }
   ],
   "orderBy":{  
      "name":"FREQUENCY",
      "ordinal":1
   }
}
```

### What is normally defined

Configuration defines the list of expressions which can indicate known errors. They are defined using [filter rules](/cucumber-reports/filter-rules). Additionally, each rule has title, description and the priority. 

In addition to the list of rules, at the top-most level we define the parameter to order by.

### Core Data Types

#### Known Errors Report Model

| Field | Type | Description |
| ----- | ---- | ----------- |
| errorDescriptions | List of [Breakdown Report Info](#known-errors-info) | The list of error description items |
| orderBy | Enumeration | The field to order errors by. Possible values: **PRIORITY, FREQUENCY, NAME** |

#### Known Errors Info

| Field | Type | Description |
| ----- | ---- | ----------- |
| title | String | The short text which gives summary to an error |
| description | String | The text which contains detailed description of the error |
| filter | [Data Dimension](/cucumber-reports/breakdown-report#data-dimension) | Contains the filter which is used to match some specific error |
| priority | Enumeration | Defines the priority of the error. Can be one of the following values: **LOWEST, LOW, MEDIUM, HIGH, HIGHEST** |

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