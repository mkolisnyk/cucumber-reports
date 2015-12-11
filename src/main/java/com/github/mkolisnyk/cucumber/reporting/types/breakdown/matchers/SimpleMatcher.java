package com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public abstract class SimpleMatcher extends BaseMatcher {

    @Override
    public boolean isComplex() {
        return false;
    }

    public abstract boolean matches(CucumberScenarioResult result, String expression);

    @Override
    public boolean matches(CucumberScenarioResult result,
            DataDimension filter) {
        Matcher matcher = create(filter.getDimensionValue());
        if (!matcher.matches(result, filter.getExpression())) {
            return false;
        }
        return true;
    }

}
