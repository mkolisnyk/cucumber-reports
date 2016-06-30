package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.codehaus.plexus.util.StringUtils;
import org.testng.Assert;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberStepResult;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberFeatureMapReport extends CucumberBreakdownReport {

    public CucumberFeatureMapReport() {
        super();
    }
    public CucumberFeatureMapReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }
    private String drawScenario(CucumberScenarioResult scenario) {
        String output = String.format("<table><tr><td>%s</td></tr><tr><td><table>", scenario.getDescription());
        for (CucumberStepResult step : scenario.getSteps()) {
            output = output.concat(String.format("<tr><td><b>%s</b> %s</td></tr>",
                    step.getKeyword(),
                    step.getName()));
            if (StringUtils.isNotBlank(step.getDocString())) {
                output = output.concat(String.format("<tr><td><i>%s</i></td></tr>",
                        step.getDocString()));
            }
            if (step.getRows() != null) {
                output += String.format(
                        Locale.US,
                        "<tr><td style=\"padding-left:20px\"><table>",
                        step.getResult().getStatus());
                for (int i = 0; i < step.getRows().length; i++) {
                    output += "<tr>";
                    for (int j = 0; j < step.getRows()[i].length; j++) {
                        output += String.format(Locale.US,
                                "<td>%s</td>", StringEscapeUtils.escapeHtml(step.getRows()[i][j]));
                    }
                    output += "</tr>";
                }
                output += "</table></td></tr>";
            }
        }
        return output + "</table></td></tr></table>";
    }
    private Map<String, CucumberScenarioResult[]> splitScenariosByFeatures(CucumberScenarioResult[] scenarios) {
        Map<String, CucumberScenarioResult[]> result = new HashMap<String, CucumberScenarioResult[]>();
        for (CucumberScenarioResult scenario : scenarios) {
            String featureName = scenario.getFeature().getName();
            if (result.containsKey(featureName)) {
                result.put(featureName, ArrayUtils.add(result.get(featureName), scenario));
            } else {
                result.put(featureName, new CucumberScenarioResult[] {scenario});
            }
        }
        return result;
    }
    private String drawCell(CucumberScenarioResult[] scenarios) {
        String output = "<td><ul>";
        int index = 0;
        Map<String, CucumberScenarioResult[]> resultsMap = splitScenariosByFeatures(scenarios);
        for (String featureName : resultsMap.keySet()) {
            output = output.concat(String.format("<li> <b>Feature:</b> %s <ul>", featureName));
            for (CucumberScenarioResult scenario : resultsMap.get(featureName)) {
                output = output.concat(String.format("<li> <a onclick=\"toggle('scenario%d')\"><b>Scenario:</b> %s</a>",
                        index,
                        scenario.getName()));
                output = output.concat(String.format("<div id=\"scenario%d\" style=\"display:none\">%s</div></li>",
                        index,
                        drawScenario(scenario)));
                index++;
            }
            output = output.concat("</ul></li>");
        }
        output += "</ul></td>";
        return output;
    }

    @Override
    public void executeReport(BreakdownReportInfo info, BreakdownTable table, boolean toPDF) throws Exception {
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + info.getReportSuffix() + ".html");
        FileUtils.writeStringToFile(outFile, generateBreakdownReport(features, info, table)
                .replaceAll("\"hoverTable\"", "\"_hoverTable\""));
        if (toPDF) {
            this.exportToPDF(outFile, info.getReportSuffix());
        }
    }

    @Override
    protected String generateBody(BreakdownTable table,
            CucumberFeatureResult[] features) throws Exception {
        CucumberScenarioResult[] scenarios = new CucumberScenarioResult[] {};
        for (int j = 0; j < features.length; j++) {
            CucumberScenarioResult[] elements = features[j].getElements();
            for (int i = 0; i < elements.length; i++) {
                elements[i].setFeature(features[j]);
            }
            scenarios = ArrayUtils.addAll(scenarios, elements);
        }
        CucumberScenarioResult[][][] results = table.valuateScenarios(scenarios);
        String rowHeadings = generateRowHeading(table);
        String[] headingRows = rowHeadings.split("</tr>");
        Assert.assertEquals(headingRows.length - 1, results.length);
        String content = "";
        for (int i = 0; i < results.length; i++) {
            String row = headingRows[i];
            for (int j = 0; j < results[i].length; j++) {
                row = row.concat(drawCell(results[i][j]));
            }
            row = row.concat("</tr>");
            content = content.concat(row);
        }
        return content;
    }
//updatedContent = updatedContent.replaceAll("\"hoverTable\"", "\"_hoverTable\"");
    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.FEATURE_MAP_REPORT;
    }
}
