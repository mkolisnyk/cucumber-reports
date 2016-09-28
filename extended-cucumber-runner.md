---
title: Extended Cucumber Runner
layout: default
---

# Introduction

Extended Cucumber Runner is the extension of standard **Cucumber** JUnit runner which additionally supports:

* [Before- and After- suite methods](/cucumber-reports/before-after-methods)
* [Failed Tests Re-run](/failed-tests-rerun)
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

| Option | Type | Description | Default Value | Applied to |
| ------ | ---- | ----------- | ------------- | ---------- |
| **jsonReport**               | **String** | | | |
| **jsonUsageReport**          | **String** | | | "cucumber-usage.json" |
| **outputFolder**             | **String** | | | "." |
| **reportPrefix**             | **String** | | | "cucumber-results" |
| **usageReport**              | **boolean** | | | false |
| **overviewReport**           | **boolean** | | | false |
| **coverageReport**           | **boolean** | | | false |
| **detailedReport**           | **boolean** | | | false |
| **detailedAggregatedReport** | **boolean** | | | false |
| **toPDF**                    | **boolean** | | | false |
| **screenShotSize**           | **String** | | | "" |
| **screenShotLocation**       | **String** | | | "" |
| **includeCoverageTags**      | **String[]** | | | { } |
| **excludeCoverageTags**      | **String[]** | | | { } |
| **retryCount**               | **int** | | | 0 |

# Parameterizing Values

In some cases we need to define dynamic values for the output. Normally it may be needed if we would like to drop reports to some folder which name corresponds to current date and time or we need to paste value from either system property or environment variable.

For this purpose the **outputFolder** and **reportPrefix** fields may contain specific expressions which are later calculated. The following expressions are supported:

| Format | Description |
| ------ | ----------- |
| DATE(<format>) | Inserts current date/time. The **format** should fit the [Joda time date format specification](http://joda-time.sourceforge.net/apidocs/org/joda/time/format/DateTimeFormat.html) |
| ${<variable>} | Inserts system property or environment variable. |

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

**cucumber.reports.<field name>** is the **ExtendedCucumberOptions** annotation field name. E.g. if we want to override **retryCount** field we should define **cucumber.reports.retryCount** property.

Note that currently we can override only booleans, integers and strings. Array values aren't supported.

where **field name** is the name of a
