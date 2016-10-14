package com.github.mkolisnyk.cucumber.reporting.types.result;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberTagResults {

    public CucumberTagResults(JsonObject<String, Object> json) {
        this.name = (String) json.get("name");
        //this.line = (Long) json.get("line");
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    private String name;
    //private Long    line;
}
