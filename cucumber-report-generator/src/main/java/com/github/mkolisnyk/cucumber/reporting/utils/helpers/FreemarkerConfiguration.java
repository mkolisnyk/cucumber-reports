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
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateHashModel;

public final class FreemarkerConfiguration {
    private static Configuration config;
    private FreemarkerConfiguration() {
    }
    private static final Map<String, String> DEFAULT_RESOURCES = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("pie_chart", "/templates/default/pie_chart.ftlh");
            put("tables", "/templates/default/tables.ftlh");
            put("benchmark", "/templates/default/benchmark.ftlh");
            put("consolidated", "/templates/default/consolidated.ftlh");
            put("overview", "/templates/default/overview.ftlh");
            put("coverage", "/templates/default/coverage.ftlh");
            put("known_errors", "/templates/default/known_errors.ftlh");
            put("system_info", "/templates/default/system_info.ftlh");
            put("feature_overview", "/templates/default/feature_overview.ftlh");
            put("overview_chart", "/templates/default/overview_chart.ftlh");
            put("detailed", "/templates/default/detailed.ftlh");
            put("retrospective", "/templates/default/retrospective.ftlh");
            put("breakdown", "/templates/default/breakdown.ftlh");
            put("feature_map", "/templates/default/feature_map.ftlh");
            put("usage", "/templates/default/usage.ftlh");
        }
    };
    private static void loadDefaultConfig() throws Exception {
        loadConfig(DEFAULT_RESOURCES);
    }
    private static void loadConfig(Map<String, String> resourceMap) throws Exception {
        config = new Configuration(Configuration.VERSION_2_3_23);

        TemplateLoader[] loaders = new TemplateLoader[] {};
        for (Entry<String, String> resource : resourceMap.entrySet()) {
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
        config.setAPIBuiltinEnabled(true);
        config.setURLEscapingCharset("UTF-8");

        TemplateHashModel staticModels = BeansWrapper.getDefaultInstance().getStaticModels();
        config.setSharedVariable("statics", staticModels);
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
