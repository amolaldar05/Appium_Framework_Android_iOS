package org.utils.BaseComponents.iOS;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.utils.pageObjects.android.CartPage;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;

public class BuiltInApps_BaseTest {

    private AppiumDriverLocalService service;
    private static final Logger log = LoggerFactory.getLogger(BuiltInApps_BaseTest.class);

    public IOSDriver driver;
    private XCUITestOptions options;

    @BeforeClass
    public void setupAppiumServer() throws MalformedURLException {
        log.info("🔧 Setting up Appium service...");

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
        setupAppAndDevice();
        log.info("📲 Initializing iOSDriver with capabilities...");

        driver = new IOSDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        log.info("✅ iOSDriver initialized successfully.");
    }

    private void setupAppAndDevice() {
        log.info("⚙️ Setting up iOS app and device configurations...");

        options = new XCUITestOptions()
                .setDeviceName("iPhone 16")
                .setPlatformVersion("18.0")
                .setAutomationName("XCUITest")
                //.setApp(System.getProperty("user.dir") + "/src/main/resources/iOSApps/UIKitCatalog.app")
                .setWdaLaunchTimeout(Duration.ofSeconds(20));

        log.info("✅ XCUITestOptions configured.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("✅ iOSDriver session ended.");
        }

        if (service != null && service.isRunning()) {
            service.stop();
            log.info("🛑 Appium server stopped.");
        }
    }
}
