<#function sum values>
	<#assign result = 0>
	<#list values as value>
		<#assign result = result + value>
	</#list>
	<#return result>
</#function>
<!--
    public String generatePieChart(int width, int height,
            int[] values, String[] labels,
            String[] colors, String[] shadowColors,
            int pieThickness,
            int shift) throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/printable_chart.html");
        String result = IOUtils.toString(is);
        is.close();
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        String drawing = "";
        drawing = drawing.concat(this.drawPieLayer(
            width, height, values, shadowColors, pieThickness, shift));
        drawing = drawing.concat(this.drawPieBorders(
            width, height, values, shadowColors, pieThickness, shift));
        drawing = drawing.concat(
            this.drawPieLayer(width, height, values, colors, 0, shift));
        for (int i = 0; i < values.length; i++) {
            final double xShiftScale = 0.6;
            final double yShiftScale = 0.15;
            final int xOffset = 10;
            final int yHeight = 30;
            final double maxRate = 100.;
            drawing = drawing.concat(String.format(
                    Locale.US,
                    "<text x=\"%d\" y=\"%d\" font-weight = \"bold\" "
                    + "font-size = \"14\">%.0f%% (%d) %s</text>",
                    (int) (xShiftScale * width) + xOffset, (int) (yShiftScale * width) + yHeight * i,
                    maxRate * (double) values[i] / (double) sum, values[i], labels[i]));
            drawing = drawing.concat(String.format(
                    Locale.US,
                    "<circle cx=\"%d\" cy=\"%d\" r=\"5\" stroke=\"%s\" stroke-width=\"1\" fill=\"%s\" />",
                    (int) (xShiftScale * width), (int) (yShiftScale * width) + yHeight * i - xOffset / 2,
                    shadowColors[i], colors[i]
                    ));
        }
        result = result.replaceAll("__DRAWING__", drawing);
        result = result.replaceAll("__HEIGHT__", String.valueOf(height));
        result = result.replaceAll("__WIDTH__", String.valueOf(width));
        return result;
    }
-->
<#macro id width height values labels colors shadowColors pieThickness shift>
<div id="${id}" style="width: 400px; height: 300px;">
    <div style="position: relative;">
        <div dir="ltr"
            style="position: relative; width: 400px; height: 300px;">
            <div aria-label="A chart."
                style="position: absolute; left: 0px; top: 0px; width: 100%; height: 100%;">
                <svg aria-label="A chart." style="overflow: hidden;" height="__HEIGHT__"
                    width="__WIDTH__">
                    <defs id="defs"></defs>
                         __DRAWING__
                    </svg>
            </div>
        </div>
    </div>
</div>
</#macro>

