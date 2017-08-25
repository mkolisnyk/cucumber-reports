<html>
<head><style type="text/css">h1 {background-color:#9999CC}
h2 {background-color:#BBBBCC}
h3 {background-color:#DDDDFF}
th {border:1px solid black;background-color:#CCCCDD;}
td{border:1px solid black;}
table {border:1px solid black;border-collapse: collapse;}
.chart {border:0px none black;border-collapse: collapse;background-color: none;}
.passed {background-color:lightgreen;font-weight:bold;color:darkgreen}
.skipped {background-color:silver;font-weight:bold;color:darkgray}
.failed {background-color:tomato;font-weight:bold;color:darkred}
.undefined {background-color:gold;font-weight:bold;color:goldenrod}
.known {background-color:goldenrod;font-weight:bold;color:darkred}

div.header {
    display: block; text-align: center;
    position: running(header);
}
div.footer {
    display: block; text-align: center;
    position: running(footer);
}
div.content {page-break-after: always;}
@page {
	 size: ${pdfPageSize};
     @top-center {
     	content: "${title}";
	    color: silver;
	    font-size: 14px;
     }
     @top-right {
     	content: date("dd MMM, yyyy hh:mm");
	    color: silver;
	    font-size: 8px;
     }
    @bottom-right {
    	content: "Page " counter(page) " of " counter(pages) ;
	    color: silver;
	    font-size: 8px;
    }
}
</style>
<title>${title}</title>
<meta charset="UTF-8" />
</head>
  <body>
    <div class='header'></div>
    <div class='footer'></div>
<#include "pie_chart">
<table>
<tr>
<th colspan="3">Run Results Status</th></tr>
<tr><th>Features</th><th>Scenarios</th><th>Steps</th>
</tr>
<tr>
    <td class="chart">
	     <@drawPieChart 
	     	id="feature_status"
	     	width=450 height=300
	     	values=[overviewData.getFeaturesPassed(),
	     		 	overviewData.getFeaturesFailed(),
	     		 	overviewData.getFeaturesUndefined(),
	     		 	overviewData.getFeaturesKnown()]
	     	labels=["Passed", "Failed", "Undefined", "Known"]
	     	colors=["green", "red", "silver", "gold"]
	     	shadowColors=["darkgreen", "darkred", "darkgray", "goldenrod"]
	     	pieThickness=20
	     	shift=2 />
	</td>
    <td class="chart">
	     <@drawPieChart 
	     	id="scenario_status"
	     	width=450 height=300
	     	values=[overviewData.getScenariosPassed(),
	     		 	overviewData.getScenariosFailed(),
	     		 	overviewData.getScenariosUndefined(),
	     		 	overviewData.getScenariosKnown()]
	     	labels=["Passed", "Failed", "Undefined", "Known"]
	     	colors=["green", "red", "silver", "gold"]
	     	shadowColors=["darkgreen", "darkred", "darkgray", "goldenrod"]
	     	pieThickness=20
	     	shift=2 />
	</td>
    <td class="chart">
	     <@drawPieChart 
	     	id="steps_status"
	     	width=450 height=300
	     	values=[overviewData.getStepsPassed(),
	     		 	overviewData.getStepsFailed(),
	     		 	overviewData.getStepsUndefined(),
	     		 	overviewData.getStepsKnown()]
	     	labels=["Passed", "Failed", "Undefined", "Known"]
	     	colors=["green", "red", "silver", "gold"]
	     	shadowColors=["darkgreen", "darkred", "darkgray", "goldenrod"]
	     	pieThickness=20
	     	shift=2 />
	</td>
</tr>
<#if coverageIncluded == true>
<tr>
	<td colspan="3"></td>
</tr>
<tr>
	<th colspan="3">Coverage Status</th>
</tr>
<tr><th>Features</th><th>Scenarios</th><th>Steps</th></tr>
<tr>
    <td class="chart">
	     <@drawPieChart 
	     	id="feature_coverage"
	     	width=450 height=300
	     	values=[overviewData.getFeaturesPassed(),
	     		 	overviewData.getFeaturesUndefined()]
	     	labels=["Covered", "Not Covered"]
	     	colors=["green", "gold"]
	     	shadowColors=["darkgreen", "GoldenRod"]
	     	pieThickness=20
	     	shift=2 />
	</td>
    <td class="chart">
	     <@drawPieChart 
	     	id="scenario_coverage"
	     	width=450 height=300
	     	values=[overviewData.getScenariosPassed(),
	     		 	overviewData.getScenariosUndefined()]
	     	labels=["Covered", "Not Covered"]
	     	colors=["green", "gold"]
	     	shadowColors=["darkgreen", "GoldenRod"]
	     	pieThickness=20
	     	shift=2 />
	</td>
    <td class="chart">
	     <@drawPieChart 
	     	id="steps_coverage"
	     	width=450 height=300
	     	values=[overviewData.getStepsPassed(),
	     		 	overviewData.getStepsUndefined()]
	     	labels=["Covered", "Not Covered"]
	     	colors=["green", "gold"]
	     	shadowColors=["darkgreen", "GoldenRod"]
	     	pieThickness=20
	     	shift=2 />
	</td>
</tr>
</#if>
</table>		
	</body>
</html>