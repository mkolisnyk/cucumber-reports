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
import org.junit.runners.model.Statement;

import com.github.mkolisnyk.cucumber.runner.runtime.BaseRuntimeOptionsFactory;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

import cucumber.api.CucumberOptions;
import cucumber.api.event.TestRunFinished;
import cucumber.api.formatter.Formatter;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.ExtendedRuntime;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.junit.Assertions;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.junit.JUnitOptions;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;

public class ExtendedCucumber extends ParentRunner<FeatureRunner> {
    private final JUnitReporter jUnitReporter;
    private final List<FeatureRunner> children = new ArrayList<FeatureRunner>();
    private final Runtime runtime;
    private final Formatter formatter;
    private Class<?> clazzValue;
    private boolean runPreDefined = true;
    private ExtendedRuntimeOptions[] extendedOptions;

    public ExtendedCucumber(Class clazz) throws Exception {
        super(clazz);
        this.clazzValue = clazz;
        ClassLoader classLoader = clazz.getClassLoader();
        Assertions.assertNoCucumberAnnotatedMethods(clazz);

        RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
        RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();

        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        runtime = createRuntime(resourceLoader, classLoader, runtimeOptions);
        formatter = runtimeOptions.formatter(classLoader);
        final JUnitOptions junitOptions = new JUnitOptions(runtimeOptions.getJunitOptions());
        final List<CucumberFeature> cucumberFeatures = runtimeOptions.cucumberFeatures(resourceLoader, runtime.getEventBus());
        jUnitReporter = new JUnitReporter(runtime.getEventBus(), runtimeOptions.isStrict(), junitOptions);
        extendedOptions = ExtendedRuntimeOptions.init(clazz);
        addChildren(cucumberFeatures);
    }
    public ExtendedCucumber(
            Class clazz, CucumberOptions baseOptions,
            ExtendedCucumberOptions[] extendedOptionsValue, boolean runPreDefinedValue) throws Exception {
        super(clazz);
        this.clazzValue = clazz;
        ClassLoader classLoader = clazz.getClassLoader();
        Assertions.assertNoCucumberAnnotatedMethods(clazz);
        BaseRuntimeOptionsFactory runtimeOptionsFactory = new BaseRuntimeOptionsFactory(clazz);
        RuntimeOptions runtimeOptions = runtimeOptionsFactory.create(baseOptions);

        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        runtime = createRuntime(resourceLoader, classLoader, runtimeOptions);
        final JUnitOptions junitOptions = new JUnitOptions(runtimeOptions.getJunitOptions());
        jUnitReporter = new JUnitReporter(runtime.getEventBus(), runtimeOptions.isStrict(), junitOptions);
        formatter = runtimeOptions.formatter(classLoader);
        extendedOptions = ExtendedRuntimeOptions.init(extendedOptionsValue);
        runPreDefined = runPreDefinedValue;
        init(runtimeOptions, classLoader, resourceLoader);
    }
    private void init(RuntimeOptions runtimeOptions,
            ClassLoader classLoader, ResourceLoader resourceLoader) throws Exception {

        /*for (ExtendedRuntimeOptions option : extendedOptions) {
            retryCount = Math.max(retryCount, option.getRetryCount());
            threadsCount = Math.max(threadsCount, option.getThreadsCount());
        }*/

        final JUnitOptions junitOptions = new JUnitOptions(runtimeOptions.getJunitOptions());
        /*final List<CucumberFeature> cucumberFeatures = runtimeOptions.cucumberFeatures(resourceLoader);
        jUnitReporter = new JUnitReporter(
                runtimeOptions.stepDefinitionReporter(classLoader),
                runtimeOptions.formatter(classLoader),
                runtimeOptions.isStrict(),
                junitOptions);
        Method[] retryMethods = this.getPredefinedMethods(RetryAcceptance.class);
        addChildren(cucumberFeatures, retryMethods);*/
    }
    protected Runtime createRuntime(ResourceLoader resourceLoader, ClassLoader classLoader,
                                    RuntimeOptions runtimeOptions) throws InitializationError, IOException {
        ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        //return new ExtendedRuntime(resourceLoader, classFinder, classLoader, runtimeOptions);
        return new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
    }
    @Override
    public List<FeatureRunner> getChildren() {
        return children;
    }

    @Override
    protected Description describeChild(FeatureRunner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(FeatureRunner child, RunNotifier notifier) {
        child.run(notifier);
    }

    @Override
    protected Statement childrenInvoker(RunNotifier notifier) {
        final Statement features = super.childrenInvoker(notifier);
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                features.evaluate();
                runtime.getEventBus().send(new TestRunFinished(runtime.getEventBus().getTime()));
                //runtime.printSummary();
            }
        };
    }

    private void addChildren(List<CucumberFeature> cucumberFeatures) throws InitializationError {
        for (CucumberFeature cucumberFeature : cucumberFeatures) {
            FeatureRunner featureRunner = new FeatureRunner(cucumberFeature, runtime, jUnitReporter);
            if (!featureRunner.isEmpty()) {
                children.add(featureRunner);
            }
        }
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
        //runtime.printSummary();
        //jUnitReporter.done();
        //jUnitReporter.close();
        for (ExtendedRuntimeOptions extendedOption : extendedOptions) {
            ReportRunner.run(extendedOption);
        }
    }
}
