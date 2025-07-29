package org.BaseComponents.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.helpers.DriverManager;
import org.helpers.LoggerUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.pageObjects.android.FormPage;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;

public class BaseTest_General_Store {

    private static final Logger log = LoggerUtil.getLogger(BaseTest_General_Store.class);

    protected AppiumDriverLocalService service;
    protected AndroidDriver driver;
    protected FormPage formPage;

    @BeforeClass(alwaysRun = true)
    public void setupAppiumServer() throws MalformedURLException {
        log.info("🔧 Starting Appium server...");
        service = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub")
                .build();

        service.start();
        log.info("✅ Appium server started at: {}", service.getUrl());

        initializeDriver();
    }

    private void initializeDriver() {
        log.info("📲 Initializing Android driver...");
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Amol_Android_VD")
                .setApp(System.getProperty("user.dir") + "/src/main/resources/androidApps/General-Store.apk");

        options.setChromedriverExecutable(System.getProperty("user.dir") + "/src/main/resources/androidApps/chromedriver");

        driver = new AndroidDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Set driver in ThreadLocal DriverManager for parallel safety
        DriverManager.setDriver(driver);

        formPage = new FormPage(driver);
        log.info("✅ Android driver initialized with General Store app");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        log.info("🧹 Cleaning up after tests...");
        if (driver != null) {
            driver.quit();
            DriverManager.removeDriver();  // Clean up ThreadLocal
            log.info("🚗 Android driver quit.");
        }
        if (service != null && service.isRunning()) {
            service.stop();
            log.info("🛑 Appium server stopped.");
        }
    }
}
