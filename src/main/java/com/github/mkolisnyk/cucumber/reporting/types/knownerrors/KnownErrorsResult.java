package com.github.mkolisnyk.cucumber.reporting.types.knownerrors;

public class KnownErrorsResult implements Comparable<KnownErrorsResult> {
    private KnownErrorsInfo info;
    private int frequency;
    private KnownErrorOrderBy orderBy;

    public KnownErrorsInfo getInfo() {
        return info;
    }
    public void setInfo(KnownErrorsInfo infoValue) {
        this.info = infoValue;
    }
    public int getFrequency() {
        return frequency;
    }
    public void setFrequency(int frequencyValue) {
        this.frequency = frequencyValue;
    }
    public KnownErrorOrderBy getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(KnownErrorOrderBy orderByValue) {
        this.orderBy = orderByValue;
    }
    @Override
    public int compareTo(KnownErrorsResult o) {
        switch (this.orderBy) {
        case PRIORITY:
            return o.getInfo().getPriority().compareTo(this.getInfo().getPriority());
        case NAME:
            return this.getInfo().getTitle().compareTo(o.getInfo().getTitle());
        case FREQUENCY:
        default:
            return (int) Math.signum(o.getFrequency() - this.getFrequency());
        }
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
        KnownErrorsResult other = (KnownErrorsResult) obj;
        return this.toString().equals(other.toString());
    }
    @Override
    public String toString() {
        return "KnownErrorsResult [info=" + info //+ ", frequency=" + frequency
                + ", orderBy=" + orderBy + "]";
    }
}
