package com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class StepSequenceMatcher extends ComplexMatcher {

    @Override
    public boolean matches(CucumberScenarioResult result, DataDimension filter) {
        int start = 0;
        if (filter.getSubElements() == null) {
            return false;
        }
        for (DataDimension filterItem : filter.getSubElements()) {
            boolean found = false;
            for (int i = start; i < result.getSteps().length; i++) {
                String expression = filterItem.getExpression();
                if (result.getSteps()[i].getName().equals(expression)
                        || result.getSteps()[i].getName().matches(expression)) {
                    start = i + 1;
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

}
