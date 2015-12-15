package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

import java.util.HashSet;
import java.util.Set;

public enum DimensionValue {
    TAG, SCENARIO, FEATURE, STEP, CONTAINER, STEP_PARAM, AND, OR, NOT, STEP_SEQUENCE;
    public Set<DimensionValue> stepValues() {
        return new HashSet<DimensionValue>() {
            {
                add(STEP);
                add(STEP_PARAM);
                add(STEP_SEQUENCE);
            }
        };
    }
    public boolean isStep() {
        return stepValues().contains(this);
    }
}
