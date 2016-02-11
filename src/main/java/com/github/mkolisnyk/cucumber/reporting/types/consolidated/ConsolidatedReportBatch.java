package com.github.mkolisnyk.cucumber.reporting.types.consolidated;

public class ConsolidatedReportBatch {
    private ConsolidatedReportModel[] models;

    public ConsolidatedReportBatch(ConsolidatedReportModel[] modelsValue) {
        super();
        this.models = modelsValue;
    }

    public ConsolidatedReportModel[] getModels() {
        return models;
    }
}
