package com.github.mkolisnyk.cucumber.reporting.types.result;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberTagResults {

    public CucumberTagResults(JsonObject<String, Object> json) {
        this.name = (String) json.get("name");
        this.line = (Long) json.get("line");
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param nameValue
     *            the name to set
     */
    public final void setName(String nameValue) {
        this.name = nameValue;
    }

    /**
     * @return the line
     */
    public final Long getLine() {
        return line;
    }

    /**
     * @param lineValue
     *            the line to set
     */
    public final void setLine(Long lineValue) {
        this.line = lineValue;
    }

    private String name;
    private Long    line;
}
