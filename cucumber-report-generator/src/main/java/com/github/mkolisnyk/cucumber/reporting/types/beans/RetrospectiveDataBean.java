package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.retrospective.RetrospectiveModel;

public class RetrospectiveDataBean extends CommonDataBean {
    private RetrospectiveModel model;
    private BreakdownStats[] stats;
    public RetrospectiveModel getModel() {
        return model;
    }
    public void setModel(RetrospectiveModel modelValue) {
        this.model = modelValue;
    }
    public BreakdownStats[] getStats() {
        return stats;
    }
    public void setStats(BreakdownStats[] statsValue) {
        this.stats = statsValue;
    }
}
