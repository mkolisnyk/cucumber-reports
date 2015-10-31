package com.github.mkolisnyk.cucumber.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.testng.IHookCallBack;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.TestNGCucumberRunner;


public class ExtendedTestNGRunner extends AbstractTestNGCucumberTests {
    private Class<?> clazz;
    private ExtendedRuntimeOptions extendedOptions;

    /* (non-Javadoc)
     * @see cucumber.api.testng.AbstractTestNGCucumberTests#run(org.testng.IHookCallBack, org.testng.ITestResult)
     */
    @Override
    public void run(IHookCallBack iHookCallBack,
            ITestResult iTestResult) {
        super.run(iHookCallBack, iTestResult);
    }

    private void runPredefinedMethods(Class<?> annotation) throws Exception {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methodList = this.clazz.getMethods();
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

    /* (non-Javadoc)
     * @see cucumber.api.testng.AbstractTestNGCucumberTests#run_cukes()
     */
    @Test(groups = "cucumber", description = "Runs Cucumber Features")
    public void runCukes() throws Exception {
        extendedOptions = new ExtendedRuntimeOptions(clazz);
        clazz = this.getClass();
        try {
            runPredefinedMethods(BeforeSuite.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new TestNGCucumberRunner(clazz).runCukes();
        try {
            runPredefinedMethods(AfterSuite.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (extendedOptions.isUsageReport()) {
            ReportRunner.runUsageReport(extendedOptions);
        }
        if (extendedOptions.isOverviewReport()) {
            ReportRunner.runOverviewReport(extendedOptions);
        }
        if (extendedOptions.isDetailedReport()) {
            ReportRunner.runDetailedReport(extendedOptions);
        }
        if (extendedOptions.isDetailedAggregatedReport()) {
            ReportRunner.runDetailedAggregatedReport(extendedOptions);
        }
        if (extendedOptions.isCoverageReport()) {
            ReportRunner.runCoverageReport(extendedOptions);
        }
    }

    @Override
    @Test(enabled = false)
    public void run_cukes() {
    }
}
