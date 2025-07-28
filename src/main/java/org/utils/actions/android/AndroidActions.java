package org.utils.actions.android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.utils.actions.AppiumUtils;

import java.time.Duration;
import java.util.Collections;

public class AndroidActions extends AppiumUtils {
    protected AndroidDriver driver;
    private static final Logger log = LoggerFactory.getLogger(AndroidActions.class);

    public AndroidActions(AndroidDriver driver) {
        super(driver);
        if (driver == null) {
            log.error("❌ WebDriver instance is null in AndroidActions constructor.");
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
        log.info("✅ AndroidActions initialized with AndroidDriver.");
    }

    public void longPressGesture(WebElement ele) {
        log.info("👉 Performing long press gesture...");
        if (!(driver instanceof JavascriptExecutor)) {
            log.error("❌ Driver does not support JavascriptExecutor for long press.");
            throw new IllegalStateException("Driver does not support JavascriptExecutor");
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        RemoteWebElement element = (RemoteWebElement) ele;
        js.executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", element.getId(),
                "duration", 2000
        ));
        log.info("✅ Long press completed.");
    }

    public void scrollToProduct(String productName) {
        log.info("🔍 Scrolling to product: {}", productName);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"" + productName + "\"));"
        ));
        log.info("✅ Product {} found.", productName);
    }

    public WebElement scrollAndClickUptoUsingUIAutomator2(String countryName) {
        log.info("🌍 Scrolling to country: {}", countryName);
        WebElement targetElement = driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"" + countryName + "\"));"
        ));
        log.info("✅ Country {} found and ready for interaction.", countryName);
        return targetElement;
    }

    public void scrollToElement(WebElement ele) {
        log.info("🔽 Scrolling to element using scroll gesture...");
        if (!(driver instanceof JavascriptExecutor)) {
            log.error("❌ Driver does not support JavascriptExecutor for scroll gesture.");
            throw new IllegalStateException("Driver does not support JavascriptExecutor");
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        RemoteWebElement element = (RemoteWebElement) ele;
        js.executeScript("mobile: scroll", ImmutableMap.of(
                "elementId", element.getId(),
                "direction", "down"
        ));
        log.info("✅ Scroll gesture completed.");
    }

    public void scrollToEnd(String direction) {
        log.info("⏩ Scrolling to end in direction: {}", direction);
        if (!(driver instanceof JavascriptExecutor)) {
            log.error("❌ Driver does not support JavascriptExecutor for scrollToEnd.");
            throw new IllegalStateException("Driver does not support JavascriptExecutor");
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean canScroll;
        do {
            canScroll = (Boolean) js.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100,
                    "top", 100,
                    "width", 200,
                    "height", 400,
                    "direction", direction,
                    "percent", 0.5
            ));
            log.debug("📜 Can scroll further: {}", canScroll);
        } while (canScroll);
        log.info("✅ Finished scrolling in direction: {}", direction);
    }

    public void swipeGesture(WebElement ele, String direction) {
        log.info("↔️ Swiping in direction: {}", direction);
        if (!(driver instanceof JavascriptExecutor)) {
            log.error("❌ Driver does not support JavascriptExecutor for swipeGesture.");
            throw new IllegalStateException("Driver does not support JavascriptExecutor");
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        RemoteWebElement element = (RemoteWebElement) ele;
        js.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", element.getId(),
                "direction", direction,
                "percent", 0.75
        ));
        log.info("✅ Swipe gesture completed.");
    }

    public void dragAndDropGesture(WebElement source, WebElement target) {
        log.info("🎯 Performing drag and drop...");
        if (!(driver instanceof JavascriptExecutor)) {
            log.error("❌ Driver does not support JavascriptExecutor for dragAndDrop.");
            throw new IllegalStateException("Driver does not support JavascriptExecutor");
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        RemoteWebElement sourceElement = (RemoteWebElement) source;
        RemoteWebElement targetElement = (RemoteWebElement) target;
        js.executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", sourceElement.getId(),
                "endX", 655,
                "endY", 655
        ));
        log.info("✅ Drag and drop gesture completed.");
    }

    public void pressAndroidKey(AndroidKey key) {
        log.info("🔘 Pressing AndroidKey enum: {}", key);
        if (driver instanceof AndroidDriver) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(key));
        } else {
            log.error("❌ Driver is not AndroidDriver instance.");
            throw new UnsupportedOperationException("pressAndroidKey is only supported for AndroidDriver");
        }
    }

    public void pressAndroidKey(String keyName) {
        log.info("🔘 Pressing AndroidKey from String: {}", keyName);
        try {
            AndroidKey key = AndroidKey.valueOf(keyName.toUpperCase());
            pressAndroidKey(key);
        } catch (IllegalArgumentException e) {
            log.error("❌ Invalid Android key name: {}", keyName);
            throw new IllegalArgumentException("Invalid Android key name: " + keyName, e);
        }
    }

    public void verticalScroll(double startPerc, double endPerc) {
        log.info("🔃 Performing vertical scroll from {}% to {}%", startPerc * 100, endPerc * 100);
        Dimension size = driver.manage().window().getSize();
        int width = size.width / 2;
        int startY = (int) (size.height * startPerc);
        int endY = (int) (size.height * endPerc);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), width, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(400), PointerInput.Origin.viewport(), width, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
        log.info("✅ Vertical scroll performed.");
    }

    public void waitForShortDelay() {
        log.debug("⏱️ Waiting for short delay (800ms)");
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            log.warn("⚠️ Thread interrupted during short delay.");
            Thread.currentThread().interrupt();
        }
    }
}
