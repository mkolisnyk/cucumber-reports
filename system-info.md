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

