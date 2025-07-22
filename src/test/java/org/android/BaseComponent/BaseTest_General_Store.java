package org.android.BaseComponent;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;

public class BaseTest_General_Store {

    // 🛠️ Manages the lifecycle of the Appium server (start/stop)
    private AppiumDriverLocalService service;

    // 🌐 AndroidDriver to interact with the mobile device
    public AndroidDriver driver;

    /**
     * 🚀 Setup method to initialize and start Appium service and AndroidDriver before tests run.
     */
    @BeforeClass
    public void setupAppiumServer() throws MalformedURLException {
        // Build the Appium server with dynamic port and session override
        service = new AppiumServiceBuilder()
                .usingAnyFreePort()                         // Use any available port to avoid conflicts
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js")) // Path to Appium.js
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)  // Force override any existing session
                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub") // Use W3C-compatible base path
                .build();
       /* service = new AppiumServiceBuilder().withAppiumJS("/usr/local/lib/node_modules/appium/build/lib/main.js")
                       .withIPAddress("127.0.0.1")
                                .usingPort(4723).build(); // Create Appium service with specified IP and port
*/
       service.start();                                  // Start the Appium server process
       System.out.println("Appium server started at: " + service.getUrl()); // Log the server URL
        initializeDriver(); // Initialize the AndroidDriver with desired capabilities

    }

    public void initializeDriver() {
        // Desired capabilities using W3C UiAutomator2Options
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Amol_Android_VD")                               // Target emulator/device
                .setApp(System.getProperty("user.dir") + "/src/main/resources/General-Store.apk"); // App under test

        // Initialize AndroidDriver with server URL and options
        driver = new AndroidDriver(service.getUrl(), options);
        // Set implicit wait for elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


//     Tear down method to quit driver and stop server after all tests.

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the mobile session cleanly
        }
        if (service != null && service.isRunning()) {
            service.stop(); // Stop the underlying Appium server
        }
    }
}

