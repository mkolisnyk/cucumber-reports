package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;

import com.github.mkolisnyk.cucumber.reporting.types.beans.CoverageDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.beans.CoverageDataBean.FeatureStatusRow;
import com.github.mkolisnyk.cucumber.reporting.types.beans.CoverageDataBean.ScenarioStatusRow;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberCoverageOverview extends CucumberResultsOverview {

    public CucumberCoverageOverview() {
        super();
    }

    public CucumberCoverageOverview(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
        this.setExcludeCoverageTags(extendedOptions.getExcludeCoverageTags());
        this.setIncludeCoverageTags(extendedOptions.getIncludeCoverageTags());
    }

    private String[] includeCoverageTags = {};
    private String[] excludeCoverageTags = {};

    public final String[] getIncludeCoverageTags() {
        return includeCoverageTags;
    }

    public final void setIncludeCoverageTags(String[] includeCoverageTagsValue) {
        if (includeCoverageTagsValue == null) {
            this.includeCoverageTags = new String[0];
        } else {
            this.includeCoverageTags = includeCoverageTagsValue;
        }
    }

    public final String[] getExcludeCoverageTags() {
        return excludeCoverageTags;
    }

    public final void setExcludeCoverageTags(String[] excludeCoverageTagsValue) {
        if (excludeCoverageTagsValue == null) {
            this.excludeCoverageTags = new String[0];
        } else {
            this.excludeCoverageTags = excludeCoverageTagsValue;
        }
    }

    private String getFeatureStatus(CucumberFeatureResult result) {
        if (result.getStatus().equals("undefined") || result.getUndefined() > 0) {
            return "undefined";
        } else {
            return "passed";
        }
    }
    private String getScenarioStatus(CucumberScenarioResult result) {
        if (result.getStatus().equals("undefined") || result.getUndefined() > 0) {
            return "undefined";
        } else {
            return "passed";
        }
    }

    @Override
    public void execute(String[] formats) throws Exception {
        File outFile = getOutputHtmlFile();
        this.validateParameters();
        CucumberFeatureResult[] features = readFileContent(true);
        CoverageDataBean data = new CoverageDataBean();
        FeatureStatusRow[] featureRows = new CoverageDataBean.FeatureStatusRow[] {};
        ScenarioStatusRow[] scenarioRows = new CoverageDataBean.ScenarioStatusRow[] {};

        for (CucumberFeatureResult feature : features) {
            feature.setIncludeCoverageTags(includeCoverageTags);
            feature.setExcludeCoverageTags(excludeCoverageTags);
            feature.valuate();
            FeatureStatusRow featureRow = new CoverageDataBean().new FeatureStatusRow();
            featureRow.setFeatureName(feature.getName());
            featureRow.setStatus(getFeatureStatus(feature));
            featureRow.setTags(feature.getAllTags(true));
            featureRow.setCovered(feature.getPassed() + feature.getFailed() + feature.getSkipped());
            featureRow.setNotCovered(feature.getUndefined());

            featureRows = ArrayUtils.add(featureRows, featureRow);

            for (CucumberScenarioResult scenario : feature.getElements()) {
                ScenarioStatusRow scenarioRow = new CoverageDataBean().new ScenarioStatusRow();
                scenario.setIncludeCoverageTags(includeCoverageTags);
                scenario.setExcludeCoverageTags(excludeCoverageTags);
                scenario.valuate();
                scenarioRow.setFeatureName(scenario.getFeature().getName());
                scenarioRow.setScenarioName(scenario.getName());
                scenarioRow.setStatus(getScenarioStatus(scenario));
                scenarioRow.setCovered(scenario.getPassed() + scenario.getFailed() + scenario.getSkipped());
                scenarioRow.setNotCovered(scenario.getUndefined());
                scenarioRow.setTags(ArrayUtils.addAll(scenario.getAllTags(), scenario.getFeature().getAllTags(false)));

                scenarioRows = ArrayUtils.add(scenarioRows, scenarioRow);
            }
        }
        data.setFeatures(featureRows);
        data.setScenarios(scenarioRows);
        generateReportFromTemplate(outFile, this.templateName(), data);
        this.export(outFile, this.reportSuffix(), formats, this.isImageExportable());
    }

    @Override
    public void execute() throws Exception {
        execute(new String[] {});
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.COVERAGE_OVERVIEW;
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.COVERAGE_OVERVIEW_URL;
    }
}
