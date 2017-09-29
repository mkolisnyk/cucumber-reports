package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import com.github.mkolisnyk.cucumber.reporting.interfaces.SimpleReport;
import com.github.mkolisnyk.cucumber.reporting.types.beans.SystemInfoDataBean;
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
        execute(new String[] {});
    }

    @Override
    public void execute(String[] formats) throws Exception {
        validateParameters();
        File outFile = getOutputHtmlFile();
        SystemInfoDataBean data = new SystemInfoDataBean();
        data.setSystemProperties(System.getProperties());
        data.setEnvironmentVariables(System.getenv());
        generateReportFromTemplate(outFile, this.templateName(), data);
        this.export(outFile, this.reportSuffix(), formats, this.isImageExportable());
    }
}
