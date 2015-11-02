---
title: Steps Usage Report
layout: default
---

# Where is it used?

# Major sections

## Overview Graph

Overview Graph is graphical representation of general usage for all the keywords. Sample usage graph looks like: 

![Overview Graph](/cucumber-reports/images/usage-report/overview-graph.png)

This is major item of the entire report and mainly it is enough to view it in order to get an idea about how good we are at steps re-use.
The X-axis goes through the number of steps re-use. The Y-axis shows how many steps are re-used X times.
So, if we see on this graph the point with dimensions like X = 28 and Y = 2 it indicates that we have 2 keywords which are re-used 28 times within the test suite. 

Key numbers displayed on the graph are:

* **Average number** - the mathematical average. This value indicates the average steps re-use count across the entire set of steps. Since it's mathematical average number it doesn't reflect statistical distribution of all steps. It means that high average number may be reached even having most of the steps used just once but 1 or 2 steps are re-used big number of times. In this case mainly we don't re-use steps but our numbers still look good. Thus our information is a bit distorted and we can get some wrong conclusions during the analysis. 
* **Median** - the statistical average which gives more precise picture of average re-use count which also takes into account the actual distribution of the steps re-use data. It is also average number but it indicates that at least 50% of steps are re-used less than this median value times. Thus, in case of huge number of single time used steps our median will still be 1 even despite we have some steps which are re-used hundreds of times while simple mathematical average shows better number. 
* **Re-use Ratio** - indicates that in current test suite %N of steps were written without implementing actual glue code. In order words this number means that %N of all our steps in current test suite are automated even during test design stage. So, the bigger this ratio is the more effective we use our steps. 

When we analyze average values we should make sure that both of them are at least greater than 2. This means that in average we use each step at least once without necessity of spending time on writing this step implementation. This already makes Gherkin use effective. If the above numbers are bigger it's even better. 
In terms of re-use ratio we just should make sure that this ratio is as big as possible. This would indicate that most of our automation is done at test design stage. 

## Overview Table

![Overview Table](/cucumber-reports/images/usage-report/overview-table.png)

## Cucumber Usage Detailed Information

![Detailed Information Table](/cucumber-reports/images/usage-report/detailed-information-table.png)

![Detailed Information Charts](/cucumber-reports/images/usage-report/detailed-information-charts.png)

# Generating report from code

{% highlight java linenos=table %}
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;

...

CucumberUsageReporting report = new CucumberUsageReporting();
report.setOutputDirectory("target");
report.setJsonUsageFile("./src/test/resources/cucumber-usage.json");
report.executeReport();
{% endhighlight %}

# Generating report via Cucumber runner

{% highlight java linenos=table %}
package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        jsonUsageReport = "target/cucumber-usage.json",
        usageReport = true,
        outputFolder = "target")
@CucumberOptions(plugin = {"usage:target/cucumber-usage.json"},
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = {"@consistent"})
public class SampleCucumberTest {
}
{% endhighlight %}

# Related Links

* [Cucumber JVM: Advanced Reporting](http://mkolisnyk.blogspot.com/2015/05/cucumber-jvm-advanced-reporting.html)