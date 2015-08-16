package com.github.mkolisnyk.cucumber.reporting;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class CucumberCoverageOverview extends CucumberResultsOverview {

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

    @Override
    protected String getFeatureData(CucumberFeatureResult[] results) {
        int passed = 0;
        int undefined = 0;

        for (CucumberFeatureResult result : results) {
            if (result.getStatus().trim().equalsIgnoreCase("undefined")) {
                undefined++;
            } else {
                passed++;
            }
        }
        return String.format("['Covered', %d], ['Not Covered', %d]", passed, undefined);
    }

    @Override
    protected String getScenarioData(CucumberFeatureResult[] results) {
        int passed = 0;
        int undefined = 0;

        for (CucumberFeatureResult result : results) {
            for (CucumberScenarioResult element : result.getElements()) {
                if (element.getStatus().trim().equalsIgnoreCase("undefined")) {
                    undefined++;
                } else {
                    passed++;
                }
            }
        }

        return String.format("['Covered', %d], ['Not Covered', %d]", passed, undefined);
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
            String status = result.getStatus();
            if (!status.equalsIgnoreCase("undefined")) {
                status = "passed";
            }
            reportContent += String.format(
                    "<tr class=\"%s\"><td>%s</td><td>%s</td><td>%d</td><td>%d</td><td>%s</td></tr>",
                    status,
                    result.getName(),
                    status,
                    result.getPassed() + result.getFailed() + result.getSkipped(),
                    result.getUndefined(),
                    StringUtils.join(result.getTags(), ", "));
        }
        reportContent += "</table>";
        reportContent += "<h1>Scenario Status</h1><table>"
                + "<tr><th rowspan=\"2\">Feature Name</th>"
                + "<th rowspan=\"2\">Scenario</th>"
                + "<th rowspan=\"2\">Status</th>"
                + "<th colspan=\"2\">Steps</th><th rowspan=\"2\">Tags</th></tr>"
                + "<tr><th>Covered</th>"
                + "<th>Not Covered</th></tr>";

        for (CucumberFeatureResult result : results) {
            for (CucumberScenarioResult element : result.getElements()) {
                String status = element.getStatus();
                if (!status.equalsIgnoreCase("undefined")) {
                    status = "passed";
                }
                Set<String> tags = new HashSet<String>();
                for (String tag : result.getAllTags(true)) {
                    tags.add(tag);
                }
                for (String tag : element.getAllTags()) {
                    if (!tags.contains(tag)) {
                        tags.add(tag);
                    }
                }
                reportContent += String.format(
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
        content = content.replaceAll("__FEATURE_DATA__", getFeatureData(results));
        content = content.replaceAll("__SCENARIO_DATA__", getScenarioData(results));
        return content;
    }

    public void executeCoverageReport() throws Exception {
        executeOverviewReport("coverage");
    }
}
