<#assign Math=statics['java.lang.Math'] >
<#macro drawOverviewStatsTable stats>

<table>
	<tr><th></th><th>Passed</th><th>Failed</th><th>Known</th><th>Undefined</th><th>Total</th><th>%Passed</th></tr>
	<tr><th>Features</th>
		<td class="passed" id="features_passed">${stats.getFeaturesPassed()}</td>
		<td class="failed" id="features_failed">${stats.getFeaturesFailed()}</td>
		<td class="known" id="features_known">${stats.getFeaturesKnown()}</td>
		<td class="undefined" id="features_undefined">${stats.getFeaturesUndefined()}</td>
		<td id="features_total">${stats.getFeaturesTotal()}</td>
		<td id="features_rate">
			<#if stats.getFeaturesTotal() == 0>
			NaN
			<#else>
				#{100 * (stats.getFeaturesPassed() + stats.getFeaturesKnown()) / stats.getFeaturesTotal() ;M0}%
			</#if>
		</td>
	</tr>
	<tr><th>Scenarios</th>
		<td class="passed" id="scenarios_passed">${stats.getScenariosPassed()}</td>
		<td class="failed" id="scenarios_failed">${stats.getScenariosFailed()}</td>
		<td class="known" id="scenarios_known">${stats.getScenariosKnown()}</td>
		<td class="undefined" id="scenarios_undefined">${stats.getScenariosUndefined()}</td>
		<td id="scenarios_total">${stats.getScenariosTotal()}</td>
		<td id="scenarios_rate">
			<#if stats.getScenariosTotal() == 0>
			NaN
			<#else>
				#{100 * (stats.getScenariosPassed() + stats.getScenariosKnown()) / stats.getScenariosTotal() ;M0}%
			</#if>
		</td>
	</tr>
	<tr><th>Steps</th>
		<td class="passed" id="steps_passed">${stats.getStepsPassed()}</td>
		<td class="failed" id="steps_failed">${stats.getStepsFailed()}</td>
		<td class="known" id="steps_known">${stats.getStepsKnown()}</td>
		<td class="undefined" id="steps_undefined">${stats.getStepsUndefined()}</td>
		<td id="steps_total">${stats.getStepsTotal()}</td>
		<td id="steps_rate">
			<#if stats.getStepsTotal() == 0>
			NaN
			<#else>
				#{100 * (stats.getStepsPassed() + stats.getStepsKnown()) / stats.getStepsTotal() ;M0}%
			</#if>
		</td>
	</tr>
</table>
<div><b>Overall Duration: ${(stats.overallDuration/3600)?string["0;; roundingMode=floor"]}h ${((stats.overallDuration % 3600) / 60)?string["00"]}m ${((stats.overallDuration % 3600) % 60)?string["00"]}s</b></div>
</#macro>