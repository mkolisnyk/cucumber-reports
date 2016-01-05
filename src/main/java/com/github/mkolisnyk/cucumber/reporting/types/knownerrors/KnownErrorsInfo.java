package com.github.mkolisnyk.cucumber.reporting.types.knownerrors;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;

public class KnownErrorsInfo {
    private String title;
    private String description;
    private DataDimension filter;
    private KnownErrorPriority priority;

    public KnownErrorsInfo(String titleValue, DataDimension filterValue) {
        this(titleValue, "", filterValue);
    }

    public KnownErrorsInfo(String titleValue, String descriptionValue,
            DataDimension filterValue) {
        this(titleValue, descriptionValue, filterValue, KnownErrorPriority.MEDIUM);
    }

    public KnownErrorsInfo(String titleValue, DataDimension filterValue,
            KnownErrorPriority priorityValue) {
        this(titleValue, "", filterValue, priorityValue);
    }

    public KnownErrorsInfo(String titleValue, String descriptionValue,
            DataDimension filterValue, KnownErrorPriority priorityValue) {
        super();
        this.title = titleValue;
        this.description = descriptionValue;
        this.filter = filterValue;
        this.priority = priorityValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titleValue) {
        this.title = titleValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descriptionValue) {
        this.description = descriptionValue;
    }

    public DataDimension getFilter() {
        return filter;
    }

    public void setFilter(DataDimension filterValue) {
        this.filter = filterValue;
    }

    public KnownErrorPriority getPriority() {
        return priority;
    }

    public void setPriority(KnownErrorPriority priorityValue) {
        this.priority = priorityValue;
    }
}
