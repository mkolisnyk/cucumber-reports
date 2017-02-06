---
title: Breakdown Report
layout: default
---

# Where is it used?

Every Cucumber scenario may have multiple checkpoints to make. They may cover different functional areas as well as different input parameter types. Main thing is that all those varying options may be applied to the same scenario or even the same step. In any case, sometimes there is a need to know how different aspects of application functionality are covered and how they are crossing each other. E.g. we may have multiple tests which operate with different payment types for different search criteria and we need to make sure that all combinations of payment type/search options are covered at least once. Also, we should be able to see how successful each combination is. Thus, we can visually identify which area we have problems with.

For this purpose the breakdown report was designed. The major purpose of the preakdown report is to **show the correspondence between multiple features and checkpoints with their execution status**. The below image shows an example of simple breakdown report:

![Breakdown Report Sample](/cucumber-reports/images/breakdown-report/breakdown-sample.png)

Each row and column corresponds to some set of criteria to match scenario or step. Each cell reflects the number of steps or scenarios which match both row and column criteria.

Each criteria can be either simple of complex. Mainly it is condition which should match some specific feature/scenario/step. More details on available filter rules can be found on [Filter Rules](/cucumber-reports/filter-rules) page.

# Configuration options

## Top-Level Parameters

At the highest level we should define the following options:

* Source file - the path to initial JSON report to generate breakdown from
* Output directory - the directory to send output to
* Enable breakdown - flag which enables breakdown report generation
* Breakdown configuration file - the path to breakdown report configuration file. This is one of the ways to specify configuration from code and the only way to do it via extended Cucumber runner

## Configuration File Format

### What is normally defined

Configuration is targeted to define [filter rules](/cucumber-reports/filter-rules) to be processed. These rules should be applied to rows and columns. Any cell shows how many items match rules for specific column and row. So, additional parameter to define is the information to be displayed in cells. It should be either scenarios or steps matching rules.

### Core Data Types

#### Breakdown Report Model

| Field | Type | Description |
| ----- | ---- | ----------- |
| reportsInfo | List of [Breakdown Report Info](#breakdown-report-info) | The list of items responsible for each specific breakdown report generation |

#### Breakdown Report Info

| Field | Type | Description |
| ----- | ---- | ----------- |
| reportSuffix | String | Defines report suffix. When multiple reports are generated this is the way to make sure that current report wouldn't overwrite any already existing reports |
| title | String | Identifies the title text which will be shown as the header of the generated report |
| nextFile | String | Usually relative path to the file where current report HTML will be redirected. Unused if **refreshTimeout** field value is 0 or less |
| refreshTimeout | Integer | Identifies the number of seconds to show current report page. After that the page would be redirected to the file specified by **nextFile** parameter. Unused if the **nextFile** field is empty |
| table | [Breakdown Report Table](#breakdown-report-table) | Contains filtering criteria for current breakdown report |

#### Breakdown Report Table

| Field | Type | Description |
| ----- | ---- | ----------- |
| rows | [Data Dimension](#data-dimension) | Contains filters which would be used for populating breakdown report row information |
| cols | [Data Dimension](#data-dimension) | Contains filters which would be used for populating breakdown report column information |
| cell | Enum | Contains the value type which is supposed to be displayed as cell value for each row/column crossing. Acceptable values are: **STEPS** (indicates that each cell in the report shows the number of steps with their corresponding status) or **SCENARIOS** (indicates that cells reflect statuses of scenarios matching row/column criteria) |

#### Data Dimension

| Field | Type | Description |
| ----- | ---- | ----------- |
| alias | String | Contains logical name of the filter which is used as the column/row header |
| dimensionValue | Enum | Identifies the way this filter should be applied  |
| expression | String | Regular expression which is used as the filter  |
| subElements | List of [Data Dimension](#data-dimension) | Nested data dimensions. In the report it would be reflected with additional level of column/row heading. In case of complex filters the scenario or step would match only if it matches current filter and all parent filters |

### Generating configuration from Java

Taking into account generally complicated nature of breakdown report and it's configuration in particular it is more convenient to initialize data structures in Java and then dump it into JSON file which would be then used as the configuration. In case of complicated tables with multiple reports it's way easier than updating configuration manually.

The below code sample shows how initialize breakdown configuration data structure and store it into JSON file:

```java
package com.sample.tests;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.cedarsoftware.util.io.JsonWriter;
import com.github.mkolisnyk.cucumber.reporting.CucumberBreakdownReport;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownCellValue;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DimensionValue;

public class CucumberBreakdownReportTest {
    @Test
    public void testGenerateSmallReport() throws Exception {
        BreakdownReportModel model = new BreakdownReportModel(
            new BreakdownReportInfo[] {
                new BreakdownReportInfo(
                    new BreakdownTable(
                        // Rows
                        new DataDimension("Search Options", DimensionValue.CONTAINER, "(.*)", new DataDimension[] {
                        }),
                        // Cols
                        new DataDimension("Check-points", DimensionValue.CONTAINER, "(.*)", new DataDimension[] {
                        }),
                        BreakdownCellValue.STEPS
                    ),
                "search", "Search Flow", 0, ""),
            }
        );
        String json = JsonWriter.objectToJson(model);
        FileUtils.writeStringToFile(new File("./base_model.json"), json);
    }
}

```

It is recommended to have some utility classes which contain such generators for project reports and run them from time to time to update configurations to fit the most recent state.

# Generation sample


## Explicit Generation using configuration

```java
CucumberBreakdownReport report = new CucumberBreakdownReport();
report.setOutputDirectory("target/multi-breakdown");
report.setOutputName("cucumber-results");
report.setSourceFile("./src/test/resources/breakdown-source/cucumber.json");
report.execute(new File("src/test/resources/breakdown-source/simple.json"), false);
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

If we use [Extended Cucumber Runner](/cucumber-reports/extended-cucumber-runner) we mainly need to specify 4 major parameters:

* **jsonReport** - the path to source test results JSON generated by standard Cucumber runner
* **outputFolder** - output folder to drop reports to
* **breakdownReport** - flag indicating whether we need to generate breakdown report. To enable breakdown report generation we **must set this flag value to true**.
* **breakdownConfig** - the path to breakdown report configuration file. This value must be specified if **breakdownReport** option value is **true**

Here is some sample class showing breakdown report enabled in extended cucumber options:

```java
package com.sample.tests;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
		jsonReport = "build/cucumber.json",
		outputFolder = "target/",
        breakdownReport = true,
        breakdownConfig = "src/test/resources/breakdown_config.json",
		)
@CucumberOptions(
        plugin = { "html:build/cucumber-html-report",
        		"junit:build/cucumber-junit.xml",
                "json:build/cucumber.json",
                "pretty:build/cucumber-pretty.txt",
                "usage:build/cucumber-usage.json"
                },
        features = { "src/test/java/com/sample/tests/features" },
        glue = { "com/sample/tests/steps" },
        tags = {}
)
public class SystemTest {
}
```

# Related Links

* [Filter Rules](/cucumber-reports/filter-rules)