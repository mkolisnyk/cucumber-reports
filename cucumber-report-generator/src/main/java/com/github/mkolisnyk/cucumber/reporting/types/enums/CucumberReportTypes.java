package com.github.mkolisnyk.cucumber.reporting.types.enums;

public enum CucumberReportTypes {
    BREAKDOWN_REPORT("Breakdown Report"),
    BENCHMARK_REPORT("Benchmark Report"),
    CHARTS_REPORT("Charts Report"),
    CONSOLIDATED_REPORT("Consolidated Report"),
    COVERAGE_OVERVIEW("Coverage Overview"),
    CUSTOM_REPORT("Custom Report"),
    DETAILED_REPORT("Detailed Results Report"),
    FEATURE_MAP_REPORT("Feature Map Report"),
    FEATURE_OVERVIEW("Feature Overview Report"),
    KNOWN_ERRORS("Known Errors Report"),
    RESULTS_OVERVIEW("Results Overview Report"),
    RETROSPECTIVE_OVERVIEW("Retrospective Overview Report"),
    SYSTEM_INFO("System Information Report"),
    USAGE("Usage Report");
    private String value;

    CucumberReportTypes(String valueData) {
        this.value = valueData;
    }
    public String toString() {
        return this.value;
    }
}
