---
title: Filter Rules
layout: default
---

# What is it?

# Where is it used?

* [Breakdown Report](/cucumber-reports/breakdown-report)
* [Known Errors Report](/cucumber-reports/known-errors-report)
* [Feature Map Report](/cucumber-reports/feature-map-report)

# How it is defined?

Data Dimension structure:

| Field | Type | Description |
| ----- | ---- | ----------- |
| alias | String | Contains logical name of the filter which is used as the column/row header |
| dimensionValue | Enum | Identifies the way this filter should be applied  |
| expression | String | Regular expression which is used as the filter  |
| subElements | List of Data Dimension | Nested data dimensions. In the report it would be reflected with additional level of column/row heading. In case of complex filters the scenario or step would match only if it matches current filter and all parent filters |

# Filters Types

## Simple filters

### CONTAINER

### FEATURE

### SCENARIO

### TAG

### STEP

### STEP_PARAM

### FAILED_STEP

### ERROR_MESSAGE

## Complex filters

### STEP_SEQUENCE

### AND, OR, NOT

# Generation sample