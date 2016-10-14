package com.github.mkolisnyk.cucumber.reporting.types.consolidated;

public class ConsolidatedReportModel {
    private ConsolidatedItemInfo[] items;
    private String reportSuffix = "consolidated";
    private String title;
    private boolean useTableOfContents;
    private int cols = 1;
    public ConsolidatedReportModel(ConsolidatedItemInfo[] itemsValue,
            String reportSuffixValue,
            String titleValue,
            boolean useTableOfContentsValue) {
        this(itemsValue, reportSuffixValue, titleValue, useTableOfContentsValue, 1);
    }
    public ConsolidatedReportModel(ConsolidatedItemInfo[] itemsValue,
            String reportSuffixValue,
            String titleValue,
            boolean useTableOfContentsValue,
            int columns) {
        super();
        this.items = itemsValue;
        this.title = titleValue;
        this.reportSuffix = reportSuffixValue;
        this.useTableOfContents = useTableOfContentsValue;
        this.cols = columns;
    }
    public ConsolidatedItemInfo[] getItems() {
        return items;
    }
    public String getTitle() {
        return title;
    }
    public String getReportSuffix() {
        return reportSuffix;
    }
    public boolean isUseTableOfContents() {
        return useTableOfContents;
    }
    public int getCols() {
        if (cols < 1) {
            cols = 1;
        }
        return cols;
    }
}
