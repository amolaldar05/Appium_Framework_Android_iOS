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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;

public class BaseTest_CrossPlatform {

    // Manages the lifecycle of the Appium server
    private AppiumDriverLocalService service;

    // Common AppiumDriver for both platforms
    public AppiumDriver driver;

    // Platform property: "android" or "ios"
    private String platformName;

    @BeforeClass(alwaysRun = true)
    public void setupAppium() throws MalformedURLException {
        // Read platform from system property or default to Android
        platformName = System.getProperty("platform", "android").toLowerCase();

        startAppiumServer();
        initializeDriver();
    }

    private void startAppiumServer() {
        service = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub")
                .build();

        service.start();
        System.out.println("Appium server started at: " + service.getUrl());
    }

    private void initializeDriver() throws MalformedURLException {
        if ("android".equals(platformName)) {
            UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName(System.getProperty("androidDeviceName", "Amol_Android_VD"))
                    .setApp(System.getProperty("androidAppPath", System.getProperty("user.dir") + "/src/main/resources/androidApps/General-Store.apk"))
                    .setChromedriverExecutable(System.getProperty("chromeDriverPath", System.getProperty("user.dir") + "/src/main/resources/androidApps/chromedriver"))
                    .setNewCommandTimeout(Duration.ofSeconds(60));

            driver = new AndroidDriver(service.getUrl(), options);

        } else if ("ios".equals(platformName)) {
            XCUITestOptions options = new XCUITestOptions()
                    .setDeviceName(System.getProperty("iosDeviceName", "iPhone 16"))
                    .setPlatformVersion(System.getProperty("iosPlatformVersion", "18.0"))
                    .setAutomationName("XCUITest")
                    .setApp(System.getProperty("iosAppPath", System.getProperty("user.dir") + "/src/main/resources/iOSApps/UIKitCatalog.app"))
                    .setWdaLaunchTimeout(Duration.ofSeconds(20))
                    .setNewCommandTimeout(Duration.ofSeconds(60));

            driver = new IOSDriver(service.getUrl(), options);

        } else {
            throw new IllegalArgumentException("Unsupported platform: " + platformName);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver session ended.");
        }
        if (service != null && service.isRunning()) {
            service.stop();
            System.out.println("Appium server stopped.");
        }
    }
}

