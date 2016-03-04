package com.github.mkolisnyk.cucumber.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.TestNGCucumberRunner;


public class ExtendedTestNGRunner extends AbstractTestNGCucumberTests {
    private Class<?> clazz;
    private ExtendedRuntimeOptions[] extendedOptions;

    private void runPredefinedMethods(Class<?> annotation) throws Exception {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methodList = this.clazz.getMethods();
        for (Method method : methodList) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    method.invoke(this);
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
        this.clazz = this.getClass();
        extendedOptions = ExtendedRuntimeOptions.init(clazz);
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
        for (ExtendedRuntimeOptions extendedOption : extendedOptions) {
            ReportRunner.run(extendedOption);
        }
    }
}
