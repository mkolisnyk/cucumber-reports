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


.hoverTable {
    display: flex;
    flex-flow: column;
    height: 90%;
    border-spacing: 0;
    border-collapse: collapse;
    overflow-y: auto;
    z-index: 1;
}

.hoverTable td, th, .ff-fix {
    cursor: pointer;
    padding: 3px;
    position: relative;
}

.hoverTable td:hover::after,
.ff-fix:hover::after {
    background-color: #ffa;
    content: '\00a0';
    height: 10000px;
    left: 0;
    position: absolute;
    top: -5000px;
    width: 100%;
    z-index: -1;
}
.hoverTable tr:hover {
          background-color: #ffff99;
    }
.hoverTable td:hover {
          background-color: #ffff99;
          border:3px solid #ffff99;
}

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
	<#macro drawCell stats type>
		<#assign total = stats.getFailed() + stats.getPassed() + stats.getSkipped() >
        <#if total gt 0>
        	<#switch type>
			  <#case "BARS_ONLY">
			     <td><@drawBarCell stats=stats /></td>
			     <#break>
			  <#case "BARS_WITH_NUMBERS">
			     <td><@drawBarNumberCell stats=stats /></td>
			     <#break>
			  <#case "NUMBERS_ONLY">
			     <td><@drawNumberOnlyCell stats=stats /></td>
			     <#break>
			  <#case "PIE_CHART">
			     <td>
	     	     <@drawPieChart 
			     	id=""
			     	width=450 height=300
			     	values=[stats.getPassed(), stats.getFailed(), stats.getSkipped()]
			     	labels=["Passed", "Failed", "Skipped"]
			     	colors=["green", "red", "silver"]
			     	shadowColors=["darkgreen", "darkred", "darkgray"]
			     	pieThickness=20
			     	shift=2 />
			     </td>
			     <#break>
			  <#default>
			     <td>${type}</td>
			</#switch>
        <#else>
	    	<td bgcolor="silver"><center><b>N/A</b></center></td>
        </#if>
	</#macro>
	
	<#macro drawBarCell stats>
		<#assign total = stats.getFailed() + stats.getPassed() + stats.getSkipped() >
        <#assign cellSize = 30>
        <#assign passedRatio = (cellSize * (stats.getPassed() / total)) >
        <#assign failedRatio = (cellSize * (stats.getFailed() / total)) >
        <#assign skippedRatio = (cellSize * (stats.getSkipped() / total)) >
        <#if stats.getFailed() gt 0>
            <#assign failedRatio++ >
        </#if>
		<table width="100%">
		<tr>
			<td><a title="Passed: ${stats.getPassed()}, Failed: ${stats.getFailed()}, Skipped: ${stats.getSkipped()}">
                <svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="100%" height="30">
                <rect y="0" width="100%" height="${passedRatio?string("0")}"
                    stroke="black" stroke-width="1" fill="green"></rect>
                <rect y="${passedRatio?string("0")}" width="100%" height="${failedRatio?string("0")}"
                    stroke="red" stroke-width="1" fill="red"></rect>
                <rect y="${(failedRatio + passedRatio)?string("0")}" width="100%" height="${skippedRatio?string("0")}"
                    stroke="silver" stroke-width="1" fill="silver"></rect>
                </svg></a>
            </td>
        </tr>
    	</table>
    </#macro>
    <#macro drawBarNumberCell stats>
    	<table width="100%">
	    	<tr>
			<td><@drawBarCell stats=stats /></td>
			</tr>
			<tr>
			<td><@drawNumberOnlyCell stats=stats /></td>
			</tr>
		</table>
    </#macro>
   <#macro drawNumberOnlyCell stats>
    	<center><b>
        <#if stats.getPassed() gt 0>
            <span class="passed">${stats.getPassed()}</span>
        </#if>
        <#if stats.getFailed() gt 0>
            <span class="failed">${stats.getFailed()}</span>
        </#if>
        <#if stats.getSkipped() gt 0 >
            <span class="skipped">${stats.getSkipped()}</span>
        </#if>
    	</b>
    	</center>
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
    <#list 0..(stats?size - 1) as i>
        <#assign row = headingRows[i]>
        ${row}
        <#list 0..(stats[i]?size - 1) as j>
            <@drawCell stats=stats[i][j] type=table.getDisplayType() />
        </#list>
        </tr>
    </#list>
	</tbody>
</table>


	</body>
</html>