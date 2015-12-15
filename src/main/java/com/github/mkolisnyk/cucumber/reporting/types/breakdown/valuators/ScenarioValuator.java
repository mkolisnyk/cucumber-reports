package com.github.mkolisnyk.cucumber.reporting.types.breakdown.valuators;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers.Matcher;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class ScenarioValuator implements Valuator {

    @Override
    public BreakdownStats valuate(CucumberScenarioResult[] results,
            String expression, Matcher[] matchers) {
        BreakdownStats stats = new BreakdownStats();
        for (CucumberScenarioResult result : results) {
            if (result.getStatus().equals("passed")) {
                stats.addPassed();
            } else if (result.getStatus().equals("failed")) {
                stats.addFailed();
            } else {
                stats.addSkipped();
            }
        }
        return stats;
    }
}
