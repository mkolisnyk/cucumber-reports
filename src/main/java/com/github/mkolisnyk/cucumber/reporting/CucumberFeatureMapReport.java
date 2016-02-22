package com.github.mkolisnyk.cucumber.reporting;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.Assert;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class CucumberFeatureMapReport extends CucumberBreakdownReport {

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
        Map<String, CucumberScenarioResult[]> resultsMap = splitScenariosByFeatures(scenarios);
        /*for (CucumberScenarioResult scenario : scenarios) {
            output = output.concat(String.format("<li> %s", scenario.getName()));
        }*/
        for (String featureName : resultsMap.keySet()) {
            output = output.concat(String.format("<li> %s <ul>", featureName));
            for (CucumberScenarioResult scenario : resultsMap.get(featureName)) {
                output = output.concat(String.format("<li> %s", scenario.getName()));
            }
            output = output.concat("</ul>");
        }
        output += "</ul></td>";
        return output;
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
        //BreakdownStats[][] results = table.valuate(scenarios);
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

}
