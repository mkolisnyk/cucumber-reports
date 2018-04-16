<#escape x as x?html?replace("\n","</br><br>")>
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
.passed_description {background-color:#D0FFD0;font-weight:bold;font-style:italic;color:black}
.failed_description {background-color:#FFA898;font-weight:bold;font-style:italic;color:black}
.comment {font-family:courier;font-weight:normal;font-size:small;color:black;border:1px dashed black;background-color:silver}
.tip {font-family:courier;font-weight:normal;font-size:small;color:white;background-color:black}
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
<title>${title}</title>
<meta charset="UTF-8" />
</head>
  <body>
    <div class='header'></div>
    <div class='footer'></div>
<#include "tables">
<h1>Overview</h1>

<@drawOverviewStatsTable stats=stats />

<a id="top"></a><h1>Table of Contents</h1>

<ol>
<#list results as result>
<li> <span class="${result.getStatus()}"><a href="#feature-${result.getId()}">${result.getName()}</a></span>
	<ol>
	<#list result.getElements() as scenario>
		<#if scenario.getKeyword()?starts_with("Scenario")>
        <li> <span class="${scenario.getStatus()}"><a href="#sc-${scenario.getId()}">${scenario.getName()}</a></span></li>
        </#if>
	</#list>
    </ol></li>
</#list>
</ol>

<h1>Detailed Results Report</h1>

<table width="700px">
<#list results as result>
	<tr class="${result.getStatus()}"><td colspan="4"><b>Feature:</b> <a id="feature-${result.getId()}">${result.getName()}</a></td></tr>
    <#if result.getDescription()?has_content>
		<tr class="${result.getStatus()}_description">
            <td colspan="4"><br>${result.getDescription()}</br></td>
        </tr>
	</#if>
    <tr class="${result.getStatus()}">
    	<td><small><b>Passed:</b> ${result.getPassed()}</small></td>
        <td><small><b>Failed:</b> ${result.getFailed()}</small></td>
        <td><small><b>Undefined:</b> ${result.getUndefined() + result.getSkipped()}</small></td>
        <td><small>Duration: ${result.getDuration()?string[".##"]}s</small></td>
    </tr>
	<tr class="${result.getStatus()}">
		<td colspan="4" style="padding-left:20px">
		<table width="100%">
			<#list result.getElements() as scenario>
				<tr class="${scenario.getStatus()}">
					<td colspan="4">
						<b>${scenario.getKeyword()}:</b> <a id="sc-${scenario.getId()}">${scenario.getName()}</a>
					</td>
				</tr>
                <#if scenario.getDescription()?has_content>
					<tr class="${scenario.getStatus()}_description">
			            <td colspan="4"><br>${scenario.getDescription()}</br></td>
		            </tr>
				</#if>
                <tr class="${scenario.getStatus()}">
                	<td><small><b>Passed:</b> ${scenario.getPassed()}</small></td>
                	<td><small><b>Failed:</b> ${scenario.getFailed()}</small></td>
					<td><small><b>Undefined:</b> ${scenario.getUndefined() + scenario.getSkipped()}</small></td>
					<td><small>Duration: ${scenario.getDuration()?string[".##"]}s</small></td>
				</tr>
                <#if scenario.getBefore()?has_content>
					<tr class="${scenario.getBefore().getResult().getStatus()}">
						<td>Before</td>
						<td colspan="2"></td>
						<td width="100">${scenario.getBefore().getResult().getDurationTimeString("HH:mm:ss:S")}</td>
					</tr>
                	<tr class="${scenario.getBefore().getResult().getStatus()}">
                		<td colspan="4">
                			<#if scenario.getBefore().getResult().getErrorMessage()?has_content>
                				<br>${scenario.getBefore().getResult().getErrorMessage()}</br>
                			</#if>
                		</td>
                	</tr>
        		</#if>
                <tr class="${scenario.getStatus()}">
	                <td colspan="4" style="padding-left:20px">
						<table width="100%">
							<#list scenario.getSteps() as step>
                            	<tr class="${step.getResult().getStatus()}">
                            		<td><b>${step.getKeyword()}</b> ${step.getName()}</td>
                            		<td width="100">${step.getResult().getDurationTimeString("HH:mm:ss:S")}</td>
                        		</tr>
                        		<#if step.getRows()?has_content >
	 								<tr class="${step.getResult().getStatus()}">
	 									<td style="padding-left:20px" colspan="2">
	 										<table>
	 											<#list step.getRows() as row>
	 											<tr>
	 												<#list row as col>
	 													<td>${col}</td>
                									</#list>
												</tr>
            									</#list>
            								</table>
        								</td>
    								</tr>
    							</#if>
    							<#if step.getDocString()?has_content>
    								<tr class="${step.getResult().getStatus()}_description">
    									<td style="padding-left:20px" colspan="2">
    										<br>${step.getDocString()}</br>
    									</td>
    								</tr>
    							</#if>
    							<#if step.result.errorMessage?has_content>
    								<tr class="${step.getResult().getStatus()}">
										<td colspan="2">
											<br>${step.result.errorMessage}</br>
										</td>
									</tr>
    							</#if>
								<#list step.getScreenShotLocations() as screenshot>
									<tr class="${step.getResult().getStatus()}">
										<td colspan="2"><img src="${screenshot}" width="${screenShotWidth}" /></td>
									</tr>
								</#list>
								<#if step.getOutput()?has_content>
									<tr class="${step.getResult().getStatus()}">
										<td colspan="2" class="comment">
											<span class="tip">Output</span>
											<pre class="comment"><br>
											<#list step.getOutput() as line>
${line}
											</#list>
											</br>
											</pre>
										</td>
									</tr>
								</#if>
							</#list>
						</table>
					</td>
				</tr>
				
				<#if scenario.getAfter()?has_content>
					<tr class="${scenario.getAfter().getResult().getStatus()}">
						<td>After</td>
						<td colspan="2"></td>
						<td width="100">${scenario.getAfter().getResult().getDurationTimeString("HH:mm:ss:S")}</td>
					</tr>
                	<tr class="${scenario.getAfter().getResult().getStatus()}">
                		<td colspan="4">
	                	<#if scenario.getAfter().getResult().getErrorMessage()?has_content>
							<br>${scenario.getAfter().getResult().getErrorMessage()}</br>
						</#if>
						</td>
                	</tr>
        		</#if>
                <tr><td colspan="5"><sup><a href="#top">Back to Table of Contents</a></sup></td></tr>
			</#list>
		</table>
	</td></tr><tr><td colspan="5"></td></tr>
</#list>
</table>

	</body>
</html>
</#escape>