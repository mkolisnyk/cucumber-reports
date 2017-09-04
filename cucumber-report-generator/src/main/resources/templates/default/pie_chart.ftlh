<#assign Math=statics['java.lang.Math'] >
<#assign  scale = 0.2 >
<#assign centerScale = 3 >
<#function sum values>
	<#assign result = 0>
	<#list values as value>
		<#assign result = result + value>
	</#list>
	<#return result>
</#function>

<#macro drawPieChart id width height values labels colors shadowColors pieThickness shift>
<div id="${id}" style="width: 400px; height: 300px;">
    <div style="position: relative;">
        <div dir="ltr"
            style="position: relative; width: 400px; height: 300px;">
            <div aria-label="A chart."
                style="position: absolute; left: 0px; top: 0px; width: 100%; height: 100%;">
                <svg aria-label="A chart." style="overflow: hidden;" height="${height}"
                    width="${width}">
                    <defs id="defs"></defs>
            		   <@drawPieLayers width=width height=height values=values colors=shadowColors pieVOffset=pieThickness shift=shift />
                       <@drawPieBorders width=width height=height values = values colors = shadowColors pieVOffset = pieThickness shift=shift />
                       <@drawPieLayers width=width height=height values=values colors=colors pieVOffset=0 shift=shift />
                       <#assign total=sum(values)>
                       <#list values as value>
	                    <#assign xShiftScale = 0.6>
			            <#assign yShiftScale = 0.15>
			            <#assign xOffset = 10>
			            <#assign yHeight = 30>
			            <#if total == 0>
			            	<#assign ratio="N/A">
			            <#else>
			            	<#assign ratio="#{100 * value / total; M0}">
			            </#if>
			            <text x="${(xShiftScale * width) + xOffset}"
			            	 y="${(yShiftScale * width) + yHeight * value?index}"
			            	 font-weight = "bold"
			            	 font-size = "14">${ratio}% (${value}) ${labels[value?index]}</text>
			           <circle cx="${xShiftScale * width}"
			            cy="${(yShiftScale * width) + yHeight * value?index - xOffset / 2}"
			             r="5" stroke="${shadowColors[value?index]}" stroke-width="1" fill="${colors[value?index]}" />
                       </#list>
                    </svg>
            </div>
        </div>
    </div>
</div>
</#macro>
<#macro drawPieBorders width height values colors pieVOffset shift>
    <#assign total=sum(values)>
    <#assign accumulated = 0 >
    <#if total == 0>
    	<#return>
    </#if>
    <#list values as value>
    	<#if value gt 0>
    		<#assign l_shift = shift>
    	    <#if value?counter == total>
            	<#assign l_shift = 0>
        	</#if>
	        <#assign startAngle = 2 * Math.PI * accumulated / total >
	        <#assign endAngle = 2 * Math.PI * (accumulated + value) / total >
	        <#assign shiftAngle = (startAngle + endAngle) / 2 >
	
	        <#assign centerX = width / centerScale + l_shift * Math.sin(shiftAngle)>
	        <#assign centerY = height / centerScale - l_shift * Math.cos(shiftAngle)>
	        <#assign startX = centerX + (scale * width) * Math.sin(startAngle)
                    + shift * Math.sin(shiftAngle) >
            <#assign startY = centerY - (scale * height) * Math.cos(startAngle)
                    - shift * Math.cos(shiftAngle) >
            
            <path fill="${colors[value?index]}" stroke-width="1" stroke="${colors[value?index]}"
				 d="M#{centerX; M8},#{centerY + pieVOffset;M8} L#{startX;M8},#{startY + pieVOffset;M8} L#{startX;M8},#{startY;M8} L#{centerX;M8},#{centerY;M8} L#{centerX;M8},#{centerY + pieVOffset;M8} "></path>
			<#assign accumulated = accumulated + value >
    	</#if>
    </#list>

    <#assign accumulated = 0 >
    <#list values as value>
	        <#assign startAngle = 2 * Math.PI * accumulated / total >
	        <#assign endAngle = 2 * Math.PI * (accumulated + value) / total >
	        <#assign shiftAngle = (startAngle + endAngle) / 2 >

            <#assign centerX = width / centerScale + shift * Math.sin(shiftAngle)>
            <#assign centerY = height / centerScale - shift * Math.cos(shiftAngle)>
            <#assign endX = 0>
            <#assign endY = 0>

            <#if value?index lt (values?size - 1)>
                <#assign endX = centerX + (scale * width) * Math.sin(endAngle) + shift * Math.sin(shiftAngle) >
                <#assign endY = centerY - (scale * height) * Math.cos(endAngle) - shift * Math.cos(shiftAngle) >
            <#else>
                <#assign endX = centerX + shift * Math.sin(shiftAngle) >
               <#assign  endY = centerY - scale * height - shift * Math.cos(shiftAngle) >
            </#if>
            
            <path fill="${colors[value?index]}" stroke-width="1" stroke="${colors[value?index]}"
				 d="M#{centerX; M8},#{centerY + pieVOffset;M8} L#{endX;M8},#{endY + pieVOffset;M8} L#{endX;M8},#{endY;M8} L#{centerX;M8},#{centerY;M8} L#{centerX;M8},#{centerY + pieVOffset;M8} "></path>
			<#assign accumulated = accumulated + value >
    </#list>    
