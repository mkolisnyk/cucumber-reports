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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result
                + ((priority == null) ? 0 : priority.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
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
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (priority != other.priority) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        return true;
    }
}
