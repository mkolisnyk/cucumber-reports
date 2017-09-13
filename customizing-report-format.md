---
title: Customizing Report Format
layout: default
---

# What is it?

Library provides a number of various reports out of the box. But those reports are generated internally and the user cannot change or add anything which is not supposed to be there by the report design. Sometimes it is acceptable when it requires some data processing logic. 

But there are cases when we simply need to change elements style, table layout or add additional value which is calculated based on known values (e.g. total number of features/scenarios/steps can be calculated as the sum of provided elements with different status). So, it would be good to provide some ability to customize standard reports with different style, layout or data representation.

For this purpose there is functionality of reports customization.

# The template document format

Each report is generated based on [Freemarker templates](http://freemarker.org/docs/dgui_template.html). Generally, it's the HTML format with special directives injection. Also, each basic report accepts some data which can be processed within the report. These data items are called **data beans**. These are pre-defined data-structures which are populated during each specific report processing. Thus, the major goal of report templates is just to display all this information.

All available data beans are explained in the [Javadoc documentation page](/cucumber-reports/site/apidocs/index.html).

# Necessary configuration

[Extended Cucumber Runner](/cucumber-reports/extended-cucumber-runner) operates with **customTemplatesPath** field of the **ExtendedCucumberOptions** annotation to point to customized templates. Generally, it defines the path to the templates based on the following logic:

* If **customTemplatesPath** is empty (as by default), the default templates are used
* If **customTemplatesPath** refers to existing file, it will process the [templates configuration file](#uploading-custom-templates-from-the-configuration-file).
* If **customTemplatesPath** refers to existing directory, it will process [template files from target folder and sub-folders](#uploading-custom-templates-from-the-folder).

## Uploading custom templates from the configuration file

If we point **customTemplatesPath** to some file it should be the configuration file of JSON format. Mainly it stores the map where the key is logical resource name and the value is the path to the template file associated with the resource name. Here is the sample JSON configuration file:

```json
{
  "benchmark": "/templates/default/benchmark.ftlh", 
  "breakdown": "/templates/default/breakdown.ftlh", 
  "consolidated": "/templates/default/consolidated.ftlh", 
  "coverage": "/templates/default/coverage.ftlh", 
  "detailed": "/templates/default/detailed.ftlh", 
  "feature_map": "/templates/default/feature_map.ftlh", 
  "feature_overview": "/templates/default/feature_overview.ftlh", 
  "known_errors": "/templates/default/known_errors.ftlh", 
  "overview": "/templates/default/overview.ftlh", 
  "overview_chart": "/templates/default/overview_chart.ftlh", 
  "pie_chart": "/templates/default/pie_chart.ftlh", 
  "retrospective": "/templates/default/retrospective.ftlh", 
  "system_info": "/templates/default/system_info.ftlh", 
  "tables": "/templates/default/tables.ftlh", 
  "usage": "/templates/default/usage.ftlh"
}
```

The above JSON actually replicates the default set of templates.

## Uploading custom templates from the folder

If we point **customTemplatesPath** to some folder, it should contain files with **ftlh** extension in this folder or it's sub-folders. Other files will be ignored. In this case, resource names will be associated to relative file names without an extension. E.g. if we want to load templates from specific folder (e.g. **test_template**) and we need some template associated with the **overview** resource name, we need to make sure the **test_template** folder contains **overview.ftlh** file. If this folder has **sample** sub-folder with the **test.ftlh** template file, this file will be associated with the **sample/test** resource.

# Naming conventions for each specific report

Each report generation template is associated to some resource name. Mainly, resource names are taken from file names templates are loaded from. At the same time the library itself generates basic reports from templates associated with some specific resources. The following table shows association between reserved resource name and actual report:

| Resource Name | Report type | Associated Data Bean |
| ------------- | ----------- | -------------------- |
| benchmark | [Benchmark Report](/cucumber-reports/benchmark) | [BenchmarkDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/BenchmarkDataBean.html) |
| breakdown | [Breakdown Report](/cucumber-reports/breakdown-report) | [BreakdownDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/BreakdownDataBean.html) |
| consolidated | [Consolidated Report](/cucumber-reports/consolidated-report) | [ConsolidatedDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/ConsolidatedDataBean.html) |
| coverage | [Coverage Report](/cucumber-reports/coverage-report) | [CoverageDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/CoverageDataBean.html) |
| detailed | [Detailed Results Report](/cucumber-reports/detailed-report) | [DetailedReportingDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/DetailedReportingDataBean.html) |
| feature_map | [Feature Map Report](/cucumber-reports/feature-map-report) | [FeatureMapDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/FeatureMapDataBean.html) |
| feature_overview | [Overview Chart Report](/cucumber-reports/overview-chart-report) | [FeatureOverviewDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/FeatureOverviewDataBean.html) |
| known_errors | [Known Errors Report](/cucumber-reports/known-errors-report) | [KnownErrorsDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/KnownErrorsDataBean.html) |
| overview | [Results Overview Report](/cucumber-reports/overview-report) | [OverviewDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/OverviewDataBean.html) |
| overview_chart | [Charts Report](/cucumber-reports/chart-report) | [OverviewChartDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/OverviewChartDataBean.html) |
| retrospective | [Retrospective Report](/cucumber-reports/retrospective-results-report) | [RetrospectiveDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/RetrospectiveDataBean.html) |
| system_info | [System Info Report](/cucumber-reports/system-info) | [SystemInfoDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/SystemInfoDataBean.html) |
| usage | [Steps Usage Report](/cucumber-reports/usage-report) | [UsageDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/UsageDataBean.html) |

# Rules how to override existing names and adding new entries for templates

## To customize basic report override existing resource names

As it was mentioned before, there are reserved resource names which are used for report generation. If you define custom report and associate it with existing resource name, it will be overridden.

## Any custom resource name can be used as inclusion

The [Freemarker templates](http://freemarker.org/docs/dgui_template.html) support [templates inclusions](http://freemarker.org/docs/ref_directive_include.html). This is useful when we would like to involve some re-usable components (e.g. some common macro/functions for drawing charts or tables). As soon as we define resource with custom name we always can include it in the Freemarker template. Just like this:

```html
<#include "custom_template">
<!-- Here we can use some instructions from included template -->
```

This way we may create multiple templates sharing some common re-usable functionality.

## If some resource names are not overridden the default templates will be picked up for this name

In most of the cases we just need to override several templates only for reports we mainly use. At the same time, all supported report types have default template. So, if we don't override some specific resource name there is always some default resource associated with some specific report.

# Sample use

For the sample purposes let's override [Results Overview Report](/cucumber-reports/overview-report) to display just summary table like this:

![Overview Table](/cucumber-reports/images/overview-report/summary.png)

To do so, let's follow the next steps:

## Create some folder with template files

Let's create our custom folder where we put our custom template files. Let's name it **templates**.

## Create template for the report to override

For overview report we need to add the template which will be associated with the **overview** resource name. The most universal way is to create **templates/overview.ftlh** file. Here we populate it with the following sample content:

```html
<#assign Math=statics['java.lang.Math'] >
<html>
<head>
	<title>${title}</title>
	<style type="text/css">
.passed {background-color:lightgreen;font-weight:bold;color:darkgreen}
.skipped {background-color:silver;font-weight:bold;color:darkgray}
.failed {background-color:tomato;font-weight:bold;color:darkred}
.undefined {background-color:gold;font-weight:bold;color:goldenrod}
.known {background-color:goldenrod;font-weight:bold;color:darkred}
	</style>
</head>
<body>
<table>
	<tr><th></th><th>Passed</th><th>Failed</th><th>Known</th><th>Undefined</th><th>Total</th><th>%Passed</th></tr>
	<tr><th>Features</th>
		<td class="passed" id="features_passed">${overallStats.getFeaturesPassed()}</td>
		<td class="failed" id="features_failed">${overallStats.getFeaturesFailed()}</td>
		<td class="known" id="features_known">${overallStats.getFeaturesKnown()}</td>
		<td class="undefined" id="features_undefined">${overallStats.getFeaturesUndefined()}</td>
		<td id="features_total">${overallStats.getFeaturesTotal()}</td>
		<td id="features_rate">
			<#if stats.getFeaturesTotal() == 0>
			NaN
			<#else>
				#{100 * (stats.getFeaturesPassed() + stats.getFeaturesKnown()) / stats.getFeaturesTotal() ;M0}%
			</#if>
		</td>
	</tr>
	<tr><th>Scenarios</th>
		<td class="passed" id="scenarios_passed">${overallStats.getScenariosPassed()}</td>
		<td class="failed" id="scenarios_failed">${overallStats.getScenariosFailed()}</td>
		<td class="known" id="scenarios_known">${overallStats.getScenariosKnown()}</td>
		<td class="undefined" id="scenarios_undefined">${overallStats.getScenariosUndefined()}</td>
		<td id="scenarios_total">${overallStats.getScenariosTotal()}</td>
		<td id="scenarios_rate">
			<#if stats.getScenariosTotal() == 0>
			NaN
			<#else>
				#{100 * (stats.getScenariosPassed() + stats.getScenariosKnown()) / stats.getScenariosTotal() ;M0}%
			</#if>
		</td>
	</tr>
	<tr><th>Steps</th>
		<td class="passed" id="steps_passed">${overallStats.getStepsPassed()}</td>
		<td class="failed" id="steps_failed">${overallStats.getStepsFailed()}</td>
		<td class="known" id="steps_known">${overallStats.getStepsKnown()}</td>
		<td class="undefined" id="steps_undefined">${overallStats.getStepsUndefined()}</td>
		<td id="steps_total">${overallStats.getStepsTotal()}</td>
		<td id="steps_rate">
			<#if stats.getStepsTotal() == 0>
			NaN
			<#else>
				#{100 * (overallStats.getStepsPassed() + overallStats.getStepsKnown()) / overallStats.getStepsTotal() ;M0}%
			</#if>
		</td>
	</tr>
</table>
<div><b>Overall Duration: ${(overallStats.overallDuration/3600)?string["0"]}h ${((overallStats.overallDuration % 3600) / 60)?string["00"]}m ${((overallStats.overallDuration % 3600) % 60)?string["00"]}s</b></div>

</body>
</html>
```

## Point Extended Cucumber Runner to custom template

Once we have the template and the folder we already can play with loading templates from folder.

This is the code we can use for this:

{% highlight java linenos=table %}
package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        overviewReport = true,
        customTemplatesPath = "templates", // Point to local folder with custom templates
        outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" })
public class SampleCucumberTest {
}
{% endhighlight %}

Since we named our template as **overview** and placed it in the templates root directory the **overview** resource name will be overridden and results overview will be generated using our custom template.

## Point Extended Cucumber Runner to custom template via configuration file

If we want to use configuration file (typically in case we associate specific resource with customized name), we need to create the configuration file. Let's create **templates.json** file with the following content.

```json
{
  "overview": "templates/overview.ftlh" 
}
```

In this case we can point out test runner to our custom templates configuration file like this:

{% highlight java linenos=table %}
package com.github.mkolisnyk.cucumber.reporting;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
        overviewReport = true,
        customTemplatesPath = "templates.json", // Point to local configuration file with custom templates
        outputFolder = "target")
@CucumberOptions(plugin = { "html:target/cucumber-html-report",
        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" },
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features" },
        glue = { "com/github/mkolisnyk/cucumber/steps" })
public class SampleCucumberTest {
}
{% endhighlight %}