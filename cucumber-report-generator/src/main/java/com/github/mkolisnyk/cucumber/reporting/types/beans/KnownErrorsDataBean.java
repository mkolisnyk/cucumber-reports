package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.List;

import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsResult;

public class KnownErrorsDataBean extends CommonDataBean {
    private List<KnownErrorsResult> results;

    public List<KnownErrorsResult> getResults() {
        return results;
    }

    public void setResults(List<KnownErrorsResult> resultsValue) {
        this.results = resultsValue;
    }
}
