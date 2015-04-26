/**
 * 
 */
package com.github.mkolisnyk.cucumber.reporting.types.usage;

import com.cedarsoftware.util.io.JsonObject;

/**
 * @author Myk Kolisnyk
 *
 */
public class CucumberStepDuration {
    private Double duration;
    private String location;
    /**
     * @return the duration
     */
    public Double getDuration() {
        return duration;
    }
    /**
     * @param duration the duration to set
     */
    public void setDuration(Double duration) {
        this.duration = duration;
    }
    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }
    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * @param duration
     * @param location
     */
    public CucumberStepDuration(Double duration, String location) {
        super();
        this.duration = duration;
        this.location = location;
    }
    
    public CucumberStepDuration(JsonObject<String, Object> json){
        this.duration = (Double)json.get("duration");
        this.location = (String)json.get("location");
    }
}
