package com.github.mkolisnyk.cucumber.runner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.reporting.MavenReportException;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import com.github.mkolisnyk.cucumber.reporting.CucumberCoverageOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;

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
    private final ExtendedRuntimeOptions extendedOptions;

    public ExtendedCucumber(Class clazz) throws InitializationError, IOException {
        super(clazz);
        ClassLoader classLoader = clazz.getClassLoader();
        Assertions.assertNoCucumberAnnotatedMethods(clazz);

        RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
        RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();

        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        runtime = createRuntime(resourceLoader, classLoader, runtimeOptions);
        extendedOptions = new ExtendedRuntimeOptions(clazz);

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

    private void runUsageReport() {
        CucumberUsageReporting report = new CucumberUsageReporting();
        report.setOutputDirectory(extendedOptions.getOutputFolder());
        report.setJsonUsageFile(extendedOptions.getJsonUsageReportPath());
        try {
            report.executeReport();
        } catch (MavenReportException e) {
            e.printStackTrace();
        }
    }

    private void runOverviewReport() {
        CucumberResultsOverview results = new CucumberResultsOverview();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        try {
            results.executeFeaturesOverviewReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runDetailedReport() {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        results.setScreenShotLocation(extendedOptions.getScreenShotLocation());
        results.setScreenShotWidth(extendedOptions.getScreenShotSize());
        try {
            results.executeDetailedResultsReport(
                    extendedOptions.isToPDF(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void runDetailedAggregatedReport() {
        CucumberDetailedResults results = new CucumberDetailedResults();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        results.setScreenShotLocation(extendedOptions.getScreenShotLocation());
        results.setScreenShotWidth(extendedOptions.getScreenShotSize());
        try {
            results.executeDetailedResultsReport(
                    extendedOptions.isToPDF(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void runCoverageReport() {
        CucumberCoverageOverview results = new CucumberCoverageOverview();
        results.setOutputDirectory(extendedOptions.getOutputFolder());
        results.setOutputName(extendedOptions.getReportPrefix());
        results.setSourceFile(extendedOptions.getJsonReportPath());
        results.setExcludeCoverageTags(extendedOptions.getExcludeCoverageTags());
        results.setIncludeCoverageTags(extendedOptions.getIncludeCoverageTags());
        try {
            results.executeCoverageReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
        jUnitReporter.done();
        jUnitReporter.close();
        runtime.printSummary();
        if (extendedOptions.isUsageReport()) {
            runUsageReport();
        }
        if (extendedOptions.isOverviewReport()) {
            runOverviewReport();
        }
        if (extendedOptions.isDetailedReport()) {
            runDetailedReport();
        }
        if (extendedOptions.isDetailedAggregatedReport()) {
            runDetailedAggregatedReport();
        }
        if (extendedOptions.isCoverageReport()) {
            runCoverageReport();
        }
    }

    private void addChildren(List<CucumberFeature> cucumberFeatures) throws InitializationError {
        for (CucumberFeature cucumberFeature : cucumberFeatures) {
            children.add(
                    new ExtendedFeatureRunner(cucumberFeature, runtime,
                            jUnitReporter, this.extendedOptions.getRetryCount()));
        }
    }
}
