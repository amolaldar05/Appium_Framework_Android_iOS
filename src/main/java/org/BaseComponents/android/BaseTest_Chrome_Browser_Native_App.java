package org.BaseComponents.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.helpers.DriverManager;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;

public class BaseTest_Chrome_Browser_Native_App {

    private AppiumDriverLocalService service;
    public AndroidDriver driver;
    private static final Logger log = LoggerFactory.getLogger(BaseTest_Chrome_Browser_Native_App.class);

    @BeforeClass
    public void setupAppiumServer() throws MalformedURLException {
        log.info("🔧 Starting Appium server...");
        try {
            service = new AppiumServiceBuilder()
                    .usingAnyFreePort()
                    .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub")
                    .build();

            service.start();
            log.info("✅ Appium server started at: {}", service.getUrl());

            initializeDriver();
        } catch (Exception e) {
            log.error("❌ Failed to start Appium server or initialize driver", e);
            throw e;
        }
    }

    public void initializeDriver() {
        log.info("📱 Initializing Android driver with Chrome browser...");
        try {
            UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName("Amol_Android_VD")
                    .setChromedriverExecutable(System.getProperty("user.dir") + "/src/main/resources/androidApps/chromedriver");
            options.setCapability("browserName", "Chrome");

            driver = new AndroidDriver(service.getUrl(), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            // Set driver in ThreadLocal DriverManager for parallel safety
            DriverManager.setDriver(driver);
            log.info("✅ Android driver initialized successfully.");
        } catch (Exception e) {
            log.error("❌ Failed to initialize Android driver", e);
            throw e;
        }
    }

    @AfterClass
    public void tearDown() {
        log.info("🛑 Tearing down test environment...");
        try {
            if (driver != null) {
                driver.quit();
                DriverManager.removeDriver();  // Clean up ThreadLocal
                log.info("✅ Android driver quit successfully.");
            }
        } catch (Exception e) {
            log.warn("⚠ Error while quitting Android driver", e);
        }

        try {
            if (service != null && service.isRunning()) {
                service.stop();
                log.info("✅ Appium server stopped successfully.");
            }
        } catch (Exception e) {
            log.warn("⚠ Error while stopping Appium server", e);
        }
    }
}
