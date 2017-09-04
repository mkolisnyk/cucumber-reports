<#function to_id text>
  <#return text?lower_case?replace("[^a-z0-9]", "-", "r")>
</#function>
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
<h1>${model.title}</h1>

<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="${model.width}" height="${model.height}">
   	<#assign offset = 0>
   	<#assign barSize = model.width / stats?size>
   	<#list stats as stat>
   		<!-- Draw bar chart -->
   		<#assign total = stat.getFailed() + stat.getPassed() + stat.getSkipped() >
        <#assign scale = 0.9>
        <#if total gt 0>
            <#assign passedRatio = (scale * model.getHeight() * (stat.getPassed() / total)) >
            <#assign failedRatio = (scale * model.getHeight() * (stat.getFailed() / total)) >
            <#assign skippedRatio = (scale * model.getHeight() * (stat.getSkipped() / total)) >
            <rect x="${(offset * scale * barSize)?string["0"]}"
        	   y="0" width="${scale * barSize}" height="${skippedRatio}"
               stroke="black" stroke-width="1" fill="silver"></rect>
            <rect x="${(offset * scale * barSize)?string["0"]}" y="${skippedRatio}"
               width="${scale * barSize}" height="${failedRatio}"
               stroke="red" stroke-width="1" fill="red"></rect>
	        <rect x="${(offset * scale * barSize)?string["0"]}" y="${failedRatio + skippedRatio}"
	           width="${scale * barSize}" height="${passedRatio}"
    	       stroke="silver" stroke-width="1" fill="green"></rect>
            <#assign scaleTicksCount = 5>
            <#assign maxScaleValue = 100>
            <#assign vOffset = 10>
            <#list 0..scaleTicksCount as i>
            	<text x="${model.width * scale}" y="${(i * scale * model.getHeight() / scaleTicksCount) + vOffset}"
            		 font-size="12">${maxScaleValue - i * maxScaleValue / scaleTicksCount}%</text>
                <line stroke-dasharray="10,10"
                      x1="0"
                      y1="${(i * scale * model.getHeight() / scaleTicksCount)?string("0")}"
                      x2="${model.getWidth() * scale}"
                      y2="${(i * scale * model.getHeight() / scaleTicksCount)?string("0")}"
                       style="stroke:darkgray;stroke-width:1" />
            </#list>
			<text x="${(offset * scale * barSize) + barSize / 2}"
				y="${(scale * model.getHeight()) + vOffset}" font-size="12">${offset + 1}</text>
        </#if>
   		<#assign offset++>
   	</#list>
</svg>

  </body>
</html>