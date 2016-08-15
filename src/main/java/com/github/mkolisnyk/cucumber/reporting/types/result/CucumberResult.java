package com.github.mkolisnyk.cucumber.reporting.types.result;

import java.util.Locale;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberResult {

    private String status;
    private String errorMessage;
    private Long duration;

    public CucumberResult(JsonObject<String, Object> json) {
        this.status = (String) json.get("status");
        this.errorMessage = (String) json.get("error_message");
        this.setDuration((Long) json.get("duration"));
    }

    /**
     * @return the status
     */
    public final String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the errorMessage
     */
    public final String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @return the duration
     */
    public final Long getDuration() {
        return duration;
    }

    /**
     * @param durationValue the duration to set
     */
    public final void setDuration(Long durationValue) {
        if (durationValue == null) {
            this.duration = 0L;
        } else {
            this.duration = durationValue;
        }
    }
    public final String getDurationTimeString(String format) {
        final int nanosecondsInMillisecond = 1000000;
        final double millesecondsInSecond = 1000.f;
        return String.format(Locale.US, "%.2fs",
                (double) (this.getDuration() / nanosecondsInMillisecond) / millesecondsInSecond);
    }
}

