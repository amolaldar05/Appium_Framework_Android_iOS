package org.utils.helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import org.utils.actions.enums.MobileKeyEvent;

import java.time.Duration;

public class MobileKeyActionUtils {

    private final AppiumDriver driver;

    public MobileKeyActionUtils(AppiumDriver driver) {
        this.driver = driver;
    }

    public void pressKey(MobileKeyEvent keyEvent) {
        if (driver instanceof AndroidDriver) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            AndroidKey androidKey = mapToAndroidKey(keyEvent);
            if (androidKey != null) {
                androidDriver.pressKey(new KeyEvent(androidKey));
            } else {
                System.err.println("Unsupported Android key event: " + keyEvent);
            }
        } else if (driver instanceof IOSDriver) {
            handleIOSKeyEvent(keyEvent);
        } else {
            System.err.println("Unknown driver type: " + driver.getClass().getSimpleName());
        }
    }

    private AndroidKey mapToAndroidKey(MobileKeyEvent keyEvent) {
        return switch (keyEvent) {
            case HOME -> AndroidKey.HOME;
            case BACK -> AndroidKey.BACK;
            case ENTER -> AndroidKey.ENTER;
            case APP_SWITCH -> AndroidKey.APP_SWITCH;
            case VOLUME_UP -> AndroidKey.VOLUME_UP;
            case VOLUME_DOWN -> AndroidKey.VOLUME_DOWN;
            default -> null;
        };
    }

    private void handleIOSKeyEvent(MobileKeyEvent keyEvent) {
        // iOS does not support native key events like Android
        switch (keyEvent) {
//            case HOME -> backgroundIOSApp(2); // simulate Home by backgrounding the app
            case BACK, ENTER, APP_SWITCH, VOLUME_UP, VOLUME_DOWN ->
                    System.out.println("iOS '" + keyEvent + "' not supported directly.");
        }
    }

//    private void backgroundIOSApp(int seconds) {
//        driver.runAppInBackground(Duration.ofSeconds(seconds));
//    }
}
