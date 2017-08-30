package com.github.mkolisnyk.cucumber.reporting.types.beans;


public class CommonDataBean {
    private String title;
    private String pdfPageSize;
    private String refreshData = "";
    public String getTitle() {
        return title;
    }
    public void setTitle(String titleValue) {
        this.title = titleValue;
    }
    public String getPdfPageSize() {
        return pdfPageSize;
    }
    public void setPdfPageSize(String pdfPageSizeValue) {
        this.pdfPageSize = pdfPageSizeValue;
    }
    public String getRefreshData() {
        return refreshData;
    }
    public void setRefreshData(String refreshDataValue) {
        this.refreshData = refreshDataValue;
    }
}
