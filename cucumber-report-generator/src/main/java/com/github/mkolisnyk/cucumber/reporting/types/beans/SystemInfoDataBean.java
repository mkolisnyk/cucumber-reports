package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.Map;
import java.util.Properties;

/**
 * Data structure which is used for the <a href="http://mkolisnyk.github.io/cucumber-reports/system-info">
 * System Info<a> report generation.
 * @author Mykola Kolisnyk
 */
public class SystemInfoDataBean extends CommonDataBean {
    /**
     * The collection of system properties.
     */
    private Properties systemProperties;
    /**
     * The map containing environment variables.
     */
    private Map<String, String> environmentVariables;
    public Properties getSystemProperties() {
        return systemProperties;
    }
    public void setSystemProperties(Properties properties) {
        this.systemProperties = properties;
    }
    public Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }
    public void setEnvironmentVariables(Map<String, String> environmentVariablesValue) {
        this.environmentVariables = environmentVariablesValue;
    }
}
