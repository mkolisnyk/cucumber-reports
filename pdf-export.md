---
title: PDF Export
layout: default
---

# Where is it used?

All supported reports generate HTML output by default. But sometimes reports may contain some pictures (e.g. screen shots referenced in HTML) or we would like to send multiple reports via e-mail (and some of the reports may appear to be too big). In such cases PDF output can be convenient option.

# Enabling PDF export

## From code

Every report generation method (mainly named **execute**) has version which accepts **toPDF** parameter. The PDF report would be generated if this flag is set to **true**. Here is an example for overview report:

```java
CucumberFeatureOverview results = new CucumberFeatureOverview();
results.setOutputDirectory("target");
results.setOutputName("cucumber-results");
results.setSourceFile("./src/test/resources/cucumber.json");
results.execute(true);
```

## Via Cucumber runner

Extended Cucumber options annotation which is used together with Cucumber options for the test class contains **toPDF** field. This is global flag indicating that each generated report should also contain PDF copy. Here is some extended runner example with PDF output option enabled:

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
        //coverageReport = true,
        jsonUsageReport = "target/cucumber-usage.json",
        usageReport = true,
        toPDF = true,
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

# Additional generation options

In some cases we need to generate PDF in the format different from A4 landscape. For this purpose there is additional **pdfPageSize** option which accepts PDF page layout string. For more details about page sizes supported, please, refer to [CSS3 specification page](http://www.w3.org/TR/css3-page/#page-size).