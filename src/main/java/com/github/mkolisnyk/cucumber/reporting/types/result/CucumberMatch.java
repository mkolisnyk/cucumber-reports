package com.github.mkolisnyk.cucumber.reporting.types.result;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberMatch {
    private String location;

    public CucumberMatch(JsonObject<String, Object> json) {
        this.location = (String) json.get("location");
    }

    /**
     * @return the location
     */
    public final String getLocation() {
        return location;
    }

    /**
     * @param locationValue
     *            the location to set
     */
    public final void setLocation(String locationValue) {
        this.location = locationValue;
    }
}
