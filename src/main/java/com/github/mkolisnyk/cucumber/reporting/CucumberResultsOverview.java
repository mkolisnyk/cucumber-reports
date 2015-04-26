package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.io.FileUtils;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberStepSource;

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
    
    private String getReportBase() {
        return "<html><head><style type=\"text/css\">" +
                "h1 {background-color:#9999CC}" + System.lineSeparator() +
                "h2 {background-color:#BBBBCC}" + System.lineSeparator() +
                "h3 {background-color:#DDDDFF}" + System.lineSeparator() +
                "th {border:1px solid black;background-color:#CCCCDD;}" + System.lineSeparator() +
                "td{border:1px solid black;}" + System.lineSeparator() +
                "table{border:1px solid black;border-collapse: collapse;}" + System.lineSeparator() +
                ".passed {background-color:lightgreen;font-weight:bold;color:darkgreen}" + System.lineSeparator() +
                ".failed {background-color:tomato;font-weight:bold;color:darkred}" + System.lineSeparator() +
                ".undefined {background-color:gold;font-weight:bold;color:goldenrod}" + System.lineSeparator() +
                //"tr:nth-child(even) {background: #CCC}" + System.lineSeparator() +
                //"tr:nth-child(odd) {background: #FFF}"
                "</style>"
                + "<title>__TITLE__</title></head>"
                + "<body>__REPORT__</body></html>";
    }
    
    private String generateFeatureOverview(CucumberFeatureResult[] results) {
        String content = this.getReportBase();
        content = content.replaceAll("__TITLE__", "Features Overview");
        
        int passed = 0;
        int failed = 0;
        int undefined = 0;
        
        String reportContent = "";
        
        for (CucumberFeatureResult result : results) {
            String status = result.getStatus();
            if (status.equalsIgnoreCase("failed")) {
                failed++;
            } else if (status.equalsIgnoreCase("passed")) {
                passed++;
            } else {
                undefined++;
            }
        }
        
        reportContent += "<h1>Features Status</h1><table><tr><th>Feature Name</th><th>Status</th><th>Passed</th><th>Failed</th><th>Undefined</th></tr>";
        
        for (CucumberFeatureResult result : results) {
            reportContent += String.format("<tr class=\"%s\"><td>%s</td><td>%s</td><td>%d</td><td>%d</td><td>%d</td></tr>",
                    result.getStatus(), result.getName(), result.getStatus(), result.getPassed(), result.getFailed(), result.getUndefined());
        }
        reportContent += "</table>";
        content = content.replaceAll("__REPORT__", reportContent);
        return content;
    }
    
    
    public void executeFeaturesOverviewReport() throws Exception {
        CucumberFeatureResult[] features= readFileContent();
        File outFile = new File(this.getOutputDirectory() + File.separator + this.getOutputName() + "-feature-overview.html");
        FileUtils.writeStringToFile(outFile, generateFeatureOverview(features));
    }
}
