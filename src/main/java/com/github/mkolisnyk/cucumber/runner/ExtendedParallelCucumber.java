package com.github.mkolisnyk.cucumber.runner;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;

import com.github.mkolisnyk.cucumber.reporting.utils.helpers.FolderUtils;
import com.github.mkolisnyk.cucumber.runner.parallel.CucumberRunnerThread;
import com.github.mkolisnyk.cucumber.runner.parallel.CucumberRunnerThreadPool;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;

public class ExtendedParallelCucumber extends ParentRunner<Runner> {
    private Class<?> clazz;
    private ExtendedCucumberOptions[] options;
    private CucumberOptions cucumberOption;
    private int threadsCount = 1;
    private ExtendedCucumber[] runners;

    public static int getThreadsCount(int threadsCountNumber, String threadsCountValue) {
        if (StringUtils.isBlank(threadsCountValue)) {
            return threadsCountNumber;
        }
        if (threadsCountValue.matches("(\\d+)")) {
            return Integer.valueOf(threadsCountValue);
        }
        if (System.getProperties().containsKey(threadsCountValue)
                && System.getProperty(threadsCountValue).matches("(\\d+)")) {
            return Integer.valueOf(System.getProperty(threadsCountValue));
        }
        return threadsCountNumber;
    }

    public ExtendedParallelCucumber(Class<?> clazzValue) throws Exception {
        super(clazzValue);
        this.clazz = clazzValue;
        this.options = clazz.getAnnotationsByType(ExtendedCucumberOptions.class);
        this.cucumberOption = clazz.getAnnotation(CucumberOptions.class);
        for (ExtendedCucumberOptions option : options) {
            threadsCount = Math.max(threadsCount,
                getThreadsCount(option.threadsCount(), option.threadsCountValue()));
        }
        this.runners = buildRunners();
    }
    private ExtendedCucumber[] buildRunners() throws Exception {
        CucumberOptions[] cucumberOptions = this.splitCucumberOption(this.cucumberOption);
        ExtendedCucumberOptions[][] extendedOptions
            = this.splitExtendedCucumberOptions(this.options, cucumberOptions.length);
        return generateTestClasses(cucumberOptions, extendedOptions);
    }

