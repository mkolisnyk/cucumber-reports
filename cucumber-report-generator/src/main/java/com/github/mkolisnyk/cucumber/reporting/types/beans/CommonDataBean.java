package com.github.mkolisnyk.cucumber.reporting.types.beans;

/**
 * Represents common part of all data beans provided for reports generation.
 * All other data beans used for each specific report generation are inherited from this class.
 * @author Mykola Kolisnyk
 */
public class CommonDataBean {
    /**
     * Contains the report title.
     */
    private String title;
    /**
     * Populates the field responsible for PDF page size. The value is similar as <b>pdfPageSize</b>
     * <a href="http://mkolisnyk.github.io/cucumber-reports/pdf-export#additional-generation-options">
     * PDF export additional generation options</a>. Values can be defined based on
     * <a href="http://www.w3.org/TR/css3-page/#page-size">CSS3 Specification</a>.
     */
    private String pdfPageSize;
    /**
     * Some of the reports may contain redirect to some other file after the timeout.
     * This string contains HTML code which performs this redirect.
     */
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
