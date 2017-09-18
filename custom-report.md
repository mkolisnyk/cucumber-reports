---
title: Custom Report
layout: default
---

# What is it?

All built-in reports implement some specific calculation logic with customized data representation. So, when it comes to the actual report generation, the specifically prepared data structures are sent to the report templates. It means that such report has very limited set of data and it is hard to extend it with some additional data which wasn't provided initially.

The idea of custom report is to provide common set of Cucumber results to user defined template and users themselves define all the processing logic they need as the part of the report.

# How to configure it.

[Extended Cucumber Runner](/cucumber-reports/extended-cucumber-runner) reserves several configuration options which enable Custom Report generation. They are:

* **customReport** - Flag which enables/disables generation of the [Custom Report](/cucumber-reports/custom-report) | false |
* **customReportTemplateNames** - The list of template names to be used for custom report. They should be defined as a part of [Custom Templates](/cucumber-reports/customizing-report-format)

# What data is transferred

