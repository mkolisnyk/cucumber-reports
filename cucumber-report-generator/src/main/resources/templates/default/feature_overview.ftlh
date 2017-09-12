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
  
	<table>
	<tr>
    <td>
	    <svg  xmlns="http://www.w3.org/2000/svg" version="1.1" width="220" height="340">
			<polygon points="10,40 100,40 120,60 100,80 10,80"
				style="fill:green" />
			<polygon points="10,85 120,85 140,105 120,125 10,125"
				style="fill:#64AE00" />
			<polygon points="10,130 140,130 160,150 140,170 10,170"
				style="fill:yellow" />
			<polygon points="10,175 160,175 180,195 160,215 10,215"
				style="fill:orange" />
			<polygon points="10,220 180,220 200,240 180,260 10,260"
				style="fill:orangered" />
			<polygon points="10,265 200,265 220,285 200,305 10,305"
				style="fill:darkred" />
			<text x="80" y="70" font-size="32" font-weight="bold" style="fill:white;stroke:silver">A</text>
			<text x="100" y="115" font-size="32" font-weight="bold" style="fill:white;stroke:silver">B</text>
			<text x="120" y="160" font-size="32" font-weight="bold" style="fill:white;stroke:silver">C</text>
			<text x="140" y="205" font-size="32" font-weight="bold" style="fill:white;stroke:silver">D</text>
			<text x="160" y="250" font-size="32" font-weight="bold" style="fill:white;stroke:silver">E</text>
			<text x="180" y="295" font-size="32" font-weight="bold" style="fill:white;stroke:silver">F</text>

			<text x="20" y="30" font-size="18" style="fill:black">Higher Quality</text>
			<text x="20" y="325" font-size="18" style="fill:black">Lower Quality</text>
	</svg>
    </td>
    <td class="chart" valign="center">
	    <svg  xmlns="http://www.w3.org/2000/svg" version="1.1" width="120" height="120">
			<polygon points="10,70 30,40 120,40 120,100 30,100"
				style="fill:black" />
			<text x="30" y="80" font-size="32" font-weight="bold" style="fill:white;stroke:silver">${overallRate}</text>
		</svg>
    </td>
    </tr>
	<tr><td><b>&#160;&#160;&#160;&#160;Overall Rate</b></td><td><center>${passRate}%</center></td></tr>
	<tr><td colspan="2"><center>Features</center></td></tr>
<#list featureRate?keys as key>
	<tr><td><b>&#160;&#160;&#160;&#160;${key}</b></td><td><center><font size="3">${"ABCDEF"?replace(featureRate[key],"<font size=\"5\">" + featureRate[key] + "</font>")}</font></center></td></tr>
</#list>
	</table>
	</body>
</html>