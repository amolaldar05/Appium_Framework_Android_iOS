package org.iOS.BaseComponent;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;

public class BaseTest {

    // 🛠 Manages the lifecycle of the Appium server (start/stop)
    private AppiumDriverLocalService service;

    // 🌐 iOSDriver to interact with iOS simulator/device
    public IOSDriver driver;

    // ⚙️ XCUITestOptions holds desired capabilities for iOS automation
    private XCUITestOptions options;

    // 🚀 Setup method to start Appium server and initialize the driver before tests
    @BeforeClass
    public void setupAppiumServer() throws MalformedURLException {
        // Build Appium server with dynamic port and base path for W3C compatibility
        service = new AppiumServiceBuilder()
                .usingAnyFreePort() // Use any available port to avoid conflicts
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js")) // Path to Appium main.js
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE) // Override any existing session
                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub") // Set base path
                .build();

        service.start(); // Start Appium server
        System.out.println("✅ Appium server started at: " + service.getUrl());

        initializeDriver(); // Initialize iOSDriver with desired capabilities
    }

    // 📲 Initialize the iOSDriver with configured capabilities
    private void initializeDriver() {
        setupAppAndDevice(); // Configure app and device capabilities

        // Initialize iOSDriver with server URL and options
        driver = new IOSDriver(service.getUrl(), options);

        // Set implicit wait for finding elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // ⚙️ Configure iOS app and device settings
    private void setupAppAndDevice() {
        options = new XCUITestOptions()
                .setDeviceName("iPhone 16") // Simulator/device name
                .setPlatformVersion("18.0") // iOS version
                .setAutomationName("XCUITest") // iOS automation engine
                .setApp(System.getProperty("user.dir"+"/src/main/resources/iOSApps/UIKitCatalog.app")) // App path
                .setWdaLaunchTimeout(Duration.ofSeconds(20)); // Timeout for WebDriverAgent launch
    }

    // 🧹 Tear down method to quit driver and stop Appium server after all tests
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the iOSDriver session
            System.out.println("✅ iOSDriver session ended.");
        }
        if (service != null && service.isRunning()) {
            service.stop(); // Stop Appium server
            System.out.println("🛑 Appium server stopped.");
        }
    }
}
