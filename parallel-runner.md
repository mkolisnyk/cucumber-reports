---
title: Parallel Runner
layout: default
---

# Where is it used?

# How to use it?

There are 2 major items which make parallel execution working:

* There is dedicated runner for parallel tests. So, you should annotate your test class with **@RunWith(ExtendedParallelCucumber.class)**
* The **ExtendedCucumberOptions** annotation should contain **threadsCount** field indicating the number of threads to run simultaneously

Here is the sample test class which uses parallel runner:

{% highlight java linenos=table %}
package com.sample.tests;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import com.github.mkolisnyk.cucumber.runner.ExtendedParallelCucumber;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedParallelCucumber.class)
@ExtendedCucumberOptions(
        threadsCount = 3,
		jsonReport = "build/cucumber.json",
		jsonUsageReport = "build/cucumber-usage.json",
		outputFolder = "build/",
		detailedReport = true,
		detailedAggregatedReport = true,
		overviewReport = true,
		toPDF = true
		)
@CucumberOptions(
        plugin = { "html:build/cucumber-html-report",
        		"junit:build/cucumber-junit.xml",
                "json:build/cucumber.json",
                "pretty:build/cucumber-pretty.txt",
                "usage:build/cucumber-usage.json"
                },
        features = { "src/test/java/com/sample/features" },
        glue = { "com/sample/steps" },
        tags = { "@sample_tag" }
)
public class SampleParallelTest {
}

{% endhighlight %}

# Related Links
