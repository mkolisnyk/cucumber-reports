package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.retrospective.RetrospectiveModel;

public class RetrospectiveDataBean extends CommonDataBean {
    private RetrospectiveModel model;
    private BreakdownStats[] stats;
    public RetrospectiveModel getModel() {
        return model;
    }
    public void setModel(RetrospectiveModel model) {
        this.model = model;
    }
    public BreakdownStats[] getStats() {
        return stats;
    }
    public void setStats(BreakdownStats[] stats) {
        this.stats = stats;
    }
}
