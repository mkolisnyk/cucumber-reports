/**
 * .
 */
package com.github.mkolisnyk.cucumber.reporting.types.usage;

import java.util.ArrayList;
import java.util.List;

import com.cedarsoftware.util.io.JsonObject;

/**
 * @author Myk Kolisnyk
 *
 */
public class CucumberStepSource {
    private String source;
    private CucumberStep[] steps;
    public String getSource() {
        return source;
    }
    public CucumberStep[] getSteps() {
        return steps;
    }
    @SuppressWarnings("unchecked")
    public CucumberStepSource(JsonObject<String, Object> json) {
        this.source = (String) json.get("source");
        Object[] objs = (Object[]) json.get("steps");
        this.steps = new CucumberStep[objs.length];
        for (int i = 0; i < objs.length; i++) {
            this.steps[i] = new CucumberStep((JsonObject<String, Object>) objs[i]);
        }
    }
    public List<Double> getDurations() {
        List<Double> results = new ArrayList<Double>();
        for (CucumberStep step : steps) {
            for (CucumberStepDuration duration : step.getDurations()) {
                results.add(duration.getDuration());
            }
        }
        return results;
    }
}
