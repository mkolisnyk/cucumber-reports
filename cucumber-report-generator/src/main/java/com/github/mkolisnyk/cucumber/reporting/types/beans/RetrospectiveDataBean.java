package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.retrospective.RetrospectiveModel;

public class RetrospectiveDataBean extends CommonDataBean {
    private RetrospectiveModel model;
    private BreakdownStats[] stats;
}
