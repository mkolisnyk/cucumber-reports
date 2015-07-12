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
    public void setSource(String sourceValue) {
        this.source = sourceValue;
    }
    public CucumberStep[] getSteps() {
        return steps;
    }
    public void setSteps(CucumberStep[] stepsValue) {
        this.steps = stepsValue;
    }
    public CucumberStepSource(String sourceValue, CucumberStep[] stepsValue) {
        super();
        this.source = sourceValue;
        this.steps = stepsValue;
    }
    @SuppressWarnings("unchecked")
    public CucumberStepSource(JsonObject<String, Object> json) {
        this.source = (String) json.get("source");
        Object[] objs = (Object[]) ((JsonObject<String, Object>) json.get("steps")).get("@items");
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
