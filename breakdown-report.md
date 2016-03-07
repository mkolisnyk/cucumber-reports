---
title: Breakdown Report
layout: default
---

# Where is it used?

Every Cucumber scenario may have multiple checkpoints to make. They may cover different functional areas as well as different input parameter types. Main thing is that all those varying options may be applied to the same scenario or even the same step. In any case, sometimes there is a need to how different aspects of application functionality are covered and how they are crossing each other. E.g. we may need to see how different payment types work depending on payment methods or how search results work depending on different combination of search parameters.

For this purpose the breakdown report was designed. The major purpose of the preakdown report is to **show the correspondence between multiple features and checkpoints with their execution status**. The below image shows an example of simple breakdown report:

![Breakdown Report Sample](/cucumber-reports/images/breakdown-report/breakdown-sample.png)

# Major sections

# Configuration options

# Generation sample

## Explicit Generation using configuration

```java
CucumberBreakdownReport report = new CucumberBreakdownReport();
report.setOutputDirectory("target/multi-breakdown");
report.setOutputName("cucumber-results");
report.setSourceFile("./src/test/resources/breakdown-source/cucumber.json");
report.executeReport(new File("src/test/resources/breakdown-source/simple.json"));
```

```json
{
	"@type":"com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportModel",
	"reportsInfo":[
		{
			"reportSuffix":"report1",
			"title":"First Breakdown",
			"refreshTimeout":5,
			"nextFile":"",
			"table":{
				"rows":{
					"alias":"Features",
					"dimensionValue":{"name":"FEATURE"},
					"expression":"(.*)",
					"subElements":[
						{
							"alias":"Booking",
							"dimensionValue":{"name":"FEATURE"},
							"expression":"(.*)[Bb]ooking(.*)",
							"subElements":[]
						},
						{
							"alias":"Payment",
							"dimensionValue":{"name":"FEATURE"},
							"expression":"(.*)[Pp]ayment(.*)",
							"subElements":[]
						}
					]
				},
				"cols":{"alias":"Scenarios","dimensionValue":{"name":"SCENARIO"},"expression":"(.*)","subElements":[]},
				"cell":{"name":"STEPS"}
			}
		},
		{
			"reportSuffix":"report2",
			"title":"Second Breakdown",
			"refreshTimeout":5,
			"nextFile":"",
			"table":{
				"rows":{"alias":"Features","dimensionValue":{"name":"FEATURE"},"expression":"(.*)","subElements":[]},
				"cols":{"alias":"Scenarios","dimensionValue":{"name":"SCENARIO"},"expression":"(.*)","subElements":[]},
				"cell":{"name":"SCENARIOS"}
			}
		}
	]
}
```

## Generation via Extended Runner