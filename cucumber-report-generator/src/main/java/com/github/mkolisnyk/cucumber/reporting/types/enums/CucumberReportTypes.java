package com.github.mkolisnyk.cucumber.reporting.types.enums;

import java.util.HashMap;

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

    private static HashMap<CucumberReportTypes, String> reportSuffixes = new HashMap<CucumberReportTypes, String>() {
        private static final long serialVersionUID = 1L;

        {
            put(BREAKDOWN_REPORT, "breakdown");
            put(BENCHMARK_REPORT, "benchmark");
            put(CHARTS_REPORT, "charts-report");
            put(CONSOLIDATED_REPORT, "");
            put(COVERAGE_OVERVIEW, "coverage");
            put(CUSTOM_REPORT, "");
            put(DETAILED_REPORT, "test-results");
            put(FEATURE_MAP_REPORT, "");
            put(FEATURE_OVERVIEW, "feature-overview-chart");
            put(KNOWN_ERRORS, "known-errors");
            put(RESULTS_OVERVIEW, "feature-overview");
            put(RETROSPECTIVE_OVERVIEW, "");
            put(SYSTEM_INFO, "system-info");
            put(USAGE, "usage");
        }
    };
    private static HashMap<CucumberReportTypes, String> templateNames = new HashMap<CucumberReportTypes, String>() {
        private static final long serialVersionUID = 1L;

        {
            put(BREAKDOWN_REPORT, "breakdown");
            put(BENCHMARK_REPORT, "benchmark");
            put(CHARTS_REPORT, "overview_chart");
            put(CONSOLIDATED_REPORT, "consolidated");
            put(COVERAGE_OVERVIEW, "coverage");
            put(CUSTOM_REPORT, "");
            put(DETAILED_REPORT, "detailed");
            put(FEATURE_MAP_REPORT, "feature_map");
            put(FEATURE_OVERVIEW, "feature_overview");
            put(KNOWN_ERRORS, "known_errors");
            put(RESULTS_OVERVIEW, "overview");
            put(RETROSPECTIVE_OVERVIEW, "retrospective");
            put(SYSTEM_INFO, "system_info");
            put(USAGE, "usage");
        }
    };

    CucumberReportTypes(String valueData) {
        this.value = valueData;
    }
    public String toString() {
        return this.value;
    }
    public String suffix() {
        return reportSuffixes.get(this);
    }
    public String template() {
        return templateNames.get(this);
    }
}
