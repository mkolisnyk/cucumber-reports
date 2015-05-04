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
    public void setName(String nameValue) {
        this.name = nameValue;
    }
    public CucumberAggregatedDuration getAggregatedDurations() {
        return aggregatedDurations;
    }
    public void setAggregatedDurations(
            CucumberAggregatedDuration aggregatedDurationsValue) {
        this.aggregatedDurations = aggregatedDurationsValue;
    }
    public CucumberStepDuration[] getDurations() {
        return durations;
    }
    public void setDurations(CucumberStepDuration[] durationsValue) {
        this.durations = durationsValue;
    }
    public CucumberStep(String nameValue,
            CucumberAggregatedDuration aggregatedDurationsValue,
            CucumberStepDuration[] durationsValue) {
        super();
        this.name = nameValue;
        this.aggregatedDurations = aggregatedDurationsValue;
        this.durations = durationsValue;
    }
    @SuppressWarnings("unchecked")
    public CucumberStep(JsonObject<String, Object> json) {
        this.name = (String) json.get("name");
        this.aggregatedDurations = new CucumberAggregatedDuration(
                (JsonObject<String, Object>) json.get("aggregatedDurations"));
        Object[] objs = (Object[]) ((JsonObject<String, Object>) json.get("durations")).get("@items");
        this.durations = new CucumberStepDuration[objs.length];

        for (int i = 0; i < objs.length; i++) {
            this.durations[i] = new CucumberStepDuration((JsonObject<String, Object>) objs[i]);
        }
    }
}
