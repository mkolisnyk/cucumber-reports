package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.github.mkolisnyk.cucumber.reporting.interfaces.SimpleReport;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberSystemInfo extends SimpleReport {

    public CucumberSystemInfo() {
    }
    public CucumberSystemInfo(ExtendedRuntimeOptions extendedOptions) {
        this.setOutputDirectory(extendedOptions.getOutputFolder());
        this.setOutputName(extendedOptions.getReportPrefix());
    }
    private String generateSystemInfo() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/consolidated-tmpl.html");
        String result = IOUtils.toString(is);
        String content = "<h1>System Properties</h1>"
                + "<table width=\"700px\"><tr><th>Property</th><th>Value</th></tr>";
        for (Entry<Object, Object> entry : System.getProperties().entrySet()) {
            content = content.concat(
                String.format("<tr><td>%s</td><td>%s</td></tr>", entry.getKey(), entry.getValue())
            );
        }
        content = content + "</table><h1>Environment Variables</h1>"
                + "<table width=\"700px\"><tr><th>Variable</th><th>Value</th></tr>";
        for (Entry<String, String> entry : System.getenv().entrySet()) {
            content = content.concat(
                String.format("<tr><td>%s</td><td>%s</td></tr>", entry.getKey(), entry.getValue())
            );
        }
        content = content + "</table>";
        result = result.replaceAll("__TITLE__", "System Info");
        result = result.replaceAll("__REFRESH__", "");
        result = result.replaceAll("__REPORT__", content);
        return result;
    }
    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.SYSTEM_INFO;
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.SYSTEM_INFO_URL;
    }

    @Override
    public void validateParameters() {
    }

    @Override
    public void execute() throws Exception {
        execute(false);
    }

    @Override
    public void execute(boolean toPDF) throws Exception {
        validateParameters();
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-system-info.html");
        FileUtils.writeStringToFile(outFile, generateSystemInfo());
        this.export(outFile, "system-info", "", toPDF, this.isImageExportable());
    }
}
