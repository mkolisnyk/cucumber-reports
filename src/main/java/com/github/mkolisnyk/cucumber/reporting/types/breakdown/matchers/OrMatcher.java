package com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class OrMatcher extends ComplexMatcher {

    @Override
    public boolean matches(CucumberScenarioResult result, DataDimension filter) {
        for (DataDimension item : filter.getSubElements()) {
            Matcher matcher = create(item.getDimensionValue());
            if (matcher.matches(result, item)) {
                return true;
            }
        }
        return false;
    }
}
