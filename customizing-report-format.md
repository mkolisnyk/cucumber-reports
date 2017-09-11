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

# Rules how to override existing names and adding new entries for templates

# Sample use