</#macro>

<#macro drawPieLayers width height values colors pieVOffset shift>
	<#assign total=sum(values)>
    <#assign accumulated = 0 >
    <#if total == 0>
    	<#return>
    </#if>
    <#list values as value>
    	<#if value gt 0>
    		<#assign l_shift = shift>
    	    <#if value?counter == total>
            	<#assign l_shift = 0>
        	</#if>
	        <#assign startAngle = 2 * Math.PI * accumulated / total >
	        <#assign endAngle = 2 * Math.PI * (accumulated + value) / total >
	        <#assign shiftAngle = (startAngle + endAngle) / 2 >
	
	        <#assign centerX = width / centerScale + l_shift * Math.sin(shiftAngle)>
	        <#assign centerY = height / centerScale - l_shift * Math.cos(shiftAngle)>
	        
	        <#assign radiusX = scale * width>
            <#assign radiusY = scale * height>

            <#assign pieParts = 5>

			<#list 0..(pieParts - 1) as j>
			    <#assign angleDiff = endAngle - startAngle >
                <#assign startX = centerX + (scale * width)
                        * Math.sin(startAngle + j * angleDiff / pieParts)
                        + l_shift * Math.sin(shiftAngle) >
                <#assign startY = centerY - (scale * height)
                        * Math.cos(startAngle + j * angleDiff / pieParts)
                        - l_shift * Math.cos(shiftAngle) >
                <#assign endX = centerX + (scale * width)
                        * Math.sin(startAngle + (j + 1) * angleDiff / pieParts)
                        + l_shift * Math.sin(shiftAngle) >
                <#assign endY = centerY - (scale * height)
                        * Math.cos(startAngle + (j + 1) * angleDiff / pieParts)
                        - l_shift * Math.cos(shiftAngle) >
                        
                <path fill="${colors[value?index]}" stroke-width="1" stroke="${colors[value?index]}"
					d="M#{centerX;M8},#{centerY + pieVOffset;M8} L#{startX;M8},#{startY + pieVOffset;M8} A#{radiusX;M8},#{radiusY;M8},0,0,1,#{endX;M8},#{endY + pieVOffset;M8} M#{endX;M8},#{endY + pieVOffset;M8} L#{centerX;M8},#{centerY + pieVOffset;M8}"></path>
 			</#list>
	        <#assign accumulated = accumulated + value >
    	</#if>
    </#list>
</#macro>