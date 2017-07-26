---
title: Benchmark Report
layout: default
---

# Where is it used?

Benchmark report is joint representation of several test results. It looks similar to [Results Overview Report](/overview-report) except it shows joint statistics for several runs.

# Major sections

Report consists of 2 major sections:

* Features Status
* Scenario Status

Both sections represent the table with similar format. It includes columns reflecting the following:

* Feature or scenario name
* Run name

If some run doesn't have some feature or scenario which present in other runs the corresponding cell will be filled with gray.

## Features Status

Features Status section is represented with the table containing the list of features by their names and scenario run statistics.
It shows the number of passed, failed and undefined scenarios for each specific features for each specific run. 
Here is the sample of feature overview table:

![Feature Status](/cucumber-reports/images/benchmark/feature-status.png)


## Scenario Status

Scenario Status section contains more detailed breakdown where features are also split into scenarios.
The table contain the number of passed, failed and undefined steps for each specific scenario for each specific run.
Sample table looks like (sample fragment):

![Scenario Status](/cucumber-reports/images/benchmark/scenario-status.png)

# Configuration options

| Option | Type | Description |
| ------ | ---- | ----------- |
| benchmarkReport | boolean | Identifies if benchmark report is to be generated |
| benchmarkReportConfig | String | The path to benchmark report configuration file |

## Configuration file format

The configuration file for current report is of JSON format.

| Field | Type | Description |
| ------ | ---- | ----------- |
| @type | String | Should be **com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkReportModel** |
| items | Array of [Benchmark Report Item Info](#benchmark-report-item-info)s | Contains the list of report items |

### Benchmark Report Item Info

| Field | Type | Description |
| ------ | ---- | ----------- |
| title | String | The title text above the report column |
| path  | String | Absolute or relative path to the generated report which is to be placed under current section |

### Configuration file example

{% highlight javascript linenos=table %}
{
  "@type": "com.github.mkolisnyk.cucumber.reporting.types.benchmark.BenchmarkReportModel", 
  "items": [
    {
      "path": "src/test/resources/benchmark-source/2/cucumber-1.json", 
      "title": "First"
    }, 
    {
      "path": "src/test/resources/benchmark-source/2/cucumber-2.json", 
      "title": "Second"
    },
    {
      "path": "src/test/resources/benchmark-source/1/cucumber-1.json", 
      "title": "Third"
    }, 
    {
      "path": "src/test/resources/benchmark-source/1/cucumber-2.json", 
      "title": "Fourth"
    }
  ]
}
{% endhighlight %}

# Generating report from code

## Using explicit model

{% highlight java linenos=table %}
		CucumberBenchmarkReport report = new CucumberBenchmarkReport();
        report.setOutputDirectory("target/benchmark/1");
        report.setOutputName("similar-tests");
        BenchmarkReportModel batch = new BenchmarkReportModel(new BenchmarkReportInfo[] {
                new BenchmarkReportInfo("First", "src/test/resources/benchmark-source/1/cucumber-1.json"),
                new BenchmarkReportInfo("Second", "src/test/resources/benchmark-source/1/cucumber-2.json")
        });
        report.execute(batch, new String[] {});
{% endhighlight %}

## Using configuration file

{% highlight java linenos=table %}
        CucumberBenchmarkReport report = new CucumberBenchmarkReport();
        report.setOutputDirectory("target/benchmark/3");
        report.setOutputName("multi-report");
        report.execute(new File("src/test/resources/benchmark-source/config.json"), new String[] {});
{% endhighlight %}

# Generating report via Cucumber runner

{% highlight java linenos=table %}
package com.sample.common.tests;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
		jsonReport = "build/cucumber.json",
		jsonUsageReport = "build/cucumber-usage.json",
		outputFolder = "build/",
		benchmarkReport = true,
		benchmarkReportConfig = "configs/reports/benchmark_config.json"
		)
@CucumberOptions(
        plugin = { "html:build/cucumber-html-report",
        		"junit:build/cucumber-junit.xml",
                "json:build/cucumber.json",
                "pretty:build/cucumber-pretty.txt",
                "usage:build/cucumber-usage.json"
                },
        features = { "src/test/java/com/sample/common/features" },
        glue = { "com/sample/common/steps" },
        tags = { "@system" }
)
public class SampleTest {
}
{% endhighlight %}


# Related Links

