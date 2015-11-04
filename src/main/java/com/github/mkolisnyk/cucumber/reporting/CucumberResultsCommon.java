package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;

public class CucumberResultsCommon {
    public static final int CHART_WIDTH = 450;
    public static final int CHART_HEIGHT = 300;
    public static final int CHART_THICKNESS = 20;

    private String sourceFile;

    /**
     * @return the sourceFile
     */
    public final String getSourceFile() {
        return sourceFile;
    }

    /**
     * @param sourceFileValue the sourceFile to set
     */
    public final void setSourceFile(String sourceFileValue) {
        this.sourceFile = sourceFileValue;
    }

    public CucumberFeatureResult[] aggregateResults(CucumberFeatureResult[] input, boolean collapse) {
        for (int i = 0; i < input.length; i++) {
            input[i].aggregateScenarioResults(collapse);
        }
        return input;
    }

    @SuppressWarnings("unchecked")
    public CucumberFeatureResult[] readFileContent(boolean aggregate) throws Exception {
        FileInputStream fis = null;
        JsonReader jr = null;
        File file = new File(this.getSourceFile());

        if (!(file.exists() && file.isFile())) {
            throw new FileNotFoundException();
        }

        fis = new FileInputStream(file);
        jr = new JsonReader(fis, true);
        JsonObject<String, Object> source = (JsonObject<String, Object>) jr.readObject();
        Object[] objs = (Object[]) source.get("@items");

        CucumberFeatureResult[] sources = new CucumberFeatureResult[objs.length];
        for (int i = 0; i < objs.length; i++) {
            sources[i] = new CucumberFeatureResult((JsonObject<String, Object>) objs[i]);
        }
        jr.close();
        fis.close();
           sources = aggregateResults(sources, aggregate);
        return sources;
    }

    @SuppressWarnings("unchecked")
    public <T extends CucumberFeatureResult> T[] readFileContent(Class<T> param) throws Exception {
        FileInputStream fis = null;
        JsonReader jr = null;
        File file = new File(this.getSourceFile());

        if (!(file.exists() && file.isFile())) {
            throw new FileNotFoundException();
        }

        fis = new FileInputStream(file);
        jr = new JsonReader(fis, true);
        JsonObject<String, Object> source = (JsonObject<String, Object>) jr.readObject();
        Object[] objs = (Object[]) source.get("@items");

        T[] sources = (T[]) Array.newInstance(param, objs.length);
        for (int i = 0; i < objs.length; i++) {
            sources[i] = (T) param.getConstructors()[0].newInstance((JsonObject<String, Object>) objs[i]);
        }
        jr.close();
        fis.close();
        return sources;
    }
    public String replaceHtmlEntitiesWithCodes(String input) throws IOException {
        String output = input;
        Map<String, String> entitiesMap = new HashMap<String, String>();
        InputStream is = this.getClass().getResourceAsStream("/html_entities_map.txt");
        String[] result = IOUtils.toString(is).split("\n");
        is.close();
        for (String line : result) {
            entitiesMap.put(line.split("(\\s+)")[0], line.split("(\\s+)")[1]);
        }
        for (Entry<String, String> entry : entitiesMap.entrySet()) {
            output = output.replace(entry.getKey(), entry.getValue());
        }
        return output;
    }
    private String drawPieBorders(int width, int height,
            int[] values, String[] labels,
            String[] colors,
            int pieVOffset,
            int shift) {
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        final double scale = 0.25;
        final double centerScale = 3.;
        String drawing = "";
        int accumulated = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == 0) {
                continue;
            }
            if (values[i] == sum) {
                shift = 0;
            }
            double startAngle = 2 * Math.PI * (double) accumulated / (double) sum;
            double endAngle = 2 * Math.PI * (double) (accumulated + values[i]) / (double) sum;
            double shiftAngle = (startAngle + endAngle) / 2.;

            double centerX = (double) width / centerScale + (double) shift * Math.sin(shiftAngle);
            double centerY = (double) height / centerScale - (double) shift * Math.cos(shiftAngle);
            double startX = centerX + (scale * (double) width) * Math.sin(startAngle)
                    + (double) shift * Math.sin(shiftAngle);
            double startY = centerY - (scale * (double) height) * Math.cos(startAngle)
                    - (double) shift * Math.cos(shiftAngle);

