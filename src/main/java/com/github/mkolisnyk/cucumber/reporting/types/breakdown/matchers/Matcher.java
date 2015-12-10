package com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public interface Matcher {
    boolean isComplex();
    boolean matches(CucumberScenarioResult result, String expression);
    boolean matches(CucumberScenarioResult result, DataDimension[] filters);
}
