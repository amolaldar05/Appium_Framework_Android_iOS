package org.utils.BaseComponents.android;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    private AppiumDriverLocalService service;
    public AndroidDriver driver;

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    /**
     * 🚀 Setup method to initialize and start Appium service and AndroidDriver before tests run.
     */
    @BeforeClass
    public void setupAppiumServer() throws MalformedURLException {
        log.info("📡 Starting Appium server...");
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

    /**
     * 📱 Initialize the Android driver with desired capabilities.
     */
    public void initializeDriver() {
        log.info("🔧 Initializing AndroidDriver with local app...");
        try {
            UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName("Amol_Android_VD")
                    .setApp(System.getProperty("user.dir") + "/src/main/resources/androidApps/ApiDemos-debug.apk");

            driver = new AndroidDriver(service.getUrl(), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            log.info("✅ AndroidDriver initialized successfully.");
        } catch (Exception e) {
            log.error("❌ Failed to initialize AndroidDriver", e);
            throw e;
        }
    }

    /**
     * 🧹 Clean up after test run: stop driver and Appium server.
     */
    @AfterClass
    public void tearDown() {
        log.info("🛑 Tearing down Appium test environment...");
        try {
            if (driver != null) {
                driver.quit();
                log.info("✅ AndroidDriver session ended.");
            }
        } catch (Exception e) {
            log.warn("⚠️ Error while quitting driver", e);
        }

        try {
            if (service != null && service.isRunning()) {
                service.stop();
                log.info("✅ Appium server stopped.");
            }
        } catch (Exception e) {
            log.warn("⚠️ Error while stopping Appium server", e);
        }
    }
}
