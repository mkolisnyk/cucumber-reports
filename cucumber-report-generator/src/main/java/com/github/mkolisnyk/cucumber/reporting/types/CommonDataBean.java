package com.github.mkolisnyk.cucumber.reporting.types;

public class CommonDataBean {
    private String title;
    private String pdfPageSize;
    private String refreshData = "";
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPdfPageSize() {
        return pdfPageSize;
    }
    public void setPdfPageSize(String pdfPageSize) {
        this.pdfPageSize = pdfPageSize;
    }
    public String getRefreshData() {
        return refreshData;
    }
    public void setRefreshData(String refreshData) {
        this.refreshData = refreshData;
    }
}
