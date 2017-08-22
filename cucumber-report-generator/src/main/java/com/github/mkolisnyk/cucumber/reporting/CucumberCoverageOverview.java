package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.mkolisnyk.cucumber.reporting.types.beans.CommonDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.beans.CoverageDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.beans.CoverageDataBean.FeatureStatusRow;
import com.github.mkolisnyk.cucumber.reporting.types.beans.CoverageDataBean.ScenarioStatusRow;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberStepResult;
import com.github.mkolisnyk.cucumber.reporting.utils.drawers.PieChartDrawer;
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

    public int[][] getStatuses(CucumberFeatureResult[] results) {
        int[][] statuses = {{0, 0}, {0, 0}, {0, 0}};
        for (CucumberFeatureResult result : results) {
            result.setIncludeCoverageTags(includeCoverageTags);
            result.setExcludeCoverageTags(excludeCoverageTags);
            if (result.getStatus().equals("undefined") || result.getUndefined() > 0) {
                statuses[0][1]++;
            } else {
                statuses[0][0]++;
            }
            for (CucumberScenarioResult element : result.getElements()) {
                element.setIncludeCoverageTags(includeCoverageTags);
                element.setExcludeCoverageTags(excludeCoverageTags);
                if (element.getStatus().equals("undefined") || element.getUndefined() > 0) {
                    statuses[1][1]++;
                } else {
                    statuses[1][0]++;
                }
                for (CucumberStepResult step : element.getSteps()) {
                    if (step.getResult().getStatus().equals("undefined")) {
                        statuses[2][1]++;
                    } else {
                        statuses[2][0]++;
                    }
                }
            }
        }
        return statuses;
    }
    @Override
    protected String generateFeatureOverview(CucumberFeatureResult[] results)
            throws IOException {
        String content = this.getReportBase();
        content = content.replaceAll("__TITLE__", "Test Coverage Overview");
        String reportContent = "";

        reportContent += "<h1>Features Status</h1><table>"
                + "<tr><th rowspan=\"2\">Feature Name</th><th rowspan=\"2\">Status</th>"
                + "<th colspan=\"2\">Scenarios</th><th rowspan=\"2\">Tags</th></tr>"
                + "<tr><th>Covered</th><th>Not Covered</th></tr>";

        for (CucumberFeatureResult result : results) {
            result.setIncludeCoverageTags(includeCoverageTags);
            result.setExcludeCoverageTags(excludeCoverageTags);

            String status = getFeatureStatus(result);
            reportContent += String.format(
                    Locale.US,
                    "<tr class=\"%s\"><td>%s</td><td>%s</td><td>%d</td><td>%d</td><td>%s</td></tr>",
                    status,
                    result.getName(),
                    status,
                    result.getPassed() + result.getFailed() + result.getSkipped(),
                    result.getUndefined(),
                    StringUtils.join(result.getAllTags(true), ", "));
        }
        reportContent += "</table>";
        reportContent += "<h1>Scenario Status</h1><table>"
                + "<tr><th rowspan=\"2\">Feature Name</th>"
                + "<th rowspan=\"2\">Scenario</th>"
                + "<th rowspan=\"2\">Status</th>"
                + "<th colspan=\"2\">Steps</th><th rowspan=\"2\">Tags</th></tr>"
                + "<tr><th>Covered</th>"
                + "<th>Not Covered</th></tr>";

        int[][] statuses = this.getStatuses(results);
        int[] featureStatuses = statuses[0];
        int[] scenarioStatuses = statuses[1];
        for (CucumberFeatureResult result : results) {
            result.setIncludeCoverageTags(includeCoverageTags);
            result.setExcludeCoverageTags(excludeCoverageTags);

            for (CucumberScenarioResult element : result.getElements()) {
                element.setIncludeCoverageTags(includeCoverageTags);
                element.setExcludeCoverageTags(excludeCoverageTags);

                String status = getScenarioStatus(element);
                Set<String> tags = new HashSet<String>();
                for (String tag : result.getAllTags(false)) {
                    tags.add(tag);
                }
                for (String tag : element.getAllTags()) {
                    if (!tags.contains(tag)) {
                        tags.add(tag);
                    }
                }
                reportContent += String.format(
                        Locale.US,
                        "<tr class=\"%s\">"
                        + "<td>%s</td><td>%s</td><td>%s</td>"
                        + "<td>%d</td><td>%d</td><td>%s</td></tr>",
                        status,
                        result.getName(),
                        element.getName(),
                        status,
                        element.getPassed() + element.getFailed() + element.getSkipped(),
                        element.getUndefined(),
                        StringUtils.join(tags, ", "));
            }
            reportContent += "<tr><td colspan=\"6\"></td></tr>";
        }
        reportContent += "</table>";
        content = content.replaceAll("__REPORT__", reportContent);
        PieChartDrawer pieChart = new PieChartDrawer();
        content = content.replaceAll("__FEATURE_DATA__", pieChart.generatePieChart(
                CHART_WIDTH, CHART_HEIGHT,
                featureStatuses,
                new String[]{"Covered", "Not Covered"},
                new String[]{"green", "gold"},
                new String[]{"darkgreen", "GoldenRod"},
                CHART_THICKNESS,
                2));
        content = content.replaceAll("__SCENARIO_DATA__", pieChart.generatePieChart(
                CHART_WIDTH, CHART_HEIGHT,
                scenarioStatuses,
                new String[]{"Covered", "Not Covered"},
                new String[]{"green", "gold"},
                new String[]{"darkgreen", "GoldenRod"},
                CHART_THICKNESS,
                2));
        return content;
    }

    @Override
    public void execute(String[] formats) throws Exception {
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-coverage.html");
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
        generateReportFromTemplate(outFile, "coverage", data);
        this.export(outFile, "coverage", formats, this.isImageExportable());
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
