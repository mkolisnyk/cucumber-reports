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
    public String getDescription() {
        return description;
    }
    public DataDimension getFilter() {
        return filter;
    }
    public KnownErrorPriority getPriority() {
        return priority;
    }
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        KnownErrorsInfo other = (KnownErrorsInfo) obj;
        return this.toString().equals(other.toString());
    }

    @Override
    public String toString() {
        return "KnownErrorsInfo [title=" + title + ", description="
                + description + ", filter=" + filter + ", priority=" + priority
                + "]";
    }
}
