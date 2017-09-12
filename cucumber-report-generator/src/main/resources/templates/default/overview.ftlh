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
<#include "tables">
  <h1>Overview Chart</h1>
	<table class="chart">
	<tr>
	<th class="chart">Features Status</th><th class="chart">Scenario Status</th>
	</tr>
	<tr class="chart">
    <td class="chart">
	     <@drawPieChart 
	     	id="feature_status"
	     	width=450 height=300
	     	values=[overallStats.getFeaturesPassed(),
	     		 	overallStats.getFeaturesFailed(),
	     		 	overallStats.getFeaturesUndefined(),
	     		 	overallStats.getFeaturesKnown()]
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
	     	values=[overallStats.getScenariosPassed(),
	     		 	overallStats.getScenariosFailed(),
	     		 	overallStats.getScenariosUndefined(),
	     		 	overallStats.getScenariosKnown()]
	     	labels=["Passed", "Failed", "Undefined", "Known"]
	     	colors=["green", "red", "silver", "gold"]
	     	shadowColors=["darkgreen", "darkred", "darkgray", "goldenrod"]
	     	pieThickness=20
	     	shift=2 />
	</td>
	</tr>
	</table>

<h1>Summary</h1>

<@drawOverviewStatsTable stats=overallStats />
	
<h1>Features Status</h1>
	
<table>
	<tr>
		<th>Feature Name</th>
		<th>Status</th>
		<th>Passed</th>
		<th>Failed</th>
		<th>Known</th>
		<th>Undefined</th>
		<th>Total</th>
		<th>Duration</th>
	</tr>
<#list features as feature>
<#if feature.featureName?has_content>
	<tr class="${feature.status}">
		<td>${feature.featureName}</td>
		<td>${feature.status}</td>
		<td>${feature.stats.scenariosPassed}</td>
		<td>${feature.stats.scenariosFailed}</td>
		<td>${feature.stats.scenariosKnown}</td>
		<td>${feature.stats.scenariosUndefined}</td>
		<td>${feature.stats.getScenariosTotal()}</td>
		<td>${feature.duration}s</td>
	</tr>	
</#if>
</#list>

</table>
	
<h1>Scenario Status</h1>

<table>
	<tr>
		<th>Feature Name</th>
		<th>Scenario</th>
		<th>Status</th>
		<th>Passed</th>
		<th>Failed</th>
		<th>Known</th>
		<th>Undefined</th>
		<th>Total</th>
		<th>Retries</th>
		<th>Duration</th>
	</tr>
<#list scenarios as scenario>
	<tr class="${scenario.status}">
		<td>${scenario.featureName}</td>
		<td>${scenario.scenarioName}</td>
		<td>${scenario.status}</td>
		<td>${scenario.stats.stepsPassed}</td>
		<td>${scenario.stats.stepsFailed}</td>
		<td>${scenario.stats.stepsKnown}</td>
		<td>${scenario.stats.stepsUndefined}</td>
		<td>${scenario.stats.getStepsTotal()}</td>
		<td>${scenario.retries}</td>
		<td>${scenario.duration}s</td>
	</tr>	
</#list>
</table>	
	</body>
</html>