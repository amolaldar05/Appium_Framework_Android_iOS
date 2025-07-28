package org.utils.BaseComponents.crossPlatforms;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;

public class BaseTest_CrossPlatform {

    private AppiumDriverLocalService service;
    public AppiumDriver driver;
    private String platformName;

    private static final Logger log = LoggerFactory.getLogger(BaseTest_CrossPlatform.class);

    @BeforeClass(alwaysRun = true)
    public void setupAppium() throws MalformedURLException {
        platformName = System.getProperty("platform", "android").toLowerCase();
        log.info("Setting up Appium tests for platform: {}", platformName);

        startAppiumServer();
        initializeDriver();
    }

    private void startAppiumServer() {
        try {
            service = new AppiumServiceBuilder()
                    .usingAnyFreePort()
                    .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub")
                    .build();

            service.start();
            log.info("✅ Appium server started at: {}", service.getUrl());
        } catch (Exception e) {
            log.error("❌ Failed to start Appium server: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void initializeDriver() throws MalformedURLException {
        try {
            if ("android".equals(platformName)) {
                log.info("🔧 Initializing Android driver...");
                UiAutomator2Options options = new UiAutomator2Options()
                        .setDeviceName(System.getProperty("androidDeviceName", "Amol_Android_VD"))
                        .setApp(System.getProperty("androidAppPath", System.getProperty("user.dir") + "/src/main/resources/androidApps/General-Store.apk"))
                        .setChromedriverExecutable(System.getProperty("chromeDriverPath", System.getProperty("user.dir") + "/src/main/resources/androidApps/chromedriver"))
                        .setNewCommandTimeout(Duration.ofSeconds(60));

                driver = new AndroidDriver(service.getUrl(), options);
                log.info("✅ Android driver initialized.");

            } else if ("ios".equals(platformName)) {
                log.info("🔧 Initializing iOS driver...");
                XCUITestOptions options = new XCUITestOptions()
                        .setDeviceName(System.getProperty("iosDeviceName", "iPhone 16"))
                        .setPlatformVersion(System.getProperty("iosPlatformVersion", "18.0"))
                        .setAutomationName("XCUITest")
                        .setApp(System.getProperty("iosAppPath", System.getProperty("user.dir") + "/src/main/resources/iOSApps/UIKitCatalog.app"))
                        .setWdaLaunchTimeout(Duration.ofSeconds(20))
                        .setNewCommandTimeout(Duration.ofSeconds(60));

                driver = new IOSDriver(service.getUrl(), options);
                log.info("✅ iOS driver initialized.");

            } else {
                String error = "Unsupported platform: " + platformName;
                log.error("❌ {}", error);
                throw new IllegalArgumentException(error);
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } catch (Exception e) {
            log.error("❌ Error initializing driver: {}", e.getMessage(), e);
            throw e;
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("🛑 Driver session ended.");
        }
        if (service != null && service.isRunning()) {
            service.stop();
            log.info("🛑 Appium server stopped.");
        }
    }
}
