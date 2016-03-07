---
title: Breakdown Report
layout: default
---

# Where is it used?



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