package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConsolidatedDataBean extends CommonDataBean {

    private boolean useTableOfContents = false;
    private int columns = 1;
    private Map<String, String> contents = new LinkedHashMap<String, String>();
    public boolean isUseTableOfContents() {
        return useTableOfContents;
    }
    public void setUseTableOfContents(boolean useTableOfContents) {
        this.useTableOfContents = useTableOfContents;
    }
    public int getColumns() {
        return columns;
    }
    public void setColumns(int columns) {
        this.columns = columns;
    }
    public Map<String, String> getContents() {
        return contents;
    }
    public void setContents(Map<String, String> contents) {
        this.contents = contents;
    }
}