            drawing = drawing.concat(String.format("<path fill=\"%s\" stroke-width=\"1\" stroke=\"%s\""
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
            double startAngle = 2 * Math.PI * (double) accumulated / (double) sum;
            double endAngle = 2 * Math.PI * (double) (accumulated + values[i]) / (double) sum;
            double shiftAngle = (startAngle + endAngle) / 2.;

            double centerX = (double) width / centerScale + (double) shift * Math.sin(shiftAngle);
            double centerY = (double) height / centerScale - (double) shift * Math.cos(shiftAngle);
            double endX = 0.f;
            double endY = 0.f;

            if (i < values.length - 1) {
            endX = centerX + (scale * (double) width) * Math.sin(endAngle) + (double) shift * Math.sin(shiftAngle);
            endY = centerY - (scale * (double) height) * Math.cos(endAngle) - (double) shift * Math.cos(shiftAngle);
            } else {
                endX = centerX + (double) shift * Math.sin(shiftAngle);
                endY = centerY - (scale * (double) height) - (double) shift * Math.cos(shiftAngle);
            }
            drawing = drawing.concat(String.format("<path fill=\"%s\" stroke-width=\"1\" stroke=\"%s\""
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
            int[] values, String[] labels,
            String[] colors,
            int pieVOffset,
            int shift) {
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        final double scale = 0.25;
        final double centerScale = 3.;
        String drawing = "";
        int accumulated = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == 0) {
                continue;
            }
            if (values[i] == sum) {
                shift = 0;
            }
            double startAngle = 2 * Math.PI * (double) accumulated / (double) sum;
            double endAngle = 2 * Math.PI * (double) (accumulated + values[i]) / (double) sum;
            double shiftAngle = (startAngle + endAngle) / 2.;

            double centerX = (double) width / centerScale + (double) shift * Math.sin(shiftAngle);
            double centerY = (double) height / centerScale - (double) shift * Math.cos(shiftAngle);
            double radiusX = (scale *  (double) width);
            double radiusY = (scale *  (double) height);

            for (int j = 0; j < 5; j++) {
                double angleDiff = endAngle - startAngle;
                double startX = centerX + (scale * (double) width) * Math.sin(startAngle + (double) j * angleDiff / 5)
                        + (double) shift * Math.sin(shiftAngle);
                double startY = centerY - (scale * (double) height) * Math.cos(startAngle + (double) j * angleDiff / 5)
                        - (double) shift * Math.cos(shiftAngle);
                double endX = centerX + (scale * (double) width) * Math.sin(startAngle + (double) (j + 1) * angleDiff / 5)
                        + (double) shift * Math.sin(shiftAngle);
                double endY = centerY - (scale * (double) height) * Math.cos(startAngle + (double) (j + 1) * angleDiff / 5)
                        - (double) shift * Math.cos(shiftAngle);
    
                /*int largeArcFlag = 0;
                if (values[i] * 2 > sum) {
                    largeArcFlag = 1;
                }*/
                drawing = drawing.concat(String.format("<path fill=\"%s\" stroke-width=\"1\" stroke=\"%s\""
                        + " d=\"M%.8f,%.8f L%.8f,%.8f A%.8f,%.8f,0,%d,1,%.8f,%.8f M%.8f,%.8f L%.8f,%.8f" + "\"></path>",
                        colors[i], colors[i],
                        centerX, centerY + pieVOffset,
                        startX, startY + pieVOffset,
                        radiusX, radiusY,
                        //largeArcFlag,
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
        drawing = drawing.concat(this.drawPieLayer(width, height, values, labels, shadowColors, pieThickness, shift));
        drawing = drawing.concat(this.drawPieBorders(width, height, values, labels, shadowColors, pieThickness, shift));
        drawing = drawing.concat(this.drawPieLayer(width, height, values, labels, colors, 0, shift));
        for (int i = 0; i < values.length; i++) {
            drawing = drawing.concat(String.format("<text x=\"%d\" y=\"%d\" font-weight = \"bold\" "
                    + "font-size = \"14\">%.0f%% (%d) %s</text>",
                    (int) (0.65 * width) + 10, (int) (0.15 * width) + 30 * i,
                    100. * (double) values[i] / (double) sum, values[i], labels[i]));
            drawing = drawing.concat(String.format(
                    "<circle cx=\"%d\" cy=\"%d\" r=\"5\" stroke=\"%s\" stroke-width=\"1\" fill=\"%s\" />",
                    (int) (0.65 * width), (int) (0.15 * width) + 30 * i - 5,
                    shadowColors[i], colors[i]
                    ));
        }
        result = result.replaceAll("__DRAWING__", drawing);
        result = result.replaceAll("__HEIGHT__", String.valueOf(height));
        result = result.replaceAll("__WIDTH__", String.valueOf(width));
        return result;
    }
}
