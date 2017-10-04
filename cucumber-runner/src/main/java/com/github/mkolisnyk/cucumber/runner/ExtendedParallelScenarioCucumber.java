package com.github.mkolisnyk.cucumber.runner;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import com.cedarsoftware.util.io.JsonWriter;
import com.github.mkolisnyk.cucumber.reporting.CucumberCustomReport;
import com.github.mkolisnyk.cucumber.reporting.CucumberSplitFeature;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

public class ExtendedParallelScenarioCucumber extends Runner {

    private Class<?> clazz;
    private CucumberOptions cucumberOptions;
    private ExtendedCucumberOptions[] extendedCucumberOptions;
    
    public ExtendedParallelScenarioCucumber(Class<?> testClass) {
        super();
        this.clazz = testClass;
        this.cucumberOptions = testClass.getAnnotation(CucumberOptions.class);
        this.extendedCucumberOptions = testClass.getAnnotationsByType(ExtendedCucumberOptions.class);
    }
    @Override
    public Description getDescription() {
        return Description.createTestDescription(clazz, clazz.getCanonicalName());
    }
    public static Annotation setAttrValue(Annotation anno, Class<? extends Annotation> type, String attrName, Object newValue) throws Exception {
        InvocationHandler handler = new AnnotationInvocationHandler(anno, attrName, newValue);
        Annotation proxy = (Annotation) Proxy.newProxyInstance(anno.getClass().getClassLoader(), new Class[]{type}, handler);
        return proxy;
    }
    @Override
    public void run(RunNotifier notifier) {
        try {
            Annotation anno = (Annotation) cucumberOptions;
            CucumberOptions dryRunOptions = (CucumberOptions) ExtendedParallelScenarioCucumber.setAttrValue(
                    cucumberOptions, CucumberOptions.class, "dryRun", true);
            ExtendedCucumber cucumber = new ExtendedCucumber(this.clazz, dryRunOptions, extendedCucumberOptions, false);
            cucumber.run(notifier);
            String jsonFile = "";
            for (String plugin : cucumberOptions.plugin()) {
                if (plugin.startsWith("json:")) {
                    jsonFile = plugin.substring("json:".length());
                }
            }
            System.out.println("JSON file: " + jsonFile);
            CucumberSplitFeature report = new CucumberSplitFeature();
            String outputFolder = new File(jsonFile).getParentFile().getAbsolutePath() + File.separator + "features";
            report.setOutputDirectory(outputFolder);
            report.setSourceFile(jsonFile);
            report.execute(true);
            
            CucumberOptions parallelRunOptions = (CucumberOptions) ExtendedParallelScenarioCucumber.setAttrValue(
                    cucumberOptions, CucumberOptions.class, "features", new String[] {outputFolder});
            ExtendedParallelCucumber parallelRunner = new ExtendedParallelCucumber(clazz, parallelRunOptions, extendedCucumberOptions);
            parallelRunner.run(notifier);
            
            String[] paths = parallelRunner.getOutputJsonPaths(false);
            Map<String, CucumberFeatureResult> featureIdMap = new LinkedHashMap<String, CucumberFeatureResult>();
            for (String path : paths) {
                System.out.println("Processing file: " + path);
                CucumberFeatureResult[] features = report.readFileContent(path, false);
                for (CucumberFeatureResult feature : features) {
                    String tag = feature.getTags()[0].getName();
                    System.out.println("Getting tag: " + tag);
                    if (featureIdMap.containsKey(tag)) {
                        CucumberFeatureResult currentResult = featureIdMap.get(tag);
                        CucumberScenarioResult[] scenarios = currentResult.getElements();
                        scenarios = (CucumberScenarioResult[]) ArrayUtils.addAll(scenarios, feature.getElements());
                        currentResult.setElements(scenarios);
                        featureIdMap.put(tag, currentResult);
                    } else {
                        featureIdMap.put(tag, feature);
                    }
                }
            }
            List<CucumberFeatureResult> consolidatedFeatures = new ArrayList<CucumberFeatureResult>();
            for (Entry<String, CucumberFeatureResult> entry : featureIdMap.entrySet()) {
                consolidatedFeatures.add(entry.getValue());
            }
            String json = JsonWriter.objectToJson(consolidatedFeatures);
            FileUtils.writeStringToFile(new File("target/final.json"), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
