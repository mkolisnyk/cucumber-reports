---
title: Retrospective Results Report
layout: default
---

# Where is it used?

Most of reports produced by the library show information just for single run. But test results are also useful to be represented in some retrospective form to see how the status was changed from time to time. This may help to identify problems like:

* Spontaneous temporary fails which appear from time to time
* Some noticeable failure which caused multiple tests to fail after some time
* Tests start failing due to lack of maintenance activity

In all those cases we need to consolidate multiple result reports in some retrospective form. So, the retrospective report is the first step on such information consolidation.

# Major sections

Retrospective report contains just one section with bar chart showing pass/fail/undefined ratio for different runs.

Here are some sample reports:

![Wide Report](/cucumber-reports/images/retrospective/retro-wide.png)
![Tall Report](/cucumber-reports/images/retrospective/retro-tall.png)

# Configuration options

Retrospective report is configured based on JSON file. Here is the sample of such configuration:

```json
{
  "@type": "com.github.mkolisnyk.cucumber.reporting.types.retrospective.RetrospectiveBatch", 
  "models": [
    {
      "height": 200, 
      "mask": "(.*)retrospective-source.1(.*)json", 
      "orderBy": {
        "@id": 5, 
        "name": "DATE_CREATED", 
        "ordinal": 0
      }, 
      "redirectTo": "", 
      "refreshTimeout": 0, 
      "reportSuffix": "retro2", 
      "title": "Nightly Runs Retrospective", 
      "width": 600
    }, 
    {
      "height": 400, 
      "mask": "(.*)retrospective-source.1.131(.*)json", 
      "orderBy": {
        "@ref": 5
      }, 
      "redirectTo": "", 
      "refreshTimeout": 0, 
      "reportSuffix": "retro3", 
      "title": "Restricted Runs Retrospective", 
      "width": 300
    }
  ]
}
```

Entire configuration is represented as the batch of models. Model is specific structure responsible for the report content and layout. Since batch may contain multiple models the retrospective report may generate multiple output files. Each model contains the following fields:

* **title** - indicates title displayed above the chart
* **mask** - file mask to pick up just specific files
* **height**, **width** - the generated chart height and width respectively
* **reportSuffix** - the suffix of generated report. It is important especially when multiple reports are generated
* **redirectTo** - the path to perform redirect to. Operational when **refreshTimeout** field is greater than 0
* **refreshTimeout** - time to wait before redirecting to another page
* **orderBy** - constant identifying the way to order input files. But default it's creation date

# Generation sample

```java
CucumberRetrospectiveOverviewReport report = new CucumberRetrospectiveOverviewReport();
report.setOutputDirectory("./target");
report.setOutputName("cucumber-results");
RetrospectiveBatch batch = new RetrospectiveBatch(
    new RetrospectiveModel[] {
        new RetrospectiveModel(
                "retro2",
                "Nightly Runs Retrospective",
                "(.*)retrospective-source.1(.*)json", 600, 200),
        new RetrospectiveModel(
                "retro3",
                "Restricted Runs Retrospective",
                "(.*)retrospective-source.1.131(.*)json", 300, 400)
    }
);
report.execute(batch, true, false);
```

```java
CucumberRetrospectiveOverviewReport report = new CucumberRetrospectiveOverviewReport();
report.setOutputDirectory("./target");
report.setOutputName("cucumber-results-batch");
report.execute(new File("./src/test/resources/retrospective-source/sample_batch.json"), true, true);
```