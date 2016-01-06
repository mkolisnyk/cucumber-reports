package com.github.mkolisnyk.cucumber.reporting.types.knownerrors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers.BaseMatcher;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers.Matcher;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class KnownErrorsResultSet {
    private List<KnownErrorsResult> results;
    public KnownErrorsResultSet() {
        results = new ArrayList<KnownErrorsResult>();
    }
    public List<KnownErrorsResult> getResults() {
        Collections.sort(results);
        return results;
    }
    public void addResult(KnownErrorsResult result) {
        if (results.contains(result)) {
            int index = results.indexOf(result);
            int frequency = results.get(index).getFrequency() + 1;
            results.get(index).setFrequency(frequency);
        } else {
            result.setFrequency(1);
            results.add(result);
        }
    }
    public void valuate(CucumberScenarioResult[] scenarios, KnownErrorsModel model) {
        for (KnownErrorsInfo info : model.getErrorDescriptions()) {
            Matcher matcher = BaseMatcher.create(info.getFilter().getDimensionValue());
            for (CucumberScenarioResult scenario : scenarios) {
                if (matcher.matches(scenario, info.getFilter())) {
                    KnownErrorsResult result = new KnownErrorsResult();
                    result.setFrequency(1);
                    result.setInfo(info);
                    result.setOrderBy(model.getOrderBy());
                    this.addResult(result);
                }
            }
        }
    }
}
