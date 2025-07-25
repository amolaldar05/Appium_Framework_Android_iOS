package org.utils.actions.iOS;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.utils.actions.AppiumUtils;

import java.util.HashMap;
import java.util.Map;

public class IosActions extends AppiumUtils {

    private final JavascriptExecutor js;

    public IosActions(WebDriver driver) {
        super(driver);
        if (driver == null) {
            throw new IllegalArgumentException("❌ WebDriver cannot be null in IosActions");
        }
        this.js = (JavascriptExecutor) driver;
    }

    private Map<String, Object> createElementParam(WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) element).getId());
        return params;
    }

    private void execute(String command, Map<String, Object> params) {
        js.executeScript("mobile: " + command, params);
    }

    /**
     * Perform touch and hold gesture on a given element for a specified duration.
     */
    public void touchAndHold(WebElement ele, int durationInMillis) {
        System.out.printf("👉 Touch and hold on element for %dms%n", durationInMillis);
        Map<String, Object> params = createElementParam(ele);
        params.put("duration", durationInMillis);
        execute("touchAndHold", params);
    }

    /**
     * Scroll inside a scrollable element in a given direction.
     */
    public void iOSScrollAction(WebElement scrollableEle, String direction) {
        System.out.println("👉 iOS scroll in direction: " + direction);
        Map<String, Object> params = createElementParam(scrollableEle);
        params.put("direction", direction);
        execute("scroll", params);
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
        Map<String, Object> params = new HashMap<>();
        params.put("predicateString", "label == '" + visibleText + "'");
        execute("scroll", params);
    }

    /**
     * Perform a swipe gesture in a given direction.
     */
    public void swipe(String direction) {
        System.out.println("👉 Swiping in direction: " + direction);
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction);
        execute("swipe", params);
    }

    /**
     * Perform swipe from coordinates (startX, startY) to (endX, endY).
     */
    public void swipeByCoordinates(int startX, int startY, int endX, int endY, int duration) {
        System.out.printf("👉 Swiping from (%d,%d) to (%d,%d) for %dms%n", startX, startY, endX, endY, duration);
        Map<String, Object> params = Map.of(
                "startX", startX,
                "startY", startY,
                "endX", endX,
                "endY", endY,
                "duration", duration
        );
        execute("swipe", new HashMap<>(params));
    }

    /**
     * Perform a tap on a specific element.
     */
    public void tap(WebElement ele) {
        System.out.println("👉 Tapping on element");
        execute("tap", createElementParam(ele));
    }

    /**
     * Perform double-tap on a specific element.
     */
    public void doubleTap(WebElement ele) {
        System.out.println("👉 Double tapping on element");
        execute("doubleTap", createElementParam(ele));
    }

    /**
     * Perform pinch gesture (zoom in or out) on an element.
     */
    public void pinch(WebElement ele) {
        System.out.println("👉 Pinching on element");
        execute("pinch", createElementParam(ele));
    }

    /**
     * Perform drag and drop from one element to another.
     */
    public void dragAndDrop(WebElement fromElement, WebElement toElement) {
        System.out.println("👉 Drag and drop");
        Map<String, Object> params = createElementParam(fromElement);
        params.put("destinationElement", ((RemoteWebElement) toElement).getId());
        execute("dragFromToForDuration", params);
    }

    /**
     * Perform long press at a specific coordinate.
     */
    public void longPressAtCoordinates(int x, int y, int duration) {
        System.out.printf("👉 Long press at (%d, %d) for %dms%n", x, y, duration);
        Map<String, Object> params = Map.of(
                "x", x,
                "y", y,
                "duration", duration
        );
        execute("longPress", new HashMap<>(params));
    }

    /**
     * Perform tap at specific coordinates.
     */
    public void tapByCoordinates(int x, int y) {
        System.out.printf("👉 Tap at coordinates (%d, %d)%n", x, y);
        Map<String, Object> params = Map.of("x", x, "y", y);
        execute("tap", new HashMap<>(params));
    }

    /**
     * Perform double tap at specific coordinates.
     */
    public void doubleTapByCoordinates(int x, int y) {
        System.out.printf("👉 Double tap at coordinates (%d, %d)%n", x, y);
        Map<String, Object> params = Map.of("x", x, "y", y);
        execute("doubleTap", new HashMap<>(params));
    }
}
