package com.github.mkolisnyk.cucumber.runner;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import org.apache.commons.lang.ArrayUtils;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import cucumber.api.CucumberOptions;

public class ExtendedParallelCucumber extends Runner {
    private Class<?> clazz;
    private ExtendedCucumberOptions[] options;
    private CucumberOptions cucumberOption;

    public ExtendedParallelCucumber(Class<?> clazzValue) {
        super();
        this.clazz = clazzValue;
        this.options = clazz.getAnnotationsByType(ExtendedCucumberOptions.class);
        this.cucumberOption = clazz.getAnnotation(CucumberOptions.class);
    }
    private String[] getFileNames(String rootFolder) throws Exception {
        String[] fileNames = {};
        for (File file : (new File(rootFolder)).listFiles()) {
            if (file.isDirectory()) {
                fileNames = (String[]) ArrayUtils.addAll(fileNames, getFileNames(file.getAbsolutePath()));
            } else {
                fileNames = (String[]) ArrayUtils.add(fileNames, file.getAbsolutePath());
            }
        }
        return fileNames;
    }
    private String[] getFilesByMask(String startFolder, String mask) throws Exception {
        String[] result = {};
        String[] input = getFileNames(startFolder);
        for (String fileName : input) {
            if (fileName.matches(mask)) {
                result = (String[]) ArrayUtils.add(result, fileName);
            }
        }
        return result;
    }

    public MemberValue getFieldMemberValue(Object object, Method field) throws Exception {
        System.out.println("Field: " + field.getName() + "; Type: " + field.getReturnType().getCanonicalName());
        ConstPool cp = new ConstPool(this.getClass().getCanonicalName());
        if (field.getReturnType().isArray()) {
            ArrayMemberValue array = new ArrayMemberValue(new StringMemberValue(cp), cp);
            String[] annoValues = (String[]) field.invoke(object);
            StringMemberValue[] values = new StringMemberValue[annoValues.length];
            for (int i = 0; i < annoValues.length; i++) {
                values[i] = new StringMemberValue(annoValues[i], cp);
            }
            array.setValue(values);
            return array;
        }
        if (field.getReturnType().equals(boolean.class)) {
            return new BooleanMemberValue((Boolean) field.invoke(object),
                    new ConstPool(this.getClass().getCanonicalName()));
        }
        return null;
    }
    public CucumberOptions[] splitCucumberOption(CucumberOptions option) throws Exception {
        CucumberOptions[] result = {};
        String[] featurePaths = option.features();
        String[] featureFiles = new String[] {};
        for (String featurePath : featurePaths) {
            File feature = new File(featurePath);
            if (feature.isDirectory()) {
                featureFiles = (String[]) ArrayUtils.addAll(
                        featureFiles, getFilesByMask(feature.getAbsolutePath(), "(.*).feature"));
            } else {
                featureFiles = (String[]) ArrayUtils.add(featureFiles, feature.getAbsolutePath());
            }
        }
        for (String file : featureFiles) {
            ConstPool cp = new ConstPool(ExtendedParallelCucumber.class.getCanonicalName());
            Annotation anno = new Annotation(CucumberOptions.class.getCanonicalName(), cp);
            System.out.println("Processing fields");
            for (Method field : CucumberOptions.class.getDeclaredMethods()) {
                System.out.println("Processing field: " + field.getName());
                String name = field.getName();
                if (name.equals("features")) {
                    ArrayMemberValue array = new ArrayMemberValue(new StringMemberValue(cp), cp);
                    array.setValue(new StringMemberValue[] {new StringMemberValue(file, cp)});
                    anno.addMemberValue(name, array);
                } else {
                    anno.addMemberValue(name, getFieldMemberValue(option, field));
                }
            }
            CucumberOptions newOption = (CucumberOptions) anno.toAnnotationType(
                    this.getClass().getClassLoader(), ClassPool.getDefault());
            result = (CucumberOptions[]) ArrayUtils.add(result, newOption);
        }
        return result;
    }
    @Override
    public Description getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void run(RunNotifier notifier) {
    }

}
