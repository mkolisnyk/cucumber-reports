package com.github.mkolisnyk.cucumber.reporting.types.breakdown.valuators;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberStepResult;

public class StepsValuator implements Valuator {

    @Override
    public BreakdownStats valuate(CucumberScenarioResult[] results,
            String expression) {
        BreakdownStats stats = new BreakdownStats();
        for (CucumberScenarioResult result : results) {
            for (CucumberStepResult step : result.getSteps()) {
                if (step.getName().equals(expression)
                        || step.getName().matches(expression)) {
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
