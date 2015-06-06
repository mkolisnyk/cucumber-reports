package com.github.mkolisnyk.cucumber.reporting.types.result;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberResult {

    private String status;
    private String errorMessage;

    public CucumberResult(JsonObject<String, Object> json) {
        this.status = (String) json.get("status");
        this.errorMessage = (String) json.get("error_message");
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

    /**
     * @return the errorMessage
     */
    public final String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessageValue the errorMessage to set
     */
    public final void setErrorMessage(String errorMessageValue) {
        this.errorMessage = errorMessageValue;
    }
}