    public final ExtendedCucumber[] getRunners() {
        return runners;
    }
    private MemberValue getArrayMemberValue(Object object, Method field, ConstPool cp) throws Exception {
        if (field.getReturnType().getComponentType().equals(String.class)) {
            ArrayMemberValue array = new ArrayMemberValue(new StringMemberValue(cp), cp);
            String[] annoValues = (String[]) field.invoke(object);
            StringMemberValue[] values = new StringMemberValue[annoValues.length];
            for (int i = 0; i < annoValues.length; i++) {
                values[i] = new StringMemberValue(annoValues[i], cp);
            }
            array.setValue(values);
            return array;
        } else {
            ArrayMemberValue array = new ArrayMemberValue(new StringMemberValue(cp), cp);
            return array;
        }
    }
    public MemberValue getFieldMemberValue(Object object, Method field) throws Exception {
        ConstPool cp = new ConstPool(this.getClass().getCanonicalName());
        if (field.getReturnType().isArray()) {
            return getArrayMemberValue(object, field, cp);
        }
        if (field.getReturnType().equals(boolean.class)) {
            return new BooleanMemberValue((Boolean) field.invoke(object), cp);
        }
        if (field.getReturnType().equals(String.class)) {
            return new StringMemberValue((String) field.invoke(object), cp);
        }
        if (field.getReturnType().equals(int.class)) {
            return new IntegerMemberValue(cp, (int) field.invoke(object));
        }
        return null;
    }
    public String[] convertPluginPaths(String[] original, int index) {
        String[] result = new String[original.length];
        for (int i = 0; i < original.length; i++) {
            File path = new File(original[i].replaceFirst("^(usage|junit|json|html|pretty):", ""));
            String name = path.getName();
            String location = path.getParent();
            result[i] = location + "/" + index + "/" + name;
            if (original[i].matches("^(usage|junit|json|html|pretty):(.*)$")) {
                result[i] = original[i].replaceFirst("^(usage|junit|json|html|pretty):(.*)$", "$1:" + result[i]);
            }
        }
        return result;
    }
    public CucumberOptions[] splitCucumberOption(CucumberOptions option) throws Exception {
        CucumberOptions[] result = {};
        String[] featurePaths = option.features();
        String[] featureFiles = new String[] {};
        for (String featurePath : featurePaths) {
            File feature = new File(featurePath);
            if (feature.isDirectory()) {
                featureFiles = (String[]) ArrayUtils.addAll(
                        featureFiles, FolderUtils.getFilesByMask(feature.getAbsolutePath(), "(.*).feature"));
            } else {
                featureFiles = (String[]) ArrayUtils.add(featureFiles, feature.getAbsolutePath());
            }
        }
        int index = 0;
        result = new CucumberOptions[featureFiles.length];
        for (String file : featureFiles) {
            ConstPool cp = new ConstPool(ExtendedParallelCucumber.class.getCanonicalName());
            Annotation anno = new Annotation(CucumberOptions.class.getCanonicalName(), cp);
            for (Method field : CucumberOptions.class.getDeclaredMethods()) {
                String name = field.getName();
                if (name.equals("features")) {
                    ArrayMemberValue array = new ArrayMemberValue(new StringMemberValue(cp), cp);
                    array.setValue(new StringMemberValue[] {new StringMemberValue(file, cp)});
                    anno.addMemberValue(name, array);
                } else if (name.equals("plugin")) {
                    String[] plugin = convertPluginPaths(option.plugin(), index);
                    ArrayMemberValue array = new ArrayMemberValue(new StringMemberValue(cp), cp);
                    StringMemberValue[] values = new StringMemberValue[plugin.length];
                    for (int i = 0; i < plugin.length; i++) {
                        values[i] = new StringMemberValue(plugin[i], cp);
                    }
                    array.setValue(values);
                    anno.addMemberValue(name, array);
                } else if (name.equals("snippets")) {
                    EnumMemberValue value = new EnumMemberValue(cp);
                    value.setType(SnippetType.class.getCanonicalName());
                    value.setValue(SnippetType.UNDERSCORE.name());
                    anno.addMemberValue(name, value);
                } else {
                    MemberValue value = getFieldMemberValue(option, field);
                    if (value != null) {
                        anno.addMemberValue(name, value);
                    }
                }
            }
            result[index] = (CucumberOptions) anno.toAnnotationType(
                   this.getClass().getClassLoader(), ClassPool.getDefault());
            index++;
        }
        return result;
    }
    private ExtendedCucumberOptions generateExtendedOption(
            ExtendedCucumberOptions extendedOption, ConstPool cp, int i, int j)  throws Exception {
        Annotation anno = new Annotation(ExtendedCucumberOptions.class.getCanonicalName(), cp);
        for (Method field : ExtendedCucumberOptions.class.getDeclaredMethods()) {
            String name = field.getName();
            if (name.equals("outputFolder")) {
                anno.addMemberValue(name,
                    new StringMemberValue(extendedOption.outputFolder() + "/" + i + "_" + j, cp));
            } else if (name.equals("jsonReport") || name.equals("jsonUsageReport")) {
                String newName = this.convertPluginPaths(
                    new String[] {(String) field.invoke(extendedOption)}, i)[0];
                anno.addMemberValue(name,
                        new StringMemberValue(newName, cp));
            } else if (name.equals("jsonReports") || name.equals("jsonUsageReports")) {
                String[] reports = convertPluginPaths((String[]) field.invoke(extendedOption), i);
                ArrayMemberValue array = new ArrayMemberValue(new StringMemberValue(cp), cp);
                StringMemberValue[] values = new StringMemberValue[reports.length];
                for (int k = 0; k < reports.length; k++) {
                    values[k] = new StringMemberValue(reports[k], cp);
                }
                array.setValue(values);
                anno.addMemberValue(name, array);
            } else {
                MemberValue value = getFieldMemberValue(extendedOption, field);
                if (value != null) {
                    anno.addMemberValue(name, getFieldMemberValue(extendedOption, field));
                }
            }
        }
        return (ExtendedCucumberOptions) anno.toAnnotationType(
                this.getClass().getClassLoader(), ClassPool.getDefault());
    }
    public ExtendedCucumberOptions[][] splitExtendedCucumberOptions(
            ExtendedCucumberOptions[] extendedOptions,
            int suitesCount) throws Exception {
        ExtendedCucumberOptions[][] result = new ExtendedCucumberOptions[suitesCount][extendedOptions.length];
        for (int i = 0; i < suitesCount; i++) {
            ConstPool cp = new ConstPool(ExtendedParallelCucumber.class.getCanonicalName());
            for (int j = 0; j < extendedOptions.length; j++) {
                result[i][j] = generateExtendedOption(extendedOptions[j], cp, i, j);
            }

        }
        return result;
    }
    public ExtendedCucumber[] generateTestClasses(CucumberOptions[] cucumberOptions,
            ExtendedCucumberOptions[][] extendedOptions) throws Exception {
        ExtendedCucumber[] classes = new ExtendedCucumber[cucumberOptions.length];
        for (int i = 0; i < cucumberOptions.length; i++) {
            classes[i] = new ExtendedCucumber(this.clazz, cucumberOptions[i], extendedOptions[i], false);
        }
        return classes;
    }
    public String[] getOutputJsonPaths(boolean usage) {
        String[] results = {};
        String basePath = "";
        String keyword = "json";
        if (usage) {
            keyword = "usage";
        }
        for (String plugin : this.cucumberOption.plugin()) {
            if (plugin.startsWith(keyword)) {
                basePath = plugin.split(":")[1];
            }
        }
        if (StringUtils.isBlank(basePath)) {
            return null;
        }
        results = new String[this.runners.length];
        for (int i = 0; i < this.runners.length; i++) {
            String folder = new File(basePath).getParent();
            String name = new File(basePath).getName();
            results[i] = folder + "/" + i + "/" + name;
        }
        for (int i = 0; i < results.length; i++) {
            if (!(new File(results[i]).exists())) {
                results = (String[]) ArrayUtils.remove(results, i);
                i--;
            }
        }
        return results;
    }
    public void runReports() {
        ExtendedRuntimeOptions[] runtimeOptions = new ExtendedRuntimeOptions[this.options.length];
        for (int i = 0; i < runtimeOptions.length; i++) {
            runtimeOptions[i] = new ExtendedRuntimeOptions(this.options[i]);
            runtimeOptions[i].setJsonReportPaths(getOutputJsonPaths(false));
            runtimeOptions[i].setJsonUsageReportPaths(getOutputJsonPaths(true));
        }
        for (ExtendedRuntimeOptions option : runtimeOptions) {
            ReportRunner.run(option);
        }
    }
    @Override
    public Description getDescription() {
        return Description.createSuiteDescription(getClass());
    }

