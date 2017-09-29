package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.github.mkolisnyk.cucumber.reporting.types.beans.FeatureOverviewDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberFeatureOverview extends CucumberResultsOverview {

    public CucumberFeatureOverview() {
        super();
    }

    public CucumberFeatureOverview(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.FEATURE_OVERVIEW_URL;
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.FEATURE_OVERVIEW;
    }

    //@Override
    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/feature-overview-tmpl-2.html");
        String result = IOUtils.toString(is);
        return result;
    }

    private String getStatusLetter(double rate) {
        String scale = "ABCDEF";
        int index = (int) ((1. - rate) * (double) (scale.length() - 1));
        return scale.substring(index, index + 1);
    }

    private String getFeatureStatusLetter(CucumberFeatureResult feature) {
        feature.valuate();
        double rate = (double) feature.getPassed()
                / (double) (feature.getPassed() + feature.getFailed()
                        + feature.getSkipped() + feature.getUndefined());
        return getStatusLetter(rate);
    }

    private double getOverallRate(CucumberFeatureResult[] results) {
        int[][] statuses = this.getStatuses(results);
        int[] scenarioStatuses = statuses[1];
        return (double) scenarioStatuses[0]
                / (double) (scenarioStatuses[0] + scenarioStatuses[1] + scenarioStatuses[2]);
    }

    @Override
    public void execute(String[] formats) throws Exception {
        execute(true, formats);
    }

    @Override
    public void execute() throws Exception {
        execute(true);
    }

    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
        final int maxRate = 100;
        validateParameters();
        CucumberFeatureResult[] features = readFileContent(aggregate);
        File outFile = getOutputHtmlFile();
        FeatureOverviewDataBean data = new FeatureOverviewDataBean();
        Map<String, String> featureData = new LinkedHashMap<String, String>();
        for (int i = 0; i < features.length; i++) {
            features[i].valuate();
            featureData.put(features[i].getName(), getFeatureStatusLetter(features[i]));
        }
        data.setFeatureRate(featureData);
        double rate = getOverallRate(features);
        data.setPassRate((int) (rate * maxRate));
        data.setOverallRate(getStatusLetter(rate));
        generateReportFromTemplate(outFile, this.templateName(), data);
        this.export(outFile, this.reportSuffix(), formats, this.isImageExportable());
    }
}
