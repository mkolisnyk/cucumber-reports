package com.github.mkolisnyk.cucumber.reporting.utils.helpers;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.cedarsoftware.util.io.JsonReader;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateHashModel;

public final class FreemarkerConfiguration {
    private static final String TEMPLATE_EXTENSION = ".ftlh";
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
            put("split_feature", "/templates/default/split_feature.ftlh");
        }
    };
    private static void loadConfig(Map<String, String> resourceMap) throws Exception {
        config = new Configuration(Configuration.VERSION_2_3_26);

        TemplateLoader[] loaders = new TemplateLoader[] {};
        for (Entry<String, String> resource : resourceMap.entrySet()) {
            String templateString = "";
            InputStream is = FreemarkerConfiguration.class.getResourceAsStream(resource.getValue());
            if (is != null) {
                templateString = IOUtils.toString(is);
            } else {
                templateString = FileUtils.readFileToString(new File(resource.getValue()), "UTF-8");
            }
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
        config.setLocale(Locale.US);

        BeansWrapperBuilder builder = new BeansWrapperBuilder(Configuration.VERSION_2_3_26);
        TemplateHashModel staticModels = builder.build().getStaticModels();
        config.setSharedVariable("statics", staticModels);
    }
    private static Map<String, String> loadTemplatesFromFile(File configFile) throws IOException {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.putAll(DEFAULT_RESOURCES);
        String content = FileUtils.readFileToString(configFile, "UTF-8");
        HashMap<String, String> loadedMap = (HashMap<String, String>) JsonReader.jsonToJava(content);
        for (Entry<String, String> entry : loadedMap.entrySet()) {
            File tmplFile = new File(entry.getValue());
            if (tmplFile.exists()) {
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }
        return resultMap;
    }
    private static File[] getTemplateFilesFromDirectory(File folder) {
        File[] subFiles = new File[] {};
        File[] resultFiles = new File[] {};
        File[] files = folder.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() || pathname.getName().endsWith(TEMPLATE_EXTENSION);
            }
        });
        for (File file : files) {
            if (file.isDirectory()) {
                subFiles = ArrayUtils.addAll(subFiles, getTemplateFilesFromDirectory(file));
            } else {
                resultFiles = ArrayUtils.add(resultFiles, file);
            }
        }
        return (File[]) ArrayUtils.addAll(resultFiles, subFiles);
    }
    private static Map<String, String> loadTemplatesFromFolder(File configFolder) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.putAll(DEFAULT_RESOURCES);
        File[] files = getTemplateFilesFromDirectory(configFolder);
        for (File file : files) {
            String baseFolder = configFolder.getAbsolutePath();
            String fileEntry = file.getAbsolutePath().substring(
                    baseFolder.length() + 1, file.getAbsolutePath().length() - TEMPLATE_EXTENSION.length());
            resultMap.put(fileEntry, file.getAbsolutePath());
        }
        return resultMap;
    }
    public static Map<String, String> getResourceMap(String location) throws Exception {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.putAll(DEFAULT_RESOURCES);
        if (StringUtils.isBlank(location)) {
            return resultMap;
        }
        File resLocation = new File(location);
        if (!resLocation.exists()) {
            return resultMap;
        }
        if (resLocation.isFile()) {
            resultMap = loadTemplatesFromFile(resLocation);
        } else {
            resultMap = loadTemplatesFromFolder(resLocation);
        }
        return resultMap;
    }
    public static Configuration get(String location) throws Exception {
        if (config == null) {
            Map<String, String> resourceMap = getResourceMap(location);
            loadConfig(resourceMap);
        }
        return config;
    }
    public static void flush() {
        config = null;
    }
}
