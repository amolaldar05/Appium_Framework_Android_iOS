package org.helpers;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.logging.Logger;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount=0;
    final private int maxRetryCount=2;
    final public static Logger log= (Logger) LoggerUtil.getLogger(RetryAnalyzer.class);
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount<maxRetryCount){
            log.info("Retrying test: " + result.getName() + " | Attempt: " + retryCount);
            retryCount++;
            return true;
        }
        return false;
    }
}
