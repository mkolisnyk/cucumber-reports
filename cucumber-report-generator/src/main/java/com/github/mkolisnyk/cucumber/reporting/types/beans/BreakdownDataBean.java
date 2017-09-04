package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;

public class BreakdownDataBean extends CommonDataBean {
    private BreakdownStats[][] stats;
    private BreakdownTable table;
    public BreakdownStats[][] getStats() {
        return stats;
    }
    public void setStats(BreakdownStats[][] stats) {
        this.stats = stats;
    }
    public BreakdownTable getTable() {
        return table;
    }
    public void setTable(BreakdownTable table) {
        this.table = table;
    }
}
