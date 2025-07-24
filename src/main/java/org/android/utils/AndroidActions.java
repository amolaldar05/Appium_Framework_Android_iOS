package org.android.utils;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AndroidActions {
    protected WebDriver driver;


    // ✅ Constructor assigns driver
    public AndroidActions(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("❌ WebDriver cannot be null in AndroidActions");
        }
        this.driver = driver;
    }

    /**
     * Perform a long press gesture on a specific element.
     */
    public void longPressGesture(WebElement ele) {
        System.out.println("👉 Performing long press gesture...");
        if (!(driver instanceof JavascriptExecutor)) {
            throw new IllegalStateException("❌ Driver does not support JavascriptExecutor");
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        RemoteWebElement element = (RemoteWebElement) ele;
        js.executeScript(
                "mobile: longClickGesture",
                ImmutableMap.of(
                        "elementId", element.getId(),
                        "duration", 2000
                )
        );
    }

    /**
     * Scroll to a specific element using mobile:scroll gesture.
     */
    public void scrollToElement(WebElement ele) {
        System.out.println("👉 Scrolling to the specified element...");
        if (!(driver instanceof JavascriptExecutor)) {
            throw new IllegalStateException("❌ Driver does not support JavascriptExecutor");
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        RemoteWebElement element = (RemoteWebElement) ele;
        js.executeScript(
                "mobile: scroll",
                ImmutableMap.of(
                        "elementId", element.getId(),
                        "direction", "down"
                )
        );
    }

    /**
     * Keep scrolling in the given direction until no more content can be scrolled.
     */
    public void scrollToEnd(String direction) {
        System.out.println("👉 Scrolling " + direction + "...");
        if (!(driver instanceof JavascriptExecutor)) {
            throw new IllegalStateException("❌ Driver does not support JavascriptExecutor");
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean canScroll;
        do {
            canScroll = (Boolean) js.executeScript(
                    "mobile: scrollGesture",
                    ImmutableMap.of(
                            "left", 100,
                            "top", 100,
                            "width", 200,
                            "height", 400,
                            "direction", direction,
                            "percent", 0.5
                    )
            );
        } while (canScroll);
        System.out.println("✅ Finished scrolling " + direction);
    }

    /**
     * Perform swipe gesture in a given direction.
     */
    public void swipeGesture(WebElement ele, String direction) {
        System.out.println("👉 Performing swipe gesture in direction: " + direction);
        if (!(driver instanceof JavascriptExecutor)) {
            throw new IllegalStateException("❌ Driver does not support JavascriptExecutor");
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        RemoteWebElement element = (RemoteWebElement) ele;
        js.executeScript(
                "mobile: swipeGesture",
                ImmutableMap.of(
                        "elementId", element.getId(),
                        "direction", direction,
                        "percent", 0.75
                )
        );
    }
    public void dragAndDropGesture(WebElement source, WebElement target) {
        System.out.println("👉 Performing drag and drop gesture...");
        if (!(driver instanceof JavascriptExecutor)) {
            throw new IllegalStateException("❌ Driver does not support JavascriptExecutor");
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        RemoteWebElement sourceElement = (RemoteWebElement) source;
        RemoteWebElement targetElement = (RemoteWebElement) target;
        js.executeScript(
                "mobile: dragGesture",
                ImmutableMap.of(
                        "elementId", sourceElement.getId(),
//                        "endElementId", targetElement.getId() //It does not work for few appium version so need to add coordinates
                        "endX", 655,
                        "endY", 655
                )
        );
    }
    // Scroll to a specific text using UIAutomator2


}











