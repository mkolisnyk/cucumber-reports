/**
 * .
 */
package com.github.mkolisnyk.cucumber.reporting.types.usage;

import com.cedarsoftware.util.io.JsonObject;

/**
 * @author Myk Kolisnyk
 *
 */
public class CucumberStep {
    private String name;
    private CucumberAggregatedDuration aggregatedDurations;
    private CucumberStepDuration[] durations;
    public String getName() {
        return name;
    }
    public CucumberAggregatedDuration getAggregatedDurations() {
        return aggregatedDurations;
    }
    public CucumberStepDuration[] getDurations() {
        return durations;
    }
    @SuppressWarnings("unchecked")
    public CucumberStep(JsonObject<String, Object> json) {
        this.name = (String) json.get("name");
        this.aggregatedDurations = new CucumberAggregatedDuration(
                (JsonObject<String, Object>) json.get("aggregatedDurations"));
        Object[] objs = (Object[]) json.get("durations");
        this.durations = new CucumberStepDuration[objs.length];

        for (int i = 0; i < objs.length; i++) {
            this.durations[i] = new CucumberStepDuration((JsonObject<String, Object>) objs[i]);
        }
    }
}
