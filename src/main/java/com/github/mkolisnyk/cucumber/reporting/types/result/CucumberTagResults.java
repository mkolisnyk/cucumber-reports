package com.github.mkolisnyk.cucumber.reporting.types.result;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberTagResults {

    public CucumberTagResults(JsonObject<String, Object> jsonObject) {
        // TODO Auto-generated constructor stub
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
    public final int getLine() {
        return line;
    }

    /**
     * @param lineValue
     *            the line to set
     */
    public final void setLine(int lineValue) {
        this.line = lineValue;
    }

    private String name;
    private int    line;
}
