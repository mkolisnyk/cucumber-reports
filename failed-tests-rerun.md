---
title: Re-running failed tests
layout: default
---

# Where is it used?

Sometimes tests may fail due to reasons irrelevant to application itself. There can be some temporary environment problems (like network connection, temporary downtime etc) or some problems with tests themselves which leads to errors of temporary nature. Such errors are not treated as bugs and in most of the cases test may pass after single re-run.

In order to reduce such "noise" of false errors there was automated re-run functionality introduced. The main idea is that if test fails the engine itself re-runs the same tests several times until it passes or exceeds the limit of re-runs. In this case we will be able to see which errors are consistent to pay attention to them first.

# Configuration options

All this functionality is applied to [ExtendedCucumber](/cucumber-reports/extended-cucumber-runner) runner and major options are defined inside the **@ExtendedCucumberOptions** annotations.

Mainly there are 2 options to set which can be relevant to re-run functionality:

| Option | Type | Description | Default Value |
| ------ | ---- | ----------- | ------------- |
| **retryCount** | int | Specifies maximal number of retries. | 0 |
| **detailedAggregatedReport** | boolean | Defines whether test results should be aggregated. Takes effect only when **retryCount** is greated than 0. | false |

# Results Aggregation

When some test is re-run it is reported multiple times. Thus final report will contain both failed and passed iterations. This may lead to additional confusion and informational noise in test results. The aggregation functionality is aimed to process result reports leaving only latest run result and the number of retries performed.

As the result, if some test eventually passed after re-run it will be shown as passed. If test fails instantly the failed status will be reported. Such functionality is applied to the [Overview](/cucumber-reports/overview-report), [Detailed](/cucumber-reports/detailed-report), [Feature Overview Chart](/cucumber-reports/overview-chart-report) and [Chart](/cucumber-reports/chart-report) reports.

**NOTE:** if results aggregation option is turned on, the Detailed report generates both original and aggregated report. For more details, please, read [Aggregated Results](/cucumber-reports/detailed-report#aggregated-results) section of Detailed report documentation page.

# Conditional Re-run

Sometimes we need to perform re-run only in some specific cases. E.g. if entire environment is down or some core component failed to initialize the re-run would be simply pointless. Usually, such errors would be resulted with specific exception and corresponding stack trace. In order to provide some condition handling there was additional **@RetryAcceptance** annotation introduced. This annotation is applied to static method which accepts one parameter of **Throwable** type.

Here is the sample code which defines re-try handler which always allows the retry:

```java
@RetryAcceptance
public static boolean retryCheck(Throwable e) {
    return true;
}
```

This method should be of **boolean** return type and if it returns **true** when re-try is applicable and **false** otherwise.

There can be multiple **@RetryAcceptance** annotated methods. In this case the re-run would be performed only when all of those methods return **true**.

# Sample

Here is sample test class with retry enabled and custom re-run handling:

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
public static class SampleTestRetry {
    public static int retries = 0;
    public SampleTestRetry() {
    }
    @RetryAcceptance
    public static boolean retryCheck(Throwable e) {
        // Does not allow re-run if error message contains "Configuration failed" phrase
        return !e.getMessage().contains("Configuration failed");
    }
}
```

# Related links

* [Extended Cucumber Runner](/cucumber-reports/extended-cucumber-runner)
* [Cucumber JVM + JUnit: Re-run failed tests](http://mkolisnyk.blogspot.com/2015/05/cucumber-jvm-junit-re-run-failed-tests.html)