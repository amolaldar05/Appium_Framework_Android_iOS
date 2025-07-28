package org.utils.BaseComponents.iOS;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.utils.helpers.LoggerUtil;
import org.utils.pageObjects.iOS.HomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;

public class BaseTest {

    private AppiumDriverLocalService service;
    private static final Logger log = LoggerUtil.getLogger(BaseTest.class);
    public IOSDriver driver;
    private XCUITestOptions options;
    public HomePage homePage;

    @BeforeClass
    public void setupAppiumServer() throws MalformedURLException {
        log.info("🛠️ Starting Appium server...");

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
        log.info("📲 Initializing iOS driver...");
        setupAppAndDevice();

        driver = new IOSDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        log.info("✅ iOS driver initialized successfully");
        homePage = new HomePage(driver);
    }

    private void setupAppAndDevice() {
        log.info("⚙️ Setting app and device capabilities...");
        options = new XCUITestOptions()
                .setDeviceName("iPhone 16")
                .setPlatformVersion("18.0")
                .setAutomationName("XCUITest")
                .setApp(System.getProperty("user.dir") + "/src/main/resources/iOSApps/UIKitCatalog.app")
                .setWdaLaunchTimeout(Duration.ofSeconds(20));

        log.info("📱 Capabilities set for device: iPhone 16, iOS 18.0");
    }

    @AfterClass
    public void tearDown() {
        log.info("🧹 Tearing down test setup...");

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
