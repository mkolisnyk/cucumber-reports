---
title: Filter Rules
layout: default
---

# What is it?

Filter rules are the list of conditions applied to scenario results and targeted to retrieve the  scenarios matching some specific conditions.

# Where is it used?

Such filters are normally used as the part of configuration for the following reports:

* [Breakdown Report](/cucumber-reports/breakdown-report)
* [Known Errors Report](/cucumber-reports/known-errors-report)
* [Feature Map Report](/cucumber-reports/feature-map-report)

# How it is defined?

Data Dimension structure:

| Field | Type | Description |
| ----- | ---- | ----------- |
| alias | String | Contains logical name of the filter which is used as the column/row header |
| dimensionValue | Enum | Identifies the way this filter should be applied. Available values are listed in the [Filter Types](#filter-types) section of this page. |
| expression | String | Regular expression which is used as the filter  |
| subElements | List of Data Dimension | Nested data dimensions. In the report it would be reflected with additional level of column/row heading. In case of complex filters the scenario or step would match only if it matches current filter and all parent filters |

# Filter Types

Depending on the way the expression matching is done there are 2 major groups of filters:

* Simple - perform matching based on regular expression defined
* Complex - perform matching based on sub-elements matching logic. Sub-elements can be either simple or complex as well.

Let's take a look at them in more details.

## Simple filters

As it was mentioned above simple filters perform matching based on **expression** parameter value. It means that with simple matchers we check some single value in the results processed. Below is the list of available simple filters.

### CONTAINER

This filter type actually doesn't filter anything. It is created to group some other subsequent filters under some common name. It is used in the [Breakdown](/cucumber-reports/breakdown-report) and [Feature Map](/cucumber-reports/feature-map-report) reports to form sections.

### FEATURE

Returns the list of scenarios which feature name matches expression defined.

### SCENARIO

Returns the list of scenarios which scenario name matches expression defined.

### TAG

Returns the list of scenarios which have tags with the name matching expression defined.

### STEP

Returns the list of scenarios which contain steps matching expression defined.

### STEP_PARAM

Returns the list of scenarios which contain steps with table params or docstring matching expression defined.

### FAILED_STEP

Returns the list of scenarios containing failed step. Actively used by the [Known Errors Report](/cucumber-reports/known-errors-report).

### ERROR_MESSAGE

Returns the list of scenarios with failed steps which error message matches expression defined. Actively used by the [Known Errors Report](/cucumber-reports/known-errors-report).

## Complex filters

Complex filter returns the list of scenarios matching the combination of simple filters provided as subsequent elements. As the result, the filter itself doesn't use **expression** parameter for filtering. It must have **subElements** field defined.

### STEP_SEQUENCE

Returns the list of scenarios which contain the sequence of steps matching subsequent expressions. The [Breakdown report](/cucumber-reports/breakdown-report) calculates the number of matches by given expression.

### AND

Returns the list of scenarios matching all subsequent conditions. Subsequent items can be both simple and complex.

### OR

Returns the list of scenarios matching any subsequent condition. Subsequent items can be both simple and complex.

### NOT

Returns the list of scenarios matching none of the subsequent conditions. Subsequent items can be both simple and complex.

# Generation sample