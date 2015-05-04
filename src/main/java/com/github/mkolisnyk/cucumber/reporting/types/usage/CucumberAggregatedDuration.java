/**
 * .
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
    public void setAverage(Double averageValue) {
        this.average = averageValue;
    }
    public Double getMedian() {
        return median;
    }
    public void setMedian(Double medianValue) {
        this.median = medianValue;
    }
    public CucumberAggregatedDuration(Double averageValue, Double medianValue) {
        super();
        this.average = averageValue;
        this.median = medianValue;
    }

    public CucumberAggregatedDuration(JsonObject<String, Object> json) {
        this.average = (Double) json.get("average");
        this.median = (Double) json.get("median");
    }
}
