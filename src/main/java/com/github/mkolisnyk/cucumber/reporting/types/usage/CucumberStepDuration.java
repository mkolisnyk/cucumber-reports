/**
 * .
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
    public void setDuration(Double durationValue) {
        this.duration = durationValue;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String locationValue) {
        this.location = locationValue;
    }
    public CucumberStepDuration(Double durationValue, String locationValue) {
        super();
        this.duration = durationValue;
        this.location = locationValue;
    }

    public CucumberStepDuration(JsonObject<String, Object> json) {
        this.duration = (Double) json.get("duration");
        this.location = (String) json.get("location");
    }
}
