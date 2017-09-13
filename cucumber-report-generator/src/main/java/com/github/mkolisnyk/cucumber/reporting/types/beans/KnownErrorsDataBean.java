package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.List;

import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsResult;

/**
 * Data structure representing the data for
 * <a href="http://mkolisnyk.github.io/cucumber-reports/known-errors-report">Known Errors Report</a> generation.
 * @author Mykola Kolisnyk
 *
 */
public class KnownErrorsDataBean extends CommonDataBean {
    /**
     * The list of structures representing Known errors result table rows.
     */
    private List<KnownErrorsResult> results;

    public List<KnownErrorsResult> getResults() {
        return results;
    }

    public void setResults(List<KnownErrorsResult> resultsValue) {
        this.results = resultsValue;
    }
}