    private void runPredefinedMethods(Class annotation) throws Exception {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methodList = this.clazz.getMethods();
        for (Method method : methodList) {
            java.lang.annotation.Annotation[] annotations = method.getAnnotations();
            for (java.lang.annotation.Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    method.invoke(null);
                    break;
                }
            }
        }
    }

    @Override
    public void run(RunNotifier notifier) {
        CucumberRunnerThreadPool.setCapacity(this.threadsCount);
        try {
            runPredefinedMethods(BeforeSuite.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.run(notifier);
        try {
            CucumberRunnerThreadPool.get().waitEmpty();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            runPredefinedMethods(AfterSuite.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        runReports();
    }
    @Override
    public int testCount() {
        return this.getRunners().length;
    }
    @Override
    protected Description describeChild(Runner runner) {
        // TODO Auto-generated method stub
        return runner.getDescription();
    }
    @Override
    protected void runChild(Runner runner, RunNotifier notifier) {
        ExtendedCucumber cucumber = (ExtendedCucumber) runner;
        if (cucumber.getChildren().size() <= 0) {
            System.out.println("Nothing to run!!!");
            return;
        }
        Thread thread = new Thread(new CucumberRunnerThread(cucumber, notifier));
        try {
            CucumberRunnerThreadPool.get().push(thread);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected List<Runner> getChildren() {
        List<Runner> children = new ArrayList<Runner>();
        for (ExtendedCucumber runner : this.getRunners()) {
            children.add(runner);
        }
        return children;
    }

}
