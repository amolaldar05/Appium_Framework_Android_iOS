package org.helpers;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("🔹 Test Started: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✅ Test Passed: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("❌ Test Failed: {}", result.getName());
        ScreenshotUtil screenshotUtil = new ScreenshotUtil();
        screenshotUtil.takeScreenshot(result.getName());
        }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⏭️ Test Skipped: {}", result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("⚠️ Test Failed but within success percentage: {}", result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("🚀 Test Suite Started: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("🏁 Test Suite Finished: {}", context.getName());
    }
}
