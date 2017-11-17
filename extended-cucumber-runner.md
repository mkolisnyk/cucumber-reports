---
title: Extended Cucumber Runner
layout: default
---

# Introduction

Extended Cucumber Runner is the extension of standard **Cucumber** JUnit runner which additionally supports:

* [Before- and After- suite methods](/cucumber-reports/before-after-methods)
* [Failed Tests Re-run](/cucumber-reports/failed-tests-rerun)
* Advanced Reporting after tests completion

This extension is done in a form of JUnit runner. Since there are some additional options there is dedicated **@ExtendedCucumberOptions** annotation for that.

# Usage

The use of Extended Cucumber runner is similar to standard runner. Actually it is wrapper above the standard Cucumber runner object.

## JUnit

{% highlight java linenos=table %}
package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
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
public class SampleCucumberTest {
}
{% endhighlight %}

## TestNG 

{% highlight java linenos=table %}
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
{% endhighlight %}

# Major options to set

| Option                       | Type         | Description | Default Value |
| ---------------------------- | ------------ | ----------- | ------------- |
| **jsonReport**               | **String**   | Contains path to standard Cucumber JSON results output | |
| **jsonReports**              | **String[]** | Contains paths to multiple Cucumber JSON result output files. This way multiple results can be merged in one report. If **jsonReport** field is used, it's value will be added to final array of paths | {} |
| **jsonUsageReport**          | **String**   | Contains path to standard Cucumber usage report | "cucumber-usage.json" |
| **jsonUsageReports**         | **String[]** | Contains paths to multiple usage reports | {} |
| **outputFolder**             | **String**   | Contains path to output folder where all reports will be written to | "." |
| **reportPrefix**             | **String**   | Common file prefix which will be used for all generated output files | "cucumber-results" |
| **usageReport**              | **boolean**  | Enables/disables [usage report](/cucumber-reports/usage-report) | false |
| **overviewReport**           | **boolean**  | Enables/disables [overview report](/cucumber-reports/overview-report) | false |
| **featureOverviewChart**     | **boolean**  | Enables/disables [feature overview chart](/cucumber-reports/overview-chart-report) | false |
| **overviewChartsReport**     | **boolean**  | Enables/disables [overview charts report](/cucumber-reports/chart-report) | false |
| **coverageReport**           | **boolean**  | Enables/disables [coverage report](/cucumber-reports/coverage-report) | false |
| **includeCoverageTags**      | **String[]** | Used to define which tags are to be included in [coverage report](/cucumber-reports/coverage-report). | { } |
| **excludeCoverageTags**      | **String[]** | Used to define which tags are to be excluded from [coverage report](/cucumber-reports/coverage-report). | { } |
| **detailedReport**           | **boolean**  | Enables/disables [detailed report](/cucumber-reports/detailed-report) | false |
| **detailedAggregatedReport** | **boolean**  | Enables/disables [detailed results aggregation](/cucumber-reports/detailed-report#aggregated-results). Important if [failed tests re-run](/cucumber-reports/failed-tests-rerun) is enabled | false |
| **breakdownReport**          | **boolean**  | Enables/disables [breakdown report](/cucumber-reports/breakdown-report) | false |
| **breakdownConfig**          | **String**   | Path to the configuration file for [breakdown report](/cucumber-reports/breakdown-report). Mandatory if this report is enabled. | "" |
| **featureMapReport**         | **boolean**  | Enables/disables [feature map report](/cucumber-reports/feature-map-report) | false |
| **featureMapConfig**         | **String**   | Path to the configuration file for [feature map report](/cucumber-reports/feature-map-report). Mandatory if this report is enabled. | "" |
| **knownErrorsReport**        | **boolean**  | Enables/disables [known errors report](/cucumber-reports/known-errors-report) | false |
| **knownErrorsConfig**        | **String**   | Path to the configuration file for [known errors report](/cucumber-reports/known-errors-report). Mandatory if this report is enabled. | "" |
| **consolidatedReport**       | **boolean**  | Enables/disables [consolidated report](/cucumber-reports/consolidated-report) | false |
| **consolidatedReportConfig** | **String**   | Path to the configuration file for [consolidated report](/cucumber-reports/consolidated-report). Mandatory if this report is enabled. | "" |
| **systemInfoReport**         | **boolean**  | Enables/disables [system information report](/cucumber-reports/system-info) | false |
| **screenShotSize**           | **String**   | Defines the size of screenshots in the report. It should be a string similar to the one which is used in **width** attribute of the **img** HTML tag. Examples: **300px**, **50%** | "" |
| **screenShotLocation**       | **String**   | Not used at the moment | "" |
| **formats**                  | **String[]** | Defines the list of [export formats](/cucumber-reports/other-formats-export) | { } |
| **toPDF**                    | **boolean**  | Enables/disables [PDF export](/cucumber-reports/pdf-export) | false |
| **pdfPageSize**              | **String**   | Defines the page size of [exported PDF](/cucumber-reports/pdf-export) | "auto" |
| **retryCount**               | **int**      | Defines the number of [failed tests re-run](/cucumber-reports/failed-tests-rerun) | 0 |
| **threadsCount**             | **int**      | Defines the number of threads to [run in parallel](/cucumber-reports/parallel-runner) | 1 |
| **threadsCountValue**        | **String**   | Defines the system property name containing the number of threads to [run in parallel](/cucumber-reports/parallel-runner) | "" |
| **benchmarkReport** | **boolean** | Flag which enables/disables generation of the [Benchmark Report](/cucumber-reports/benchmark) | false |
| **benchmarkReportConfig** | **String** | The configuration which defines the way the [Benchmark Report](/cucumber-reports/benchmark) is to be generated | "" |
| **customTemplatesPath** | **String** | Defines the path for [Custom report templates](/cucumber-reports/customizing-report-format) | "" |
| **customReport** | **boolean** | Flag which enables/disables generation of the [Custom Report](/cucumber-reports/custom-report) | false |
| **customReportTemplateNames** | **String[]** | The list of template names to be used for custom report | {} |

# Parameterizing Values

In some cases we need to define dynamic values for the output. Normally it may be needed if we would like to drop reports to some folder which name corresponds to current date and time or we need to paste value from either system property or environment variable.

For this purpose the **outputFolder** and **reportPrefix** fields may contain specific expressions which are later calculated. The following expressions are supported:

| Format | Description |
| ------ | ----------- |
| DATE(&lt;format&gt;) | Inserts current date/time. The **format** should fit the [Joda time date format specification](http://joda-time.sourceforge.net/apidocs/org/joda/time/format/DateTimeFormat.html) |
| ${&lt;variable&gt;} | Inserts system property or environment variable. |

The following sample demonstrates the use of parameterizing values:

{% highlight java linenos=table %}
@ExtendedCucumberOptions(
        jsonReport = "target/cucumber.json",
        overviewReport = true,
        outputFolder = "${user.dir}/DATE(dd-MM-yyyy)"
        reportPrefix = "results-${user}")
{% endhighlight %}

In the above example entries **${user.dir}** and **${user}** will be replaced with system properties named **user.dir** and **user**. Otherwise, engine will try to replace values with environment variables of the same name.

The **DATE(dd-MM-yyyy)** statement will be replaced with actual current date in the specified format. E.g. **28-09-2016**.

# Overriding properties from external values

Sometimes we would like to define values externally depending on various parameters calculated in build scripts or so. For this purpose library reserves an ability to override fields in **ExtendedCucumberOptions** annotation with system properties. Such system properties should fit the following format:

**cucumber.reports.&lt;field name&gt;** 

where **field name** is the name of the **ExtendedCucumberOptions** annotation field name. E.g. if we want to override **retryCount** field we should define **cucumber.reports.retryCount** property.

Note that currently we can override only booleans, integers and strings. Array values aren't supported.


