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
    /**
     * .
     */
    private Double average;
    /**
     * .
     */
    private Double median;    
    /**
     * @return the average
     */
    public Double getAverage() {
        return average;
    }
    /**
     * @param average the average to set
     */
    public void setAverage(Double average) {
        this.average = average;
    }
    /**
     * @return the median
     */
    public Double getMedian() {
        return median;
    }
    /**
     * @param median the median to set
     */
    public void setMedian(Double median) {
        this.median = median;
    }
    /**
     * @param average
     * @param median
     */
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
