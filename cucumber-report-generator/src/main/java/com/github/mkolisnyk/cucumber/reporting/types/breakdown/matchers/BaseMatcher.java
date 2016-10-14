package com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DimensionValue;

public abstract class BaseMatcher implements Matcher {
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
            put(DimensionValue.NOT, new NotMatcher());
            put(DimensionValue.AND, new AndMatcher());
            put(DimensionValue.OR, new OrMatcher());
            put(DimensionValue.STEP_SEQUENCE, new StepSequenceMatcher());
            put(DimensionValue.ERROR_MESSAGE, new ErrorMessageMatcher());
            put(DimensionValue.FAILED_STEP, new FailedStepMatcher());
        }
    };
    protected boolean stringMatches(String expected, String expression) {
        return StringUtils.isNotEmpty(expected)
                && (expected.equals(expression) || expected.matches(expression));
    }
    public static Matcher create(DimensionValue value) {
        return DIMENSION_MATCHER_MAP.get(value);
    }
}
