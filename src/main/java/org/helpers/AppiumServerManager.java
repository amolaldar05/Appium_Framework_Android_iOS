package org.helpers;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AppiumServerManager {

    private static final Logger log = LoggerFactory.getLogger(AppiumServerManager.class);
    private static AppiumDriverLocalService service;

    public static void startServer() {
        if (service == null || !service.isRunning()) {
            try {
                service = new AppiumServiceBuilder()
                        .usingAnyFreePort()
                        .withAppiumJS(new File(ConfigReader.get("mainJSPath"))) // adjust path as needed
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
    }

    public static void stopServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            log.info("🛑 Appium server stopped.");
        }
    }

    public static AppiumDriverLocalService getService() {
        return service;
    }
}
