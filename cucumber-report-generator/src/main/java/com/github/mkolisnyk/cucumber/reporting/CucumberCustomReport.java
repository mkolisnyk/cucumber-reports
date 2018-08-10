package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.SimpleReport;
import com.github.mkolisnyk.cucumber.reporting.types.beans.CustomReportDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberStepSource;
import com.github.mkolisnyk.cucumber.reporting.utils.helpers.FreemarkerConfiguration;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberCustomReport extends SimpleReport {

    private String[] jsonUsageReportPaths;
    private String[] customReportTemplateNames;
    private CustomReportDataBean data;
        {
            data = new CustomReportDataBean();
        }

    public CucumberCustomReport() {
        super();
    }

    public CucumberCustomReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
        this.setJsonUsageReportPaths(extendedOptions.getJsonUsageReportPaths());
        this.setCustomReportTemplateNames(extendedOptions.getCustomReportTemplateNames());
    }

    public String[] getJsonUsageReportPaths() {
        return jsonUsageReportPaths;
    }

    public void setJsonUsageReportPaths(String[] jsonUsageReportPathsValue) {
        this.jsonUsageReportPaths = jsonUsageReportPathsValue;
    }

    public String[] getCustomReportTemplateNames() {
        return customReportTemplateNames;
    }

    public void setCustomReportTemplateNames(String[] customReportTemplateNamesValue) {
        this.customReportTemplateNames = customReportTemplateNamesValue;
    }

    @Override
    public void execute() throws Exception {
        execute(new String[] {});
    }

    @Override
    public void execute(String[] formats) throws Exception {
        this.validateParameters();
        Map<String, CucumberStepSource[]> usageResults = new LinkedHashMap<String, CucumberStepSource[]>();
        Map<String, CucumberFeatureResult[]> runResults = new LinkedHashMap<String, CucumberFeatureResult[]>();
        for (String file : this.getJsonUsageReportPaths()) {
            CucumberUsageReporting usageReport = new CucumberUsageReporting();
            CucumberStepSource[] value = usageReport.getStepSources(file);
            usageResults.put(file, value);
        }
        for (String file : this.getSourceFiles()) {
            CucumberFeatureResult[] value = this.readFileContent(file);
            runResults.put(file, value);
        }
        data.setRunResults(runResults);
        data.setUsageResults(usageResults);
        for (String reportSuffix : this.getCustomReportTemplateNames()) {
            File outFile = new File(
                    this.getOutputDirectory() + File.separator + this.getOutputName()
                    + "-" + reportSuffix + ".html");
            generateReportFromTemplate(outFile, reportSuffix, data);
            this.export(outFile, reportSuffix, formats, this.isImageExportable());
        }
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.CUSTOM_REPORT;
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.CUSTOM_URL;
    }

    @Override
    public void validateParameters() {
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_DIRECTORY, ""),
                this.getOutputDirectory());
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_NAME, ""),
                this.getOutputName());
        if (this.getSourceFiles() != null) {
            for (String sourceFile : this.getSourceFiles()) {
                Assert.assertNotNull(
                        this.constructErrorMessage(CucumberReportError.NO_SOURCE_FILE, ""), sourceFile);
                File path = new File(sourceFile);
                Assert.assertTrue(this.constructErrorMessage(CucumberReportError.NON_EXISTING_SOURCE_FILE, "")
                        + ". Was looking for path: \"" + path.getAbsolutePath() + "\"", path.exists());
            }
        }
        if (this.getJsonUsageReportPaths() != null) {
            for (String usageFile : this.getJsonUsageReportPaths()) {
                Assert.assertNotNull(
                        this.constructErrorMessage(CucumberReportError.NO_SOURCE_FILE, ""), usageFile);
                File path = new File(usageFile);
                Assert.assertTrue(this.constructErrorMessage(CucumberReportError.NON_EXISTING_SOURCE_FILE, "")
                        + ". Was looking for path: \"" + path.getAbsolutePath() + "\"", path.exists());
            }
        }
        Map<String, String> resourceMap = new HashMap<String, String>();
        try {
            resourceMap = FreemarkerConfiguration.getResourceMap(getTemplatesLocation());
        } catch (Exception e) {
            Assert.fail(this.constructErrorMessage(CucumberReportError.RESOURCE_LOAD_FAILED, ""));
        }
        for (String name : this.getCustomReportTemplateNames()) {
            Assert.assertTrue(
                    name + " template wasn't found. "
                       + this.constructErrorMessage(CucumberReportError.MISSING_TEMPLATE, ""),
                    resourceMap.containsKey(name));
        }
    }
    
    public void setPersonalDataBean(CustomReportDataBean dataBean) {
        data = dataBean;
    }
        
    public CustomReportDataBean getDataBean() {
        return data;
    }

}
