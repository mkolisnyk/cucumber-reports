package com.github.mkolisnyk.cucumber.reporting.utils.helpers;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public final class FreemarkerConfiguration {
    private static Configuration config;
    private FreemarkerConfiguration() {
    }
    private static void loadDefaultConfig() throws Exception {
        config = new Configuration(Configuration.VERSION_2_3_23);

        InputStream is = FreemarkerConfiguration.class.getResourceAsStream("/templates/default/benchmark.ftlh");
        String templateString = IOUtils.toString(is);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("benchmark", templateString);
        config.setTemplateLoader(stringLoader);
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setLogTemplateExceptions(false);
    }
    public static Configuration get(String location) throws Exception {
        if (config == null) {
            if (StringUtils.isBlank(location)) {
                loadDefaultConfig();
            } else {
                config = null;
            }
        }
        return config;
    }
}
