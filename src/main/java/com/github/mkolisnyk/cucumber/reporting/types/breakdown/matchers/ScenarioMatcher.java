package com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class ScenarioMatcher extends SimpleMatcher {
    @Override
    public boolean matches(CucumberScenarioResult result, String expression) {
        String name = result.getName();
        return name.equals(expression) || name.matches(expression);
    }
}
