package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.util.UUID;

import com.cedarsoftware.util.io.JsonObject;
import com.github.mkolisnyk.cucumber.reporting.interfaces.AggregatedReport;
import com.github.mkolisnyk.cucumber.reporting.types.beans.SplitFeatureDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberTagResults;

public class CucumberSplitFeature extends AggregatedReport {

    public CucumberSplitFeature() {
    }

    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
        CucumberFeatureResult[] features = readFileContent(aggregate);
        execute(aggregate, features, formats);
    }

    @Override
    public void execute(String[] formats) throws Exception {
        execute(false, formats);
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.SPLIT_FEATURE;
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return null;
    }

    @Override
    public void validateParameters() {
    }

    @Override
    public void execute(boolean aggregate, CucumberFeatureResult[] features,
            String[] formats) throws Exception {
        validateParameters();
        for (CucumberFeatureResult feature : features) {
            JsonObject<String, Object> json = new JsonObject<String, Object>();
            json.put("name", "@" + UUID.randomUUID().toString());
            CucumberTagResults[] tags = new CucumberTagResults[] {new CucumberTagResults(json)};
            feature.setTags(tags);
            // Add other tags
            for (CucumberScenarioResult scenario : feature.getElements()) {
                File outFile = new File(
                        this.getOutputDirectory() + File.separator + scenario.getId().replaceAll("[^A-Za-z0-9]", "_")
                        + ".feature");
                SplitFeatureDataBean data = new SplitFeatureDataBean();
                data.setScenario(scenario);
                generateReportFromTemplate(outFile, this.templateName(), data);
            }
        }
    }

}
