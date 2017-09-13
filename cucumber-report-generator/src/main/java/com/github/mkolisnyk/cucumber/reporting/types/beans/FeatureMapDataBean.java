package com.github.mkolisnyk.cucumber.reporting.types.beans;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

/**
 * Represents data structure for the <a href="http://mkolisnyk.github.io/cucumber-reports/feature-map-report">
 * Feature Map</a> report generation.
 * @author Mykola Kolisnyk
 */
public class FeatureMapDataBean extends CommonDataBean {
    /**
     * 3-dimensional array of scenarios. First 2 dimensions correspond to the table row and column.
     * The third dimension contains actual cell data where the list of scenarios matching search
     * criteria defined by the <b>table</b> field.
     */
    private CucumberScenarioResult[][][] scenarios;
    /**
     * The data structure containing Feature Map report table row and column data including headers
     * with their labels.
     */
    private BreakdownTable table;
    public BreakdownTable getTable() {
        return table;
    }
    public void setTable(BreakdownTable tableValue) {
        this.table = tableValue;
    }
    public CucumberScenarioResult[][][] getScenarios() {
        return scenarios;
    }
    public void setScenarios(CucumberScenarioResult[][][] scenariosValue) {
        this.scenarios = scenariosValue;
    }
}
