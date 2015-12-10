package com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers;

import java.util.HashMap;
import java.util.Map;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DimensionValue;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public abstract class SimpleMatcher implements Matcher {
    private static final Map<DimensionValue, Matcher> DIMENSION_MATCHER_MAP
        = new HashMap<DimensionValue, Matcher>() {
        private static final long serialVersionUID = 1L;
        {
            put(DimensionValue.FEATURE, new FeatureMatcher());
            put(DimensionValue.SCENARIO, new ScenarioMatcher());
            put(DimensionValue.TAG, new TagMatcher());
            put(DimensionValue.STEP, new StepMatcher());
            put(DimensionValue.CONTAINER, new ContainerMatcher());
            put(DimensionValue.STEP_PARAM, new StepParamMatcher());
        }
    };
    @Override
    public boolean isComplex() {
        return false;
    }

    public abstract boolean matches(CucumberScenarioResult result, String expression);

    @Override
    public boolean matches(CucumberScenarioResult result,
            DataDimension[] filters) {
        for (DataDimension filter : filters) {
            Matcher matcher = DIMENSION_MATCHER_MAP.get(filter.getDimensionValue());
            if (!matcher.matches(result, filter.getExpression())) {
                return false;
            }
        }
        return true;
    }

}
