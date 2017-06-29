---
title: System Info
layout: default
---

# What is it?

System info report is aimed to display system information. This includes system properties and environment variables.

# Where is it used?

Despite the fact that it is separate report, it is good addition to [detailed results report](/cucumber-reports/detailed-report). It is supposed to show system-specific information which also can be helpful in problems investigations as quite wide range of potential errors can be related to some improper or very specific environment settings.

So, typically it can be used as a part of [consolidated report](/cucumber-reports/consolidated-report) which joins system information with some other detailed results.

# Major sections

Currently the report contains 2 major sections:

  * **System Properties** - shows Java system properties
  * **Environment Variables** - shows environment variables

# Generation sample

## From code

```java
CucumberSystemInfo report = new CucumberSystemInfo();
report.setOutputDirectory("target");
report.setOutputName("cucumber-results");
report.execute(true);
```

## From Extended Cucumber runner

ExtendedCucumberOptions annotation has **systemInfoReport** flag. The System info report will be generated if this flag is set to **true**.

{% highlight java linenos=table %}
package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        jsonUsageReport = "target/cucumber-usage.json",
        systemInfoReport = true,
        outputFolder = "target")
@CucumberOptions(plugin = {"usage:target/cucumber-usage.json"},
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        tags = {"@consistent"})
public class SampleCucumberTest {
}
{% endhighlight %}