package com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public interface Matcher {
    boolean matches(CucumberScenarioResult result, String expression);
}
