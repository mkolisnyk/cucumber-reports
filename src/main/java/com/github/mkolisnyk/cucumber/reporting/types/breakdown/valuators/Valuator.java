package com.github.mkolisnyk.cucumber.reporting.types.breakdown.valuators;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public interface Valuator {
    BreakdownStats valuate(CucumberScenarioResult[] results, String expression);
}
