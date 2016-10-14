package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

import java.util.HashSet;
import java.util.Set;

public enum DimensionValue {
    TAG, SCENARIO, FEATURE, STEP, CONTAINER, STEP_PARAM, AND, OR, NOT, STEP_SEQUENCE, ERROR_MESSAGE, FAILED_STEP;
    public Set<DimensionValue> stepValues() {
        return new HashSet<DimensionValue>() {
            {
                add(STEP);
                add(STEP_PARAM);
                add(STEP_SEQUENCE);
                add(ERROR_MESSAGE);
                add(FAILED_STEP);
            }
        };
    }
    public boolean isStep() {
        return stepValues().contains(this);
    }
}
