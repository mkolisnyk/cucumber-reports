package com.github.mkolisnyk.cucumber.reporting.utils.helpers;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public final class FreemarkerConfiguration {
    private static Configuration config;
    private FreemarkerConfiguration() {
    }
    private static final Map<String, String> defaultResources = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("benchmark", "/templates/default/benchmark.ftlh");
            put("consolidated", "/templates/default/consolidated.ftlh");
            put("coverage", "/templates/default/coverage.ftlh");
            put("known_errors", "/templates/default/known_errors.ftlh");
            put("system_info", "/templates/default/system_info.ftlh");
            put("feature_overview", "/templates/default/feature_overview.ftlh");
        }
    };
    private static void loadDefaultConfig() throws Exception {
        config = new Configuration(Configuration.VERSION_2_3_23);

        TemplateLoader[] loaders = new TemplateLoader[] {};
        for (Entry<String, String> resource : defaultResources.entrySet()) {
            InputStream is = FreemarkerConfiguration.class.getResourceAsStream(resource.getValue());
            String templateString = IOUtils.toString(is);
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate(resource.getKey(), templateString);
            loaders = ArrayUtils.add(loaders, stringLoader);
        }
        MultiTemplateLoader multiLoader = new MultiTemplateLoader(loaders);
        
        config.setTemplateLoader(multiLoader);
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