<!-- 
    private void initBaseDimensions(int width, int height, double accumulated, double sum, double value, double shift) {
        if (value == sum) {
            shift = 0;
        }
        startAngle = 2 * Math.PI * (double) accumulated / (double) sum;
        endAngle = 2 * Math.PI * (double) (accumulated + value) / (double) sum;
        shiftAngle = (startAngle + endAngle) / 2.;

        centerX = (double) width / centerScale + (double) shift * Math.sin(shiftAngle);
        centerY = (double) height / centerScale - (double) shift * Math.cos(shiftAngle);
    }

    private String drawPieBorders(int width, int height,
            int[] values,
            String[] colors,
            int pieVOffset,
            int shift) {
        int sum = Arrays.stream(values).sum();
        String drawing = "";
        int accumulated = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == 0) {
                continue;
            }
            initBaseDimensions(width, height, accumulated, sum, values[i], shift);
            double startX = centerX + (scale * (double) width) * Math.sin(startAngle)
                    + (double) shift * Math.sin(shiftAngle);
            double startY = centerY - (scale * (double) height) * Math.cos(startAngle)
                    - (double) shift * Math.cos(shiftAngle);

            drawing = drawing.concat(String.format(
                    Locale.US,
                    "<path fill=\"%s\" stroke-width=\"1\" stroke=\"%s\""
                    + " d=\"M%.8f,%.8f L%.8f,%.8f "
                    + "L%.8f,%.8f "
                    + "L%.8f,%.8f "
                    + "L%.8f,%.8f "
                    + "" + "\"></path>",
                    colors[i], colors[i],
                    centerX, centerY + pieVOffset,
                    startX, startY + pieVOffset,
                    startX, startY,
                    centerX, centerY,
                    centerX, centerY + pieVOffset
                    ));
            accumulated += values[i];
        }
        accumulated = 0;

        for (int i = 0; i < values.length; i++) {
            startAngle = 2 * Math.PI * (double) accumulated / (double) sum;
            endAngle = 2 * Math.PI * (double) (accumulated + values[i]) / (double) sum;
            shiftAngle = (startAngle + endAngle) / 2.;

            centerX = (double) width / centerScale + (double) shift * Math.sin(shiftAngle);
            centerY = (double) height / centerScale - (double) shift * Math.cos(shiftAngle);
            double endX = 0.f;
            double endY = 0.f;

            if (i < values.length - 1) {
                endX = centerX + (scale * (double) width) * Math.sin(endAngle)
                        + (double) shift * Math.sin(shiftAngle);
                endY = centerY - (scale * (double) height) * Math.cos(endAngle)
                        - (double) shift * Math.cos(shiftAngle);
            } else {
                endX = centerX + (double) shift * Math.sin(shiftAngle);
                endY = centerY - scale * (double) height - (double) shift * Math.cos(shiftAngle);
            }
            drawing = drawing.concat(String.format(Locale.US,
                    "<path fill=\"%s\" stroke-width=\"1\" stroke=\"%s\""
                    + " d=\"M%.8f,%.8f L%.8f,%.8f "
                    + "L%.8f,%.8f "
                    + "L%.8f,%.8f "
                    + "L%.8f,%.8f "
                    + "" + "\"></path>",
                    colors[i], colors[i],
                    centerX, centerY + pieVOffset,
                    endX, endY + pieVOffset,
                    endX, endY,
                    centerX, centerY,
                    centerX, centerY + pieVOffset
                    ));
            accumulated += values[i];
        }
        return drawing;
    }
    private String drawPieLayer(int width, int height,
            int[] values,
            String[] colors,
            int pieVOffset,
            int shift) {
        int sum = Arrays.stream(values).sum();
        String drawing = "";
        int accumulated = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == 0) {
                continue;
            }
            initBaseDimensions(width, height, accumulated, sum, values[i], shift);
            double radiusX = scale *  (double) width;
            double radiusY = scale *  (double) height;

            final int pieParts = 5;

            for (int j = 0; j < pieParts; j++) {
                double angleDiff = endAngle - startAngle;
                double startX = centerX + (scale * (double) width)
                        * Math.sin(startAngle + (double) j * angleDiff / pieParts)
                        + (double) shift * Math.sin(shiftAngle);
                double startY = centerY - (scale * (double) height)
                        * Math.cos(startAngle + (double) j * angleDiff / pieParts)
                        - (double) shift * Math.cos(shiftAngle);
                double endX = centerX + (scale * (double) width)
                        * Math.sin(startAngle + (double) (j + 1) * angleDiff / pieParts)
                        + (double) shift * Math.sin(shiftAngle);
                double endY = centerY - (scale * (double) height)
                        * Math.cos(startAngle + (double) (j + 1) * angleDiff / pieParts)
                        - (double) shift * Math.cos(shiftAngle);

                drawing = drawing.concat(String.format(Locale.US,
                        "<path fill=\"%s\" stroke-width=\"1\" stroke=\"%s\""
                        + " d=\"M%.8f,%.8f L%.8f,%.8f A%.8f,%.8f,0,%d,1,%.8f,%.8f M%.8f,%.8f L%.8f,%.8f" + "\"></path>",
                        colors[i], colors[i],
                        centerX, centerY + pieVOffset,
                        startX, startY + pieVOffset,
                        radiusX, radiusY,
                        0,
                        endX, endY + pieVOffset,
                        endX, endY + pieVOffset,
                        centerX, centerY + pieVOffset
                        ));
            }
            accumulated += values[i];
        }
        return drawing;
    }
-->

<#macro drawPieBorders width height values colors pieVOffset shift>
    <#assign total=sum(values)>
    <#assign accumulated = 0 >
    <#list values as value>
    	<#if !values?first>
    	    <#if value == sum>
            	<#assign l_shift = 0>
        	</#if>
	        <#assign startAngle = 2 * Math.PI * (double) accumulated / (double) sum >
	        <#assign endAngle = 2 * Math.PI * (double) (accumulated + value) / (double) sum >
	        <#assign shiftAngle = (startAngle + endAngle) / 2.;
	
	        <#assign centerX = (double) width / centerScale + (double) l_shift * Math.sin(shiftAngle)>
	        <#assign centerY = (double) height / centerScale - (double) l_shift * Math.cos(shiftAngle)>
    	</#if>
    </#list>
        for (int i = 0; i < values.length; i++) {
            if (values[i] == 0) {
                continue;
            }
            initBaseDimensions(width, height, accumulated, sum, values[i], shift);
            double startX = centerX + (scale * (double) width) * Math.sin(startAngle)
                    + (double) shift * Math.sin(shiftAngle);
            double startY = centerY - (scale * (double) height) * Math.cos(startAngle)
                    - (double) shift * Math.cos(shiftAngle);

            drawing = drawing.concat(String.format(
                    Locale.US,
                    "<path fill=\"%s\" stroke-width=\"1\" stroke=\"%s\""
                    + " d=\"M%.8f,%.8f L%.8f,%.8f "
                    + "L%.8f,%.8f "
                    + "L%.8f,%.8f "
                    + "L%.8f,%.8f "
                    + "" + "\"></path>",
                    colors[i], colors[i],
                    centerX, centerY + pieVOffset,
                    startX, startY + pieVOffset,
                    startX, startY,
                    centerX, centerY,
                    centerX, centerY + pieVOffset
                    ));
            accumulated += values[i];
        }
</#macro>

<#macro drawPieLayers width height values colors pieVOffset shift>
</#macro>