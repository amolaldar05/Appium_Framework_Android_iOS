package org.ios.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

public class IosActions {

    private final WebDriver driver;

    public IosActions(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Perform touch and hold gesture on a given element for a specified duration.
     */
    public void touchAndHold(WebElement ele, int durationInMillis) {
        System.out.printf("👉 Performing touch and hold on element for %dms%n", durationInMillis);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) ele).getId());
        params.put("duration", durationInMillis);
        js.executeScript("mobile: touchAndHold", params);
    }

    /**
     * Scroll inside a scrollable element in a given direction.
     */
    public void iOSScrollAction(WebElement scrollableEle, String direction) {
        System.out.println("👉 Performing iOS scroll in direction: " + direction);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) scrollableEle).getId());
        params.put("direction", direction); // up, down, left, right
        js.executeScript("mobile: scroll", params);
    }

    /**
     * Scroll and then click on an element.
     */
    public void scrollToElementAndClick(WebElement ele, String direction) {
        iOSScrollAction(ele, direction);
        ele.click();
    }

    /**
     * Scroll until a static text element with given label becomes visible.
     */
    public void scrollToText(String visibleText) {
        System.out.println("👉 Scrolling to text: " + visibleText);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("predicateString", "label == '" + visibleText + "'");
        js.executeScript("mobile: scroll", params);
    }

    /**
     * Perform a swipe gesture in a given direction.
     */
    public void swipe(String direction) {
        System.out.println("👉 Swiping in direction: " + direction);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction); // up, down, left, right
        js.executeScript("mobile: swipe", params);
    }

    /**
     * Perform swipe from coordinates (startX, startY) to (endX, endY).
     */
    public void swipeByCoordinates(int startX, int startY, int endX, int endY, int duration) {
        System.out.printf("👉 Swiping from (%d,%d) to (%d,%d) for %dms%n", startX, startY, endX, endY, duration);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("startX", startX);
        params.put("startY", startY);
        params.put("endX", endX);
        params.put("endY", endY);
        params.put("duration", duration);
        js.executeScript("mobile: swipe", params);
    }

    /**
     * Perform a tap on a specific element.
     */
    public void tap(WebElement ele) {
        System.out.println("👉 Performing tap on element");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) ele).getId());
        js.executeScript("mobile: tap", params);
    }

    /**
     * Perform double-tap on a specific element.
     */
    public void doubleTap(WebElement ele) {
        System.out.println("👉 Performing double tap on element");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) ele).getId());
        js.executeScript("mobile: doubleTap", params);
    }

    /**
     * Perform pinch gesture (zoom in or out) on an element.
     */
    public void pinch(WebElement ele) {
        System.out.println("👉 Performing pinch on element");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) ele).getId());
        js.executeScript("mobile: pinch", params);
    }

    /**
     * Perform drag and drop from one element to another.
     */
    public void dragAndDrop(WebElement fromElement, WebElement toElement) {
        System.out.println("👉 Performing drag and drop");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) fromElement).getId());
        params.put("destinationElement", ((RemoteWebElement) toElement).getId());
        js.executeScript("mobile: dragFromToForDuration", params);
    }

    /**
     * Perform long press at a specific coordinate.
     */
    public void longPressAtCoordinates(int x, int y, int duration) {
        System.out.printf("👉 Long press at (%d, %d) for %dms%n", x, y, duration);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        params.put("duration", duration);
        js.executeScript("mobile: longPress", params);
    }

    /**
     * Perform tap at specific coordinates.
     */
    public void tapByCoordinates(int x, int y) {
        System.out.printf("👉 Tap at coordinates (%d, %d)%n", x, y);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        js.executeScript("mobile: tap", params);
    }

    /**
     * Perform double tap at specific coordinates.
     */
    public void doubleTapByCoordinates(int x, int y) {
        System.out.printf("👉 Double tap at coordinates (%d, %d)%n", x, y);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        js.executeScript("mobile: doubleTap", params);
    }
}
