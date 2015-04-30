package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class CucumberResultsOverview {

    private String sourceFile;
    private String outputDirectory;
    private String outputName;
    
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

    public CucumberFeatureResult[] readFileContent() throws Exception {
        FileInputStream fis = null;
        JsonReader jr = null;
        File file = new File(this.getSourceFile());

        if (!(file.exists() && file.isFile())) {
            throw new FileNotFoundException();
        }

        fis = new FileInputStream(file);
        jr = new JsonReader(fis,true);
        JsonObject<String,Object> source = (JsonObject<String,Object>)jr.readObject();
        Object[] objs = (Object[])source.get("@items");
        
        CucumberFeatureResult[] sources = new CucumberFeatureResult[objs.length];
        for(int i=0;i<objs.length;i++){
            sources[i] = new CucumberFeatureResult((JsonObject<String,Object>)objs[i]);
        }
        jr.close();
        fis.close();
        return sources;
    }
    
    private String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/feature-overview-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }
    
    private String getFeatureData(CucumberFeatureResult[] results) {
        int passed = 0;
        int failed = 0;
        int undefined = 0;
        
        for (CucumberFeatureResult result : results) {
            passed += result.getStatus().trim().equalsIgnoreCase("passed") ? 1 : 0;
            failed += result.getStatus().trim().equalsIgnoreCase("failed") ? 1 : 0;
            undefined += result.getStatus().trim().equalsIgnoreCase("undefined") ? 1 : 0;
        }
        
        return String.format("['Passed', %d], ['Failed', %d], ['Undefined', %d]", passed, failed, undefined);
    }
    
    private String getScenarioData(CucumberFeatureResult[] results) {
        int passed = 0;
        int failed = 0;
        int undefined = 0;

        for (CucumberFeatureResult result : results) {
            for (CucumberScenarioResult element : result.getElements()) {
                passed += element.getStatus().trim().equalsIgnoreCase("passed") ? 1 : 0;
                failed += element.getStatus().trim().equalsIgnoreCase("failed") ? 1 : 0;
                undefined += element.getStatus().trim().equalsIgnoreCase("undefined") ? 1 : 0;
            }
        }

        return String.format("['Passed', %d], ['Failed', %d], ['Undefined', %d]", passed, failed, undefined);
    }
    
    private String generateFeatureOverview(CucumberFeatureResult[] results) throws IOException {
        String content = this.getReportBase();
        content = content.replaceAll("__TITLE__", "Features Overview");
        String reportContent = "";
        
        reportContent += "<h1>Features Status</h1><table><tr><th>Feature Name</th><th>Status</th><th>Passed</th><th>Failed</th><th>Undefined</th></tr>";
        
        for (CucumberFeatureResult result : results) {
            reportContent += String.format("<tr class=\"%s\"><td>%s</td><td>%s</td><td>%d</td><td>%d</td><td>%d</td></tr>",
                    result.getStatus(), result.getName(), result.getStatus(), result.getPassed(), result.getFailed(), result.getUndefined());
        }
        reportContent += "</table>";
        reportContent += "<h1>Scenario Status</h1><table><tr><th>Feature Name</th><th>Scenario</th><th>Status</th><th>Passed</th><th>Failed</th><th>Undefined</th></tr>";
        
        for (CucumberFeatureResult result : results) {
            for (CucumberScenarioResult element : result.getElements()) {
                reportContent += String.format("<tr class=\"%s\"><td>%s</td><td>%s</td><td>%s</td><td>%d</td><td>%d</td><td>%d</td></tr>",
                        element.getStatus(), result.getName(), element.getName(), element.getStatus(), element.getPassed(), element.getFailed(), element.getUndefined());
            }
        }
        reportContent += "</table>";
        content = content.replaceAll("__REPORT__", reportContent);
        content = content.replaceAll("__FEATURE_DATA__", getFeatureData(results));
        content = content.replaceAll("__SCENARIO_DATA__", getScenarioData(results));
        return content;
    }
    
    
    public void executeFeaturesOverviewReport() throws Exception {
        CucumberFeatureResult[] features= readFileContent();
        File outFile = new File(this.getOutputDirectory() + File.separator + this.getOutputName() + "-feature-overview.html");
        FileUtils.writeStringToFile(outFile, generateFeatureOverview(features));
    }
}
