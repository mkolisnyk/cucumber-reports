package com.github.mkolisnyk.cucumber.runner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.junit.Assertions;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;

public class ExtendedCucumber extends ParentRunner<ExtendedFeatureRunner> {
    private final JUnitReporter jUnitReporter;
    private final List<ExtendedFeatureRunner> children = new ArrayList<ExtendedFeatureRunner>();
    private final Runtime runtime;
    private final ExtendedRuntimeOptions[] extendedOptions;
    private Class clazzValue;
    private int retryCount = 0;

    public ExtendedCucumber(Class clazz) throws InitializationError, IOException {
        super(clazz);
        this.clazzValue = clazz;
        ClassLoader classLoader = clazz.getClassLoader();
        Assertions.assertNoCucumberAnnotatedMethods(clazz);

        RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
        RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();

        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        runtime = createRuntime(resourceLoader, classLoader, runtimeOptions);
        extendedOptions = ExtendedRuntimeOptions.init(clazz);
        for (ExtendedRuntimeOptions option : extendedOptions) {
            retryCount = Math.max(retryCount, option.getRetryCount());
        }

        final List<CucumberFeature> cucumberFeatures = runtimeOptions.cucumberFeatures(resourceLoader);
        jUnitReporter = new JUnitReporter(
                runtimeOptions.reporter(classLoader),
                runtimeOptions.formatter(classLoader),
                runtimeOptions.isStrict());
        addChildren(cucumberFeatures);
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

    private void runPredefinedMethods(Class annotation) throws Exception {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methodList = this.clazzValue.getMethods();
        for (Method method : methodList) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    method.invoke(null);
                    break;
                }
            }
        }
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            runPredefinedMethods(BeforeSuite.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.run(notifier);
        try {
            runPredefinedMethods(AfterSuite.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        runtime.printSummary();
        jUnitReporter.done();
        jUnitReporter.close();
        for (ExtendedRuntimeOptions extendedOption : extendedOptions) {
            if (extendedOption.isUsageReport()) {
                ReportRunner.runUsageReport(extendedOption);
            }
            if (extendedOption.isOverviewReport()) {
                ReportRunner.runOverviewReport(extendedOption);
            }
            if (extendedOption.isFeatureOverviewChart()) {
                ReportRunner.runFeatureOverviewChartReport(extendedOption);
            }
            if (extendedOption.isDetailedReport()) {
                ReportRunner.runDetailedReport(extendedOption);
            }
            if (extendedOption.isDetailedAggregatedReport()) {
                ReportRunner.runDetailedAggregatedReport(extendedOption);
            }
            if (extendedOption.isCoverageReport()) {
                ReportRunner.runCoverageReport(extendedOption);
            }
            if (extendedOption.isBreakdownReport()) {
                ReportRunner.runBreakdownReport(extendedOption);
            }
            if (extendedOption.isKnownErrorsReport()) {
                ReportRunner.runKnownErrorsReport(extendedOption);
            }
            if (extendedOption.isConsolidatedReport()) {
                ReportRunner.runConsolidatedReport(extendedOption);
            }
        }
    }

    private void addChildren(List<CucumberFeature> cucumberFeatures) throws InitializationError {
        for (CucumberFeature cucumberFeature : cucumberFeatures) {
            children.add(
                    new ExtendedFeatureRunner(cucumberFeature, runtime,
                            jUnitReporter, this.retryCount));
        }
    }
}
