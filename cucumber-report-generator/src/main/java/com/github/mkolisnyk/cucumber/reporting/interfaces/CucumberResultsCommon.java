package com.github.mkolisnyk.cucumber.reporting.interfaces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXB;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang.ArrayUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.simple.Graphics2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;
import com.github.mkolisnyk.cucumber.reporting.types.beans.CommonDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.utils.helpers.FreemarkerConfiguration;
import com.github.mkolisnyk.cucumber.reporting.utils.helpers.StringConversionUtils;
//import com.google.common.io.Files;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;

public abstract class CucumberResultsCommon {
    public static final int CHART_WIDTH = 450;
    public static final int CHART_HEIGHT = 300;
    public static final int CHART_THICKNESS = 20;

    private String[] sourceFiles;
    private String outputDirectory;
    private String outputName;
    private String pdfPageSize = "auto";

    public abstract CucumberReportTypes getReportType();
    public abstract CucumberReportLink getReportDocLink();
    public abstract void validateParameters();

    public CucumberResultsCommon() {
    }
    public CucumberResultsCommon(ExtendedRuntimeOptions extendedOptions) {
        this.setOutputDirectory(extendedOptions.getOutputFolder());
        this.setOutputName(extendedOptions.getReportPrefix());
        this.setSourceFiles(extendedOptions.getJsonReportPaths());
        this.setPdfPageSize(extendedOptions.getPdfPageSize());
    }

    public String constructErrorMessage(CucumberReportError error, String suffix) {
        return String.format("%s: %s. For more information, please, refer to: %s%s",
            this.getReportType(), error, this.getReportDocLink(), suffix);
    }
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
        this.outputDirectory = StringConversionUtils.transformPathString(outputDirectoryValue);
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
        this.outputName = StringConversionUtils.transformPathString(outputNameValue);
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
        if (sourceFileValue == null) {
            this.sourceFiles = null;
        } else {
            this.sourceFiles = new String[] {sourceFileValue};
        }
    }

    public String[] getSourceFiles() {
        return sourceFiles;
    }

    public void setSourceFiles(String[] sourceFilesValue) {
        this.sourceFiles = sourceFilesValue;
    }

    public String getPdfPageSize() {
        return pdfPageSize;
    }

    public void setPdfPageSize(String pdfPageSizeValue) {
        this.pdfPageSize = pdfPageSizeValue;
    }
    public boolean isImageExportable() {
        return false;
    }
    @SuppressWarnings("unchecked")
    public CucumberFeatureResult[] readFileContent(String sourceFileValue) throws Exception {
        FileInputStream fis = null;
        JsonReader jr = null;
        File file = new File(sourceFileValue);

        if (!(file.exists() && file.isFile())) {
            throw new FileNotFoundException();
        }

        fis = new FileInputStream(file);
        jr = new JsonReader(fis, true);
        //Object raw = jr.readObject();
        //JsonObject<String, Object> source = (JsonObject<String, Object>) raw;
        Object[] objs = (Object[]) jr.readObject(); //source.get("@items");

        CucumberFeatureResult[] sources = new CucumberFeatureResult[objs.length];
        for (int i = 0; i < objs.length; i++) {
            sources[i] = new CucumberFeatureResult((JsonObject<String, Object>) objs[i]);
        }
        jr.close();
        fis.close();
        return sources;
    }

    public void dumpOverviewStats(File outFile, CucumberFeatureResult[] results) throws IOException {
        int[][] stats = getStatuses(results);
        JAXB.marshal(stats, outFile);
        String content = FileUtils.readFileToString(outFile);
        content = content.replaceAll("\\[]\\[]", "");
        FileUtils.writeStringToFile(outFile, content);
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
    protected <T extends CommonDataBean> void generateReportFromTemplate(
            File outFile, String templateName, T bean) throws Exception {
        Configuration cfg = FreemarkerConfiguration.get("");
        cfg.setAPIBuiltinEnabled(true);
        /* Get the template (uses cache internally) */
        Template temp = cfg.getTemplate(templateName);
        outFile.getAbsoluteFile().getParentFile().mkdirs();
        /* Merge data-model with template */
        FileWriter writer = new FileWriter(outFile);
        bean.setPdfPageSize(this.getPdfPageSize());
        if (StringUtils.isBlank(bean.getTitle())) {
            bean.setTitle(this.getReportType().toString());
        }
        BeansWrapper wrapper = new BeansWrapper(Configuration.VERSION_2_3_23);
        TemplateHashModel staticModels = wrapper.getStaticModels();
        TemplateHashModel mathStatics =
            (TemplateHashModel) staticModels.get("java.lang.Math");
        wrapper.wrap(bean);
        //model.put("Math", BeansWrapper.getDefaultInstance().getStaticModels().get("java.lang.Math"));
        temp.process(bean, writer);
        writer.close();
    }
    private File generateBackupFile(File htmlFile) throws Exception {
        File backupFile = new File(htmlFile.getAbsolutePath() + ".bak.html");
        String updatedContent = replaceSvgWithPng(htmlFile);
        updatedContent = updatedContent.replaceAll("\"hoverTable\"", "\"_hoverTable\"");
        updatedContent = updatedContent.replaceAll("__PAGESIZE__", this.getPdfPageSize());
        FileUtils.writeStringToFile(backupFile, updatedContent);
        return backupFile;
    }
    private void exportToPDF(File htmlFile, String suffix) throws Exception {
        File bakupFile = generateBackupFile(htmlFile);
        String url = bakupFile.toURI().toURL().toString();
        String outputFile = this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + suffix + ".pdf";

        OutputStream os = new FileOutputStream(outputFile);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(url);
        renderer.layout();
        renderer.createPDF(os);

        os.close();
    }
    private void exportToImage(File htmlFile, String suffix, String format) throws Exception {
        final int defaultWidth = 1024;
        File bakupFile = generateBackupFile(htmlFile);
        String outputFile = this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + suffix + "." + format;

        BufferedImage buff = null;
        buff = Graphics2DRenderer.renderToImageAutoSize(bakupFile.toURI().toURL().toString(), defaultWidth);
        FSImageWriter imageWriter = new FSImageWriter();
        imageWriter.write(buff, outputFile);
    }

    public void export(File htmlFile, String suffix, String[] formats, boolean isImageExportable) throws Exception {
        for (String format : formats) {
            if (format.trim().equalsIgnoreCase("pdf")) {
                this.exportToPDF(htmlFile, suffix);
            } else if (isImageExportable) {
                this.exportToImage(htmlFile, suffix, format);
            }
        }
    }
    public int[][] getStatuses(CucumberFeatureResult[] results) {
        return null;
    }
}
