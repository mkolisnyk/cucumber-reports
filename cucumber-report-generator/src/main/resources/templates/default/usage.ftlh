<#function sum values>
	<#assign result = 0>
	<#list values as value>
		<#assign result = result + value>
	</#list>
	<#return result>
</#function>
<html>
	<head>
		<style type="text/css">
h1 {background-color:#9999CC}
h2 {background-color:#BBBBCC}
h3 {background-color:#DDDDFF}
th {border:1px solid black;background-color:#CCCCDD;}
td{border:1px solid black;}
.none {border:0px none black;}
table{border:1px solid black;border-collapse: collapse;}
tr:nth-child(even) {background: #CCC}
tr:nth-child(odd) {background: #FFF}
        </style>
        <title>Cucumber Steps Usage Report</title>
    </head>
    <body>
    	<h1>Cucumber Usage Statistics</h1>
    	<h2>Overview Graph</h2>
    	<p>
        <#assign hsize = 400 >
        <#assign vsize = 400 >
        <#assign hstart = 40 >
        <#assign vstart = 30 >
        <#assign hend = 350 >
        <#assign vend = 300 >

        <#assign hstep = 0 >
        <#assign vstep = 0 >

        <#assign lastKey = 0>
        <#if !usageCounts?has_content >
            <#assign hscale = 1>
            <#assign vscale = 1>
        <#else>
            <#assign lastKey = usageCounts?keys?last >
            <#assign hscale = (hend - 2 * hstart) / (lastKey + 1) >
            <#assign vscale = (vend - 2 * vstart) / (stepsUseMax + 1) >
        </#if>
        <#assign stepScale = 30 >

        <#assign hstep = (stepScale / hscale) + 1>
        <#assign vstep = (stepScale / vscale) + 1>

        <#assign hsizeMargin = 100 >
        <#assign smallMargin = 5 >
        <#assign midMargin = 10 >
        <#assign largeMargin = 20 >
<svg xmlns="http://www.w3.org/2000/svg" version="1.1"
	width="${hsize + hsizeMargin}"
    height="${vsize}">
	<defs>
		<filter id="f1" x="0" y="0" width="200%" height="200%">
        	<feOffset result="offOut" in="SourceAlpha" dx="10" dy="10" />
            <feGaussianBlur result="blurOut" in="offOut" stdDeviation="10" />
            <feBlend in="SourceGraphic" in2="blurOut" mode="normal" />
        </filter>
        <radialGradient id="grad1" cx="0%" cy="100%" r="150%" fx="0%" fy="100%">
            <stop offset="0%" style="stop-color:white;stop-opacity:0.1" />
            <stop offset="100%" style="stop-color:gold;stop-opacity:0.7" />
        </radialGradient>
        <linearGradient id="grad2" cx="0%" cy="100%" r="150%" fx="0%" fy="100%">
        	<stop offset="0%" style="stop-color:red;stop-opacity:0.7" />
            <stop offset="50%" style="stop-color:yellow;stop-opacity:0.7" />
            <stop offset="100%" style="stop-color:green;stop-opacity:0.7" />
        </linearGradient>
    </defs>
    <rect width="90%" height="90%" stroke="black" stroke-width="1" fill="url(#grad1)" filter="url(#f1)" />
		<line x1="${hstart}" y1="${vstart}" x2="${hstart}" y2="${vend}" style="stroke:black;stroke-width:1" />
        <line x1="${hstart}" y1="${vend}" x2="${hend}" y2="${vend}" style="stroke:black;stroke-width:1" />
        <polygon points="${hstart - smallMargin},${vstart + largeMargin} ${hstart},${vstart} ${hstart + smallMargin},${vstart + largeMargin}"
            style="fill:black;stroke:black;stroke-width:1" />
        <polygon points="${hend},${vend} ${hend - largeMargin},${vend + smallMargin} ${hend - largeMargin},${vend - smallMargin}"
            style="fill:black;stroke:black;stroke-width:1" />
        <polygon points="${hstart},${vend}
        	<#list 0..(lastKey+1) as i>
        		<#assign value=0>
        		<#if usageCounts?api.containsKey[i]>
        			<#assign value = usageCounts?api.get[i]>
        		</#if>
        		 ${hstart + (i * hscale)},${vend - (value * vscale)}
        	</#list>
        " style="stroke:black;stroke-width:1"  fill="url(#grad2)" />

		<#list 0..(lastKey/hstep + 1) as i>
			<line x1="${(hstart + (hstep * i * hscale))}"
				y1="${vend}"
				x2="${(hstart + (hstep * i * hscale))}" y2="${(vend + smallMargin)}"
					style="stroke:black;stroke-width:1" />
            <text x="${(hstart + (hstep * i * hscale))}" y="${(vend + midMargin)}" font-size="8">${(i * hstep)?string("0")}</text>
		</#list>

		<#list 0..(stepsUseMax/vstep + 1) as i>
			<line x1="${hstart}" y1="${(vend - (vstep * i * vscale))}"
				x2="${(hstart - smallMargin)}" y2="${(vend - (vstep * i * vscale))}"
				style="stroke:black;stroke-width:1" />
            <text x="${(hstart - smallMargin)}" y="${(vend - (vstep * i * vscale))}"
            	transform="rotate(-90 ${(hstart - smallMargin)},${(vend - (vstep * i * vscale))})" font-size = "8">${(i * vstep)?string("0")}</text>
		</#list>

		<#assign totalSteps = 0>
		<#list usageCounts?keys as key>
			<#assign totalSteps += key * usageCounts?api.get(key)>
		</#list>
        <#assign usage = 100 * (1 - sum(usageCounts?values)/ totalSteps)>

        <#assign statusColor = "silver">

        <#if (usage lt 30)>
            <#assign statusColor = "red" >
        <#elseif (usage gt 70)>
            <#assign statusColor = "green">
        <#else>
            <#assign statusColor = "#BBBB00">
        </#if>

        <line stroke-dasharray="10,10" x1="${(hstart + stepsUseMedian * hscale)}" y1="${(vstart)}"
        	 x2="${(hstart + stepsUseMedian * hscale)}" y2="${vend}" style="stroke:yellow;stroke-width:3" />
        <line stroke-dasharray="10,10" x1="${(hstart + (stepsUseAverage * hscale))}" y1="${(vstart)}"
        	 x2="${(hstart + (stepsUseAverage * hscale))}" y2="${vend}" style="stroke:red;stroke-width:3" />
        <rect x="60%" y="20%" width="28%" height="20%" stroke="black"
             stroke-width="1" fill="white" filter="url(#f1)" />
        <line x1="62%" y1="29%" x2="67%" y2="29%" stroke-dasharray="5,5"
             style="stroke:red;stroke-width:3" />
         <text x="69%" y="30%"
             font-weight = "bold" font-size = "12">Average: ${stepsUseAverage?string("0.#")}</text>"
        <line x1="62%" y1="34%" x2="67%" y2="34%" stroke-dasharray="5,5"
        	style="stroke:yellow;stroke-width:3" /><text x="69%" y="35%"
        	 font-weight = "bold" font-size = "12">Median: ${stepsUseMedian}</text>
        <text x="60%" y="55%" font-weight = "bold" font-size = "40" fill="${statusColor}">${usage?string("0.#")}%</text>
        <text x="66%" y="60%" font-weight = "bold" font-size = "16" fill="${statusColor}">Re-use</text>
        <text x="120" y="330" font-weight = "bold" font-size = "14" >Step re-use count</text>
        <text x="20" y="220" font-weight = "bold" font-size = "14"
         transform="rotate(-90 20,220)">Steps count</text>

</svg>
        </p>
        <h2>Overview Table</h2>
        <p>
    protected String generateUsageOverviewTableReport(CucumberStepSource[] sources) throws Exception {
        <table>
        	<tr>
        		<th rowspan="2"><a id="top">#</a></th>
        		<th rowspan="2">Expression</th>
        		<th rowspan="2">Occurences</th>
        		<th colspan="5">Duration</th>
    		</tr>
    		<tr>
        		<th>Average</th>
        		<th>Median</th>
        		<th>Minimal</th>
        		<th>Maximal</th>
        		<th>Total</th>
    		</tr>
<#--        int index = 0;
        for (String key:map.keySet()) {
            String color = getGroupColor(map, groupsCount, index);
            content += "<tr style=\"background:" + color + "\"><td>" + (++index) + "</td>"
                    + "<td width=\"60%\"><a href=\"#" + getKeyId(key) + "\">" + key + "</a></td>"
                    + "<td>" + map.get(key) + "</td>";
            CucumberStepSource source = getSourceByString(sources, key);
            if (source == null) {
                content += "<td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>";
            } else {
                List<Double> durations = source.getDurations();
                if (durations.size() <= 0) {
                    return "";
                }
                Collections.sort(durations);
                Double median = durations.get(durations.size() / 2);
                Double total = 0.;
                for (Double duration : durations) {
                    total += duration;
                }
                Double average = total / durations.size();
                Double min = Collections.min(durations);
                Double max = Collections.max(durations);
                content += String.format(
                        Locale.US,
                        "<td>%.2fs</td><td>%.2fs</td><td>%.2fs</td><td>%.2fs</td><td>%.2fs</td>",
                        average, median, min, max, total);
            }
            content += "</tr>";
        }
        content += "</table>";
        return content;
    }-->
    	</table>
        </p>
        <h1>Cucumber Usage Detailed Information</h1>
        <p>
        	generateUsageDetailedReport(sources)
        </p>
    </body>
</html>