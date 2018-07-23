<#escape x as x?html?replace("\n","</br><br>")>
<#assign Math=statics['java.lang.Math'] >
<#function sum values>
	<#assign result = 0>
	<#list values as value>
		<#assign result = result + value>
	</#list>
	<#return result>
</#function>
<#macro emptyHystogram>
<svg  xmlns="http://www.w3.org/2000/svg" version="1.1" width="400" height="300">
	<defs>
		<filter id="f1" x="0" y="0" width="200%" height="200%">
		<feOffset result="offOut" in="SourceAlpha" dx="10" dy="10" />
		<feGaussianBlur result="blurOut" in="offOut" stdDeviation="10" />
		<feBlend in="SourceGraphic" in2="blurOut" mode="normal" /></filter>
		<radialGradient id="grad4" cx="0%" cy="100%" r="150%" fx="0%"
			fy="100%">
		<stop offset="0%" style="stop-color:white;stop-opacity:0.1" />
		<stop offset="100%" style="stop-color:silver;stop-opacity:0.7" /></radialGradient>
	</defs>
<rect width="90%" height="90%" stroke="black" stroke-width="1" fill="url(#grad4)" filter="url(#f1)"/>
			<line x1="30" y1="30" x2="30" y2="240"
			style="stroke:black;stroke-width:1" />
		<line x1="30" y1="240" x2="350" y2="240"
			style="stroke:black;stroke-width:1" />
		<polygon points="25,50 30,30 35,50"
			style="fill:black;stroke:black;stroke-width:1" />
		<polygon points="350,240 330,245 330,235"
			style="fill:black;stroke:black;stroke-width:1" />
		<!-- Scale Marks-->
		<line x1="40" y1="240" x2="40" y2="245"
			style="stroke:black;stroke-width:1" />
		<text x="42" y="253" font-size="8">0.0s</text>
		<line x1="320" y1="240" x2="320" y2="245"
			style="stroke:black;stroke-width:1" />
		<text x="322" y="253" font-size="8">0.0s</text>

		<text x="60" y="220" font-weight="bold" font-size="14"
			transform="rotate(-90 20,220)">Frequency</text>
			<text x="140" y="260" font-weight="bold" font-size="14">Duration</text>
		<!-- Polygon -->
			<text x="22%" y="55%" font-weight="bold" font-size="120" fill="gray">N/A</text>
</svg>
</#macro>
<#macro filledHystogram source>
<svg  xmlns="http://www.w3.org/2000/svg" version="1.1" width="400" height="300">
	<defs>
		<filter id="f1" x="0" y="0" width="200%" height="200%">
		<feOffset result="offOut" in="SourceAlpha" dx="10" dy="10" />
		<feGaussianBlur result="blurOut" in="offOut" stdDeviation="10" />
		<feBlend in="SourceGraphic" in2="blurOut" mode="normal" /></filter>
		<radialGradient id="grad1" cx="0%" cy="100%" r="150%" fx="0%"
			fy="100%">
		<stop offset="0%" style="stop-color:white;stop-opacity:0.1" />
		<stop offset="100%" style="stop-color:gold;stop-opacity:0.7" /></radialGradient>
		<linearGradient id="grad3" cx="0%" cy="100%" r="150%" fx="0%"
			fy="100%">
		<stop offset="0%" style="stop-color:green;stop-opacity:0.7" />
		<stop offset="50%" style="stop-color:yellow;stop-opacity:0.7" />
		<stop offset="100%" style="stop-color:red;stop-opacity:0.7" />
		</linearGradient>
	</defs>
<rect width="90%" height="90%" stroke="black" stroke-width="1" fill="url(#grad1)" filter="url(#f1)"/>
			<line x1="30" y1="30" x2="30" y2="240"
			style="stroke:black;stroke-width:1" />
		<line x1="30" y1="240" x2="350" y2="240"
			style="stroke:black;stroke-width:1" />
		<polygon points="25,50 30,30 35,50"
			style="fill:black;stroke:black;stroke-width:1" />
		<polygon points="350,240 330,245 330,235"
			style="fill:black;stroke:black;stroke-width:1" />
		<!-- Scale Marks-->
		<!--<line x1="40" y1="240" x2="40" y2="245"
			style="stroke:black;stroke-width:1" />
		<text x="42" y="253" font-size="8">0.6s</text>
		<line x1="320" y1="240" x2="320" y2="245"
			style="stroke:black;stroke-width:1" />
		<text x="322" y="253" font-size="8">11.6s</text>-->

		<text x="60" y="220" font-weight="bold" font-size="14"
			transform="rotate(-90 20,220)">Frequency</text>
			<text x="140" y="260" font-weight="bold" font-size="14">Duration</text>
		<!-- Polygon -->
		
		<#assign startX = 40 >
    	<#assign endX = 320 >
    	<#assign topY = 50 >
    	<#assign bottomY = 240 >
    	<#assign max = 0 >
    	<#assign shiftX = 7 >
    	<#assign shiftY = 8>
    	<#assign stepX = (endX - startX) / source.frequencies?size >
    	<#list source.frequencies as item>
    		<#assign  max = Math.max(max, item) >
    	</#list>
        <#assign stepY = (bottomY - topY) / max >

		<polygon
			points="
	        <#list source.frequencies as item>
	        	${startX + item?index * stepX},${bottomY}
	        	${startX + item?index * stepX},${bottomY - (item * stepY)}
	        	${startX + (item?index + 1) * stepX},${bottomY - (item * stepY)}
	        	${startX + (item?index + 1) * stepX},${bottomY} 
	        </#list>
			"
			style="stroke:black;stroke-width:1" fill="url(#grad3)" />
		<#list source.frequencies as item>
			<#if item gt 0>
				<text x="${startX + item?index * stepX + stepX / 2 - shiftX}"
				 	y="${bottomY - (item * stepY)?floor - 2}" font-weight="bold" font-size="14">${item}</text>
			</#if>
            <#assign step = (source.maxDuration - source.minDuration) / source.frequencies?size >
            <line x1="${startX + item?index * stepX}" y1="${bottomY}"
            	  x2="${startX + item?index * stepX}" y2="${bottomY + 2}" style="stroke:black;stroke-width:1" />
            <text x="${startX + item?index * stepX + 2}" y="${bottomY + shiftY}" font-size="8">
            	${(source.minDuration + item?index * step)?string("0.##")}
        	</text>
		</#list>
        <#--<line x1="${endX}" y1="${bottomY}" x2="${endX}"
              y2="${bottomY + 2}" style="stroke:black;stroke-width:1" />
        <text x="${endX + 2}" y="${bottomY + shiftY}" font-size="8">source.maxDuration?string("0.##")</text> -->
