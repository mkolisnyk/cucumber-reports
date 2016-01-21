package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers.BaseMatcher;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers.Matcher;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.valuators.ScenarioValuator;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.valuators.StepsValuator;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.valuators.Valuator;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class BreakdownTable {
    private static final Map<BreakdownCellValue, Valuator> VALUATORS_MAP
        = new HashMap<BreakdownCellValue, Valuator>() {
            private static final long serialVersionUID = 1L;

        {
            put(BreakdownCellValue.FEATURES, null);
            put(BreakdownCellValue.SCENARIOS, new ScenarioValuator());
            put(BreakdownCellValue.STEPS, new StepsValuator());
        }
    };
    private DataDimension rows;
    private DataDimension cols;
    private BreakdownCellValue cell;
    private BreakdownCellDisplayType displayType;
    public BreakdownTable() {
        this(DataDimension.allFeatures(), DataDimension.allScenarios(), BreakdownCellValue.STEPS);
    }
    public BreakdownTable(BreakdownCellDisplayType displayTypeValue) {
        this(DataDimension.allFeatures(), DataDimension.allScenarios(), BreakdownCellValue.STEPS, displayTypeValue);
    }
    public BreakdownTable(DataDimension rowsValue, DataDimension colsValue) {
        this(rowsValue, colsValue, BreakdownCellValue.STEPS);
    }
    public BreakdownTable(DataDimension rowsValue, DataDimension colsValue,
            BreakdownCellValue cellValue) {
        this(rowsValue, colsValue, cellValue, BreakdownCellDisplayType.BARS_ONLY);
    }
    public BreakdownTable(DataDimension rowsValue, DataDimension colsValue,
            BreakdownCellDisplayType displayTypeValue) {
        this(rowsValue, colsValue, BreakdownCellValue.STEPS, displayTypeValue);
    }
    public BreakdownTable(DataDimension rowsValue, DataDimension colsValue,
            BreakdownCellValue cellValue, BreakdownCellDisplayType displayTypeValue) {
        super();
        this.rows = rowsValue;
        this.cols = colsValue;
        this.cell = cellValue;
        this.displayType = displayTypeValue;
    }
    public DataDimension getRows() {
        return rows;
    }
    public void setRows(DataDimension rowsValue) {
        this.rows = rowsValue;
    }
    public DataDimension getCols() {
        return cols;
    }
    public void setCols(DataDimension colsValue) {
        this.cols = colsValue;
    }
    public BreakdownCellValue getCell() {
        return cell;
    }
    public void setCell(BreakdownCellValue cellValue) {
        this.cell = cellValue;
    }
    public BreakdownCellDisplayType getDisplayType() {
        return displayType;
    }
    public void setDisplayType(BreakdownCellDisplayType displayType) {
        this.displayType = displayType;
    }
    public CucumberScenarioResult[] filter(CucumberScenarioResult[] array, DataDimension[] filters) {
        CucumberScenarioResult[] result = {};
        for (CucumberScenarioResult item : array) {
            boolean matches = true;
            for (DataDimension filter : filters) {
                Matcher matcher = BaseMatcher.create(filter.getDimensionValue());
                if (!matcher.matches(item, filter)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                result = ArrayUtils.add(result, item);
            }
        }
        return result;
    }
    public BreakdownStats valuateCell(CucumberScenarioResult[] array, DataDimension[] filters, Matcher[] matchers) {
        BreakdownStats stats = new BreakdownStats();
        Valuator valuator = VALUATORS_MAP.get(this.getCell());
        if (filters != null && filters.length > 0) {
            for (DataDimension filter : filters) {
                stats.add(valuator.valuate(array, filter.getExpression(), matchers));
            }
        } else {
            stats.add(valuator.valuate(array, "(.*)", matchers));
        }
        return stats;
    }
    public BreakdownStats[][] valuate(CucumberScenarioResult[] array) {
        DataDimension[][] rowData = this.getRows().expand();
        DataDimension[][] colData = this.getCols().expand();
        BreakdownStats[][] stats = new BreakdownStats[rowData.length][colData.length];
        for (int i = 0; i < rowData.length; i++) {
            for (int j = 0; j < colData.length; j++) {
                CucumberScenarioResult[] filteredData = this.filter(array, rowData[i]);
                filteredData = this.filter(filteredData, colData[j]);
                DataDimension[] stepFilters = {};
                Matcher[] matchers = {};
                if (colData[j][colData[j].length - 1].getDimensionValue().isStep()) {
                    stepFilters = ArrayUtils.add(stepFilters, colData[j][colData[j].length - 1]);
                    //matchers = ArrayUtils.add(matchers,
                    //        BaseMatcher.create(colData[j][colData[j].length - 1].getDimensionValue()));
                }
                if (stepFilters.length <= 0 && rowData[i][rowData[i].length - 1].getDimensionValue().isStep()) {
                    stepFilters = ArrayUtils.add(stepFilters, rowData[i][rowData[i].length - 1]);
                    //matchers = ArrayUtils.add(matchers,
                    //        BaseMatcher.create(rowData[i][rowData[i].length - 1].getDimensionValue()));
                }
                stats[i][j] = this.valuateCell(filteredData, stepFilters, matchers);
            }
        }
        return stats;
    }
}
