package com.github.mkolisnyk.cucumber.reporting;

import java.io.IOException;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class CucumberCoverageOverview extends CucumberResultsOverview {

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
        return String.format("['Passed', %d], ['Undefined', %d]", passed, undefined);
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

        return String.format("['Passed', %d], ['Undefined', %d]", passed, undefined);
    }

    @Override
    protected String generateFeatureOverview(CucumberFeatureResult[] results)
            throws IOException {
        String content = this.getReportBase();
        content = content.replaceAll("__TITLE__", "Test Coverage Overview");
        String reportContent = "";

        reportContent += "<h1>Features Status</h1><table><tr><th>Feature Name</th><th>Status</th>"
                + "<th>Covered</th><th>Not Covered</th></tr>";

        for (CucumberFeatureResult result : results) {
            String status = result.getStatus();
            if (!status.equalsIgnoreCase("undefined")) {
                status = "passed";
            }
            reportContent += String.format(
                    "<tr class=\"%s\"><td>%s</td><td>%s</td><td>%d</td><td>%d</td></tr>",
                    status,
                    result.getName(),
                    status,
                    result.getPassed() + result.getFailed() + result.getSkipped(),
                    result.getUndefined());
        }
        reportContent += "</table>";
        reportContent += "<h1>Scenario Status</h1><table>"
                + "<tr><th>Feature Name</th>"
                + "<th>Scenario</th>"
                + "<th>Status</th>"
                + "<th>Covered</th>"
                + "<th>Not Covered</th></tr>";

        for (CucumberFeatureResult result : results) {
            for (CucumberScenarioResult element : result.getElements()) {
                String status = element.getStatus();
                if (!status.equalsIgnoreCase("undefined")) {
                    status = "passed";
                }
                reportContent += String.format(
                        "<tr class=\"%s\">"
                        + "<td>%s</td><td>%s</td><td>%s</td>"
                        + "<td>%d</td><td>%d</td></tr>",
                        status,
                        result.getName(),
                        element.getName(),
                        status,
                        element.getPassed() + element.getFailed() + element.getSkipped(),
                        element.getUndefined());
            }
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
