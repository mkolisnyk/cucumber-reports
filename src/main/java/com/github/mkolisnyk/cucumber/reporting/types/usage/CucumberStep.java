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
    /**
     * .
     */
    private String name;
    /**
     * .
     */
    private CucumberAggregatedDuration aggregatedDurations;
    /**
     * .
     */
    private CucumberStepDuration[] durations;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the aggregatedDurations
     */
    public CucumberAggregatedDuration getAggregatedDurations() {
        return aggregatedDurations;
    }
    /**
     * @param aggregatedDurations the aggregatedDurations to set
     */
    public void setAggregatedDurations(
            CucumberAggregatedDuration aggregatedDurations) {
        this.aggregatedDurations = aggregatedDurations;
    }
    /**
     * @return the durations
     */
    public CucumberStepDuration[] getDurations() {
        return durations;
    }
    /**
     * @param durations the durations to set
     */
    public void setDurations(CucumberStepDuration[] durations) {
        this.durations = durations;
    }
    /**
     * @param name
     * @param aggregatedDurations
     * @param durations
     */
    public CucumberStep(String name,
            CucumberAggregatedDuration aggregatedDurations,
            CucumberStepDuration[] durations) {
        super();
        this.name = name;
        this.aggregatedDurations = aggregatedDurations;
        this.durations = durations;
    }
    /**
     * @param jsonObject
     */
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
