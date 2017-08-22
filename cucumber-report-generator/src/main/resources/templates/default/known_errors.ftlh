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

   <table>
  	<tr><th>Description</th><th>Priority</th><th># of Occurrences</th></tr>
<#list results as result>
<tr class="${result.info.priority?lower_case}"><td><p><b>${result.info.title}</b></p><p>${result.info.description}</p></td><td>${result.info.priority}</td><td>${result.frequency}</td></tr>
</#list>
	</table>
	<div>
	<font size="2" color="darkgray"><i>Note: the errors listed above may actually appear due to reasons different from described ones.
	Report itself shows errors matching specific pattern and groups them according to this common pattern. So, the information
	viewed in the report may just provide an idea of what could probably happen but more precise analysis is still needed.</i></font>
	</div>
	</body>
</html>