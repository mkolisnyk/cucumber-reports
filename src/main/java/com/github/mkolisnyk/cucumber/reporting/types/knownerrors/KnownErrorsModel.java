package com.github.mkolisnyk.cucumber.reporting.types.knownerrors;

public class KnownErrorsModel {
    private KnownErrorsInfo[] errorDescriptions;
    private KnownErrorOrderBy orderBy;

    public KnownErrorsModel(KnownErrorsInfo[] errorDescriptionsValue) {
        super();
        this.errorDescriptions = errorDescriptionsValue;
        this.orderBy = KnownErrorOrderBy.NAME;
    }
    public KnownErrorsModel(KnownErrorsInfo[] errorDescriptionsValue,
            KnownErrorOrderBy orderByValue) {
        super();
        this.errorDescriptions = errorDescriptionsValue;
        this.orderBy = orderByValue;
    }
    public KnownErrorsInfo[] getErrorDescriptions() {
        return errorDescriptions;
    }
    public void setErrorDescriptions(KnownErrorsInfo[] errorDescriptionsValue) {
        this.errorDescriptions = errorDescriptionsValue;
    }
    public KnownErrorOrderBy getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(KnownErrorOrderBy orderByValue) {
        this.orderBy = orderByValue;
    }
}
