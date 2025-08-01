package org.helpers;

import org.slf4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {
    final public static Logger log= LoggerUtil.getLogger(RetryListener.class);
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod){
            // Directly set RetryAnalyzer
            annotation.setRetryAnalyzer(RetryAnalyzer.class);

    }
}
