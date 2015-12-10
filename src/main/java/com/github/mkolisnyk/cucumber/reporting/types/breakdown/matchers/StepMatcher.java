package com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberStepResult;

public class StepMatcher extends SimpleMatcher {
    @Override
    public boolean matches(CucumberScenarioResult result, String expression) {
        for (CucumberStepResult step : result.getSteps()) {
            if (step.getName().equals(expression) || step.getName().matches(expression)) {
                return true;
            }
        }
        return false;
    }
}
