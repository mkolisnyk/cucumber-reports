package com.github.mkolisnyk.cucumber.reporting.types.breakdown.valuators;

import org.apache.commons.lang.StringUtils;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers.Matcher;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberStepResult;

public class StepsValuator implements Valuator {

    private boolean docstringMatches(String docstring, String expression) {
        if (StringUtils.isEmpty(docstring)) {
            return false;
        }
        return docstring.equals(expression) || docstring.matches(expression);
    }
    private boolean rowsMatch(String[][] rows, String expression) {
        if (rows == null) {
            return false;
        }
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length; j++) {
                if (rows[i][j].equals(expression) || rows[i][j].matches(expression)) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public BreakdownStats valuate(CucumberScenarioResult[] results,
            String expression, Matcher[] matchers) {
        BreakdownStats stats = new BreakdownStats();
        for (CucumberScenarioResult result : results) {
            /*boolean fits = false;
            for (Matcher matcher : matchers) {
                if (matcher.matches(result, expression)) {
                    fits = true;
                    break;
                }
            }
            if (!fits) {
                continue;
            }*/
            for (CucumberStepResult step : result.getSteps()) {
                if (step.getName().equals(expression)
                        || step.getName().matches(expression)
                        || docstringMatches(step.getDocString(), expression)
                        || rowsMatch(step.getRows(), expression)) {
                    String status = step.getResult().getStatus();
                    if (status.equalsIgnoreCase("passed")) {
                        stats.addPassed();
                    } else if (status.equalsIgnoreCase("failed")) {
                        stats.addFailed();
                    } else {
                        stats.addSkipped();
                    }
                }
            }
        }
        return stats;
    }

}
