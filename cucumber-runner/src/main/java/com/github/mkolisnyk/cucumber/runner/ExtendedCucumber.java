package com.github.mkolisnyk.cucumber.runner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import com.github.mkolisnyk.cucumber.runner.runtime.BaseRuntimeOptionsFactory;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

import cucumber.api.CucumberOptions;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.junit.Assertions;
import cucumber.runtime.junit.JUnitOptions;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;

public class ExtendedCucumber extends ParentRunner<ExtendedFeatureRunner> {
    private JUnitReporter jUnitReporter;
    private final List<ExtendedFeatureRunner> children = new ArrayList<ExtendedFeatureRunner>();
    private final Runtime runtime;
    private final ExtendedRuntimeOptions[] extendedOptions;
    private Class clazzValue;
    private int retryCount = 0;
    private int threadsCount = 1;
    private boolean runPreDefined = true;

    public ExtendedCucumber(Class clazz) throws Exception {
        super(clazz);
        this.clazzValue = clazz;
        ClassLoader classLoader = clazz.getClassLoader();
        Assertions.assertNoCucumberAnnotatedMethods(clazz);

        RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
        RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();

        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        runtime = createRuntime(resourceLoader, classLoader, runtimeOptions);
        extendedOptions = ExtendedRuntimeOptions.init(clazz);
        init(runtimeOptions, classLoader, resourceLoader);
    }
    public ExtendedCucumber(
            Class clazz, CucumberOptions baseOptions,
            ExtendedCucumberOptions[] extendedOptionsValue, boolean runPreDefinedValue) throws Exception {
        super(clazz);
        this.clazzValue = clazz;
        this.runPreDefined = runPreDefinedValue;
        ClassLoader classLoader = clazz.getClassLoader();
        Assertions.assertNoCucumberAnnotatedMethods(clazz);
        BaseRuntimeOptionsFactory runtimeOptionsFactory = new BaseRuntimeOptionsFactory(clazz);
        RuntimeOptions runtimeOptions = runtimeOptionsFactory.create(baseOptions);

        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        runtime = createRuntime(resourceLoader, classLoader, runtimeOptions);
        extendedOptions = ExtendedRuntimeOptions.init(extendedOptionsValue);
        init(runtimeOptions, classLoader, resourceLoader);
    }
    private void init(RuntimeOptions runtimeOptions,
            ClassLoader classLoader, ResourceLoader resourceLoader) throws Exception {

        for (ExtendedRuntimeOptions option : extendedOptions) {
            retryCount = Math.max(retryCount, option.getRetryCount());
            threadsCount = Math.max(threadsCount, option.getThreadsCount());
        }

        final JUnitOptions junitOptions = new JUnitOptions(runtimeOptions.getJunitOptions());
        final List<CucumberFeature> cucumberFeatures = runtimeOptions.cucumberFeatures(resourceLoader);
        jUnitReporter = new JUnitReporter(
                runtimeOptions.reporter(classLoader),
                runtimeOptions.formatter(classLoader),
                runtimeOptions.isStrict(),
                junitOptions);
        Method[] retryMethods = this.getPredefinedMethods(RetryAcceptance.class);
        addChildren(cucumberFeatures, retryMethods);
    }
    protected Runtime createRuntime(ResourceLoader resourceLoader, ClassLoader classLoader,
                                    RuntimeOptions runtimeOptions) throws InitializationError, IOException {
        ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        return new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
    }

    @Override
    public List<ExtendedFeatureRunner> getChildren() {
        return children;
    }

    @Override
    protected Description describeChild(ExtendedFeatureRunner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(ExtendedFeatureRunner child, RunNotifier notifier) {
        child.run(notifier);
    }

    private Method[] getPredefinedMethods(Class annotation) {
        if (!annotation.isAnnotation()) {
            return new Method[] {};
        }
        Method[] filteredMethodList = new Method[] {};
        Method[] methodList = this.clazzValue.getMethods();
        for (Method method : methodList) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    filteredMethodList = (Method[]) ArrayUtils.add(filteredMethodList, method);
                }
            }
        }
        return filteredMethodList;
    }

    private void runPredefinedMethods(Class annotation) throws Exception {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methodList = getPredefinedMethods(annotation);
        for (Method method : methodList) {
            method.invoke(null);
        }
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            if (this.runPreDefined) {
                runPredefinedMethods(BeforeSuite.class);
            }
            runPredefinedMethods(BeforeSubSuite.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.run(notifier);
        try {
            if (this.runPreDefined) {
                runPredefinedMethods(AfterSuite.class);
            }
            runPredefinedMethods(AfterSubSuite.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        runtime.printSummary();
        jUnitReporter.done();
        jUnitReporter.close();
        for (ExtendedRuntimeOptions extendedOption : extendedOptions) {
            ReportRunner.run(extendedOption);
        }
    }

    private void addChildren(List<CucumberFeature> cucumberFeatures, Method[] retryMethods) throws InitializationError {
        for (CucumberFeature cucumberFeature : cucumberFeatures) {
            children.add(
                    new ExtendedFeatureRunner(cucumberFeature, runtime,
                            jUnitReporter, this.retryCount, retryMethods));
        }
    }
}
