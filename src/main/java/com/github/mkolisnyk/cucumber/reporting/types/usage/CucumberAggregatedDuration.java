/**
 * 
 */
package com.github.mkolisnyk.cucumber.reporting.types.usage;

import com.cedarsoftware.util.io.JsonObject;

/**
 * @author Myk Kolisnyk
 *
 */
public class CucumberAggregatedDuration {
    private Double average;
    private Double median;    
    public Double getAverage() {
        return average;
    }
    public void setAverage(Double average) {
        this.average = average;
    }
    public Double getMedian() {
        return median;
    }
    public void setMedian(Double median) {
        this.median = median;
    }
    public CucumberAggregatedDuration(Double average, Double median) {
        super();
        this.average = average;
        this.median = median;
    }
    
    public CucumberAggregatedDuration(JsonObject<String, Object> json){
        this.average = (Double)json.get("average");
        this.median = (Double)json.get("median");
    }
}
