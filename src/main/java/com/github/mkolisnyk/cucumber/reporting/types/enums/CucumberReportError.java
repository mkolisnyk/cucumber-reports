package com.github.mkolisnyk.cucumber.reporting.types.enums;

public enum CucumberReportError {
    NO_SOURCE_FILE("The source file isn't defined"),
    NON_EXISTING_SOURCE_FILE("The source file specified doesn't exist"),
    NO_OUTPUT_DIRECTORY("The output folder isn't defined"),
    NO_OUTPUT_NAME("The output file base name wasn't defined");
    private String value;

    CucumberReportError(String valueData) {
        this.value = valueData;
    }
    public String toString() {
        return this.value;
    }
}
