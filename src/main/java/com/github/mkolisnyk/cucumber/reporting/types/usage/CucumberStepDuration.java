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
    public Double getDuration() {
        return duration;
    }
    public void setDuration(Double duration) {
        this.duration = duration;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
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
