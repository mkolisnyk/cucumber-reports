package com.github.mkolisnyk.cucumber.runner.runtime;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cucumber.api.CucumberOptions;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;

public class BaseRuntimeOptionsFactory {
    private final Class clazz;
    private boolean featuresSpecified = false;
    private boolean glueSpecified = false;
    private boolean pluginSpecified = false;

    public BaseRuntimeOptionsFactory(Class clazzValue) {
        this.clazz = clazzValue;
    }

    public RuntimeOptions create(CucumberOptions options) {
        List<String> args = buildArgsFromOptions(options);
        return new RuntimeOptions(args);
    }

    private List<String> buildArgsFromOptions(CucumberOptions options) {
        List<String> args = new ArrayList<String>();
        if (options != null) {
            addDryRun(options, args);
            addMonochrome(options, args);
            addTags(options, args);
            addPlugins(options, args);
            addStrict(options, args);
            addName(options, args);
            addSnippets(options, args);
            addGlue(options, args);
            addFeatures(options, args);
        }
        addDefaultFeaturePathIfNoFeaturePathIsSpecified(args, clazz);
        addDefaultGlueIfNoGlueIsSpecified(args, clazz);
        addNullFormatIfNoPluginIsSpecified(args);
        return args;
    }
    private void addName(CucumberOptions options, List<String> args) {
        for (String name : options.name()) {
            args.add("--name");
            args.add(name);
        }
    }

    private void addSnippets(CucumberOptions options, List<String> args) {
        args.add("--snippets");
        args.add(options.snippets().toString());
    }

    private void addDryRun(CucumberOptions options, List<String> args) {
        if (options.dryRun()) {
            args.add("--dry-run");
        }
    }

    private void addMonochrome(CucumberOptions options, List<String> args) {
        if (options.monochrome() || runningInEnvironmentWithoutAnsiSupport()) {
            args.add("--monochrome");
        }
    }

    private void addTags(CucumberOptions options, List<String> args) {
        for (String tags : options.tags()) {
            args.add("--tags");
            args.add(tags);
        }
    }

    private void addPlugins(CucumberOptions options, List<String> args) {
        List<String> plugins = new ArrayList<String>();
        plugins.addAll(asList(options.plugin()));
        //plugins.addAll(asList(options.format()));
        for (String plugin : plugins) {
            args.add("--plugin");
            args.add(plugin);
            pluginSpecified = true;
        }
    }

    private void addNullFormatIfNoPluginIsSpecified(List<String> args) {
        if (!pluginSpecified) {
            args.add("--plugin");
            args.add("null");
        }
    }

    private void addFeatures(CucumberOptions options, List<String> args) {
        if (options != null && options.features().length != 0) {
            Collections.addAll(args, options.features());
            featuresSpecified = true;
        }
    }

    private void addDefaultFeaturePathIfNoFeaturePathIsSpecified(List<String> args, Class clazzValue) {
        if (!featuresSpecified) {
            args.add(MultiLoader.CLASSPATH_SCHEME + packagePath(clazzValue));
        }
    }

    private void addGlue(CucumberOptions options, List<String> args) {
        for (String glue : options.glue()) {
            args.add("--glue");
            args.add(glue);
            glueSpecified = true;
        }
    }

    private void addDefaultGlueIfNoGlueIsSpecified(List<String> args, Class clazzValue) {
        if (!glueSpecified) {
            args.add("--glue");
            args.add(MultiLoader.CLASSPATH_SCHEME + packagePath(clazzValue));
        }
    }


    private void addStrict(CucumberOptions options, List<String> args) {
        if (options.strict()) {
            args.add("--strict");
        }
    }

    static String packagePath(Class clazz) {
        return packagePath(packageName(clazz.getName()));
    }

    static String packagePath(String packageName) {
        return packageName.replace('.', '/');
    }

    static String packageName(String className) {
        return className.substring(0, Math.max(0, className.lastIndexOf(".")));
    }

    private boolean runningInEnvironmentWithoutAnsiSupport() {
        boolean intelliJidea = System.getProperty("idea.launcher.bin.path") != null;
        return intelliJidea;
    }
}
