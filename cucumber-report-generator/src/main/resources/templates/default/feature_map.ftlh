<html>
<head><style type="text/css">h1 {background-color:#9999CC}
h2 {background-color:#BBBBCC}
h3 {background-color:#DDDDFF}
th {border:1px solid black;background-color:#CCCCDD;}
td{border:1px solid black;}
table {border:1px solid black;border-collapse: collapse;}
.chart {border:0px none black;border-collapse: collapse;background-color: none;}
.passed {background-color:lightgreen;font-weight:bold;color:darkgreen}
.failed {background-color:tomato;font-weight:bold;color:darkred}
.undefined {background-color:gold;font-weight:bold;color:goldenrod}
.skipped {background-color:silver;font-weight:bold;color:darkgray}
.passed_description {background-color:#D0FFD0;font-weight:bold;font-style:italic;color:black}
.failed_description {background-color:#FFA898;font-weight:bold;font-style:italic;color:black}
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
${refreshData}
<title>${title}</title>
<script>
  var toggle = function(id) {
  var mydiv = document.getElementById(id);
  if (mydiv.style.display === 'block' || mydiv.style.display === '')
    mydiv.style.display = 'none';
  else
    mydiv.style.display = 'block'
  }
</script>
  </head>
  <body>
    <div class='header'></div>
    <div class='footer'></div>
  
<h1>${title}</h1>
<#include "pie_chart">
<table class="hoverTable">
	<thead>
        <#assign colOffset = table.getRows().depth() >
        <#assign rowOffset = table.getCols().depth() >
        <tr><th colspan="${colOffset}" rowspan="${rowOffset}">&#160;</th>
        <#list 0..(rowOffset-1) as i>
            <#assign line = table.getCols().getRow(i) >
            <#list line as item>
                <#if item.depth() == 1>
                    <th colspan="${item.width()}" rowspan="${rowOffset - item.depth() - i + 1}">${item.getAlias()}</th>
                <#else>
                    <th colspan="${item.width()}" rowspan="1">${item.getAlias()}</th>
                </#if>
            </#list>
            </tr><tr>
        </#list>
        </tr>	
	</thead>
	<tbody>
		<#macro drawCell scenarios>
			<td><ul>
	        <#assign index = 0>
	        <#assign prevFeature = "">
	        <#assign first = true>
	        <#list scenarios as scenario>
	        	<#if scenario.getFeature().getName() != prevFeature>
		        	<#if first>
		        		<#assign first=false>
		        	<#else>
		        		</ul></li>
		        	</#if>
	        		<li> <b>Feature:</b> ${scenario.getFeature().getName()} <ul>
	        	</#if>
	        	<li>
					<a onclick="toggle('scenario${index}')"><b>Scenario:</b> ${scenario.getName()}</a>
	        		<div id="scenario${index}" style="display:none">
	        		<@drawScenario scenario=scenario />
	        		</div>
        		</li>
        		<#assign prevFeature = scenario.getFeature().getName() >
	        	<#assign index++ >
	        </#list>
        	<#if !first>
        		</ul></li>
        	</#if>
	        </ul></td>
		</#macro>
		<#macro drawScenario scenario>
			<table>
				<tr>
					<td>${scenario.getDescription()}</td>
				</tr>
				<tr>
					<td>
					<table>
					<#list scenario.getSteps() as step>
					   <tr><td><b>${step.getKeyword()}</b> ${step.getName()}</td></tr>
					   <#if step.getDocString()?has_content>
			            	<tr><td><i>${step.getDocString()}</i></td></tr>
			           </#if>
			        	<#if step.getRows()?has_content>
		                	<tr><td style="padding-left:20px">
		                		<table>
				                <#list step.getRows() as row>
				                    <tr>
				                    <#list row as cell >
				                        <td>${cell}</td>
				                    </#list>
				                    </tr>
		               			</#list>
		                		</table>
		            		</td></tr>
		            	</#if>
					</#list>
					</table>
					</td>
				</tr>
			</table>
		</#macro>
 
	<#function generateRowHeading data maxDepth level>
		<#assign cellDepth = 1 >
        <#assign aliasText = data.getAlias() >
        <#if data.depth() == 1>
            <#assign cellDepth = maxDepth - level + 1 >
        </#if>
        <#assign content = "<th colspan=\"${cellDepth}\" rowspan=\"${data.width()}\">${aliasText}</th>" >
        <#if data.hasSubElements()>
            <#list data.getSubElements() as item >
                <#assign content = content + generateRowHeading(item, maxDepth, level + 1) >
            </#list>
        <#else>
            <#assign content = content + "</tr><tr>" >
        </#if>
        <#return content>
	</#function>

    <#function generateRowHeadingFromTable table>
        <#assign rows = table.getRows() >
        <#return "<tr>" + generateRowHeading(rows, rows.depth(), 1) + "</tr>">
    </#function>
    
    <#assign rowHeadings = generateRowHeadingFromTable(table) >
    <#assign headingRows = rowHeadings?split("</tr>") >
    <#list 0..(scenarios?size - 1) as i>
        <#assign row = headingRows[i]>
        ${row}
        <#list 0..(scenarios[i]?size - 1) as j>
			<@drawCell scenarios=scenarios[i][j] />
        </#list>
        </tr>
    </#list>
	</tbody>
</table>
	</body>
</html>