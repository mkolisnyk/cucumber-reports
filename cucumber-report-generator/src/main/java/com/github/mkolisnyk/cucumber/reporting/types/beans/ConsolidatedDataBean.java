package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contains data structure which is passed as the data bean durine generation
 * of <a href="http://mkolisnyk.github.io/cucumber-reports/consolidated-report">Consolidated Report</a>.
 * @author Mykola Kolisnyk
 */
public class ConsolidatedDataBean extends CommonDataBean {

    /**
     * Flag indicating whether table of contents should be generated.
     */
    private boolean useTableOfContents = false;
    /**
     * Defines how many subsequent reports should appear in the same row of the report.
     */
    private int columns = 1;
    /**
     * Map containing the report text content. The key of the map is the heading of each specific report.
     */
    private Map<String, String> contents = new LinkedHashMap<>();
    public boolean isUseTableOfContents() {
        return useTableOfContents;
    }
    public void setUseTableOfContents(boolean useTableOfContentsValue) {
        this.useTableOfContents = useTableOfContentsValue;
    }
    public int getColumns() {
        return columns;
    }
    public void setColumns(int columnsValue) {
        this.columns = columnsValue;
    }
    public Map<String, String> getContents() {
        return contents;
    }
    public void setContents(Map<String, String> contentsValue) {
        this.contents = contentsValue;
    }
}
