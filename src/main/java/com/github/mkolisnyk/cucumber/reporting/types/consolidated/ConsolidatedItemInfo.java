package com.github.mkolisnyk.cucumber.reporting.types.consolidated;

public class ConsolidatedItemInfo {
    private String title;
    private String path;
    public ConsolidatedItemInfo() {
        this.title = "";
        this.path = "";
    }
    public ConsolidatedItemInfo(String titleValue, String pathValue) {
        super();
        this.title = titleValue;
        this.path = pathValue;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String titleValue) {
        this.title = titleValue;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String pathValue) {
        this.path = pathValue;
    }
}
