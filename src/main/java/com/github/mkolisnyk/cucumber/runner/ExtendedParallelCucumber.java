package com.github.mkolisnyk.cucumber.runner;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import org.apache.commons.lang.ArrayUtils;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;

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
            result[i] = location + File.separator + index + File.separator + name;
        }
        return result;
    }
    public Annotation[] splitCucumberOption(CucumberOptions option) throws Exception {
        Annotation[] result = {};
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
            int index = 0;
            for (Method field : CucumberOptions.class.getDeclaredMethods()) {
                System.out.println("Processing field: " + field.getName());
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
                    value.setValue(SnippetType.UNDERSCORE.name());
                    anno.addMemberValue(name, value);
                } else {
                    MemberValue value = getFieldMemberValue(option, field);
                    if (value != null) {
                        anno.addMemberValue(name, value);
                    }
                }
                index++;
            }
            //CucumberOptions newOption = (CucumberOptions) anno.toAnnotationType(
            //        this.getClass().getClassLoader(), ClassPool.getDefault());
            result = (Annotation[]) ArrayUtils.add(result, anno);
        }
        return result;
    }
    public Annotation[][] splitExtendedCucumberOptions(
            ExtendedCucumberOptions[] extendedOptions,
            int suitesCount) throws Exception {
        Annotation[][] result = new Annotation[suitesCount][extendedOptions.length];
        for (int i = 0; i < suitesCount; i++) {
            ConstPool cp = new ConstPool(ExtendedParallelCucumber.class.getCanonicalName());
            for (int j = 0; j < extendedOptions.length; j++) {
                Annotation anno = new Annotation(ExtendedCucumberOptions.class.getCanonicalName(), cp);
                for (Method field : ExtendedCucumberOptions.class.getDeclaredMethods()) {
                    System.out.println("Processing field: " + field.getName());
                    String name = field.getName();
                    if (name.equals("outputFolder")) {
                        anno.addMemberValue(name,
                            new StringMemberValue(extendedOptions[j].outputFolder() + "/" + i + "_" + j, cp));
                    } else if (name.equals("jsonReport") || name.equals("jsonUsageReport")) {
                        String newName = this.convertPluginPaths(
                            new String[] {(String) field.invoke(extendedOptions[j])}, i)[0];
                        anno.addMemberValue(name,
                                new StringMemberValue(newName, cp));
                    } else if (name.equals("jsonReports") || name.equals("jsonUsageReports")) {
                        String[] reports = convertPluginPaths((String[]) field.invoke(extendedOptions[j]), i);
                        ArrayMemberValue array = new ArrayMemberValue(new StringMemberValue(cp), cp);
                        StringMemberValue[] values = new StringMemberValue[reports.length];
                        for (int k = 0; k < reports.length; k++) {
                            values[k] = new StringMemberValue(reports[k], cp);
                        }
                        array.setValue(values);
                        anno.addMemberValue(name, array);
                    } else {
                        MemberValue value = getFieldMemberValue(extendedOptions[j], field);
                        if (value != null) {
                            anno.addMemberValue(name, getFieldMemberValue(extendedOptions[j], field));
                        }
                    }
                }
                //result[i][j] = (ExtendedCucumberOptions) anno.toAnnotationType(
                //        this.getClass().getClassLoader(), ClassPool.getDefault());
                result[i][j] = anno;
            }

        }
        return result;
    }
    public Class<?>[] generateTestClasses(Annotation[] cucumberOptions,
            Annotation[][] extendedOptions) throws Exception {
        Class<?>[] classes = new Class<?>[cucumberOptions.length];
        for (int i = 0; i < cucumberOptions.length; i++) {
            String className = this.clazz.getCanonicalName() + i;
            ClassFile cf = new ClassFile(false, className, null);
            ClassPool pool = ClassPool.getDefault();
            ConstPool cp = new ConstPool(className);
            cf.setAccessFlags(AccessFlag.PUBLIC);
            cf.setMajorVersion(ClassFile.JAVA_8);
            AnnotationsAttribute attr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
            //attr.addAnnotation(cucumberOptions[i]);
            Annotation annoWebService = new Annotation(cp, pool.get(ExtendedCucumberOptions.class.getCanonicalName()));
            //for (int j = 0; j < extendedOptions[i].length; j++) {
            //    System.out.println("Adding: " + i + "-" + j + ": " + extendedOptions[i][j].toString());
            //    attr.addAnnotation(extendedOptions[i][j]);
            //}
            System.out.println(annoWebService.getMemberNames());
            for (Object member : annoWebService.getMemberNames()) {
                annoWebService.addMemberValue((String) member, extendedOptions[i][0].getMemberValue((String) member));
            }
            attr.setAnnotation(annoWebService);
            cf.addAttribute(attr);
            cf.write(new DataOutputStream(new FileOutputStream(className + ".class")));
            CtClass newClass = pool.makeClass(cf);
            //Object[] objs = newClass.getAvailableAnnotations();
            classes[i] = newClass.toClass();
            
        }
        return classes;
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
