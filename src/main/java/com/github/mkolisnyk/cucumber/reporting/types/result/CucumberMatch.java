package com.github.mkolisnyk.cucumber.reporting.types.result;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberMatch {
    private String location;

    public CucumberMatch(JsonObject<String, Object> json) {
        this.location = (String) json.get("location");
    }
}
