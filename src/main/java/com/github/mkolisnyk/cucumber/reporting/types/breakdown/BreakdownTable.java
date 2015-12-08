package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

public class BreakdownTable {
    private DataDimension rows;
    private DataDimension cols;
    private BreakdownCellValue cell;
    public BreakdownTable() {
        this(DataDimension.allFeatures(), DataDimension.allScenarios(), BreakdownCellValue.STEPS);
    }
    public BreakdownTable(DataDimension rowsValue, DataDimension colsValue) {
        this(rowsValue, colsValue, BreakdownCellValue.STEPS);
    }
    public BreakdownTable(DataDimension rowsValue, DataDimension colsValue,
            BreakdownCellValue cellValue) {
        super();
        this.rows = rowsValue;
        this.cols = colsValue;
        this.cell = cellValue;
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
    
}
