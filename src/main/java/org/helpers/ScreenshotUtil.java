package org.helpers;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtil.class);

    public void takeScreenshot(String baseFileName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            logger.error("❌ WebDriver instance is null. Cannot take screenshot.");
            return;
        }

        try {
            // Append timestamp to file name to avoid overwrite
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String fileName = baseFileName + "_" + timestamp + ".png";

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("screenshots/" + fileName);

            if (!destination.getParentFile().exists()) {
                destination.getParentFile().mkdirs();
            }

            boolean success = source.renameTo(destination);
            if (success) {
                logger.info("✅ Screenshot saved at: {}", destination.getAbsolutePath());
            } else {
                logger.warn("⚠️ Could not move screenshot to: {}", destination.getAbsolutePath());
            }
        } catch (Exception e) {
            logger.error("❌ Failed to save screenshot: {}", e.getMessage(), e);
        }
    }
}