</svg>
</#macro>
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
		<#if totalSteps == 0>
			<#assign usage = 0>
		<#else>
        	<#assign usage = 100 * (1 - sum(usageCounts?values)/ totalSteps)>
        </#if>

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
    		<#list stepsData as step>
    			<#assign groupColor = "silver">
    			<#if stepsData?size gt 3>
    				<#switch (step?index / (stepsData?size / 3))?floor?string("0")>
    					<#case "0">
    						<#assign groupColor = "lightgreen">
    						<#break>
    					<#case "1">
    						<#assign groupColor = "gold">
    						<#break>
    					<#case "2">
    						<#assign groupColor = "tomato">
    						<#break>
    					<#default>
    						<#break>
    				</#switch>
    			<#else>
    				<#assign groupColor = "red">
    			</#if>
    		<tr style="background:${groupColor}">
    			<td>${step?counter}</td>
                <td width="60%"><a href="#${step.source.source?url?replace("\\+", "-", "r")?replace("\\%", "_", "r")}">${step.source.source}</a></td><td>${step.totalUsed}</td>
                <#if !step?has_content>
                	<td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>
               	<#else>
               		<td>${step.averageDuration?string("0.##")}s</td>
               		<td>${step.medianDuration?string("0.##")}s</td>
               		<td>${step.minDuration?string("0.##")}s</td>
               		<td>${step.maxDuration?string("0.##")}s</td>
               		<td>${step.totalDuration?string("0.##")}s</td>
                </#if>
            </tr>
    		</#list>
    	</table>
        </p>
        <h1>Cucumber Usage Detailed Information</h1>

        <#list stepsData as source>
        	<h3><a id="${source.source.source?url?replace("\\+", "-", "r")?replace("\\%", "_", "r")}">${source.source.source}</a></h3>
        <p>
        	<table>
        		<tr>
        			<th>Step Name</th>
        			<th>Duration</th>
        			<th>Location</th>
    			</tr>
    			<#list source.source.steps as step>
    				<tr><td rowspan="${step.durations?size + 1}">${step.name}</td><td colspan="2">&#160;</td></tr>
    				<#list step.durations as duration>
    					<tr>
    						<td>${duration.getDuration()}</td>
    						<td>${duration.getLocation()}</td>
						</tr>
    				</#list>
    			</#list>
            </table>
        </p>
        <p>
        	<table class="none">
        		<tr>
        			<td class="none">
        				<#if source.totalUsed lt 6>
        					<@emptyHystogram />
        				<#else>
					        <@filledHystogram source=source/>
        				</#if>
    				</td>
                    <td valign="top" class="none">
                    	<p><table>
                    	<tr><th colspan="2">Duration Characteristic</th></tr>
		                <tr><th>Average</th><td>${source.averageDuration?string("0.##")}s</td></tr>
		                <tr><th>Median</th><td>${source.medianDuration?string("0.##")}s</td></tr>
		                <tr><th>Variance</th><td>${source.variance?string("0.##")}</td></tr>
		                <tr><th>Skewness</th><td>${source.skewness?string("0.##")}</td></tr>
		                <tr><th>Minimum</th><td>${source.minDuration?string("0.##")}s</td></tr>
		                <tr><th>Maximum</th><td>${source.maxDuration?string("0.##")}s</td></tr>
		                <tr><th>Total</th><td>${source.totalDuration?string("0.##")}s</td></tr>
		                </table>
		                </p>
                	</td>
                </tr>
            </table>
        </p>
        <p>
        	<a href="#top">To Overview Table</a>
    	</p>
        </#list>

    </body>
</html>
</#escape>