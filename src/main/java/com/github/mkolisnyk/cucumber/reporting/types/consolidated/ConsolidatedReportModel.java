package com.github.mkolisnyk.cucumber.reporting.types.consolidated;

public class ConsolidatedReportModel {
    private ConsolidatedItemInfo[] items;
    private String reportSuffix = "consolidated";
    private String title;
    private boolean useTableOfContents;
    public ConsolidatedReportModel(ConsolidatedItemInfo[] itemsValue,
            String reportSuffixValue,
            String titleValue,
            boolean useTableOfContentsValue) {
        super();
        this.items = itemsValue;
        this.title = titleValue;
        this.reportSuffix = reportSuffixValue;
        this.useTableOfContents = useTableOfContentsValue;
    }
    public ConsolidatedItemInfo[] getItems() {
        return items;
    }
    public void setItems(ConsolidatedItemInfo[] itemsValue) {
        this.items = itemsValue;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String titleValue) {
        this.title = titleValue;
    }
    public String getReportSuffix() {
        return reportSuffix;
    }
    public void setReportSuffix(String reportSuffixValue) {
        this.reportSuffix = reportSuffixValue;
    }
    public boolean isUseTableOfContents() {
        return useTableOfContents;
    }
    public void setUseTableOfContents(boolean useTableOfContentsValue) {
        this.useTableOfContents = useTableOfContentsValue;
    }
}
