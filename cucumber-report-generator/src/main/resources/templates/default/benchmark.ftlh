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

.lowest {background-color:lightyellow;color:black}
.low {background-color:gold;color:black}
.medium {background-color:orange;color:black}
.high {background-color:orangered;color:black}
.highest {background-color:darkred;color:black}

.passed_description {background-color:#D0FFD0;font-weight:bold;font-style:italic;color:black}
.failed_description {background-color:#FFA898;font-weight:bold;font-style:italic;color:black}

.noborder {border: none;border-collapse: collapse;vertical-align: top;}

OL { counter-reset: item }
OL>LI { display: block }
OL>LI:before { content: counters(item, ".") " "; counter-increment: item }

div.header {
    display: block; text-align: center;
    position: running(header);
}
div.footer {
    display: block; text-align: center;
    position: running(footer);
}
div.content {page-break-after: always;}
div.content:last-child {page-break-after: avoid;}
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
ol a::after {
  content: leader('.') target-counter(attr(href), page);
}
</style>
${refreshData}
<title>${title}</title>
<meta charset="UTF-8" />
</head>
  <body>
    <div class='header'></div>
    <div class='footer'></div>
    <h1>Features Status</h1>
    <table>
    	<tr>
            <th>Feature Name</th>
<#list headers as header>
            <th>${header}</th>
</#list>
    	</tr>
<#list featureRows as row>
        <tr><td>${row.name}</td>
    <#list row.getResults()>
        <#items as item>
			<td class="${item.getFeatureStatus()}">${item.getScenariosPassed()} / ${item.getScenariosFailed()} / ${item.getScenariosUndefined()}</td>
        </#items>
    </#list>
        </tr>
</#list>
    </table>
    <h1>Scenarios Status</h1>
    <table>
    	<tr>
            <th>Feature/Scenario Name</th>
<#list headers as header>
            <th>${header}</th>
</#list>
    	</tr>
<#list scenarioRows as row>
        <tr><td>${row.name}</td>
    <#list row.getResults()>
        <#items as item>
			<td class="${item.getScenarioStatus()}">${item.getStepsPassed()} / ${item.getStepsFailed()} / ${item.getStepsUndefined()}</td>
        </#items>
    </#list>
        </tr>
</#list>
    </table>    
  </body>
</html>