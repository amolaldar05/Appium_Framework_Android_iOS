package org.BaseComponents.crossPlatforms;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import org.helpers.AppiumServerManager;
import org.helpers.ConfigReader;
import org.helpers.DriverManager;
import org.helpers.PlatformManager;
import org.pageObjects.android.FormPage;
import org.pageObjects.iOS.HomePage;

import java.net.MalformedURLException;
import java.time.Duration;

public class BaseTest_CrossPlatform {

    private AppiumDriverLocalService service;
    public  AppiumDriver driver;

    // 🔸 Made protected so subclasses can access platformName
    protected String platformName;

    public FormPage formPage;
    public HomePage homePage;

    private static final Logger log = LoggerFactory.getLogger(BaseTest_CrossPlatform.class);

    @BeforeSuite(alwaysRun = true)
    public void startAppiumServer() {
        AppiumServerManager.startServer();
    }

    @Parameters("platformName")
    @BeforeClass(alwaysRun = true)
    public void setupAppium(@Optional String platformName) throws MalformedURLException {
        if (platformName == null || platformName.isEmpty()) {
            platformName = System.getProperty("platformName", "android");
        }
        PlatformManager.detectPlatform(this.getClass());
        this.platformName = PlatformManager.getPlatform();  // Store it in the instance field
        log.info("Setting up Appium tests for platform: {}", this.platformName);
        initializeDriver();
    }

    private void initializeDriver() throws MalformedURLException {
        try {
            if ("android".equalsIgnoreCase(platformName)) {
                log.info("🔧 Initializing Android driver...");
                UiAutomator2Options options = new UiAutomator2Options()
                        .setDeviceName(System.getProperty("androidDeviceName", "Amol_Android_VD"))
                        .setApp(System.getProperty("user.dir") + ConfigReader.getOrDefault("androidGeneralStoreAppPath", "/src/main/resources/androidApps/General-Store.apk"))
                        .setChromedriverExecutable(System.getProperty("user.dir") + ConfigReader.getOrDefault("chromeDriverPath", "/src/main/resources/chromedriver"))
                        .setNewCommandTimeout(Duration.ofSeconds(60));
                service = AppiumServerManager.getService();
                driver = new AndroidDriver(service.getUrl(), options);
                formPage = new FormPage((AndroidDriver) driver);
                log.info("✅ Android driver initialized.");

            } else if ("ios".equalsIgnoreCase(platformName)) {
                log.info("🔧 Initializing iOS driver...");
                XCUITestOptions options = new XCUITestOptions()
                        .setDeviceName(System.getProperty("iosDeviceName", "iPhone 16"))
                        .setPlatformVersion(System.getProperty("iosPlatformVersion", "18.0"))
                        .setAutomationName("XCUITest")
                        .setApp(System.getProperty("user.dir") + ConfigReader.getOrDefault("iosAppPath", "/src/main/resources/iosApps/General-Store.app"))
                        .setWdaLaunchTimeout(Duration.ofSeconds(20))
                        .setNewCommandTimeout(Duration.ofSeconds(60));
                service = AppiumServerManager.getService();
                driver = new IOSDriver(service.getUrl(), options);
                homePage = new HomePage((IOSDriver) driver);
                log.info("✅ iOS driver initialized.");

            } else {
                String error = "Unsupported platform: " + platformName;
                log.error("❌ {}", error);
                throw new IllegalArgumentException(error);
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Store driver in DriverManager
            DriverManager.setDriver(driver);

        } catch (Exception e) {
            log.error("❌ Error initializing driver: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ✅ Suggestion A: Add getDriver() for convenience
    public AppiumDriver getDriver() {
        WebDriver driver = DriverManager.getDriver();
        if (driver instanceof AppiumDriver) {
            return (AppiumDriver) driver;
        } else {
            throw new IllegalStateException("Driver is not an instance of AppiumDriver: " + driver.getClass());
        }
    }


    // ✅ Optional: Add getter for platform name (if needed instead of direct access)
    public String getPlatformName() {
        return this.platformName;
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        log.info("🧹 Cleaning up driver and Appium service...");
        if (driver != null) {
            driver.quit();
            DriverManager.removeDriver();
            log.info("🛑 Driver session ended.");
        }
    }

    @AfterSuite
    public void stopAppiumServer() {
        PlatformManager.resetPlatform();
        AppiumServerManager.stopServer();
    }
}
