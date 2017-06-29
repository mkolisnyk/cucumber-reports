---
title: Other Formats Export
layout: default
---

# Where is it used?

All supported reports generate HTML output by default. But sometimes reports may contain some pictures (e.g. screen shots referenced in HTML) or we would like to send multiple reports via e-mail (and some of the reports may appear to be too big). Also, some reports are not properly rendered if we send them via e-mail. Mainly it's due to SVG elements which are poorly rendered by some mail clients. For this purpose we need to have an ability to export HTML reports in various formats, including images. Partially it is implemented via [PDF export](/cucumber-reports/pdf-export) functionality. But we need some more common mechanism which allows export to JPEG, PNG and some other formats.

# Enabling export

## From code

Every report generation method (mainly named **execute**) has version which accepts **formats** parameter. This is string array which contains the format names (e.g. **pdf, png, jpg**) to use for export. Here is an example for overview report:

```java
CucumberFeatureOverview results = new CucumberFeatureOverview();
results.setOutputDirectory("target");
results.setOutputName("cucumber-results");
results.setSourceFile("./src/test/resources/cucumber.json");
results.execute(new String[] {"pdf", "png"});
```

## Via Cucumber runner

The **ExtendedCucumberOptions** annotation contains field called **formats**. This field is also string array of formats to export. Typical sample code looks like this (here we export to PDF and PNG files):

```java
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
        jsonUsageReport = "target/cucumber-usage.json",
        usageReport = true,
        formats = {"pdf", "png"},
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
```

# Formats and toPDF flag compatibility

If we use Cucumber runners from this library we still need to use **toPDF** flag. However, the same format can be defined via **formats** field. If we need to do export to PDF we still need to define **toPDF** flag anyway. Otherwise, even if **formats** field contains **"pdf"** entry, it will be removed and no PDF report generated.