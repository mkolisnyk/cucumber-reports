package com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberStepResult;

public class StepParamMatcher extends SimpleMatcher {

    private boolean cellMatches(String[][] rows, String expression) {
        if (rows != null && rows.length > 0) {
            for (String[] row : rows) {
                if (row == null || row.length <= 0) {
                    continue;
                }
                for (int j = 0; j < row.length; j++) {
                    String cell = row[j];
                    if (this.stringMatches(cell, expression)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @Override
    public boolean matches(CucumberScenarioResult result, String expression) {
        for (CucumberStepResult step : result.getSteps()) {
            String docString = step.getDocString();
            if (this.stringMatches(docString, expression) || this.cellMatches(step.getRows(), expression)) {
                return true;
            }
        }
        return false;
    }
}
