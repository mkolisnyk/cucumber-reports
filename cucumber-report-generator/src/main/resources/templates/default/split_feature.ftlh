<#list scenario.feature.tags as tag>${tag.name} </#list>
Feature: ${scenario.feature.name}
  ${scenario.feature.description}
  <#list scenario.tags as tag>${tag.name} </#list>
  Scenario: ${scenario.name}
  	${scenario.description}
  	<#list scenario.steps as step>
    ${step.keyword} ${step.name}
    	<#if step.docString?has_content>
      """
      ${step.docString}
      """
    	</#if>
    	<#if step.rows?has_content>
    		<#list step.rows as row>
      <#list row as item> | ${item}</#list> |
    		</#list>
    	</#if>
  	</#list>
    