package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXB;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
//import com.google.common.io.Files;

public abstract class CucumberResultsCommon {
    public static final int CHART_WIDTH = 450;
    public static final int CHART_HEIGHT = 300;
    public static final int CHART_THICKNESS = 20;

    private String[] sourceFiles;
    private String outputDirectory;
    private String outputName;
    private String pdfPageSize = "auto";

    /**
     * @return the outputDirectory
     */
    public final String getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * @param outputDirectoryValue the outputDirectory to set
     */
    public final void setOutputDirectory(String outputDirectoryValue) {
        this.outputDirectory = outputDirectoryValue;
    }

    /**
     * @return the outputName
     */
    public final String getOutputName() {
        return outputName;
    }

    /**
     * @param outputNameValue the outputName to set
     */
    public final void setOutputName(String outputNameValue) {
        this.outputName = outputNameValue;
    }
    /**
     * @return the sourceFile
     */
    public final String getSourceFile() {
        return sourceFiles[0];
    }

    /**
     * @param sourceFileValue the sourceFile to set
     */
    public final void setSourceFile(String sourceFileValue) {
        this.sourceFiles = new String[] {sourceFileValue};
    }

    public String[] getSourceFiles() {
        return sourceFiles;
    }

    public void setSourceFiles(String[] sourceFiles) {
        this.sourceFiles = sourceFiles;
    }

    public String getPdfPageSize() {
        return pdfPageSize;
    }

    public void setPdfPageSize(String pdfPageSizeValue) {
        this.pdfPageSize = pdfPageSizeValue;
    }

    public CucumberFeatureResult[] aggregateResults(CucumberFeatureResult[] input, boolean collapse) {
        for (int i = 0; i < input.length; i++) {
            input[i].setId("" + input[i].getLine() + "" + input[i].getId());
            input[i].aggregateScenarioResults(collapse);
        }
        return input;
    }

    @SuppressWarnings("unchecked")
    public CucumberFeatureResult[] readFileContent(String sourceFileValue, boolean aggregate) throws Exception {
        FileInputStream fis = null;
        JsonReader jr = null;
        File file = new File(sourceFileValue);

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
    public CucumberFeatureResult[] readFileContent(boolean aggregate) throws Exception {
        return readFileContent(this.getSourceFiles(), aggregate);
    }

    private CucumberFeatureResult[] readFileContent(String[] sourceFilesValue,
            boolean aggregate) throws Exception {
        CucumberFeatureResult[] output = {};
        for (String sourceFile : sourceFilesValue) {
            output = (CucumberFeatureResult[]) ArrayUtils.addAll(output, readFileContent(sourceFile, aggregate));
        }
        return output;
    }

    /*@SuppressWarnings("unchecked")
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
    }*/
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
        final double scale = 0.2;
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
            int[] values, String[] labels,
            String[] colors,
            int pieVOffset,
            int shift) {
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        final double scale = 0.2;
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

                /*int largeArcFlag = 0;
                if (values[i] * 2 > sum) {
                    largeArcFlag = 1;
                }*/
                drawing = drawing.concat(String.format(Locale.US,
                        "<path fill=\"%s\" stroke-width=\"1\" stroke=\"%s\""
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

    public void dumpOverviewStats(File outFile, CucumberFeatureResult[] results) {
        int[][] stats = getStatuses(results);
        JAXB.marshal(stats, outFile);
    }

    private void convertSvgToPng(File svg, File png) throws Exception {
        String svgUriInput = svg.toURI().toURL().toString();
        TranscoderInput inputSvgImage = new TranscoderInput(svgUriInput);
        //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
        OutputStream pngOStream = new FileOutputStream(png);
        TranscoderOutput outputPngImage = new TranscoderOutput(pngOStream);
        // Step-3: Create PNGTranscoder and define hints if required
        PNGTranscoder myConverter = new PNGTranscoder();
        // Step-4: Convert and Write output
        myConverter.transcode(inputSvgImage, outputPngImage);
        // Step 5- close / flush Output Stream
        pngOStream.flush();
        pngOStream.close();
    }
    public String replaceSvgWithPng(File htmlFile) throws Exception {
        String tempPath = this.getOutputDirectory() + File.separator + "temp" + (new Date()).getTime();
        File folder = new File(tempPath);
        //Files.createTempDirectory("temp").toFile();
        folder.mkdirs();
        folder.deleteOnExit();
        String htmlText = FileUtils.readFileToString(htmlFile);
        Pattern p = Pattern.compile("<svg(.*?)</svg>", Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlText);
        int index = 0;
        while (m.find()) {
            String svg = m.group(0);
            File svgOutput = new File(tempPath + File.separator + index + ".svg");
            svgOutput.deleteOnExit();
            FileUtils.writeStringToFile(svgOutput, svg);
            File png = new File(tempPath + File.separator + index + ".png");
            png.deleteOnExit();
            convertSvgToPng(svgOutput, png);
            htmlText = m.replaceFirst(
                Matcher.quoteReplacement(String.format(Locale.US,
                        "<img src=\"%s\"></img>",
                    folder.getName() + "/" + index + ".png")));
            m = p.matcher(htmlText);
            index++;
        }
        return htmlText;
    }
    public void exportToPDF(File htmlFile, String suffix) throws Exception {
        File bakupFile = new File(htmlFile.getAbsolutePath() + ".bak.html");
        String url = bakupFile.toURI().toURL().toString();
        String outputFile = this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + suffix + ".pdf";
        String updatedContent = replaceSvgWithPng(htmlFile);
        updatedContent = updatedContent.replaceAll("\"hoverTable\"", "\"_hoverTable\"");
        updatedContent = updatedContent.replaceAll("__PAGESIZE__", this.getPdfPageSize());
        FileUtils.writeStringToFile(bakupFile, updatedContent);
        OutputStream os = new FileOutputStream(outputFile);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(url);
        renderer.layout();
        renderer.createPDF(os);

        os.close();
    }

    public int[][] getStatuses(CucumberFeatureResult[] results) {
        return null;
    }
}
