package com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers;

import org.apache.commons.lang3.StringUtils;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberStepResult;

public class StepParamMatcher extends SimpleMatcher {

    @Override
    public boolean matches(CucumberScenarioResult result, String expression) {
        for (CucumberStepResult step : result.getSteps()) {
            String docString = step.getDocString();
            if (StringUtils.isNotEmpty(docString)
                    && (docString.equals(expression) || docString.matches(expression))) {
                return true;
            }
            String[][] rows = step.getRows();
            if (rows != null && rows.length > 0) {
                for (int i = 0; i < rows.length; i++) {
                    if (rows[i] == null || rows[i].length <= 0) {
                        continue;
                    }
                    for (int j = 0; j < rows[i].length; j++) {
                        String cell = rows[i][j];
                        if (StringUtils.isNotEmpty(cell)
                                && (docString.equals(cell) || docString.matches(cell))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
