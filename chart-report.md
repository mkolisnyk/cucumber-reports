---
title: Charts Report
layout: default
---

# Where is it used?

Charts report combines overview charts for run results, coverage and some other metrics. It contains only pie charts showing statistics per features, scenarios and steps respectively.

# Major sections

Report contains a combination of pie chart sections. Here is an example of charts containing only run results overview charts:

![Single Chart](/cucumber-reports/images/charts-report/single-chart.png)

With additional settings report may also contain coverage information. Here is an example of such report: 

![Chart With Coverage](/cucumber-reports/images/charts-report/chart-with-coverage.png)

# Generation options

This report is dependent on [Test Results Overview](/cucumber-reports/overview-report) and [Test Coverage](/cucumber-reports/coverage-report) reports. So, in order to generate charts for different sections we should enable overview and/or coverage reports.

## Aggregated results

## PDF export

# Generating report from code

```java
ExtendedRuntimeOptions options = new ExtendedRuntimeOptions();
options.setOutputFolder("target/charts");
options.setReportPrefix("cucumber-reports");
options.setJsonReportPaths(new String[] {"src/test/resources/cucumber.json"});
options.setOverviewReport(true);
options.setCoverageReport(true);
CucumberOverviewChartsReport report = new CucumberOverviewChartsReport(options);
report.execute(true);
```

# Generating report via Cucumber runner

```java
    @ExtendedCucumberOptions(
            jsonReport = "target/81/cucumber.json",
            jsonUsageReport = "target/81/cucumber-usage.json",
            usageReport = true,
            detailedReport = true,
            detailedAggregatedReport = true,
            overviewReport = true,
            overviewChartsReport = true,
            pdfPageSize = "A4 Landscape",
            toPDF = true,
            outputFolder = "target/81",
            retryCount = 3)
    @CucumberOptions(
            features = { "src/test/java/com/github/mkolisnyk/cucumber/features/Test.feature" },
            tags = { "@failed" },
            glue = "com/github/mkolisnyk/cucumber/steps", plugin = {
            "html:target/81", "json:target/81/cucumber.json",
            "pretty:target/81/cucumber-pretty.txt",
            "usage:target/81/cucumber-usage.json", "junit:target/81/cucumber-results.xml" })
    public class SampleTestRetryIsOn {
    }
```