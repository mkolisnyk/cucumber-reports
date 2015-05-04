package com.github.mkolisnyk.cucumber.reporting.types.result;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberResult {

    private String status;

    public CucumberResult(JsonObject<String, Object> json) {
        this.status = (String) json.get("status");
    }

    /**
     * @return the status
     */
    public final String getStatus() {
        return status;
    }

    /**
     * @param statusValue
     *            the status to set
     */
    public final void setStatus(String statusValue) {
        this.status = statusValue;
    }
}
