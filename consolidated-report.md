---
title: Consolidated Report
layout: default
---

# Where is it used?

Consolidated report is actually a combination of any other reports produced by the library. It is used when we
need to provide single file report from multiple different reports. Typically, it may be needed for:

* E-mail HTML-based body containing multiple reports
* PDF which groups multiple reports
* All above cases for multiple reports of the same type to show some slices of the same data set (e.g. if we would like to view multiple retrospective reports for all available branches)

# Major sections

The report itself contains 2 major sections:

* **Table of Contents** - optional section which contains links all sub-reports.
* Report content - normally it is the sequence of reports. Every top-level heading corresponds to the sub-report itself. If sub-report contains headings, they are lowered by 1 level

# Configuration options

| Option | Type | Description |
| ------ | ---- | ----------- |
| consolidatedReport | boolean | Identifies if consolidated report is to be generated |
| consolidatedReportConfig | String | The path to consolidated report configuration file |

## Configuration file format

The configuration file for current report is of JSON format.

| Field | Type | Description |
| ------ | ---- | ----------- |
| @type | String | Should be **com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportBatch** |
| models | Array of [Consolidated Report Model](#consolidated-report-model)s | Contains the configuration for all consolidated reports produced |

### Consolidated Report Model

| Field | Type | Description |
| ------ | ---- | ----------- |
| items | Array of [Consolidated Report Item](#consolidated-report-item)s | Contains the list of report items |
| reportSuffix | String | the suffix to be added at the end of generated report file name |
| title | String | The entire report title | 
| useTableOfContents | boolean | If set to **true** the table of contents is generated at the top of the report |

### Consolidated Report Item

| Field | Type | Description |
| ------ | ---- | ----------- |
| title | String | The title text above the report section |
| path  | String | Absolute or relative path to the generated report which is to be placed under current section |

# Generation sample

## Using configuration file

The configuration file:

```json
{
  "@type": "com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportBatch", 
  "models": [
    {
      "items": [
        {
          "path": "src/test/resources/consolidated-source/cucumber-results-feature-overview-chart.html", 
          "title": "Overview Chart"
        }, 
        {
          "path": "src/test/resources/consolidated-source/cucumber-results-known-errors.html", 
          "title": "Known Errors"
        }, 
        {
          "path": "src/test/resources/consolidated-source/cucumber-results-feature-overview.html", 
          "title": "Feature Overview"
        },
        {
          "path": "src/test/resources/consolidated-source/cucumber-results-agg-test-results.html", 
          "title": "Detailed Results"
        },
        {
          "path": "src/test/resources/consolidated-source/cucumber-usage-report.html", 
          "title": "Usage Report"
        }
      ], 
      "reportSuffix": "batch-config1", 
      "title": "Overall Results Batch 1", 
      "useTableOfContents": true
    }, 
    {
      "items": [
        {
          "path": "src/test/resources/consolidated-source/cucumber-results-2-feature-overview-chart.html", 
          "title": "Overview Chart"
        }, 
        {
          "path": "src/test/resources/consolidated-source/cucumber-results-coverage-filtered.html", 
          "title": "Test Coverage"
        }
      ], 
      "reportSuffix": "batch-config2", 
      "title": "Overall Results Batch 2", 
      "useTableOfContents": false
    }
  ]
}
```

The code which generates consolidated report (given that all necessary files are already available):

{% highlight java linenos=table %}
CucumberConsolidatedReport results = new CucumberConsolidatedReport();
results.setOutputDirectory("target");
results.setOutputName("cucumber-results");
results.setPdfPageSize("A4 landscape");
results.setSourceFile("./src/test/resources/cucumber.json");
results.executeConsolidatedReport(
	new File("./src/test/resources/consolidated-source/sample_batch.json"),
	true
);
{% endhighlight %}

## Using ExtendedCucumber runner

Below is the example of multiple reports generation including consolidated report. Major parameters are set in the last 3 fields of **ExtendedCucumberOptions** annotation. Since consolidated report actually concatenates other reports it is important that all other reports to be generated should take place and refer to existing resources.

The overall sample test class looks like:

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
		detailedReport = true,
		detailedAggregatedReport = true,
		overviewReport = true,
		featureOverviewChart = true,
		knownErrorsReport = true,
		knownErrorsConfig = "configs/reports/known_errors.json",
		usageReport = true,
		coverageReport = false,
		retryCount = 1,
        breakdownReport = true,
        breakdownConfig = "src/test/resources/breakdown_config.json",
		screenShotLocation = "screenshots/",
		screenShotSize = "300px",
		toPDF = true,
		pdfPageSize = "auto",
		consolidatedReport = true,
		consolidatedReportConfig = "configs/reports/consolidated_batch.json"
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