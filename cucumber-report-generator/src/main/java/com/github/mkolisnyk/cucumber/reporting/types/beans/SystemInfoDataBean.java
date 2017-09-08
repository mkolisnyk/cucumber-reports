package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.Map;
import java.util.Properties;

public class SystemInfoDataBean extends CommonDataBean {
    private Properties systemProperties;
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
