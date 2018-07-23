package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.codehaus.plexus.util.StringUtils;

import com.github.mkolisnyk.cucumber.reporting.types.beans.FeatureMapDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberFeatureMapReport extends CucumberBreakdownReport {

    public CucumberFeatureMapReport() {
        super();
    }
    public CucumberFeatureMapReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }
    @Override
    public void executeReport(BreakdownReportInfo info, BreakdownTable table, String[] formats) throws Exception {
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + info.getReportSuffix() + ".html");
        CucumberScenarioResult[] scenarios = new CucumberScenarioResult[] {};
        for (CucumberFeatureResult feature : features) {
            CucumberScenarioResult[] elements = feature.getElements();
            for (CucumberScenarioResult element : elements) {
                element.setFeature(feature);
            }
            scenarios = ArrayUtils.addAll(scenarios, elements);
        }
        CucumberScenarioResult[][][] results = table.valuateScenarios(scenarios);
        FeatureMapDataBean data = new FeatureMapDataBean();
        data.setTitle(info.getTitle());
        if (info.getRefreshTimeout() > 0 && StringUtils.isNotBlank(info.getNextFile())) {
            data.setRefreshData(String.format(Locale.US,
                    "<meta http-equiv=\"Refresh\" content=\"%d; url=%s\" />",
                        info.getRefreshTimeout(), info.getNextFile()));
        }
        data.setScenarios(results);
        data.setTable(table);
        generateReportFromTemplate(outFile, this.templateName(), data);
        this.export(outFile, info.getReportSuffix(), formats, this.isImageExportable());
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.FEATURE_MAP_REPORT;
    }
}
