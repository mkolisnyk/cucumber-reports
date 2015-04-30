/**
 * 
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
    public void setName(String name) {
        this.name = name;
    }
    public CucumberAggregatedDuration getAggregatedDurations() {
        return aggregatedDurations;
    }
    public void setAggregatedDurations(
            CucumberAggregatedDuration aggregatedDurations) {
        this.aggregatedDurations = aggregatedDurations;
    }
    public CucumberStepDuration[] getDurations() {
        return durations;
    }
    public void setDurations(CucumberStepDuration[] durations) {
        this.durations = durations;
    }
    public CucumberStep(String name,
            CucumberAggregatedDuration aggregatedDurations,
            CucumberStepDuration[] durations) {
        super();
        this.name = name;
        this.aggregatedDurations = aggregatedDurations;
        this.durations = durations;
    }
    public CucumberStep(JsonObject<String, Object> json) {
        this.name = (String)json.get("name");
        this.aggregatedDurations = new CucumberAggregatedDuration((JsonObject<String, Object>)json.get("aggregatedDurations"));
        Object[] objs = (Object[])((JsonObject<String, Object>)json.get("durations")).get("@items");
        this.durations = new CucumberStepDuration[objs.length];
        
        for(int i=0;i<objs.length;i++){
            this.durations[i] = new CucumberStepDuration((JsonObject<String, Object>)objs[i]);
        }
    }    
}
