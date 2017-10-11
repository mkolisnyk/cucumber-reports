package com.github.mkolisnyk.cucumber.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class AnnotationInvocationHandler implements InvocationHandler {
    private Annotation orig;
    private String attrName;
    private Object newValue;

    public AnnotationInvocationHandler(
            Annotation origParam,
            String attrNameParam,
            Object newValueParam) throws Exception {
        this.orig = origParam;
        this.attrName = attrNameParam;
        this.newValue = newValueParam;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // "override" the return value for the property we want
        if (method.getName().equals(attrName) && args == null) {
            return newValue;
        } else {
            Class<?>[] paramTypes = toClassArray(args);
            return orig.getClass().getMethod(method.getName(), paramTypes).invoke(orig, args);
        }
    }

    private static Class<?>[] toClassArray(Object[] arr) {
        if (arr == null) {
            return null;
        }
        Class<?>[] classArr = new Class[arr.length];
        for (int i = 0; i < arr.length; i++) {
            classArr[i] = arr[i].getClass();
        }
        return classArr;
    }

}
