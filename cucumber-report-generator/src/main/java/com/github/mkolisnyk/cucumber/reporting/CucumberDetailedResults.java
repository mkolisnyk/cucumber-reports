package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.github.mkolisnyk.cucumber.reporting.interfaces.KECompatibleReport;
import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;
import com.github.mkolisnyk.cucumber.reporting.types.beans.DetailedReportingDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberEmbedding;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberStepResult;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

/**
 * @author Myk Kolisnyk
 */
public class CucumberDetailedResults extends KECompatibleReport {
    public CucumberDetailedResults() {
        super();
    }

    public CucumberDetailedResults(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
        this.setScreenShotLocation(extendedOptions.getScreenShotLocation());
        this.setScreenShotWidth(extendedOptions.getScreenShotSize());
    }

    private String screenShotLocation;
    private String screenShotWidth = "100%";

    /**
     * @return the screenShotLocation
     */
    public final String getScreenShotLocation() {
        return screenShotLocation;
    }

    /**
     * @param screenShotLocationValue the screenShotLocation to set
     */
    public final void setScreenShotLocation(String screenShotLocationValue) {
        this.screenShotLocation = screenShotLocationValue;
    }

    /**
     * @return the screenShotWidth
     */
    public final String getScreenShotWidth() {
        return screenShotWidth;
    }

    /**
     * @param screenShotWidthValue the screenShotWidth to set
     */
    public final void setScreenShotWidth(String screenShotWidthValue) {
        this.screenShotWidth = screenShotWidthValue;
    }
    public String generateNameFromId(String scId) {
        if (scId == null) {
            scId = "null";
        }
        String result = scId.replaceAll("[^A-Za-z0-9]", "_");
        return result;
    }
    private String getExtensionFromMime(String mime) {
        if (mime.contains("png")) {
            return "png";
        }
        if (mime.contains("jpg") || mime.contains("jpeg")) {
            return "jpg";
        }
        return "txt";
    }
    private String[] generateEmbededScreenShots(
            CucumberScenarioResult scenario, CucumberStepResult step) throws IOException {
        String[] outputs = new String[] {};
        String scenarioId = scenario.getId();
        if (StringUtils.isBlank(scenarioId)) {
            scenarioId = "background";
        }
        if (step.getEmbeddings() != null) {
            int index = 0;
            long base = new Date().getTime();
            for (CucumberEmbedding embedding : step.getEmbeddings()) {
                String embedPath = Paths.get(
                        this.getScreenShotLocation(),
                        this.generateNameFromId(scenarioId) + (base + index) + "."
                        + getExtensionFromMime(embedding.getMimeType())).toString();
                File embedShot = new File(this.getOutputDirectory() + embedPath);
                FileUtils.writeByteArrayToFile(embedShot, embedding.getData());
                outputs = (String[]) ArrayUtils.add(outputs, embedPath);
                index++;
            }
        }
        return outputs;
    }

    @Override
    public int[][] getStatuses(CucumberFeatureResult[] results) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.DETAILED_REPORT;
    }

    @Override
    public void validateParameters() {
        // TODO Auto-generated method stub
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.DETAILED_URL;
    }
    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
        execute((KnownErrorsModel) null, aggregate, formats);
    }
    public void execute(KnownErrorsModel batch, CucumberFeatureResult[] features, boolean aggregate, String[] formats)
            throws Exception {
        String formatName = "";
        for (CucumberFeatureResult feature : features) {
            feature.valuate();
            if (batch != null) {
                feature.valuateKnownErrors(batch);
            }
        }
        if (aggregate) {
            formatName = "%s%s%s-agg-" + this.reportSuffix() + ".html";
        } else {
            formatName = "%s%s%s-" + this.reportSuffix() + ".html";
        }
        File outFile = new File(
                String.format(Locale.US,
                        formatName,
                        this.getOutputDirectory(), File.separator, this.getOutputName()));
        DetailedReportingDataBean data = new DetailedReportingDataBean();
        OverviewStats stats = new OverviewStats();
        data.setStats(stats.valuate(features));
        data.setResults(features);
        for (CucumberFeatureResult feature : features) {
            for (CucumberScenarioResult scenario : feature.getElements()) {
                for (CucumberStepResult step : scenario.getSteps()) {
                    String[] screenShotLocations = this.generateEmbededScreenShots(scenario, step);
                    step.setScreenShotLocations(screenShotLocations);
                }
            }
        }
        data.setScreenShotWidth(getScreenShotWidth());
        generateReportFromTemplate(outFile, this.templateName(), data);
        this.export(outFile, this.reportSuffix(), formats, this.isImageExportable());
    }
    @Override
    public void execute(KnownErrorsModel batch, boolean aggregate, String[] formats)
            throws Exception {
        CucumberFeatureResult[] features = readFileContent(aggregate);
        execute(batch, features, aggregate, formats);
    }

    @Override
    public void execute(boolean aggregate, CucumberFeatureResult[] features,
            String[] formats) throws Exception {
        execute((KnownErrorsModel) null, features, aggregate, formats);
    }
}
