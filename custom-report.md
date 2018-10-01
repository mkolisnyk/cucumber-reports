---
title: Custom Report
layout: default
---

# What is it?

All built-in reports implement some specific calculation logic with customized data representation. So, when it comes to the actual report generation, the specifically prepared data structures are sent to the report templates. It means that such report has very limited set of data and it is hard to extend it with some additional data which wasn't provided initially.

The idea of custom report is to provide common set of Cucumber results to user defined template and users themselves define all the processing logic they need as the part of the report.

# How to configure it.

[Extended Cucumber Runner](/cucumber-reports/extended-cucumber-runner) reserves several configuration options which enable Custom Report generation. They are:

* **customReport** - Flag which enables/disables generation of the [Custom Report](/cucumber-reports/custom-report)
* **customReportTemplateNames** - The list of template names to be used for custom report. They should be defined as a part of [Custom Templates](/cucumber-reports/customizing-report-format).

**NOTE:** All custom templates defined with **customReportTemplateNames** must be loaded.

# What data is transferred

The report sends data structure represented with [CustomReportDataBean](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/beans/CustomReportDataBean.html).

It contains 2 maps:

* Usage results - associates path to usage report with corresponding usage data stored in the [CucumberStepSource](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/usage/CucumberStepSource.html) structure
* Execution results- associates path to results report with corresponding execution results data stored in the [CucumberFeatureResult](/cucumber-reports/site/cucumber-report-generator/apidocs/com/github/mkolisnyk/cucumber/reporting/types/result/CucumberFeatureResult.html) structure

## System properties and environment variables.

Also, since version 1.3 the set of system properties and map of environment variables was added. It is needed if we want to align results data to some system information.

Here is the example of custom template which lists all system properties and environment variables:

``` xml
<h1>System Properties</h1>
<table width="700px">
<tr><th>Property</th><th>Value</th></tr>

<#list systemProperties?keys as key>
<tr><td>${key}</td><td>${systemProperties[key]}</td></tr>
</#list>

</table>

<h1>Environment Variables</h1>
<table width="700px">
<tr><th>Variable</th><th>Value</th></tr>

<#list environmentVariables?keys as key>
<tr><td>${key}</td><td>${environmentVariables[key]}</td></tr>
</#list>
```

If you need to use specific environment variable or system property in your freemarker template, you can use instructions like that:

``` xml
${systemProperties['some.property']}
${environmentVariables['MY_VARIABLE']}
```
 
So, each custom template should operate with the above structures