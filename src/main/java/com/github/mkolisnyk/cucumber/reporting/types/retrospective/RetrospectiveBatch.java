package com.github.mkolisnyk.cucumber.reporting.types.retrospective;

public class RetrospectiveBatch {
    private RetrospectiveModel[] models;

    public RetrospectiveBatch(RetrospectiveModel[] modelsValue) {
        super();
        this.models = modelsValue;
    }

    public RetrospectiveModel[] getModels() {
        return models;
    }

    public void setModels(RetrospectiveModel[] modelsValue) {
        this.models = modelsValue;
    }
}
