package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;

/**
 * Represents data structure which is passed as the data bean while generating
 * <a href="http://mkolisnyk.github.io/cucumber-reports/breakdown-report">Breakdown Report</a>
 * @author Mykola Kolisnyk
 */
public class BreakdownDataBean extends CommonDataBean {
    /**
     * Contains the matrix of result statistics. Each cell corresponds to the breakdown
     * table cell.
     */
    private BreakdownStats[][] stats;
    /**
     * Contains the structure of the breakdown table row and column headers.
     */
    private BreakdownTable table;
    public BreakdownStats[][] getStats() {
        return stats;
    }
    public void setStats(BreakdownStats[][] statsValue) {
        this.stats = statsValue;
    }
    public BreakdownTable getTable() {
        return table;
    }
    public void setTable(BreakdownTable tableValue) {
        this.table = tableValue;
    }
}
