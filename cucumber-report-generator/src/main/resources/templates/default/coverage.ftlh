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
  
  <h1>Overview Chart</h1>
	<table class="chart">
	<tr>
	<th class="chart">Features Status</th><th class="chart">Scenario Status</th>
	</tr>
	<tr class="chart">
    <td class="chart">
	     <@drawPieChart 
	     	id="feature_coverage"
	     	width=450 height=300
	     	values=featureStatuses
	     	labels=["Covered", "Not Covered"]
	     	colors=["green", "gold"]
	     	shadowColors=["darkgreen", "GoldenRod"]
	     	pieThickness=20
	     	shift=2 />
	</td>
    <td class="chart">
 	     <@drawPieChart 
	     	id="feature_coverage"
	     	width=450 height=300
	     	values=scenarioStatuses
	     	labels=["Covered", "Not Covered"]
	     	colors=["green", "gold"]
	     	shadowColors=["darkgreen", "GoldenRod"]
	     	pieThickness=20
	     	shift=2 />
	</td>
	</tr>
	</table>

<h1>Features Status</h1>
<table>
	<tr>
		<th rowspan="2">Feature Name</th><th rowspan="2">Status</th><th colspan="2">Scenarios</th><th rowspan="2">Tags</th>
	</tr>
	<tr>
		<th>Covered</th><th>Not Covered</th>
	</tr>
<#list features as row>
	<tr class="${row.status}">
		<td>${row.featureName}</td>
		<td>${row.status}</td>
		<td>${row.covered}</td>
		<td>${row.notCovered}</td>
		<td>${row.tags?join(", ")}</td>
	</tr>
</#list>

</table>
<h1>Scenario Status</h1>
<table>
	<tr>
		<th rowspan="2">Feature Name</th><th rowspan="2">Scenario</th><th rowspan="2">Status</th><th colspan="2">Steps</th><th rowspan="2">Tags</th>
	</tr>
	<tr>
		<th>Covered</th><th>Not Covered</th>
	</tr>
<#list scenarios as row>
	<tr class="${row.status}">
		<td>${row.featureName}</td>
		<td>${row.scenarioName}</td>
		<td>${row.status}</td>
		<td>${row.covered}</td>
		<td>${row.notCovered}</td>
		<td>${row.tags?join(", ")}</td>
	</tr>
</#list>

</table>
	</body>
</html>